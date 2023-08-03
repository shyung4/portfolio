#!/usr/bin/env python
# coding: utf-8

# In[49]:


# !pip install schedule
# !pip install sqlalchemy
# !pip install mariadb


# In[50]:


import pymysql
pymysql.install_as_MySQLdb()


# In[51]:


from bs4 import BeautifulSoup
import requests
import re
import pandas as pd
import os 

from datetime import date
from datetime import datetime
import schedule
import time

import threading
from sqlalchemy import create_engine
import sqlalchemy

output_path = '/home/shyung4/dl-dev/newscrawl/output/stream'
company_file = '/home/shyung4/dl-dev/newscrawl/input/company_list.txt'

isExist = os.path.exists(output_path)
if not isExist:
    os.makedirs(output_path)
    print("The new directory is created!")



# In[52]:


# 뉴스 본문 크롤링하는 function 정의
def crawl_content(link_result):

        url = link_result

        html = requests.get(url).content
        html = BeautifulSoup(html,'html.parser')
        
#         req = requests.get('http://<<웹사이트>>')
#         // utf-8로 바꾸어 주기
#         html = req.content.decode('utf-8','replace') 
#         soup = BeautifulSoup(html, 'html.parser')

        

        news_contents=[]
        only_content = html.find('div', 'scr01')
        only_content = only_content.get_text()

        if isinstance(only_content, str):
                only_index = only_content.find('해당 언론사에서 선정하며 언론사 페이지(아웃링크)로 이동해 볼 수 있습니다.')
                only_content = only_content[:only_index]
                only_content = re.sub('\n', ' ',only_content)
                news_contents.append(only_content)
        return news_contents




# In[68]:


# 한글깨진거, 걸러내는 function
def kor_filter(title, content):
    
    hanFilter = re.compile(r'[^ -=+_,#/\?:^$.@*\"※~&%ㆍ!』\\"\"‘|\(\)\[\]\<\>`\'\’…》·|A-Za-z|0-9|가-힣|一-龥+]')
    
    tit_exists = re.findall(hanFilter,str(title[3:6]))
    con_exists = re.findall(hanFilter,str(content[10:13]))
    
    if (len(tit_exists)==0) & (len(con_exists)==0 ):
        sentenceList = str(content).split('.')
        li=0
        for i, sen in enumerate(sentenceList):
            if len(sen)<512:
#                 print('텅과')
                li=li+1
        if li==len(sentenceList):
            return 1

    


# In[69]:


# 본문 및 제목, 링크, 언론사, 날짜 크롤링하는 function 

def crawler(company, company_code):
    
    
    # page = 1 
    today = date.today()
    today = today.strftime("%Y.%m.%d")

    url_front = 'https://finance.naver.com/item/news_news.nhn?code=' + str(company_code) + '&page=1' 
    url_last = "&sm=title_entity_id.basic&clusterId="
    url = url_front + url_last

#     html = requests.get(url).content
    html = requests.get(url).content
    html = BeautifulSoup(html,'html.parser')
    
    # 뉴스 제목 
    titles = html.select('.title')
    title_result=[]
    for title in titles: 
        title = title.get_text() 
        title_result.append(title)


    # 뉴스 링크
    links = html.select('.title') 

    link_result =[]
    for link in links: 
        add = 'https://finance.naver.com' + link.find('a')['href']
        link_result.append(add)


    # 뉴스 날짜 
    dates = html.select('.date') 
    date_result = [date.get_text() for date in dates] 

    # 뉴스 매체     
    sources = html.select('.info')
    source_result = [source.get_text() for source in sources] 

    # 뉴스 본문
    content_result = []
    for i, link in enumerate(link_result):
        content = crawl_content(link) # 위의 셀에서 정의했음
        
        content_result.append(content)


    # 제일 최신 뉴스 제목만 csv로 따로 저장
    
    csv_name = '/' + str(company)+ '_titles.csv'
    csv_full = output_path +csv_name
    
    if os.path.isfile(csv_full):
        df_title = pd.read_csv(csv_full)
        recent_title = df_title['titles'][0]
    else:
        recent_title = 'Void'

    newnum=0
    # 변수들 합쳐서 해당 디렉토리에 csv파일로 저장하기 
    
    for i in range(len(title_result)):
        if title_result[i]!=recent_title:
            result= {"NewsDate" : date_result[i], "NewsTitle" : title_result[i], "NewsContent" :content_result[i]} 
            df_result = pd.DataFrame(result)
            
#             print(title_result[i])
            AAA=kor_filter(title_result[i], content_result[i])
#             print(AAA)
            if AAA ==1:
            
                print(str(company)+'에서 새로운 뉴스를 받았습니다:')
                print('업데이트 뉴스: '+title_result[i]+'\n')

                ###### csv파일로 만들기
                df_result.to_csv(output_path+'/'+str(company)+ date_result[i] + 'news.csv', mode='w', encoding='utf-8-sig') 
                temp_df = pd.DataFrame(columns=['titles'])
                temp_df['titles'] = title_result
                temp_df.to_csv(csv_full)
                newnum=newnum+1

                ###### 바로 db에 넣기
                conn = pymysql.connect(host='34.64.197.181', user='SUJIN', password='SUJIN',
                                       db='shin_db', charset='utf8')
                curs = conn.cursor()

                ##############


                if str(company) == '신세계푸드':
                    sql = "INSERT IGNORE INTO shinFood VALUES (%s, %s, %s)"
                    val = (str(date_result[i]), str(title_result[i]), str(content_result[i]))
                    curs.execute(sql, val)
                    conn.commit()
                    print("뉴스가 추가되었습니다")
                    conn.close()


                elif str(company) == '신세계':
                    sql = "INSERT IGNORE INTO shin VALUES (%s, %s, %s)"
                    val = (str(date_result[i]), str(title_result[i]), str(content_result[i]))
                    curs.execute(sql, val)
                    conn.commit()
                    print("뉴스가 추가되었습니다")
                    conn.close()

                elif str(company) == '이마트':
                    sql = "INSERT IGNORE INTO eMart VALUES (%s, %s, %s)"
                    val = (str(date_result[i]), str(title_result[i]), str(content_result[i]))
                    curs.execute(sql, val)
                    conn.commit()
                    print("뉴스가 추가되었습니다")
                    conn.close()
                #############




        


# In[70]:


# 종목 리스트 파일 열기  
# 회사명을 종목코드로 변환 
        
def convert_to_code(company):
    
    data = pd.read_csv(company_file, dtype=str, sep='\t')   # 종목코드 추출 
    company_name = data['회사명']
    keys = [i for i in company_name]    #데이터프레임에서 리스트로 바꾸기 
 
    company_code = data['종목코드']
    values = [j for j in company_code]
 
    dict_result = dict(zip(keys, values))  # 딕셔너리 형태로 회사이름과 종목코드 묶기 
    
    pattern = '[a-zA-Z가-힣]+' 
    
    if bool(re.match(pattern, company)) == True:         # Input에 이름으로 넣었을 때  
        company_code = dict_result.get(str(company))
        crawler(company, company_code)       

    
    else:                                                # Input에 종목코드로 넣었을 때       
        company_code = str(company) 
        crawler(company, company_code)

 
def main(shcode):
        
    company = shcode 
    convert_to_code(company)
    
 
 


# In[71]:


def multithread(): 
        threads=[]

#         shcode_list = ['004170','031430','031440','034300','035510'] # '신세계', '신세계인터내셔날', '신세계푸드', '신세계건설', '신세계I&C'
        shcode_list = ['신세계푸드', '신세계', '이마트'] 
#         shcode_list = ['삼성전자']
        for shcode in shcode_list:
                t = threading.Thread(target=main(shcode))
                t.start()
                threads.append(t)

        for thread in threads:
                thread.join()


# In[72]:


now = datetime.now()
now = now.strftime("%Y/%m/%d %H:%M:%S")
print("현재시각:", now)
multithread()


# In[ ]:


# # i = 0



# while True:
#     now = datetime.now()
#     now = now.strftime("%Y/%m/%d %H:%M:%S")
#     print("현재시각:", now)

#     if datetime.now().strftime('%H') == '16':
#         break

#     multithread()

# #     time.sleep(150)
    


# In[ ]:


# # i = 0
# while True:
#     print("dkdk")
#     time.sleep(1)



#     # 15시30분 수집 종료
#     if datetime.now().strftime('%H:%M') == '16:56':
#         # print(i)
#         break


# In[ ]:


# # 제일 최신 뉴스 제목만 csv로 따로 저장
# csv_path = r'C:\Users\hyung\Desktop\big16\dl-dev\temp\output'
# csv_name =  r'\신세계_titles.csv'
# csv_full = csv_path +csv_name

# print(csv_full)

# os.path.isfile(csv_full)

