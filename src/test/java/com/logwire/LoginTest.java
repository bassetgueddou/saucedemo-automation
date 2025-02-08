package com.logwire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;

import com.logwire.pages.LoginPage;
import com.logwire.utils.WebDriverManager;

public class LoginTest {
    LoginPage loginPage;
    WebDriver driver;
    WebDriverManager Webdrivermanager;

    @BeforeEach
    public void setup(){
        Webdrivermanager = new WebDriverManager();
        Webdrivermanager.setup();
        driver = Webdrivermanager.getDriver();
        loginPage = new LoginPage(driver);

    }

    @AfterEach
    public void tearDown(){
        Webdrivermanager.tearDown();
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