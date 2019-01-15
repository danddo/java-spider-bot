import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Main {

    public static void main(String[] args) {
        String url = "https://www.bringg.com/";
        String word = "delivery";
        SpiderBot spiderBot = new SpiderBot(true);
        spiderBot.leech(url,word);
    }

}

