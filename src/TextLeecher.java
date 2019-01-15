
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.util.ArrayList;
import java.util.List;

public class TextLeecher {
    private boolean verbose;


    public TextLeecher() {
        this.verbose = false;
    }

    public TextLeecher(boolean verbose) {
        this.verbose = verbose;
    }

    public int getAllOccurrences(Document doc,String toFind){
        Element body = doc.body();
        String allText = body.text();

        int lastIndex = 0;
        List<Integer> result = new ArrayList<Integer>();

        while(lastIndex != -1) {

            lastIndex = allText.indexOf(toFind,lastIndex);

            if(lastIndex != -1){
                result.add(lastIndex);
                lastIndex += 1;
            }
        }
        return result.size();
    }
}


