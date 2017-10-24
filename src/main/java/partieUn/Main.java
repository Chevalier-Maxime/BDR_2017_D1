package partieUn;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.MapReduceAction;
import org.bson.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


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
                String spellName = r.getSpellName().toString();
                boolean spell_resistance = r.isSpell_resistance();
                String classes ="";
                //TODO J'ai modifié ce FOR
                for (int j= 0; j<r.getClasses().length; j++)
                {
                    if(j==0)
                        classes = "(\""+  r.getClasses()[j] +"\")";
                    else
                        classes = classes + ", (\""+  r.getClasses()[j] +"\")";

                }
                String components ="";
                for (int j= 0; j<r.getComponents().length; j++)
                {
                	components = components + " "+  r.getComponents()[j];
                }
                String level ="";
                for (int j= 0; j<r.getLevel().length; j++)
                {
                	level = level + " "+  r.getLevel()[j];
                }
                
                EntreSQLite entree = new EntreSQLite(spellName, classes, level, components, spell_resistance);
                //System.out.println(classes);
                sqliteDAO.insertRawEntry(entree);
            } catch (Exception e) {//page inexistante}
            }    	
           
    }
        
        System.out.println("Fini !!! ");
    }
    
    public static void main(String[] args) {

    	SQLiteDAO sqliteDAO = new SQLiteDAO();
    	sqliteDAO.deleteTable("Classe");
    	sqliteDAO.deleteTable("Utilise");
    	sqliteDAO.deleteTable("Composant");
    	sqliteDAO.deleteTable("Niveau");
    	sqliteDAO.deleteTable("Sort");
    	sqliteDAO.createBDD();
        getWithBddSQLite();
        }
    }



