package com.logwire.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage {
    public CheckoutPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    @FindBy(id="first-name")
    public WebElement FirstNameElement;

    @FindBy(id="last-name")
    public WebElement LastNameElement;

    @FindBy(id="postal-code")
    public WebElement PostalCodeElement;

    @FindBy(id="continue")
    public WebElement ContinueButtonElement;

    @FindBy(css="div[data-test='subtotal-label']")
    public WebElement TotalPriceElement;

    @FindBy(name="finish")
    public WebElement finishButton;

    @FindBy(css="h2[data-test='complete-header']")
    public WebElement ThanksElement;

}
