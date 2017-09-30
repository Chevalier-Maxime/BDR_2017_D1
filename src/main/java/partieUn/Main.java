package partieUn;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {

    private final static int START_INDEX = 1;
    private final static int END_INDEX = 1975;
    private final static String URL = "http://www.dxcontent.com/SDB_SpellBlock.asp?SDBID=";

    public static void main(String[] args) {
        try {
            Parser p = new Parser(new URL(URL));
            p.next(1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
