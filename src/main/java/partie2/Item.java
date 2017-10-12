package partie2;

import org.bson.Document;

import java.util.Arrays;

public class Item {
    private String id;
    private int pg;
    private String adj[];


    public Item(String id, int pg, String[] adj) {
        this.id = id;
        this.pg = pg;
        this.adj = adj;
    }

    public void itemChange(String id, int pg, String[] adj) {
        this.id = id;
        this.pg = pg;
        this.adj = adj;
    }

    public Document getDoc(){
        Document doc = new Document();
        Document value = new Document();
        doc.put("_id", id);
        value.put("rank", pg);
        value.put("adjList", Arrays.asList(adj));
        value.put("type","full");
        doc.put("value",value);
        return doc;
    }
}
