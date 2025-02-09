package com.logwire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import org.openqa.selenium.support.ui.Select;
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

    @ParameterizedTest
    @CsvFileSource(resources = "/users.csv", numLinesToSkip = 1)
    @Tag("SortingFunctionality")
    @Tag("test Tri AZ")
    public void testInventoryProductSortingAZ(String username){
        loginPage.login(username);
        assertTrue(driver.getCurrentUrl().contains("/inventory"));

        Select select = new Select(Inventorypage.SelectElement);

        select.selectByValue("az");

        List<WebElement> ItemsElementAZ = Inventorypage.ItemNames;
        List<String> ItemsTitleAZ = new ArrayList<>();

        for (WebElement item: ItemsElementAZ){
            ItemsTitleAZ.add(item.getText());
        }
        
        // copier la liste et la trier afin de comparer après
        List<String>TriedItemsTitle= new ArrayList<>(ItemsTitleAZ);
        Collections.sort(TriedItemsTitle);

        assertEquals(ItemsTitleAZ, TriedItemsTitle);
    }
 

    @ParameterizedTest
    @CsvFileSource(resources = "/usersWithNoSelectTriProblem.csv")
    @Tag("SortingFunctionality")
    @Tag("test Tri ZA")
    public void testInventoryProductSortingZA(String username){
        loginPage.login(username);
        assertTrue(driver.getCurrentUrl().contains("/inventory"));

        Select select = new Select(Inventorypage.SelectElement);

        select.selectByValue("za");

        List<WebElement> ItemsElementAZ = Inventorypage.ItemNames;
        List<String> ItemsTitleZA = new ArrayList<>();

        for (WebElement item: ItemsElementAZ){
            ItemsTitleZA.add(item.getText());
        }
        
        // copier la liste et la trier puis l'inverser afin de comparer après
        List<String>TriedItemsTitle= new ArrayList<>(ItemsTitleZA);
        Collections.sort(TriedItemsTitle);
        Collections.reverse(TriedItemsTitle);

        assertEquals(ItemsTitleZA, TriedItemsTitle);
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/usersWithNoPriceSelectTriProblem.csv")
    @Tag("SortingFunctionality")
    @Tag("test Tri price low to high")
    public void testInventoryProductSortingPriceLH(String username){

        loginPage.login(username);
        assertTrue(driver.getCurrentUrl().contains("/inventory"));

        Select select = new Select(Inventorypage.SelectElement);

        select.selectByValue("lohi");

        List<WebElement> ItemsPrices = Inventorypage.ItemPrices;
        List<Float> ItemsPriceLH = new ArrayList<>();

        for (WebElement item: ItemsPrices){
           String priceText = item.getText().replace("$", "");
           float price = Float.parseFloat(priceText);

           ItemsPriceLH.add(price);

        }

        // copier la liste et la trier afin de comparer après
        List<Float>TriedItemsPRices= new ArrayList<>(ItemsPriceLH);
        Collections.sort(TriedItemsPRices);

        assertEquals(ItemsPriceLH, TriedItemsPRices);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/usersWithNoPriceSelectTriProblem.csv")
    @Tag("SortingFunctionality")
    @Tag("test Tri price high to low")
    public void testInventoryProductSortingPriceHL(String username){

        loginPage.login(username);
        assertTrue(driver.getCurrentUrl().contains("/inventory"));

        Select select = new Select(Inventorypage.SelectElement);

        select.selectByValue("hilo");

        List<WebElement> ItemsPrices = Inventorypage.ItemPrices;
        List<Float> ItemsPriceHL = new ArrayList<>();

        for (WebElement item: ItemsPrices){
           String priceText = item.getText().replace("$", "");
           float price = Float.parseFloat(priceText);

           ItemsPriceHL.add(price);

        }
        
        // copier la liste et la trier puis l'inverser afin de comparer après
        List<Float>TriedItemsPRices= new ArrayList<>(ItemsPriceHL);
        Collections.sort(TriedItemsPRices);
        Collections.reverse(TriedItemsPRices);

        assertEquals(ItemsPriceHL, TriedItemsPRices);
    }

    }