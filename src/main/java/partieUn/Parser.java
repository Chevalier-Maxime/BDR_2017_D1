package partieUn;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

/**
 * Classe permettant de parser certaines informations du site www.dxcontent.com
 */
public class Parser {

    private URL url;

    public Parser(URL url) {
        this.url = url;
    }

    /**
     * Creer l'objet RawEntry
     * @param index l'index de la page HTML
     * @return un RawEntry complet
     * @throws IOException
     */
    public RawEntry next(int index) throws IOException {
        Document doc = Jsoup.connect(url.toString()+index).get();
        RawEntry spell = new RawEntry(
                getSpellName(doc),
                getClasses(doc),
                getLevels(doc),
                getComponents(doc),
                getSpellResistance(doc)
        );
        return spell;
    }

    /**
     * Retourne le nom du sort
     * @param doc Document HTML où récuperer le sort
     * @return le nom du sort
     */
    private String getSpellName(Document doc){
        Element spellName = doc.select("div.heading").first();
        return spellName.text();
    }

    /**
     * Fonction permettant de renvoyer toutes les classes associées à un sort
     * @param doc Document HTML où récuperer le sort
     * @return les classes permettant de lancer le sort
     */
    private String[] getClasses(Document doc){
        Element spellName = doc.select("P.SPDet").first();
        String string = spellName.text();
        string = string.substring(string.lastIndexOf(';') + 7);
        String stringFormatted = string.replace('/',',');
        stringFormatted = stringFormatted.replaceAll("\\d|\\s","");

        return stringFormatted.split(",");

    }

    /**
     * Fontion retournant les niveaux associés aux classes (@see getClasses)
     * @param doc Document HTML où récuperer le sort
     * @return Les niveaux associés au sort
     */
    private int[] getLevels(Document doc){
        Element spellName = doc.select("P.SPDet").first();
        String string = spellName.text().replace('/',',');
        System.out.println(string);
        String stringFormatted = string.substring(string.lastIndexOf(';') + 7);
        System.out.println(stringFormatted);
        stringFormatted = stringFormatted.replaceAll("[^0-9,]","");
        System.out.println(stringFormatted);

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
        return Arrays.stream(finalString.split(",")).mapToInt(Integer::parseInt).toArray();
    }

    /**
     * Fonction permettant de recuperer les types de besoin d'un sort
     * @param doc Document HTML où récuperer le sort
     * @return Tableau des types de composants
     */
    private String[] getComponents(Document doc){
        Element component = doc.select("P.SPDet").get(2);
        String string = component.text();
        string = string.substring(11).replaceAll("[^A-Z,]","");
        return string.split(",");
    }

    /**
     * Fonction permettant de recuperer le spell resistance d'un sort dans la page HTML
     * @param doc Document HTML où récuperer le sort
     * @return spell resistance
     */
    private boolean getSpellResistance(Document doc){
        Element spellResistance = doc.select("P.SPDet").get(6);
        String string = spellResistance.text().substring(spellResistance.text().lastIndexOf(';'));
        if (string.contains(" no")) return false;

        return true;
    }
}
