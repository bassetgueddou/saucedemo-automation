package com.logwire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.logwire.pages.CartPage;
import com.logwire.pages.CheckoutPage;
import com.logwire.pages.InventoryPage;
import com.logwire.pages.LoginPage;
import com.logwire.utils.WebDriverManager;


public class CheckoutTest {
    
    WebDriver driver;
    LoginPage loginPage;
    WebDriverManager Webdrivermanager;
    InventoryPage Inventorypage;
    CartPage Cartpage;
    CheckoutPage checkoutPage;

    @BeforeEach
    public void setup(){
        Webdrivermanager = new WebDriverManager();
        Webdrivermanager.setup();
        driver = Webdrivermanager.getDriver();
        loginPage= new LoginPage(driver);
        Inventorypage = new InventoryPage(driver);
        Cartpage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    @AfterEach
    public void tearDown(){
        Webdrivermanager.tearDown();
    }

    
    @ParameterizedTest
    @CsvFileSource(resources = "/usersWithoutproblemPriceCalcule.csv")
    @Tag("Tester le checkout")
    @Tag("Tester coherence du prix entre ce qui a été selectionné et le prix affiché")
    public void TestCheckoutAndOrder(String username){
        loginPage.login(username);
        assertTrue(driver.getCurrentUrl().contains("/inventory"));

        List<WebElement>ItemsButton = Inventorypage.ItemButton;
        List<WebElement> ItemPrices = Inventorypage.ItemPrices;

        float AllAtriclesPrice =0;
        for (WebElement price: ItemPrices){
            String priceText = price.getText().replace("$", "");
            float priceFloat = Float.parseFloat(priceText);
            AllAtriclesPrice+=priceFloat;
        }

        for (WebElement itemButton: ItemsButton){
            assertEquals("Add to cart", itemButton.getText());
            itemButton.click();
        }

        Cartpage.Badge.click();
        assertTrue(driver.getCurrentUrl().contains("/cart"));

        Cartpage.CheckoutButton.click();
        assertTrue(driver.getCurrentUrl().contains("/checkout-step-one"));

        checkoutPage.FirstNameElement.sendKeys("madara");
        checkoutPage.LastNameElement.sendKeys("Uchiwa");
        checkoutPage.PostalCodeElement.sendKeys("93100");
        checkoutPage.ContinueButtonElement.click();
        assertTrue(driver.getCurrentUrl().contains("/checkout-step-two"));

        String finalpriceString = checkoutPage.TotalPriceElement.getText();
        float finalprice = Float.parseFloat(finalpriceString.replaceAll("[^0-9.]", ""));

        // s'assurer que y'a pas d'anomalie dans le prix
        assertEquals(AllAtriclesPrice, finalprice);

        checkoutPage.finishButton.click();

        assertEquals("Thank you for your order!", checkoutPage.ThanksElement.getText());
    }

}
