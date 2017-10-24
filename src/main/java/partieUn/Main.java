package partieUn;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.MapReduceAction;
import org.bson.Document;
import org.sqlite.SQLiteException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;


public class Main {

    private final static int START_INDEX = 1;
    private final static int END_INDEX = 1975;
    private final static String URL = "http://www.dxcontent.com/SDB_SpellBlock.asp?SDBID=";

    static void getWithMongoDB()
    {
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


            //MongoCollection<Document> collection = database.getCollection("sorts");

            String map = "function() {" +
                    "var components = this.Components;" +
                    "var AvailableFor = this.AvailableFor;" +
                        "for( var i of components){" +
                            "if ( i == \"V\" &&  components.length==1){" +
                                "print(tojson(this));" +
                                "for( var j in AvailableFor){" +
                                    "if(j == \"wizard\" && AvailableFor[j] <= 4){" +
                                        "print(this);" +
                                        "emit(this['_id'],{SpellName :this['SpellName']});" +
                                    "}" +
                                "}" +
                            "}" +
                        "}" +
                    "}";
            String reduce = "function(key, values){" +
                    "}";

            collection.mapReduce(map, reduce).action(MapReduceAction.REPLACE).collectionName("res_sorts").first();

            MongoCollection<Document> res = database.getCollection("res_sorts");
            for (Document d : res.find()) {
                System.out.println(((Document) d.get("value")).get("SpellName"));
            }

            //db.sorts.mapReduce(map,reduce,{out : 'sorts_wizard_lvl4_V'});
            //db.sorts_wizard.find();

            mongoClient.close();
    }
        }

    static void getWithBddSQLite()
    {
    	SQLiteDAO sqliteDAO = new SQLiteDAO();
        Parser p = null;
       // sqliteDAO.createBDD();
        try {
            p = new Parser(new URL(URL));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        for (int i = START_INDEX; i <= END_INDEX; i++) {
            System.out.println("index courant : " + i);
            try {
                RawEntry r = p.next(i);
                r.insertSQL(sqliteDAO.c);
            }catch(SQLException s){
                //Il y a parfois des erreurs de duplicata, mais si c'est déjà dans la base, on a pas besoin de traiter
                //Ici on skip les erreurs sur SQLITE_CONSTRAINT_PRIMARYKEY
                if (s.getErrorCode() != 1555)
                    s.printStackTrace();
            }
            catch (Exception e) {
                //e.printStackTrace();
                //page inexistante}
            }    	
           
    }
        
        System.out.println("Fini !!! ");
        try {
            sqliteDAO.c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
    	
    	ArrayList<String> listeNomSort = new ArrayList<String>();
    	SQLiteDAO sqliteDAO = new SQLiteDAO();
    	/*sqliteDAO.deleteTable("Classe");
    	sqliteDAO.deleteTable("Utilise");
    	sqliteDAO.deleteTable("Composant");
    	sqliteDAO.deleteTable("Niveau");
    	sqliteDAO.deleteTable("Sort");
    	sqliteDAO.createBDD();
        getWithBddSQLite();*/
    	listeNomSort = sqliteDAO.selectSort();
    	for(int i=0; i<listeNomSort.size(); i++)
    	{
    		System.out.println(listeNomSort.get(i) );
    	}
        }
    }



