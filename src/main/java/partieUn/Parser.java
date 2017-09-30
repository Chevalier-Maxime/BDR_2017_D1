package partieUn;

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
        System.out.printf(spellName.text());

        //current_index++;
        return null;
    }
}
