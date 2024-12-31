package Test;

import PageObjects.HomePage;
import PageObjects.OpinionPage;
import Utils.Config;
import org.json.JSONArray;
import org.testng.annotations.Test;

public class Test_ELPAIS extends Config {


    String url = "https://elpais.com/";

    @Test(description = "Verify website loads with spanish as default language")
    public void verifyWebTextOnVisit(){

        driver.get(url);
        HomePage homePage = new HomePage();
        homePage.acceptCookie();


        // Verify Spanish is selected by default
        homePage.verifyDefaultLanguage("ESPAÑA");

        //Scrape Articles from the Opinion Section

        homePage.clickOnOpinionTab();
        OpinionPage opinionPage = new OpinionPage();

        /** Print article title and content. Also saving image present in article with name
        Article_(position of article whose image in getting saved) in ImageOutput folder **/
        opinionPage.printArticleDetails();

        JSONArray translatedTitles =  opinionPage.translateAndPrintArticleTitle();

        opinionPage.findCountOfRepeatedWords(translatedTitles);

    }



}
