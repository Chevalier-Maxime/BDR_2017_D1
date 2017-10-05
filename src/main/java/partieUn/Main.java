package partieUn;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.model.Indexes;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Filters.eq;
import static java.util.Arrays.asList;



public class Main {

    private final static int START_INDEX = 1;
    private final static int END_INDEX = 1975;
    private final static String URL = "http://www.dxcontent.com/SDB_SpellBlock.asp?SDBID=";

    public static void main(String[] args) {
        
    	
    	
    	// 1. Connect to MongoDB instance running on localhost
    	MongoClient mongoClient = new MongoClient();

    	/*// Access database named 'test'
    	MongoDatabase database = mongoClient.getDatabase("test");

    	// Access collection named 'restaurants'
    	MongoCollection<Document> collection = database.getCollection("sorts");*/

    	DB db =  mongoClient.getDB("test");
        DBCollection collection =db.getCollection("sorts");

    	
    	try {
            Parser p = new Parser(new URL(URL));
            RawEntry r = p.next(1);
            collection.insert(new BasicDBObject(r.getDocumentMap()));

            System.out.println("Fini");
        } catch (IOException e) {
            e.printStackTrace();
        }

        mongoClient.close();
    }


}
