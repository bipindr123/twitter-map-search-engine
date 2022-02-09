import twint
import json

c = twint.Config()
with open('list_cities.json',"r") as f:
    i = json.load(f)

for idx,j in enumerate(i.values()):
    geo = str(j[0])+","+str(j[1])+", 100km"
    print(idx, geo)
    c.Geo = geo
    c.Since = '2010-01-01'
    c.Limit = 5000
    c.Hide_output = True
    c.Store_json =True
    c.Lang = 'en'
    c.Output = "data_tweets.json"
    c.Custom["tweet"] = ["id", "created_at", "user_id", "username", "name", "place", "link", "urls", "hashtags", "geo"]
    twint.run.Search(c)