package partieUn;

import org.bson.Document;

import java.util.Arrays;

public class RawEntry {
    private Document doc = new Document();

    public RawEntry(String spellName, String[] classes, int[] level, String[] components, boolean spell_resistance) {

        doc.put("SpellName", spellName);
        doc.put("SpellResistance",spell_resistance);
        doc.put("Components", Arrays.asList(components));
        Document availableFor = new Document();

        for (int i = 0 ; i < classes.length ; i++) {
            availableFor.put(classes[i], level[i]);
        }
        doc.put("AvailableFor", availableFor);

    }

    public Document getDoc() {
        return doc;
    }

}
