import json
import  sys
from tracemalloc import start 
from mrjob.job import MRJob
from mrjob.protocol import JSONProtocol
import nltk
from nltk.corpus import stopwords
import string
import time
from collections import Counter

nltk.download('stopwords')

class Appearance:
    """
    Represents the appearance of a term in a given document, along with the
    frequency of appearances in the same one.
    """
    def __init__(self, docId, frequency):
        self.docId = docId
        self.frequency = frequency
        
    def __repr__(self):
        """
        String representation of the Appearance object
        """
        return str(self.__dict__)


class Invertedindex(MRJob):
    def mapper(self, _, line):
        data = json.loads(line)
        k=data['id']
        v=data['tweet'].translate(str.maketrans("","",string.punctuation))
        stop_words = set(stopwords.words('english'))
        words = v.split()
                
        for w in words:
            w = w.lower()
            if w not in stop_words:
                yield(w,k)

    def reducer(self, key, values):
        yield(key, list(values))


if __name__ == '__main__':
    INPUT_PROTOCOL = JSONProtocol
    start = time.time()
    Invertedindex().run()
    end = time.time()
    print(end-start)


