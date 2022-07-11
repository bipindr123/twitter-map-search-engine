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
import java.util.Scanner;



public class Searcher {
   public static void main(String[] args) {
      String searchTerm = "def";
	try {
            searchTerm = args[0];

           }
        catch (Exception e) {
            System.out.println("Usage: java Searcher <search term>");
        }
    

	JSONParser parser = new JSONParser();
      try {
         JSONArray a = (JSONArray) parser.parse(new FileReader("data_tweets.json"));
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
        Query query = qparser.parse(searchTerm);
        
        //System.out.println(query.toString());
        int topHitCount = 100;
        ScoreDoc[] hits = indexSearcher.search(query, topHitCount).scoreDocs;
        //System.out.println(hits.length);
        System.out.println("[");
	for (int rank = 0; rank < hits.length-1; ++rank) {
                Document hitDoc = indexSearcher.doc(hits[rank].doc);
           // System.out.println((rank + 1) + " (score:" + hits[rank].score + ")--> " + hitDoc.get("username") + " : " + hitDoc.get("tweet") + "," + 
           // "Timestamp" +":"+ hitDoc.get("created_at") + "," + 
           // "location" +":"+ hitDoc.get("geo") + "," +
           // "Tags" +":"+ hitDoc.get("hashtags"));		
	System.out.println("{ \"score\":\""+hits[rank].score +"\",\"username\":\""+hitDoc.get("username")+"\",\"link\":\""+hitDoc.get("link")+"\", \"tweet\":\""+hitDoc.get("tweet")+"\",\"timestamp\":\""+hitDoc.get("created_at")+"\",\"location\":\""+hitDoc.get("geo")+"\"}," );
	}       
	int rank = hits.length -1;
	Document hitDoc = indexSearcher.doc(hits[rank].doc);

	System.out.println("{ \"score\":\""+hits[rank].score +"\",\"username\":\""+hitDoc.get("username")+"\",\"link\":\""+hitDoc.get("link")+"\" ,\"tweet\":\""+hitDoc.get("tweet")+"\",\"timestamp\":\""+hitDoc.get("created_at")+"\",\"location\":\""+hitDoc.get("geo")+"\"}" );
	System.out.println("]");
	
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

