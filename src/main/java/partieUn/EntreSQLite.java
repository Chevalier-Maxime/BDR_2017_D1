package partieUn;

public class EntreSQLite {
	String spellName;
	String classes; String level;
	String components;
	boolean spell_resistance;
	public EntreSQLite(String spellName, String classes, String level, String components, boolean spell_resistance) {
		super();
		this.spellName = spellName;
		this.classes = classes;
		this.level = level;
		this.components = components;
		this.spell_resistance = spell_resistance;
	}
	@Override
	public String toString() {
		return "EntreSQLite [spellName=" + spellName + ", classes=" + classes + ", level=" + level + ", components="
				+ components + ", spell_resistance=" + spell_resistance + "]";
	}
	public String getSpellName() {
		return spellName;
	}
	public void setSpellName(String spellName) {
		this.spellName = spellName;
	}
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes = classes;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getComponents() {
		return components;
	}
	public void setComponents(String components) {
		this.components = components;
	}
	public boolean isSpell_resistance() {
		return spell_resistance;
	}
	public void setSpell_resistance(boolean spell_resistance) {
		this.spell_resistance = spell_resistance;
	}
}
