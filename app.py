#!/bin/sh
from flask import Flask, redirect, url_for, request
import subprocess
from flask_cors import CORS

app = Flask(__name__)
CORS(app)
@app.route("/", methods=["GET"])
def hello_world():
    sTerm = request.args.get("searchTerm")
    sType = request.args.get("searchType")
    
    
    #sTerm = sTerm.decode('utf-8')
    print(sTerm, sType)
    cmd = ['sh','Searcher.sh', sTerm]
    result = subprocess.run(cmd, stdout=subprocess.PIPE)
    res =result.stdout.decode('utf-8')
    
    return res
if __name__ == "__main__":
	app.run(host="localhost",port=5001)
