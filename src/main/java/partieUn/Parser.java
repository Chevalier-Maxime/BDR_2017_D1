package partieUn;

import java.net.URL;

public class Parser {

    private final static int START_INDEX = 1;
    private final static int END_INDEX = 1975;


    private URL url;
    private  int current_index = START_INDEX;

    public Parser(URL url) {
        this.url = url;
    }

    public RawEntry next(){
        return null;
    }
}
