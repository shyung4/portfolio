#!/usr/bin/env python
# coding: utf-8

# In[9]:


from sqlalchemy import create_engine
import pymysql
import pandas as pd
from datetime import datetime 
import os
import threading
import shutil
from datetime import timedelta
import time
pd.options.mode.chained_assignment = None

# from sklearn.preprocessing import RobustScaler
from sklearn.preprocessing import StandardScaler


# In[10]:


codelist = ['004170','031440','139480'] # 신세계, 신세계푸드, 이마트
timenow = datetime.now().strftime('%Y-%m-%d %H:%M')
# code = '004170'


# In[11]:


def get_table(code):
    
    # scaler = RobustScaler()
    scaler = StandardScaler()
    timenow = (datetime.now()-timedelta(minutes=0)).strftime('%Y-%m-%d')
    db_connection_str = 'mysql+pymysql://SUJIN:SUJIN@34.64.197.181:3306/shin_db'
    db_connection = create_engine(db_connection_str)

    shin_trainData = pd.read_sql("SELECT * FROM New_{} WHERE Datetime LIKE ('{}%%') ORDER BY Datetime DESC LIMIT 6".format(code,timenow), con=db_connection)


    for col in shin_trainData.columns[6:len(shin_trainData.columns)-2]:
        scaler.fit(shin_trainData[[col]])
        shin_trainData[col] = scaler.transform(shin_trainData[[col]])

    if shin_trainData.shape[0] != 0:
        print("there is new data!")
        for i in range(shin_trainData.shape[0]):
                shin_trainData['Datetime'][i]=shin_trainData['Datetime'][i][:-3]

        shin_trainData['Datetime'] = pd.to_datetime(shin_trainData['Datetime'], format = '%Y-%m-%d %H:%M')
        shin_trainData['Datetime'] = shin_trainData['Datetime'].dt.strftime('%Y%m%d%H%M')

        shin_trainData.rename(columns={'Datetime':'date'},inplace=True)

#         shin_trainData = shin_trainData[-1]
        
        if not os.path.isfile('/home/shyung4/dl-dev/rl_dev/rltrader-master/data/v3/{}_신세계.csv'.format(code)):
            shin_trainData.iloc[0:1].to_csv('/home/shyung4/dl-dev/rl_dev/rltrader-master/data/v3/{}_신세계.csv'.format(code))
            print('making new file..{}'.format(code))
        else:
            shin_trainData.iloc[0:1].to_csv('/home/shyung4/dl-dev/rl_dev/rltrader-master/data/v3/{}_신세계.csv'.format(code), index=True, mode='a', header=False)
            print('appending..{}'.format(code))


# In[12]:


while True:
    now_time = datetime.now().strftime('%S')
    if now_time=='15':
        threads=[]
        for code in codelist:
            t = threading.Thread(target=get_table(code))
            t.start()
            threads.append(t)

        for thread in threads:
            thread.join()

        now_time = datetime.now().strftime('%H%M')
        time.sleep(60)

        if int(now_time) > 1520: #15시21분이면 종료

            pathname = './stack_csv/' + datetime.now().strftime('%Y%m%d%H%M')
            print(pathname)
            isExist = os.path.exists(pathname)
            if not isExist:
                os.makedirs(pathname)
                for code in codelist:
                    shutil.move('/home/shyung4/dl-dev/rl_dev/rltrader-master/data/v3/{}_신세계.csv'.format(code), pathname + '/')
                    print('moving file to {}'.format(pathname))
                    print('종료시각: '+ datetime.now().strftime('%Y%m%d %H:%M'))
            break


# In[ ]:




