package com.logwire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.logwire.pages.CartPage;
import com.logwire.pages.InventoryPage;
import com.logwire.pages.LoginPage;
import com.logwire.utils.WebDriverManager;

public class CartTest {
    WebDriver driver;
    LoginPage loginPage;
    WebDriverManager Webdrivermanager;
    InventoryPage Inventorypage;
    CartPage Cartpage;

    @BeforeEach
    public void setup(){
        Webdrivermanager = new WebDriverManager();
        Webdrivermanager.setup();
        driver = Webdrivermanager.getDriver();
        loginPage= new LoginPage(driver);
        Inventorypage = new InventoryPage(driver);
        Cartpage = new CartPage(driver);
    }

    @AfterEach
    public void tearDown(){
        Webdrivermanager.tearDown();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/usersWithNoProblemButtonCart.csv")
    @Tag("Test Add Cart Button with no problem user button")
    @Tag("Test Add product to cart")
    public void AddCartButtonTest(String username){
        loginPage.login(username);
        assertTrue(driver.getCurrentUrl().contains("/inventory"));

        List<WebElement>ItemsButton = Inventorypage.ItemButton;
        int ButtonCartListSizeBeforeclick= ItemsButton.size();

        for (WebElement itemButton: ItemsButton){
            assertEquals("Add to cart", itemButton.getText());
            itemButton.click();
        }

        String BadgeText = Cartpage.Badge.getText();
        int BadgeNumber = BadgeText.isEmpty() ?  0: Integer.parseInt(BadgeText);

        //Vérifier si tout les produit ont étaient rajouté au panier après les clicks
        assertEquals(BadgeNumber, ButtonCartListSizeBeforeclick);
        List<WebElement>RemoveButtons= Cartpage.RemoveButtons;

        // Vérifier que tt les buttons sont devenu remove après le click et l'ajout
        assertEquals(ButtonCartListSizeBeforeclick, RemoveButtons.size());
   

        for(WebElement button: RemoveButtons){
            assertEquals("Remove", button.getText());
            button.click();
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/usersWithNoProblemButtonCart.csv")
    @Tag("Tester l'ajout dans la page cart")
    @Tag("tester les button remove page cart")
    @Tag("tester les buttons continue shipping et checkout")
    public void TestCartAndDisplayProduct(String username){
        loginPage.login(username);
        assertTrue(driver.getCurrentUrl().contains("/inventory"));

        List<WebElement>ItemsButton = Inventorypage.ItemButton;
        int ButtonCartListSizeBeforeclick= ItemsButton.size();

        for (WebElement itemButton: ItemsButton){
            assertEquals("Add to cart", itemButton.getText());
            itemButton.click();
        }

        Cartpage.Badge.click();
        assertTrue(driver.getCurrentUrl().contains("/cart"));

        int initialCartItemListSize = Cartpage.CartItems.size();
        assertEquals(initialCartItemListSize, ButtonCartListSizeBeforeclick);
        
        Cartpage.RemoveButtonCart.click();
        assertTrue(Cartpage.CartItems.size()==initialCartItemListSize-1);

        Cartpage.ContinueButton.click();
        assertTrue(driver.getCurrentUrl().contains("/inventory"));

        Cartpage.Badge.click();
        assertTrue(driver.getCurrentUrl().contains("/cart"));

        Cartpage.CheckoutButton.click();
        assertTrue(driver.getCurrentUrl().contains("/checkout-step-one"));

    }


}
