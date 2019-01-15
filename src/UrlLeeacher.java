import javafx.util.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class UrlLeeacher {
        private String baseUrl;
        private HashSet<String> urlsFoundOnBaseUrl;
        private boolean verbose;

        public UrlLeeacher(){
            this.baseUrl = "";
            urlsFoundOnBaseUrl = new HashSet<String>();
            verbose = false;
        }
        public UrlLeeacher(String url){
            this.baseUrl = url;
            urlsFoundOnBaseUrl = new HashSet<String>();
            verbose = false;
        }
        public UrlLeeacher(String url,Boolean verbose){
            this.baseUrl = url;
            urlsFoundOnBaseUrl = new HashSet<String>();
            verbose = verbose;
        }

        public void getAllUrls (){
            try{
            Document doc = Jsoup.connect(baseUrl).get();
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                //getting only baseUrl related sub urls
                if (link.attr("abs:href").matches("(.*)bringg.com(.*)")){
                    urlsFoundOnBaseUrl.add(link.attr("abs:href"));
                }
              }
            }
            catch (IOException e)  {
                System.out.println("io - " + e);
            }
            if (verbose){
                System.out.printf("URL processed: %s \n",baseUrl);
            }
        }

    public HashSet<String> getNewUrlsFound() {
        return urlsFoundOnBaseUrl;
    }
}
