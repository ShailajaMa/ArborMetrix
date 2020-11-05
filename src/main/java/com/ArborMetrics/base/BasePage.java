package com.ArborMetrics.base;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class BasePage {

    public static WebDriver driver;
    private WebDriverWait wait;
    JavascriptExecutor javascriptExecutor;
    public static Logger log = Logger.getLogger(BasePage.class.getName());


    static {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/java/com/ArborMetrics/browserDrivers/chromedriver.exe");
    }
    public static String getProperties(String property) throws IOException {
        PropertyConfigurator.configure("./src/main/java/com/ArborMetrics/properties/log4j.properties");
        Properties or = new Properties();
        FileInputStream file = new FileInputStream(new File(System.getProperty("user.dir") + "/src/main/java/com/ArborMetrics/properties/OR.properties"));
        or.load(file);
       return or.getProperty(property);
    }

    protected Object executeJs(String jsString, WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        return executor.executeScript(jsString, element);
    }
    public static void initDriver(String browser, String url){
        driver = null;
        switch (browser){
            case "chrome":
              driver = new ChromeDriver();
              break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            default:
                System.out.println("Please enter lower case browser name");

        }
        log.info("driver got initiated");
        driver.get(url);

    }

    public boolean isClickable (WebElement element){
        boolean isClickable = false;

        try {
            wait = new WebDriverWait(driver, 20);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            isClickable = true;
        }catch (ElementClickInterceptedException e) {
            e.printStackTrace();

        }
        return isClickable;
    }

    public boolean isVisible (WebElement element){
        boolean isVisible = false;
        try {
            wait = new WebDriverWait(driver, 20);
            wait.until(ExpectedConditions.visibilityOf(element));
            isVisible = true;
        } catch (ElementNotVisibleException e){
            e.printStackTrace();

        }
        return isVisible;
    }
    public String isSelect(WebElement element, String value) {
        Select select = new Select(element);
        if(isVisible(element))
            select.selectByVisibleText(value);
        return value;
    }

    public void scrollToLocation(String x, String y, WebElement element) {
        javascriptExecutor =(JavascriptExecutor) driver;
        javascriptExecutor.executeScript("window.scrollTo(" + x + "," + y + ");", element);
    }
    

    public void takeScreenShot( ) throws IOException {
        File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE );
       try {
           FileUtils.copyFile(src, new File(System.getProperty("user.dir" )+ "/src/test/java/com/ArborMetrics/screenShots/confirmationPage.png"));
       }catch (FileNotFoundException e){
           e.printStackTrace();
       }

    }

    protected WebDriver switchFrame(WebElement element) {
        WebDriver driver1 = driver.switchTo().frame(element);
         return driver1;
    }

    public static void closeDriver(){
        driver.quit();
    }



}
