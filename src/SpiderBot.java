
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpiderBot {
    private Set<String> checkedUrls;
    private UrlLeeacher urlLeecher;
    private TextLeecher textLeecher;
    private int numOfOccur;


    public SpiderBot(boolean verbose){
        this.checkedUrls = new HashSet<String>();
        this.textLeecher = new TextLeecher(verbose);
        this.urlLeecher = new UrlLeeacher(verbose);
        this.numOfOccur = 0;
    }

    public void leech (String targetUrl,String targetWord){
        String tragetName = extractNameFromUrl(targetUrl);
        Document urlDoc;
        String currentUrl;
        //we will use a  queue to  know which urls we need to process
        LinkedList<String> urlsToProcess = new LinkedList<String>();
        urlsToProcess.add(targetUrl);  //init the queue with base url
        while (!urlsToProcess.isEmpty()){
            currentUrl = urlsToProcess.pop();
            //adding url to checked urls
            checkedUrls.add(currentUrl);
            //connect url
            urlDoc = queryServer(currentUrl);
            //process url
            Set<String> newUrlsToCheck = urlLeecher.getNewUrls(urlDoc,tragetName);
            numOfOccur += textLeecher.getAllOccurrences(urlDoc,targetWord);
            System.out.printf("Number of Occurrences: %d\n", numOfOccur);
            //search for new urls to process
            for (String newUrl : newUrlsToCheck){
                if (!urlsToProcess.contains(newUrl) && !checkedUrls.contains(newUrl)){
                    urlsToProcess.add(newUrl);
                    System.out.printf("New Url queued:%s\n",newUrl);
                }
            }
            System.out.printf("URL processed: %s \n",currentUrl);
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

    private Document queryServer(String baseUrl){
        Document doc = new Document("");
        try{
            doc = Jsoup.connect(baseUrl).get();

        }
        catch (IOException e) {
            System.out.println("io - " + e);
        }
        return doc;
    }



}
