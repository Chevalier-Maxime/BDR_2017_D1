package partieUn;

import java.util.Map;

public class RawEntry {
    private Map<String,Integer> availableFor;
    private String spellName;
    //private String[] classes;
    //private int[] level;
    private String[] components;
    private boolean spell_resistance;


    public RawEntry(String spellName, String[] classes, int[] level, String[] components, boolean spell_resistance) {
        this.spellName = spellName;
        //this.classes = classes;
        //this.level = level;
        for (int i = 0 ; i < classes.length ; i++) {
            availableFor.put(classes[i], level[i]);
        }
        this.components = components;
        this.spell_resistance = spell_resistance;
    }

    public String getSpellName() {
        return spellName;
    }

//    public String[] getClasses() {
//        return classes;
//    }
//
//    public int[] getLevel() {
//        return level;
//    }

    public Map<String,Integer>  getAvaillability(){
        return availableFor;
    }

    public String[] getComponents() {
        return components;
    }

    public boolean isSpell_resistance() {
        return spell_resistance;
    }
}
