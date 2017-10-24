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
		   
		   System.out.println(" La table a ete supprime avec succès !!!");
		   
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
	         
sql = "CREATE TABLE Utilise" 
		 
		
		 + "(nomSort STRING REFERENCES Sort(nomSort),"
		 + "components STRING REFERENCES Composant(components))"
		 ;
stmt.executeUpdate(sql);


	         stmt.close(); // Fermeture du statement.
	         c.close(); // Fermeture de la connexion.
	         
	    
	   }
		   catch ( Exception e ) {
	  	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	  	         System.exit(0);
	  	      }
		   
		   System.out.println("Tables cres avec succès !!! ");
		   
		   }
	
	   public void insertRawEntry( EntreSQLite entree)
	   {
		   
		   
		   try{
			   
			   c.setAutoCommit(false);
		   stmt = c.createStatement();
		   
		/*   String tableauClasse[];
		   tableauClasse = r.getClasses();
		   String classes = "";
		   for (int i=0; i<tableauClasse.length; i++)
		   {
			   classes  = classes + " " + tableauClasse[i];
		   }
		   
		   
		   int tableauLevel[];
		   tableauLevel = r.getLevel();
		   String level = "";    // Obligation de mettre une chaine de caractère si je veux mettre tous les niveaux sur la même ligne.
		   for (int i=0; i<tableauLevel.length; i++)
		   {
			   level  = level + " " + tableauLevel[i];
		   }
		   
		   String tableauComponents[];
		   tableauComponents = r.getComponents();
		   String components = "";    
		   for (int i=0; i<tableauComponents.length; i++)
		   {
			   components  = components + " " + tableauComponents[i];
		   }
		   
		   */
		   // Insertion dans la table Classe.
		   

               //TODO J'ai modifié ce string
		   String sql = "INSERT INTO Classe (nomClasse) " +
                   "VALUES "+ entree.getClasses() + ";";
		   //TODO et le int c'est juste pour le debug
    int i = stmt.executeUpdate(sql);
    
    
 // Insertion dans la table Sort.
    sql = "INSERT INTO Sort (nomSort, spell_resistance) " +
            "VALUES (\""+ entree.getSpellName() + "\",'" + entree.isSpell_resistance()+ "');";
 i = stmt.executeUpdate(sql);

// Insertion dans la table Composant.
sql = "INSERT INTO Composant (components) " +
        "VALUES "+ entree.getComponents() + ";";
i = stmt.executeUpdate(sql);

// Insertion dans la table Niveau.
//TODO ne fonctionne pas
sql = "INSERT INTO Niveau (nomSort,nomClasse, level  ) " +
        "VALUES ("+ entree.getSpellName() +", "+ entree.getClasses() +"," + entree.getLevel() +  ");";
i = stmt.executeUpdate(sql);

// Insertion dans la table Utilise.
sql = "INSERT INTO Utilise (nomSort,components ) " +
        "VALUES ("+ entree.getSpellName() +", "+ entree.getComponents() +  ");";
i = stmt.executeUpdate(sql); 

stmt.close(); // Fermeture du statement.

c.close(); // Fermeture de la connexion
		   }
		   
		   catch ( Exception e ) {
	  	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	  	         System.exit(0);
	  	      }
		   System.out.println(" Lignes inserees avec succès !!! ");
	   }
	   
	 
	 
}


