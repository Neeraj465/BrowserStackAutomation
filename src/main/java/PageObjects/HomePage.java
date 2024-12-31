package PageObjects;

import Helpers.CommonHelper;
import Utils.Config;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;


public class HomePage extends Config {

    @FindBy (xpath="//button[@id='didomi-notice-agree-button']")
    public WebElement acceptCookieBtn;

    @FindBy (id="edition_head")
    public WebElement defaultLanguage;

    @FindBy (xpath = "//a[text()='Opinión']")
    public WebElement opinionTab;


    public HomePage(){
        PageFactory.initElements(driver, this);
    }


    public void acceptCookie(){
        try{
            new CommonHelper().waitForElementToBeClickable(acceptCookieBtn);
            acceptCookieBtn.click();
        } catch (Exception e){
            // do nothing
        }
    }

    public void verifyDefaultLanguage(String desiredLang){
        new CommonHelper().waitForElementToBeClickable( defaultLanguage);
        String lang = defaultLanguage.getText();
        Assert.assertEquals(lang, desiredLang);
    }

    public void clickOnOpinionTab(){
        opinionTab.click();
    }
}
