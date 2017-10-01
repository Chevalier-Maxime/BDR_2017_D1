package partieUn;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;

public class Parser {

    private URL url;

    public Parser(URL url) {
        this.url = url;
    }

    public RawEntry next(int index) throws IOException {

        RawEntry spell = new RawEntry();
        Document doc = Jsoup.connect(url.toString()+index).get();

        //Spell Name
        Element spellName = doc.select("div.heading").first();
        System.out.println(spellName.text());

        //Classes and levels
        spellName = doc.select("P.SPDet").first();
        System.out.println(spellName.text());

        //Components
        spellName = doc.select("P.SPDet").get(2);
        System.out.println(spellName.text());

        //Spell resistance
        spellName = doc.select("P.SPDet").get(6);
        System.out.println(spellName.text());
        //current_index++;

        getSpellResistance(doc);
        System.out.println(getSpellResistance(doc));
        return null;
    }

    private String getSpellName(Document doc){
        Element spellName = doc.select("div.heading").first();
        return spellName.text();
    }

    private String[] getClasses(Document doc){
        Element spellName = doc.select("P.SPDet").first();
        String string = spellName.text();
        string = string.substring(string.lastIndexOf(';') + 7);
        String stringFormatted = string.replace('/',',');
        stringFormatted = stringFormatted.replaceAll("\\d|\\s","");

        return stringFormatted.split(",");

    }

    private String[] getLevels(Document doc){
        Element spellName = doc.select("P.SPDet").first();
        String string = spellName.text().replace('/',',');
        String stringFormatted = string.substring(string.lastIndexOf(';') + 7);
        stringFormatted = stringFormatted.replaceAll("[^0-9,]","");

        String finalString = "";

        char precedent = stringFormatted.charAt(0);
        for(int i = 1 ; i < stringFormatted.length(); i++){
            char current = stringFormatted.charAt(i);
            if ((precedent == ',') && (i == 1)){
                finalString+=stringFormatted.charAt(i) + "," + stringFormatted.charAt(i);
            } else if ((precedent == ',') && current ==','){
                finalString+=stringFormatted.charAt(i+1) + ",";
            } else {
                finalString+=stringFormatted.charAt(i);
            }

            precedent = current;
        }
        return stringFormatted.split(",");
    }

    private String[] getComponents(Document doc){
        Element component = doc.select("P.SPDet").get(2);
        String string = component.text();
        string = string.substring(11).replaceAll("[^A-Z,]","");
        return string.split(",");
    }

    private boolean getSpellResistance(Document doc){
        Element spellResistance = doc.select("P.SPDet").get(6);
        String string = spellResistance.text().substring(spellResistance.text().lastIndexOf(';'));
        if (string.contains(" no")) return false;

        return true;
    }
}
