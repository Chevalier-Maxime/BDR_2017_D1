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
		   
		   System.out.println(" La table a �t� supprim� avec succ�s !!!");
		   
	   }
	   
	   public void createBDD()
	   {
		   try {
		   stmt = c.createStatement();
	         String sql ;
	         
	         sql = "CREATE TABLE Classe " +
	                        "(nomClasse VARCHAR PRIMARY KEY     NOT NULL)"; 
	         stmt.executeUpdate(sql);
	         
	         //Cr�ation de la table Sort.
	         
	          sql = "CREATE TABLE Sort " +
	                        "(nomSort VARCHAR PRIMARY KEY     NOT NULL," + 
	        		  "spell_resistance BOOLEAN) "; 
	         stmt.executeUpdate(sql);
	         
	        //Cr�ation de la table Composant.
	         
	         sql = "CREATE TABLE Composant " +
                "(components VARCHAR PRIMARY KEY     NOT NULL)"; 
 stmt.executeUpdate(sql);
 
// Cr�ation de la table Niveau.

 sql = "CREATE TABLE Niveau" 
		+
		  "(nomSort VARCHAR REFERENCES Sort(nomSort),"
		 + "nomClasse VARCHAR REFERENCES Classe(nomClasse),"
		  + "level VARCHAR,"
		 + "PRIMARY KEY (nomSort,nomClasse))"
		 ;
stmt.executeUpdate(sql);


        
 
	         // Cr�ation de la table Utilise.
	         
sql = "CREATE TABLE Utilise" 
		 
		
		 + "(nomSort VARCHAR REFERENCES Sort(nomSort),"
		 + "components VARCHAR REFERENCES Composant(components))"
		 ;
stmt.executeUpdate(sql);


	         stmt.close(); // Fermeture du statement.
	         c.close(); // Fermeture de la connexion.
	         
	    
	   }
		   catch ( Exception e ) {
	  	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	  	         System.exit(0);
	  	      }
		   
		   System.out.println("Tables cr��s avec succ�s !!! ");
		   
		   }
	
	   public void insertRawEntry( EntreSQLite entr�e)
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
		   String level = "";    // Obligation de mettre une chaine de caract�re si je veux mettre tous les niveaux sur la m�me ligne.
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
		
		   String sql = "INSERT INTO Classe (nomClasse) " +
                   "VALUES ("+ entr�e.getClasses() + ");"; 
    stmt.executeUpdate(sql); 
    
    
 // Insertion dans la table Sort.
    sql = "INSERT INTO Sort (nomSort, spell_resistance) " +
            "VALUES ("+ entr�e.getSpellName() + "," + entr�e.isSpell_resistance()+ ");"; 
stmt.executeUpdate(sql);

// Insertion dans la table Composant.
sql = "INSERT INTO Composant (components) " +
        "VALUES ("+ entr�e.getComponents() + ");"; 
stmt.executeUpdate(sql);

// Insertion dans la table Niveau.

sql = "INSERT INTO Niveau (nomSort,nomClasse, level  ) " +
        "VALUES ("+ entr�e.getSpellName() +", "+ entr�e.getClasses() +"," + entr�e.getLevel() +  ");";  
stmt.executeUpdate(sql);

// Insertion dans la table Utilise.
sql = "INSERT INTO Utilise (nomSort,components ) " +
        "VALUES ("+ entr�e.getSpellName() +", "+ entr�e.getComponents() +  ");";  
stmt.executeUpdate(sql); 

stmt.close(); // Fermeture du statement.

c.close(); // Fermeture de la connexion
		   }
		   
		   catch ( Exception e ) {
	  	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	  	         System.exit(0);
	  	      }
		   System.out.println(" Lignes ins�r�es avec succ�s !!! ");
	   }
	   
	 
	 
}


