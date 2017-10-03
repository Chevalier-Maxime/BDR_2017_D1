package partieUn;

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

    public String[] getClasses() {
        return classes;
    }

    public int[] getLevel() {
        return level;
    }

    public String[] getComponents() {
        return components;
    }

    public boolean isSpell_resistance() {
        return spell_resistance;
    }
}
