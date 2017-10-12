package partie2;

import com.mongodb.MapReduceCommand;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MapReduceIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.MapReduceAction;
import org.bson.Document;

public class Main {
    public final static double DUMPING_FACTOR = 0.5;
    public final static int NB_ITERATION = 10;
    public static void main(String[] args) {

        // 1. Connect to MongoDB instance running on localhost
        MongoClient mongoClient = new MongoClient();

        // Access database named 'test'
        MongoDatabase database = mongoClient.getDatabase("part2");
        // Access collection named 'restaurants'
        MongoCollection<Document> collection = database.getCollection("initial");
        collection.drop();
        database.getCollection("res").drop();
        Item item = new Item("Ed",1, new String[]{"Frank","Julien"});
        collection.insertOne(item.getDoc());
        item.itemChange("Frank",1, new String[]{"Ed"});
        collection.insertOne(item.getDoc());
        item.itemChange("Julien",1, new String[]{"Ed","Florentin","Valere"});
        collection.insertOne(item.getDoc());
        item.itemChange("Valere",1, new String[]{"Julien","Florentin","Bruno"});
        collection.insertOne(item.getDoc());
        item.itemChange("Bruno",1, new String[]{"Valere"});
        collection.insertOne(item.getDoc());
        item.itemChange("Florentin",1, new String[]{"Julien","Valere"});
        collection.insertOne(item.getDoc());

        String map="function() {"+
                //"var objet = {type:\"full\", rank:this.rank, adjList:this.adjList};"+
                "emit(this._id, this.value);"+
                "var adjlist = this.value.adjList;"+
                "var currentRank = this.value.rank;"+
                "for(var i = 0; i < adjlist.length; i++) {"+
                    "var adj = adjlist[i];"+
                    "var yourRank = currentRank/adjlist.length;"+
                    "objet =  {type:\"compact\", rank:yourRank};"+
                    "emit(adj, objet);"+
                "}"+
         "}";

        String reduce="function(key, values) {"+
                //"print(\"1er Reduce : \", tojson(key), tojson(values));" +
                "var full = {};"+
               // "//First, find the original one" +
                "for (var i = 0; i < values.length; i++)" +
                "{" +
                    "var val = values[i];" +
                    "if (val.type == \"full\") {" +
                        "full = val;" +
                    "}" +
                "}"+
                //"//Then improve on it" +
                "var computeRank = 0;"+
                "for (var i = 0; i < values.length; i++)" +
                "{" +
                    "var val = values[i];" +
                    "if (val.type == \"compact\") {" +
                        "computeRank = computeRank + val.rank ;"+
                    "}" +
                "}"+
                "computeRank = (1-"+ DUMPING_FACTOR + ") + "+ DUMPING_FACTOR +" * computeRank;" +
                "full.rank = computeRank;" +
                "return full;" +
        "}";
        collection.mapReduce(map, reduce).collectionName("res").first(); //le first c'est pour obliger MongoDB à faire le mapReduce (lazy)
        MongoCollection<Document> res = database.getCollection("res");
        MapReduceIterable<Document> mapReduce = null;
        for(int i = 1; i < NB_ITERATION ; i++ ){
            System.out.println(i);
            mapReduce = res.mapReduce(map, reduce).action(MapReduceAction.REPLACE);
        }
        System.out.println("FIN : ");
        for(Document d:mapReduce){
            System.out.println(d);
        }
        mongoClient.close();
    }
}
