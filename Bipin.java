import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import java.nio.file.*;
public class Bipin {
   public static void main(String[] args) {
      JSONParser parser = new JSONParser();
      try {
         JSONArray a = (JSONArray) parser.parse(new FileReader("data_tweets2.json"));
         for (Object o:a)
         {
            JSONObject tweetinfo = (JSONObject) o;
            String name = (String)tweetinfo.get("username");
            //System.out.println("Name: " + name);
         }
      } catch(Exception e) {
         e.printStackTrace();
      }
	
	Analyzer analyzer = new StandardAnalyzer();
	try {
	Directory directory = FSDirectory.open(Paths.get("/opt/home/cs242-w22/ir-project/Bhai"));
        DirectoryReader indexReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        QueryParser qparser = new QueryParser("tweet", analyzer);
        Query query = qparser.parse("sex");
        
        //System.out.println(query.toString());
        int topHitCount = 100;
        ScoreDoc[] hits = indexSearcher.search(query, topHitCount).scoreDocs;
        System.out.println(hits.length);
        for (int rank = 0; rank < hits.length; ++rank) {
                Document hitDoc = indexSearcher.doc(hits[rank].doc);
            System.out.println((rank + 1) + " (score:" + hits[rank].score + ")--> " + hitDoc.get("tweet"));
        }
	indexReader.close();
        directory.close();    }
	
	catch(IOException e){
		e.printStackTrace();
		}

	catch(Exception e){
		e.printStackTrace();
	}	
   }
}

