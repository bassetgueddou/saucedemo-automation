package com.logwire.pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class LoginPage {

    private WebDriver driver;
    String password = "secret_sauce";
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "user-name")
    private WebElement usernaElement;

    @FindBy(id= "password")
    private WebElement passwordElement;

    @FindBy(id = "login-button")
    private WebElement loginbuttonElement;

    @FindBy(css = "h3[data-test='error']")
    private WebElement ErrorMessageElement;

    public void login(String username){
        System.out.println("heeeeere "+username+password);
        usernaElement.sendKeys(username);
        passwordElement.sendKeys(password);
        loginbuttonElement.click();
    }


    public String getErrorMessager(){
        return this.ErrorMessageElement.getText();
    }
}
