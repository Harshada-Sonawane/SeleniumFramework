package com.qa.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class OrdersPage extends BasePage {

    WebDriver driver;

    public OrdersPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "[routerlink*='myorders']")
    WebElement ordersHeader;

    @FindBy(css = "tr td:nth-child(3)")
    List<WebElement> productNames;

    public void clickOrdersHeader() {
        ordersHeader.click();
    }

    public boolean verifyOrderDisplay(String productName) {
        return productNames.stream().anyMatch(product ->
                product.getText().equalsIgnoreCase(productName));

    }
}
