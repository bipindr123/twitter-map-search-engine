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

import java.io.File;


public class Searcher {

    public static void main(String[] args) throws Exception {
        try {
            String indexDir = args[0];
            int numHits = Integer.parseInt(args[1]);

            Searcher tweetSearcher = new Searcher();
            tweetSearcher.termSearch(new File(indexDir), numHits);
            // tweetSearcher.wildcardQuery(new File(indexDir), numHits);
            
        }
        catch (Exception e) {
            System.out.println("Usage: java TweetSearcher <index directory>");
        }
    }

    
    private void termSearch(File indexDir, int numHits) throws Exception {
        System.out.println("Find tweets by user @scotthamilton:");

        Directory directory = FSDirectory.open(indexDir);
        DirectoryReader directoryReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(directoryReader);

        Term term = new Term(TWEET, "BDSM");

        Query query = new TermQuery(term);

        TopDocs topDocs = indexSearcher.search(query, numHits);

        printResults(topDocs.scoreDocs, indexSearcher);
    }

    private void wildcardQuery(File indexDir, int numHits) throws Exception {
        System.out.println("Find tweets that mention another user:");

        Directory directory = FSDirectory.open(indexDir);
        DirectoryReader directoryReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(directoryReader);

        Term term = new Term(TEXT, "*@*");
        Query query = new WildcardQuery(term);

        TopDocs topDocs = indexSearcher.search(query, numHits);

        printResults(topDocs.scoreDocs, indexSearcher);
    }

    

    
    private void printResults(ScoreDoc[] results, IndexSearcher indexSearcher) throws Exception {
        for (int i = 0; i < results.length; i++) {
            int docId = results[i].doc;
            Document foundDocument = indexSearcher.doc(docId);
            System.out.println(foundDocument.get(USER) + " : " + foundDocument.get(TWEET) + "," + 
            "Timestamp" +":"+ foundDocument.get(TIMESTAMP) + "," + 
            "location" +":"+ foundDocument.get(GEO) + "," +
            "Tags" +":"+ foundDocument.get(HASHTAGS) );
        }
        System.out.println("Found " + results.length + " results");
        
    }

}
