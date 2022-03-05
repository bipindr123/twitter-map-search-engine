from time import sleep
from typing import final
import twint
import json
import aiohttp
import pandas

c = twint.Config()
with open('list_cities.json',"r") as f:
    i = json.load(f)
final_df = pandas.DataFrame()
def search_tweets(c):
    global final_df
    try:
        twint.run.Search(c)
        # df = twint.storage.panda.Tweets_df
        # final_df = final_df.append(df)
        

    except aiohttp.client_exceptions.ClientOSError:
        sleep(60)
        print("client os exception on "+c.geo)
        search_tweets(c)

    except Exception as e:
        print(e)

for idx,j in enumerate(i.values()):
    tweet = {}
    geo = str(j[0])+","+str(j[1])+", 500km"
    print(idx, geo)
    # c.Custom["user"] = ["id", "username"]
    c.Custom["tweet"] = ["id", "created_at", "user_id", "username", "name", "tweet" , "place", "link", "urls", "hashtags", "geo", "language"]
    c.Geo = geo
    c.Since = '2010-01-01'
    c.Limit = 100
    c.Hide_output = True
    c.Lang = 'en'
    # c.Count = True
    # c.Store_object = True
    # c.Store_object_tweets_list = tweet
    c.Store_json = True
    c.Output = "data_tweets3.json"
    # c.Pandas = True
    try:
        search_tweets(c)
    except KeyboardInterrupt:
        exit(0)
    except Exception as e:
        print(e)
        continue
