from time import sleep
import twint
import json
import aiohttp

c = twint.Config()
with open('list_cities.json',"r") as f:
    i = json.load(f)

def search_tweets(c):
    try:
        twint.run.Search(c)
    except aiohttp.client_exceptions.ClientOSError:
        sleep(60)
        print("client os exception on "+c.geo)
        search_tweets(c)

for idx,j in enumerate(i.values()):
    if idx < 632:
        continue
    geo = str(j[0])+","+str(j[1])+", 100km"
    print(idx, geo)
    c.Geo = geo
    c.Since = '2010-01-01'
    c.Limit = 5000
    c.Hide_output = True
    c.Store_json =True
    c.Lang = 'en'
    c.Output = "data_tweets2.json"
    c.Custom["tweet"] = ["id", "created_at", "user_id", "username", "name", "place", "link", "urls", "hashtags", "geo"]
    try:
        search_tweets(c)
    except:
        continue
