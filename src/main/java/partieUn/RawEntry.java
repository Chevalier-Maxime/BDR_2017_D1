package partieUn;

import org.bson.Document;

import java.util.Arrays;

public class RawEntry {

	private String spellName;
	private String[] classes;

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

	private int[] level;
	private String[] components;
	@Override
	public String toString() {
		return "RawEntry [spellName=" + spellName + ", classes=" + Arrays.toString(classes) + ", level="
				+ Arrays.toString(level) + ", components=" + Arrays.toString(components) + ", spell_resistance="
				+ spell_resistance + "]";
	}

	private boolean spell_resistance;

	public RawEntry(String spellName, String[] classes, int[] level, String[] components, boolean spell_resistance) {

		this.spellName = spellName;
		this.classes = classes;
		this.level = level;
		this.components = components;
		this.spell_resistance = spell_resistance;

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

}
