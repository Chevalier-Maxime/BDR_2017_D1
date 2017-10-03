package partieUn;

import java.util.HashMap;
import java.util.Map;

public class RawEntry {
    private Map<String, Object> documentMap = new HashMap<>();
    private Map<String,Integer> availableFor = new HashMap<>();



    public RawEntry(String spellName, String[] classes, int[] level, String[] components, boolean spell_resistance) {

        documentMap.put("SpellName",spellName);
        documentMap.put("SpellResistance", spell_resistance);
        documentMap.put("Components", components);

        for (int i = 0 ; i < classes.length ; i++) {
            availableFor.put(classes[i], level[i]);
        }

        documentMap.put("AvailableFor", availableFor);

    }

    public Map<String, Object> getDocumentMap() {
        return documentMap;
    }
}
