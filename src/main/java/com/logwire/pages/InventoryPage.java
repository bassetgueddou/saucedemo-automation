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

    public List<WebElement> getInventoryItems(){
        return this.InventoryItems;
    }


}
