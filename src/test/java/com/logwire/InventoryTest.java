package com.logwire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.logwire.pages.InventoryPage;
import com.logwire.pages.LoginPage;
import com.logwire.utils.WebDriverManager;

public class InventoryTest {
    LoginPage loginPage;
    WebDriver driver;
    WebDriverManager Webdrivermanager;
    InventoryPage Inventorypage;

    @BeforeEach
    public void setup(){
        Webdrivermanager = new WebDriverManager();
        Webdrivermanager.setup();
        driver = Webdrivermanager.getDriver();
        loginPage = new LoginPage(driver);
        Inventorypage = new InventoryPage(driver);
    }

    @AfterEach
    public void tearDown(){
        Webdrivermanager.tearDown();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/users.csv", numLinesToSkip = 1)
    @Tag("Get Inventory TEST")
    public void GetInventoryList(String username){
        loginPage.login(username);
        assertTrue(driver.getCurrentUrl().contains("/inventory"));

        assertTrue(Inventorypage.getInventoryItems().size()>=6);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/users.csv", numLinesToSkip= 1)
    @Tag("Test Inventory Product Display")
    public void testProductDisplayAndAddToCart(String username){
        loginPage.login(username);
        assertTrue(driver.getCurrentUrl().contains("/inventory"));

        List<WebElement> items = Inventorypage.getInventoryItems();
        assertEquals(items.size(), Inventorypage.ItemTitles.size());
        assertEquals(items.size(), Inventorypage.ItemImgs.size());
        assertEquals(items.size(), Inventorypage.ItemPrices.size());
        assertEquals(items.size(), Inventorypage.ItemDescs.size());
        assertEquals(items.size(), Inventorypage.ItemButton.size());

    }

    // @Test
    // @Tag("SortingFunctionality")
    // public void testInventoryProductSorting(){

    // }

}
