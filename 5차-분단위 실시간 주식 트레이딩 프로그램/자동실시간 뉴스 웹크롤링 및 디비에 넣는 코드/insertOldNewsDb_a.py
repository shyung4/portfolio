#!/usr/bin/env python
# coding: utf-8

# In[1]:


# !pip install konlpy
# !pip3 install scikit-learn
# !pip install nltk


# In[10]:


import pandas as pd
import numpy as np
import glob
from datetime import date
import re

import os
import pymysql
import warnings
warnings.filterwarnings("ignore")

stock = 'shinFood'
# stock = 'stackEMart' #csv 과거 뉴스데이터가 모여있는 폴더, 종목
# 주의: 현수서버의 table명이랑 같아야 db에 들어감, 아래는 db table명임 (stack은 '쌓인'이란 뜻입니다-과거뉴스인거에요)
# stackShinFood는 신세계푸드, 
# stackShin은 신세계, 
# stackEMart는 이마트, 
# stackShinCon은 신세계건설, 
# stackShinIC는 신세계아이앤씨
# -2023.07.06 현재까지 만든 db table-

path = '/home/shyung4/dl-dev/newscrawl/input/{}/'.format(stock) # 본인경로


# In[11]:


# db에 넣는 함수 정의
def insert_db(stock,shin):
    conn = pymysql.connect(host='34.64.197.181', user='SUJIN', password='SUJIN',
                           db='shin_db', charset='utf8')
    curs = conn.cursor()
    sql = '''INSERT IGNORE INTO {} VALUES (%s, %s, %s)'''.format(
            stock)
    
    for i in range(shin.shape[0]):
        val = (str(shin['date'][i]), str(shin['title'][i]), str(shin['content'][i]))
        curs.execute(sql, val)
    conn.commit()
    conn.close()


# In[12]:


# stock에 종목명
def merge_news(path, stock):
    # 해당경로의 종목 .csv가져와서 리스트에 담기
    file_list = os.listdir(path)
    file_list_py = [file for file in file_list if file.endswith('.csv')]
    
    # 데이터 프레임 담을 리스트 만들기
    li=[]
    # 신세계 푸드 데이터 프레임화 후 리스트에 담기
    # date 타입 변경
    for lt in file_list_py:
        df=pd.read_csv(path+lt,index_col=0,header=0,sep=',')
        df.rename(columns={'text':'content'},inplace=True)
        df['date'] = pd.to_datetime(df['date'])
        df['date']=df['date'].dt.strftime('%Y.%m.%d %H:%M')
        df['date']=df['date'].astype('datetime64[ns]')
        df.sort_values('date', ascending=False, inplace=True)
        li.append(df)
    # 데이터 프레임 합치기
    frame = pd.concat(li, axis=0, ignore_index=True)

    #필요 없는 컬럼 삭제
    if 'link' in frame.columns and 'press' in frame.columns:
        frame.drop(['link', 'press'], axis=1, inplace=True)

    #컬럼 순서 변경
    shin=frame[['date','title','content']]
    
    #####제목, 본문에 필요없는 특수문자 지우기 ==> 마침표가 사라져서 주석처리함(마침표가 있어야 tokenizer를 할수 있음)
#     pt=re.compile(r'[^\w\s\d]+')
#     cs_t=shin['title']

#     for cs_ct in range(0,shin.shape[0]):
#         AAA=str(cs_t[cs_ct]).replace('\n','')
#         shin['title'][cs_ct] = AAA.replace('\t','')
#         pat_match=pt.sub('',shin['title'][cs_ct])
#         shin['title'][cs_ct]=pat_match
        
#     cs_t=shin['content']
#     for cs_ct in range(0,shin.shape[0]):
#         AAA=str(cs_t[cs_ct]).replace('\n','')
#         shin['content'][cs_ct] = AAA.replace('\t','')
#         pat_match=pt.sub('',shin['content'][cs_ct])
#         shin['content'][cs_ct]=pat_match
    
    
    ##### 제목, 본문에 한글깨진 뉴스 걸러내기
    hanFilter = re.compile(r'[^ -=+_,#/\?:^$.@*\"※~&%ㆍ!』\\"\"‘|\(\)\[\]\<\>`\'\’…》·|A-Za-z|0-9|가-힣|一-龥+]')
    shin_t=shin['title']
    temp_ind = []
    for shin_ct in range(0,shin.shape[0]):
        exists = re.findall(hanFilter,str(shin_t[shin_ct][3:6]))
        if len(exists)!=0:
            temp_ind.append(shin_ct)
            
    shin_c=shin['content']
    for shin_ct in range(0,shin.shape[0]):
        print(shin['date'][shin_ct])
        print(shin['title'][shin_ct])
        exists = re.findall(hanFilter,shin_c[shin_ct][10:13])
        if len(exists)!=0:
            temp_ind.append(shin_ct)
    
    temp_ind = list(set(temp_ind))
    shin.drop(temp_ind,inplace=True)
    shin.reset_index(drop=True,inplace=True)
    
    
    ##### 문장길이가 512단어 이상인 애들 삭제
    temp_ind = []
    for i in range(shin.shape[0]):
        sentenceList = shin['content'][i].split('.')
        for j in range(len(sentenceList)):
            if len(sentenceList[j])>512:
                temp_ind.append(i)
#                 sentenceList[j].replace("다","다.")
#             SSS = SSS+sentenceList[j]
#         shin['content'][i] = SSS
    temp_ind = list(set(temp_ind))
    print(temp_ind)
    shin.drop(temp_ind,inplace=True)
    shin.reset_index(drop=True,inplace=True)
    

    ##### db에 넣기
    insert_db(stock,shin)
    
    return shin


# In[13]:


df = merge_news(path, stock)


# In[5]:


df


# In[41]:


# df.to_csv('./emart1.csv',encoding='utf-8-sig')


# In[35]:


# hangul=re.compile('/[ㄱ-ㅎ가-힣|a-zA-Z|0-9|]/')
# hanFilter = re.compile(r'[^ -=+,#/\?:^$.@*\"※~&%ㆍ!』\\‘|\(\)\[\]\<\>`\'…》A-Za-z0-9가-힣一-龥+]')
hanFilter = re.compile(r'[^ -=+_,#/\?:^$.@*\"※~&%ㆍ!』\\"\"‘|\(\)\[\]\<\>`\'\’…》·|A-Za-z|0-9|가-힣|一-龥+]')
text = "아아아아"
aa=re.findall(hanFilter,text)
len(aa)


# In[ ]:




