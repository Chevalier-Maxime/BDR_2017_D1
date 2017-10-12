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
    public final static double DUMPING_FACTOR = 0.85;
    public final static int NB_ITERATION = 20;
    public static void main(String[] args) {

        // 1. Connect to MongoDB instance running on localhost
        MongoClient mongoClient = new MongoClient();

        // Access database named 'test'
        MongoDatabase database = mongoClient.getDatabase("part2");
        // Access collection named 'restaurants'
        MongoCollection<Document> collection = database.getCollection("initial");
        collection.drop();
        database.getCollection("res").drop();
        Item item = new Item("A",1, new String[]{"B","C"});
        collection.insertOne(item.getDoc());
        item.itemChange("B",1, new String[]{"C"});
        collection.insertOne(item.getDoc());
        item.itemChange("C",1, new String[]{"A"});
        collection.insertOne(item.getDoc());
        item.itemChange("D",1, new String[]{"C"});
        collection.insertOne(item.getDoc());

        String map="function() {"+
                //"var objet = {type:\"full\", rank:this.rank, adjList:this.adjList};"+
                //"print(tojson(this));"+
                "emit(this._id, this.value);"+
                "var adjlist = this.value.adjList;"+
                "var currentRank = this.value.rank;"+
                "var beSure =  {type:\"null\", rank:0};"+
                "emit(this._id,beSure);" + //afin d'etre sur que le reduce sera appelé
                "for(var i = 0; i < adjlist.length; i++) {"+
                    "var adj = adjlist[i];"+
                    "var yourRank = currentRank/adjlist.length;"+
                    "var objet =  {type:\"compact\", rank:yourRank};"+
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
               // "print(\"Qui suis-je ?\",tojson(full));"+
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
                "print(computeRank);"+
                "full.rank = computeRank;" +
                //"print(\"Qui serais-je ?\",tojson(full));"+
                "return full;" +
        "}";
        collection.mapReduce(map, reduce).collectionName("res").first(); //le first c'est pour obliger MongoDB à faire le mapReduce (lazy)
        MongoCollection<Document> res = database.getCollection("res");
        for(int i = 1; i < NB_ITERATION ; i++ ){
            System.out.println(i);
            res.mapReduce(map, reduce).action(MapReduceAction.REPLACE).collectionName("res").first();
        }
        System.out.println("FIN : ");
        for(Document d:res.find()){
            System.out.println(d);
        }
        mongoClient.close();
    }
}
