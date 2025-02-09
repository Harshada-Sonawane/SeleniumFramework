package com.automation.pages;

import com.automation.utilities.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrderHistoryPage extends WaitUtils {

    WebDriver driver;

    public OrderHistoryPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".hero-primary")
    WebElement countryPlaceholder;

    public boolean verifyOrderPlaced(String successMsg) {
        return countryPlaceholder.getText().equals(successMsg);
    }
}
