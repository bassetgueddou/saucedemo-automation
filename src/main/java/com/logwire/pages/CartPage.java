package com.logwire.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage {

    public CartPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "button.btn.btn_secondary.btn_small.btn_inventory")
    public List<WebElement> RemoveButtons;


    @FindBy(css = "span[data-test='shopping-cart-badge']")
    public WebElement Badge;
}
