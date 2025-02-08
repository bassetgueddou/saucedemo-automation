package com.logwire.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverManager {
    
    public WebDriver driver;

    public void setup(){

        if(driver ==null){
            String browser = System.getProperty("browser", "chrome").toLowerCase();
            switch (browser) {
                case "firefox":
                    driver = new FirefoxDriver();
                    break;
                case "chrome":
                default:
                    driver = new ChromeDriver();
                    break;
            }
            driver.get("https://www.saucedemo.com/");
        }
    }

    public WebDriver getDriver(){
        return driver;
    }

    public void tearDown(){
        if(driver!=null){
            driver.quit();
            driver=null;
        }
    }
}
