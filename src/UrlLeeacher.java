import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlLeeacher {
        private String baseUrl;
        private HashSet<String> urlsFoundOnBaseUrl;
        private boolean verbose;

        public UrlLeeacher(){
            this.baseUrl = "";
            this.urlsFoundOnBaseUrl = new HashSet<String>();
            this.verbose = false;
        }
        public UrlLeeacher(String url){
            this.baseUrl = url;
            this.urlsFoundOnBaseUrl = new HashSet<String>();
            this.verbose = false;
        }
        public UrlLeeacher(String url,Boolean verbose){
            this.baseUrl = url;
            this.urlsFoundOnBaseUrl = new HashSet<String>();
            this.verbose = verbose;
        }

        public void getAllUrls (){
            String baseUrlSiteName = extractNameFromUrl(this.baseUrl);
            try{
            Document doc = Jsoup.connect(baseUrl).get();
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                //getting only baseUrl name related sub urls
                if (link.attr("abs:href").matches("(.*)baseUrlSiteName(.*)") ){
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
        /*Takes a full url string and returns site name and domain
        example: from "https://www.google.com" the result is "google.com"*/
        private String extractNameFromUrl(String url){
            final Pattern pattern = Pattern.compile("(https?://)?(www\\.)?([a-zA-Z0-9\\-]*)(\\.[\\w|\\.]*)", Pattern.DOTALL);
            final Matcher matcher = pattern.matcher(url);
            matcher.find();
            return (matcher.group(3)+matcher.group(4));
        }

        public HashSet<String> getNewUrlsFound() {
            return urlsFoundOnBaseUrl;
        }
}
