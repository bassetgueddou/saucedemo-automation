package com.logwire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.logwire.pages.LoginPage;

public class LoginTest {
    private WebDriver driver;
    String password ="secret_sauce";
    LoginPage loginPage;

    @BeforeEach
    public void setup(){
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPage(driver);

    }

    @AfterEach
    public void tearDown(){
        if(driver!=null){
            driver.quit();
            driver = null;
        }
    }

    @ParameterizedTest
    @Tag("LoginTest")
    @CsvFileSource(resources = "/users.csv", numLinesToSkip = 1)
    public void testLogin(String username) {

        loginPage.login(username);

        switch (username) {
            case "locked_out_user":
                assertEquals("Epic sadface: Sorry, this user has been locked out.", loginPage.getErrorMessager());
                break;
                
            default:
                assertTrue(driver.getCurrentUrl().contains("/inventory.html"));
                break;

        }
    }
}