package Helpers;

import Utils.Config;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.time.Duration;

public class CommonHelper extends Config {


    public void waitForElementToBeClickable( WebElement element){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
}
