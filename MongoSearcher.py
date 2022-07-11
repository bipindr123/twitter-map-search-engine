import json
from textwrap import indent
from pymongo import MongoClient 
  
  
# Making Connection
myclient = MongoClient("mongodb://localhost:27017/") 
   
# database 
db = myclient["GFG"]
   
# Created or Switched to collection 
# names: GeeksForGeeks
Collection = db["data"]
  
# Loading or Opening the json file
with open('test_data.json') as file:
    file_data = json.load(file)
      
# Inserting the loaded data in the Collection
# if JSON contains data more than one entry
# insert_many is used else inser_one is used
if isinstance(file_data, list):
    Collection.insert_many(file_data)  
else:
    Collection.insert_one(file_data)

import pprint

# for doc in Collection.find():
#     pprint.pprint(doc)

from bson.json_util import dumps, loads

sample = Collection.find()

list_cur = list(sample)

json_data=dumps(list_cur,indent=2)

with open('sample_mongo_output.json', 'w') as file:
    file.write(json_data)



