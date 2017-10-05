package partieUn;

import org.bson.Document;

import java.util.Arrays;

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

    public Document getDoc() {
        Document doc = new Document();
        doc.put("SpellName", spellName);
        doc.put("SpellResistance",spell_resistance);
        doc.put("Components", Arrays.asList(components));
        Document availableFor = new Document();

        for (int i = 0 ; i < classes.length ; i++) {
            availableFor.put(classes[i], level[i]);
        }
        doc.put("AvailableFor", availableFor);
        return doc;
    }

}
