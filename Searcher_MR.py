import json

with open("tfid.json",'r', encoding='utf-8') as data_file:    
    data = json.load(data_file)

with open("test_data.json",'r', encoding='utf-8') as data_file:    
    doc = json.load(data_file)

#print(len(doc))

def search(term):
    if term in data:
        docs = data[term]
        for i in range(len(doc)):
            for d in docs:
                if doc[i]['id'] == d:
                    print({ "score":"4.0587225","username":doc[i]["username"],"link":doc[i]["link"], "tweet":doc[i]["tweet"],"timestamp":doc[i]["tweet"],"location":doc[i]["geo"]})

search(str(input("Enter your Search Term: ")))
