package partieUn;

import org.bson.Document;

import java.sql.*;
import java.util.Arrays;
import java.util.Map;

public class RawEntry {

	private String spellName;
	private String[] classes;
	private int[] level;
	private String[] components;
	private boolean spell_resistance;

	public RawEntry(String spellName, String[] classes, int[] level, String[] components, boolean spell_resistance) {

		this.spellName = spellName;
		this.classes = classes;
		this.level = level;
		this.components = components;
		this.spell_resistance = spell_resistance;

	}

	public String getSpellName() {
		return spellName;
	}

	public void setSpellName(String spellName) {
		this.spellName = spellName;
	}

	public String[] getClasses() {
		return classes;
	}

	public void setClasses(String[] classes) {
		this.classes = classes;
	}

	public int[] getLevel() {
		return level;
	}

	public void setLevel(int[] level) {
		this.level = level;
	}

	public String[] getComponents() {
		return components;
	}

	public void setComponents(String[] components) {
		this.components = components;
	}

	public boolean isSpell_resistance() {
		return spell_resistance;
	}

	public void setSpell_resistance(boolean spell_resistance) {
		this.spell_resistance = spell_resistance;
	}

	@Override
	public String toString() {
		return "RawEntry [spellName=" + spellName + ", classes=" + Arrays.toString(classes) + ", level="
				+ Arrays.toString(level) + ", components=" + Arrays.toString(components) + ", spell_resistance="
				+ spell_resistance + "]";
	}

	public Document getDoc() {
		Document doc = new Document();
		doc.put("SpellName", spellName);
		doc.put("SpellResistance", spell_resistance);
		doc.put("Components", Arrays.asList(components));
		Document availableFor = new Document();

		for (int i = 0; i < classes.length; i++) {
			availableFor.put(classes[i], level[i]);
		}
		doc.put("AvailableFor", availableFor);
		return doc;
	}

	public void insertSQL(Connection con) throws SQLException {
        //Insertion des classes
        StringBuffer sqlClasseSelect = new StringBuffer("SELECT nomClasse FROM Classe WHERE nomClasse=?");
	    StringBuffer sqlClasseInsert = new StringBuffer(
                "INSERT INTO Classe (nomClasse) VALUES (?)");


        /*for (int i = 0; i < classes.length - 1; i++) {
            sqlClasse.append(", (?)");
            sqlClasse.append(" ON DUPLICATE KEY UPDATE nomClasse=VALUES(nomClasse)");
        }*/



        PreparedStatement ps ;
        PreparedStatement ps2;
        for (int i = 1; i <= classes.length; i++) {
            ps = con.prepareStatement(sqlClasseSelect.toString());
            ps.setString(1,classes[i-1]);
            if(ps.executeQuery().isBeforeFirst()) {
                continue;
            }
            ps2  = con.prepareStatement(sqlClasseInsert.toString());
            ps2.setString(1,classes[i-1]);
            ps2.executeUpdate();
        }

        //Insertion dans sorts

        StringBuffer sqlSorts = new StringBuffer(
                "INSERT INTO Sort (nomSort, spell_resistance) VALUES (?,?)");
        ps = con.prepareStatement(sqlSorts.toString());
        ps.setString(1,this.spellName);
        ps.setBoolean(2,this.spell_resistance);
        ps.executeUpdate();

        //Composant
        StringBuffer sqlComposantsSelect = new StringBuffer("SELECT components FROM Composant WHERE components=?");
        StringBuffer sqlComposantsInsert = new StringBuffer(
                "INSERT INTO Composant (components) VALUES (?)");
        for (int i = 0; i < components.length - 1; i++) {
            ps = con.prepareStatement(sqlComposantsSelect.toString());
            ps.setString(1,components[i]);
            if(ps.executeQuery().isBeforeFirst()) {
                continue;
            }
            ps2  = con.prepareStatement(sqlComposantsInsert.toString());
            ps2.setString(1,components[i]);
            ps2.executeUpdate();
        }


        //Niveau
        StringBuffer sqlNiveau = new StringBuffer(
                "INSERT INTO Niveau (nomSort,nomClasse, level) VALUES (?,?,?)");
        for (int i = 0; i < level.length - 1; i++) {
            sqlNiveau.append(", (?,?,?)");
        }

        ps = con.prepareStatement(sqlNiveau.toString());
        int j =0;
        for (int i = 1; i <= level.length*3; i+=3) {
            ps.setString(i,spellName);
            ps.setString(i+1,classes[j]);
            ps.setInt(i+2,level[j]);
            j++;
        }
        ps.executeUpdate();


        //Utilise
        StringBuffer sqlUtilise = new StringBuffer(
                "INSERT INTO Utilise (nomSort,components )  VALUES (?,?)");
        for (int i = 0; i < components.length - 1; i++) {
            sqlUtilise.append(", (?,?)");
        }

        ps = con.prepareStatement(sqlUtilise.toString());
        j =0;
        for (int i = 1; i <= components.length*2; i+=2) {
            ps.setString(i,spellName);
            ps.setString(i+1,components[j]);
            j++;
        }
        ps.executeUpdate();

        /*try {
            con.setAutoCommit(false);
            updateSales = con.prepareStatement(updateString);
            updateTotal = con.prepareStatement(updateStatement);

            for (Map.Entry<String, Integer> e : salesForWeek.entrySet()) {
                updateSales.setInt(1, e.getValue().intValue());
                updateSales.setString(2, e.getKey());
                updateSales.executeUpdate();
                updateTotal.setInt(1, e.getValue().intValue());
                updateTotal.setString(2, e.getKey());
                updateTotal.executeUpdate();
                con.commit();
            }
        } catch (SQLException e ) {
            JDBCTutorialUtilities.printSQLException(e);
            if (con != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    con.rollback();
                } catch(SQLException excep) {
                    JDBCTutorialUtilities.printSQLException(excep);
                }
            }
        } finally {
            if (updateSales != null) {
                updateSales.close();
            }
            if (updateTotal != null) {
                updateTotal.close();
            }
            con.setAutoCommit(true);
        }*/
    }
}
