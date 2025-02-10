package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductsPage extends BasePage {

    WebDriver driver;

    public ProductsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".mb-3")
    List<WebElement> products;

    @FindBy(css = ".ng-animating")
    WebElement spinner;

    @FindBy(css = "[routerlink*='cart']")
    WebElement cartHeader;

    By product = By.cssSelector(".mb-3");
    By addToCartBtn = By.cssSelector(".card-body button:last-of-type");
    By toasterMsg = By.cssSelector("#toast-container");

    public List<WebElement> getProductsList() {
        waitUtils.waitForVisibilityOfElementLocated(product);
        return products;
    }

    public WebElement addProductToCart(String productName) {
        return getProductsList().stream().filter(product ->
                product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
    }

    public void clickAddToCart(String productName) throws InterruptedException {
        try {
            WebElement prodToAdd = addProductToCart(productName);
            prodToAdd.findElement(addToCartBtn).click();
            waitUtils.waitForInvisibilityOfElement(spinner);
            waitUtils.waitForVisibilityOfElementLocated(toasterMsg);
        } catch (TimeoutException te) {
            te.printStackTrace();
        }
    }

    public void clickCartHeader() {
        cartHeader.click();
    }

}
