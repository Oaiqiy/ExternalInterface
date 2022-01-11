import time
import pymysql
import requests
from pymysql import Error


def epidemicData():
    startTime = time.time()
    timeArray = time.localtime(startTime)
    formatTime = time.strftime("%Y-%m-%d %H:%M:%S", timeArray)
    secs = (startTime - int(startTime)) * 1000
    formatTime = "%s.%03d" % (formatTime, secs)
    print(formatTime + ' : 开始同步疫情数据！')

    date = time.strftime("%Y-%m-%d", timeArray)
    try:
        cursor.execute("DELETE FROM epidemicdata where date = '%s'" % date)
        db.commit()
    except Error as err:
        print("OS error: {0}".format(err))
        db.rollback()

    url = "https://lab.isaaclin.cn/nCoV/api/overall"
    r = requests.get(url)
    jsonData = r.json()
    currentConfirmedCount = jsonData['results'][0]['currentConfirmedCount']
    currentConfirmedIncr = jsonData['results'][0]['currentConfirmedIncr']
    confirmedCount = jsonData['results'][0]['confirmedCount']
    confirmedIncr = jsonData['results'][0]['confirmedIncr']
    overseasCount = jsonData['results'][0]['suspectedCount']
    overseasIncr = jsonData['results'][0]['suspectedIncr']
    curedCount = jsonData['results'][0]['curedCount']
    curedIncr = jsonData['results'][0]['curedIncr']
    deadCount = jsonData['results'][0]['deadCount']
    deadIncr = jsonData['results'][0]['deadIncr']
    asymptomaticCount = jsonData['results'][0]['seriousCount']
    asymptomaticIncr = jsonData['results'][0]['seriousIncr']
    sql = "INSERT INTO epidemicdata VALUES ('%s',%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)" \
          % (date, currentConfirmedCount, currentConfirmedIncr, confirmedCount, confirmedIncr, overseasCount,
             overseasIncr, curedCount, curedIncr, deadCount, deadIncr, asymptomaticCount, asymptomaticIncr)
    try:
        cursor.execute(sql)
        db.commit()
    except Error:
        print("Epidemic Data Update Failed!")
        db.rollback()

    finishTime = time.time()
    timeArray = time.localtime(finishTime)
    formatTime = time.strftime("%Y-%m-%d %H:%M:%S", timeArray)
    secs = (finishTime - int(finishTime)) * 1000
    formatTime = "%s.%03d" % (formatTime, secs)
    print(formatTime + ' : 疫情数据同步完成！（耗时: ' + str(finishTime - startTime) + 's）')


def epidemicNews():
    startTime = time.time()
    timeArray = time.localtime(startTime)
    formatTime = time.strftime("%Y-%m-%d %H:%M:%S", timeArray)
    secs = (startTime - int(startTime)) * 1000
    formatTime = "%s.%03d" % (formatTime, secs)
    print(formatTime + ' : 开始同步疫情新闻！')

    url = "https://lab.isaaclin.cn/nCoV/api/news"
    r = requests.get(url)
    jsonData = r.json()
    for news in jsonData['results']:
        pubDate = news['pubDate']
        title = news['title']
        summary = news['summary']
        infoSource = news['infoSource']
        sourceUrl = news['sourceUrl']
        sql = "INSERT INTO epidemicnews VALUES (%s,'%s','%s','%s','%s')" \
              % (pubDate, title, summary, infoSource, sourceUrl)
        try:
            cursor.execute(sql)
            db.commit()
        except Error as err:
            print("OS error: {0}".format(err))
            db.rollback()

    finishTime = time.time()
    timeArray = time.localtime(finishTime)
    formatTime = time.strftime("%Y-%m-%d %H:%M:%S", timeArray)
    secs = (finishTime - int(finishTime)) * 1000
    formatTime = "%s.%03d" % (formatTime, secs)
    print(formatTime + ' : 疫情新闻同步完成！（耗时: ' + str(finishTime - startTime) + 's）')


def readAdcode():
    sql = "SELECT adcode FROM adcode"
    try:
        cursor.execute(sql)
        db.commit()
        results = cursor.fetchall()
    except Error as err:
        print("OS error: {0}".format(err))
    for _adcode in results:
        adcode.append(int(_adcode[0]))


def nucleicAcidDetectionPoint():
    startTime = time.time()
    timeArray = time.localtime(startTime)
    formatTime = time.strftime("%Y-%m-%d %H:%M:%S", timeArray)
    secs = (startTime - int(startTime)) * 1000
    formatTime = "%s.%03d" % (formatTime, secs)
    print(formatTime + ' : 开始同步核酸检测点信息！')

    try:
        cursor.execute("DELETE FROM nucleicaciddetectionpoint")
        db.commit()
    except Error as err:
        print("OS error: {0}".format(err))
        db.rollback()

    url = "https://restapi.amap.com/v3/place/text"
    parameters = {
        'key': '662701ec7ea0e5ef4df1b32d14dda476',
        'keywords': '核酸检测点',
        'types': '090000',
        'city': '',
        'children': '',
        'offset': 50,
        'page': 1,
        'extensions': 'all'
    }
    for _adcode in adcode:
        parameters['page'] = 1
        parameters['city'] = _adcode
        r = requests.get(url, params=parameters)
        jsonData = r.json()
        isFinish = False
        j = 0
        while not isFinish:
            for points in jsonData['pois']:
                name = points['name']
                try:
                    if points['address']:
                        address = points['pname'] + points['cityname'] + points['adname'] + points['address']
                    else:
                        address = points['pname'] + points['cityname'] + points['adname']
                    if points['tel']:
                        phone = points['tel']
                    else:
                        phone = '暂无'
                except KeyError as err:
                    address = points['pname'] + points['cityname'] + points['adname']
                    if points['tel']:
                        phone = points['tel']
                    else:
                        phone = '暂无'
                sql = "INSERT INTO nucleicaciddetectionpoint (adcode,name,address,phone) VALUES (%s,'%s','%s','%s')" \
                      % (_adcode, name, address, phone)
                try:
                    cursor.execute(sql)
                    db.commit()
                except Error as err:
                    print("OS error: {0}".format(err))
                    db.rollback()
                j += 1
            if j < 50:
                isFinish = True
            else:
                parameters['page'] += 1
                r = requests.get(url, params=parameters)
                jsonData = r.json()
                j = 0

    finishTime = time.time()
    timeArray = time.localtime(finishTime)
    formatTime = time.strftime("%Y-%m-%d %H:%M:%S", timeArray)
    secs = (finishTime - int(finishTime)) * 1000
    formatTime = "%s.%03d" % (formatTime, secs)
    print(formatTime + ' : 核酸检测点信息同步完成！（耗时: ' + str(finishTime - startTime) + 's）')


def vaccinationPoint():
    startTime = time.time()
    timeArray = time.localtime(startTime)
    formatTime = time.strftime("%Y-%m-%d %H:%M:%S", timeArray)
    secs = (startTime - int(startTime)) * 1000
    formatTime = "%s.%03d" % (formatTime, secs)
    print(formatTime + ' : 开始同步疫苗接种点信息！')

    try:
        cursor.execute("DELETE FROM vaccinationpoint")
        db.commit()
    except Error as err:
        print("OS error: {0}".format(err))
        db.rollback()

    url = "https://restapi.amap.com/v3/place/text"
    parameters = {
        'key': '662701ec7ea0e5ef4df1b32d14dda476',
        'keywords': '新冠疫苗接种点',
        'types': '090000',
        'city': '',
        'children': '',
        'offset': 50,
        'page': 1,
        'extensions': 'all'
    }
    for _adcode in adcode:
        parameters['page'] = 1
        parameters['city'] = _adcode
        r = requests.get(url, params=parameters)
        jsonData = r.json()
        isFinish = False
        j = 0
        while not isFinish:
            for points in jsonData['pois']:
                name = points['name']
                try:
                    if points['address']:
                        address = points['pname'] + points['cityname'] + points['adname'] + points['address']
                    else:
                        address = points['pname'] + points['cityname'] + points['adname']
                    if points['tel']:
                        phone = points['tel']
                    else:
                        phone = '暂无'
                except KeyError as err:
                    address = points['pname'] + points['cityname'] + points['adname']
                    if points['tel']:
                        phone = points['tel']
                    else:
                        phone = '暂无'
                sql = "INSERT INTO vaccinationpoint (adcode,name,address,phone)  VALUES (%s,'%s','%s','%s')" \
                      % (_adcode, name, address, phone)
                try:
                    cursor.execute(sql)
                    db.commit()
                except Error as err:
                    print("OS error: {0}".format(err))
                    db.rollback()
                j += 1
            if j < 50:
                isFinish = True
            else:
                parameters['page'] += 1
                r = requests.get(url, params=parameters)
                jsonData = r.json()
                j = 0

    finishTime = time.time()
    timeArray = time.localtime(finishTime)
    formatTime = time.strftime("%Y-%m-%d %H:%M:%S", timeArray)
    secs = (finishTime - int(finishTime)) * 1000
    formatTime = "%s.%03d" % (formatTime, secs)
    print(formatTime + ' : 疫苗接种点信息同步完成！（耗时: ' + str(finishTime - startTime) + 's）')


def travelPolicy():
    startTime = time.time()
    timeArray = time.localtime(startTime)
    formatTime = time.strftime("%Y-%m-%d %H:%M:%S", timeArray)
    secs = (startTime - int(startTime)) * 1000
    formatTime = "%s.%03d" % (formatTime, secs)
    print(formatTime + ' : 开始同步出行政策！')

    try:
        cursor.execute("DELETE FROM travelpolicy")
        db.commit()
    except Error as err:
        print("OS error: {0}".format(err))
        db.rollback()

    cityCode = []
    sql = "SELECT citycode FROM citycode"
    try:
        cursor.execute(sql)
        db.commit()
        results = cursor.fetchall()
    except Error as err:
        print("OS error: {0}".format(err))
    for _cityCode in results:
        cityCode.append(_cityCode[0])

    url = "https://r.inews.qq.com/api/trackmap/citypolicy"
    parameters = {
        'city_id': ''
    }
    for _cityCode in cityCode:
        parameters['city_id'] = _cityCode
        r = requests.get(url, params=parameters)
        jsonData = r.json()
        if jsonData['result']['data']:
            back_policy = jsonData['result']['data'][0]['back_policy']
            back_policy_date = jsonData['result']['data'][0]['back_policy_date']
            leave_policy = jsonData['result']['data'][0]['leave_policy']
            leave_policy_date = jsonData['result']['data'][0]['leave_policy_date']
            stay_info = jsonData['result']['data'][0]['stay_info']
            sql = "INSERT INTO travelpolicy VALUES (%s,'%s','%s','%s','%s','%s')" \
                  % (_cityCode, back_policy, back_policy_date, leave_policy, leave_policy_date, stay_info)
            try:
                cursor.execute(sql)
                db.commit()
            except Error as err:
                print("OS error: {0}".format(err))
                db.rollback()

    finishTime = time.time()
    timeArray = time.localtime(finishTime)
    formatTime = time.strftime("%Y-%m-%d %H:%M:%S", timeArray)
    secs = (finishTime - int(finishTime)) * 1000
    formatTime = "%s.%03d" % (formatTime, secs)
    print(formatTime + ' : 出行政策同步完成！（耗时: ' + str(finishTime - startTime) + 's）')




if __name__ == '__main__':
    adcode = []
    db = pymysql.connect(host='rm-2ze4i1r57b5lvvhv61o.mysql.rds.aliyuncs.com',
                         user='ooad',
                         password='2022Ooad',
                         database='ooad')
    cursor = db.cursor()
    epidemicData()
    time.sleep(1)
    epidemicNews()
    time.sleep(1)
    readAdcode()
    nucleicAcidDetectionPoint()
    time.sleep(1)
    vaccinationPoint()
    time.sleep(1)
    travelPolicy()
    time.sleep(1)
    print('所有信息同步完成！')
    db.close()
