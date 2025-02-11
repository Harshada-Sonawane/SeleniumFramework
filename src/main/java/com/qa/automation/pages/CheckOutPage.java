package com.qa.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckOutPage extends BasePage {

    WebDriver driver;

    public CheckOutPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "[placeholder='Select Country']")
    WebElement countryPlaceholder;

    @FindBy(xpath = "(//button[contains(@class,'ta-item')])[2]")
    WebElement countryOption;

    @FindBy(css = ".action__submit")
    WebElement placeOrderButton;


    By searchResult = By.cssSelector(".ta-results");

    public void selectCountry(String country) {
        Actions a = new Actions(driver);
        a.sendKeys(countryPlaceholder, country).build().perform();
        waitUtils.waitForVisibilityOfElementLocated(searchResult);
        countryOption.click();
    }

    public void clickPlaceOrderButton() {
        placeOrderButton.click();
    }

}
