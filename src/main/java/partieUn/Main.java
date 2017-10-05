package partieUn;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;



public class Main {

    private final static int START_INDEX = 1;
    private final static int END_INDEX = 1975;
    private final static String URL = "http://www.dxcontent.com/SDB_SpellBlock.asp?SDBID=";

    public static void main(String[] args) {


        // 1. Connect to MongoDB instance running on localhost
        MongoClient mongoClient = new MongoClient();

        // Access database named 'test'
        MongoDatabase database = mongoClient.getDatabase("test");

        // Access collection named 'restaurants'
        MongoCollection<Document> collection = database.getCollection("sorts");

        Parser p = null;
        try {
            p = new Parser(new URL(URL));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        for (int i = START_INDEX; i <= END_INDEX; i++) {
            System.out.println("index courant : " + i);
            try {
                RawEntry r = p.next(i);
                collection.insertOne(r.getDoc());
            } catch (Exception e) {//page inexistante}
            }

            System.out.println("Fini");

            mongoClient.close();
        }
    }


}
