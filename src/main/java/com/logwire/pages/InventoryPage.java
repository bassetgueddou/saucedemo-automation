package com.logwire.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class InventoryPage {

    public InventoryPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".inventory_item")
    List<WebElement> InventoryItems;

    @FindBy(className = "inventory_item_name")
    public List<WebElement> ItemTitles;

    @FindBy(css = ".inventory_item_img img")
    public List<WebElement> ItemImgs;

    @FindBy(className = "inventory_item_price")
    public List<WebElement> ItemPrices;

    @FindBy(css = "div[data-test='inventory-item-desc']")
    public List<WebElement> ItemDescs;

    @FindBy(css = "button.btn.btn_primary.btn_small.btn_inventory")
    public List<WebElement> ItemButton;

    public List<WebElement> getInventoryItems(){
        return this.InventoryItems;
    }


}
