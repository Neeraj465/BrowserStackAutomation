package PageObjects;

import Utils.Config;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.*;

import static io.restassured.RestAssured.given;


public class OpinionPage extends Config {

    @FindBy (xpath = "(//article)[position() < 6]")
    public List<WebElement> articles;

    public OpinionPage(){
        PageFactory.initElements(driver, this);
    }


    public void printArticleDetails(){

        int articleNumber=1;
        for(WebElement article: articles){


            //Print title of article
            System.out.println("============ Article "+ articleNumber + " ============");
            System.out.println(article.findElement(By.xpath("header/h2")).getText());

            //Print Content
            System.out.println(article.findElement(By.xpath("p")).getText() + "\n");

            try{
                // get image url if present
                String imgUrlString = article.findElement(By.xpath("figure/a/img")).getAttribute("src");
                URI uri = new URI(imgUrlString);
                URL imgURL = uri.toURL();
                BufferedImage saveImage = ImageIO.read(imgURL.openStream());

                //download image to the workspace where the project exists
                String path = System.getProperty("user.dir") + File.separator+ "ImageOutput";
                ImageIO.write(saveImage, "jpg", new File(path + File.separator + "Article_"+articleNumber + ".jpg"));
                articleNumber++;
            } catch(Exception e){
                articleNumber++;
            }
        }
    }

    public JSONArray translateAndPrintArticleTitle(){

        String[] articleTitleList = new String[5];
        for(int i=-0; i<5; i++){
            articleTitleList[i] = articles.get(i).findElement(By.xpath("header/h2")).getText();
        }

        String url = "https://rapid-translate-multi-traduction.p.rapidapi.com/t";

        JSONObject request = new JSONObject();
        request.put("from", "es");
        request.put("to", "en");
        request.put("e", "");
        request.put("q", articleTitleList);

        Map<String, String> headers = new HashMap<>();
        headers.put("x-rapidapi-host", "rapid-translate-multi-traduction.p.rapidapi.com");
        headers.put("x-rapidapi-key", "fc469c5f96mshd46bb33dbaaf9c2p1c7595jsn8334df7236a4");
        headers.put("Content-Type", "application/json");

        RequestSpecification requestSpecification = given();
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.headers(headers);
        requestSpecification.body(request.toString());

        Response response = requestSpecification.when().post(url).then().extract().response();

        JSONArray translatedTitles = new JSONArray(response.getBody().asString());

        System.out.println("============== Translated Article Titles ==============");
        for(int i=0; i<translatedTitles.length(); i++){
            System.out.println(translatedTitles.getString(i));
        }

        return translatedTitles;


    }

    public void findCountOfRepeatedWords(JSONArray translatedTitles){

        String combinedText="";
        for(int i=0; i<translatedTitles.length(); i++){
            combinedText += translatedTitles.getString(i).toLowerCase()+" ";
        }

        String[] words = combinedText.replaceAll("[(),']", "").split(" ");
        HashMap<String, Integer> hashMap = new HashMap<>();
        for(String word: words){
            if(hashMap.containsKey(word)) {
                hashMap.replace(word, hashMap.get(word) + 1);
            } else
                hashMap.put(word, 1);

        }

        //the year of all dangers five years with covid photos with carter alice munro and the man in the bag ‘bonanza (the line of conception)’

        String requiredWords = "Words with more than 2 occurrences: ";

        System.out.println("======= Count of repeated words =======");
        for(String word: hashMap.keySet()){
            if(hashMap.get(word) > 1)
                System.out.println(word + ":" + hashMap.get(word));
            if(hashMap.get(word) > 2)
                requiredWords += word + " ";
        }

        System.out.println(requiredWords);


    }

}
