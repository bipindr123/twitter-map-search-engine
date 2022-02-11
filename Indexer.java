import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
 
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class Indexer {

    // protected static final String COMMA = "\",\"";
    protected static final String ID = "id";
    protected static final String TIMESTAMP = "created_at";
    protected static final String GEO = "geo";
    protected static final String USER = "username";
    protected static final String TWEET = "tweet";
    protected static final String HASHTAGS = "hashtags";

    public static void main(String[] args) throws Exception {
        try {
            String indexDir = args[0];
            String dataFile = args[1];

            Indexer tweetIndexer = new Indexer();

            int count = tweetIndexer.index(new File(indexDir), new File(dataFile));

        }
        catch (Exception e) {
            System.out.println("Usage: java Indexer <index directory> <json data file>");
        }
    }

    private int index(File indexDir, File dataFile) throws Exception {
        // IndexWriter indexWriterKey = new IndexWriter(
        //         FSDirectory.open(indexDir),
        //         new IndexWriterConfig(new KeywordAnalyzer()));
        
        IndexWriter indexWriter = new IndexWriter(
            FSDirectory.open(indexDir.toPath()),
            new IndexWriterConfig(new StandardAnalyzer()));
        
        int count = indexFile(indexWriter, dataFile);

        indexWriter.close();

        return count;
    }

    private int indexFile(IndexWriter indexWriter, File dataFile) throws IOException {
       // FieldType fieldType = new FieldType();
       //fieldType.setStored(true);
       // fieldType.setIndexed(true);

        int count = 0;
        JSONParser parser = new JSONParser();
        try
	{
		JSONArray a = (JSONArray) parser.parse(new FileReader(dataFile));

	        for (Object o : a)
        	{
            		JSONObject tweetInfo = (JSONObject) o; 
    

            		Document document = new Document();

            // document.add(new Field(ID, tweetInfo.get(ID), fieldType));
        //    document.add(new Field(TIMESTAMP, tweetInfo.get(TIMESTAMP).toString(), fieldType));
            		document.add(new StringField(USER, tweetInfo.get(USER).toString(), Field.Store.YES));
            		System.out.println( tweetInfo.get(USER).toString());
			document.add(new TextField(TWEET, tweetInfo.get(TWEET).toString(), Field.Store.YES));
            		document.add(new StringField(GEO, tweetInfo.get(GEO).toString(), Field.Store.YES));
            		document.add(new StringField(HASHTAGS, tweetInfo.get(HASHTAGS).toString(), Field.Store.YES));

            		indexWriter.addDocument(document);
            		count++;
        	}
	}
	catch(ParseException e)
	{
		e.printStackTrace();
	}

        return count;
    }
}
