package partieUn;
import java.sql.*;
import java.util.ArrayList;

public class SQLiteDAO {
       Statement stmt = null;
       Connection c = null;

       public SQLiteDAO ()
       {
           try {
             Class.forName("org.sqlite.JDBC");
             c = DriverManager.getConnection("jdbc:sqlite:BDR_2017_D1.db");

             }
           catch ( Exception e ) {
             System.err.println( e.getClass().getName() + ": " + e.getMessage());
             System.exit(0);
          }

           System.out.println("Opened database successfully");
       }

       public void deleteTable( String name)
       {
           try{
               stmt = c.createStatement();
               String sql = "DROP TABLE " + name;
               stmt.executeUpdate(sql);
           }
           catch ( Exception e ) {
                 System.err.println( e.getClass().getName() + ": " + e.getMessage());
                 System.exit(0);
              }

           System.out.println(" La table a ete supprime avec succ√®s !!!");

       }
       
       public ArrayList<String> selectSort()
       {
    	   ArrayList<String> listeSort = new ArrayList<String>();
    	   try {
    		   stmt = c.createStatement();
    	   ResultSet resultat;
    	   String sql;
    	   sql = "SELECT nomSort"
    	   		+  "from Classe"  
    			+ "inner join Niveau  on classe.nomClasse = Niveau.nomClasse"
    			   +"inner join Sort  on Niveau.nomSort = Sort.nomSort"
    			   + "inner join Utilise  on Sort.nomSort = Utilise.nomSort"
    			    + "inner join Composant  on Utilise.components = Composant.components"
    			    + "where  Niveau.classe = wizard and Composant.components = v and Niveau.level < 4 ; " ;
    	   
    	   resultat = stmt.executeQuery(sql);
    	   while(resultat.next())
    	   {
    		   listeSort.add(resultat.getString("nomSort"));
    	   }
    	   }
    	   catch ( Exception e ) {
               System.err.println( e.getClass().getName() + ": " + e.getMessage());
               System.exit(0);
            }
    	   
    	   System.out.println(" Liste rÈcupÈrÈe !!!");
    	   return listeSort;
    	   }

       public void createBDD()
       {
           try {
                 stmt = c.createStatement();
                 String sql ;

                 sql = "CREATE TABLE Classe " +
                                "(nomClasse STRING PRIMARY KEY     NOT NULL)";
                 stmt.executeUpdate(sql);

                 //Creation de la table Sort.

                  sql = "CREATE TABLE Sort " +
                                "(nomSort STRING PRIMARY KEY     NOT NULL," +
                          "spell_resistance BOOLEAN) ";
                 stmt.executeUpdate(sql);

                //Creation de la table Composant.

                 sql = "CREATE TABLE Composant " +
                    "(components STRING PRIMARY KEY     NOT NULL)";
                 stmt.executeUpdate(sql);

                // Creation de la table Niveau.

                 sql = "CREATE TABLE Niveau"
                        +
                          "(nomSort STRING REFERENCES Sort(nomSort),"
                         + "nomClasse STRING REFERENCES Classe(nomClasse),"
                          + "level STRING,"
                         + "PRIMARY KEY (nomSort,nomClasse))"
                         ;
                stmt.executeUpdate(sql);


                 // Creation de la table Utilise.

                sql = "CREATE TABLE Utilise"  + "(nomSort STRING REFERENCES Sort(nomSort),"
                    + "components STRING REFERENCES Composant(components))";
                stmt.executeUpdate(sql);
                stmt.close(); // Fermeture du statement.
                // c.close(); // Fermeture de la connexion.


           }
           catch ( Exception e ) {
               System.err.println( e.getClass().getName() + ": " + e.getMessage() );
               System.exit(0);
           }

           System.out.println("Tables cres avec succ√®s !!! ");

       }
}


