import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlLeeacher {
        private HashSet<String> urlsFoundOnBaseUrl;
        private boolean verbose;

        public UrlLeeacher(){
            this.urlsFoundOnBaseUrl = new HashSet<String>();
            this.verbose = false;
        }
        public UrlLeeacher(Boolean verbose){
            this.urlsFoundOnBaseUrl = new HashSet<String>();
            this.verbose = verbose;
        }

        private void findAllSubUrls (Document urlDoc , String baseUrlSiteName){
            Elements links = urlDoc.select("a[href]");
            for (Element link : links) {
                //getting only baseUrl name related sub urls
                if (link.attr("abs:href").matches("(.*)" + baseUrlSiteName + "(.*)") ){
                    urlsFoundOnBaseUrl.add(link.attr("abs:href"));
                }
            }
        }


        public HashSet<String> getNewUrls(Document urlDoc,String baseUrlSiteName) {
            findAllSubUrls(urlDoc,baseUrlSiteName);
            return urlsFoundOnBaseUrl;
        }

}
