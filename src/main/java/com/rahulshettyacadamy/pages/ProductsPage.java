package com.rahulshettyacadamy.pages;

import com.rahulshettyacadamy.utilities.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductsPage extends WaitUtils {

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
    WebElement cartButton;

    By product = By.cssSelector(".mb-3");
    By addToCartBtn = By.cssSelector(".card-body button:last-of-type");
    By toasterMsg = By.cssSelector("#toast-container");

    public List<WebElement> getProductsList() {
        waitForVisibilityOfElementLocated(product);
        return products;
    }

    public WebElement addProductToCart(String productName) {
        return getProductsList().stream().filter(product ->
                product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
    }

    public void clickAddToCart(String productName) {
        WebElement prodToAdd = addProductToCart(productName);
        prodToAdd.findElement(addToCartBtn).click();
        waitForInvisibilityOfElement(spinner);
        waitForVisibilityOfElementLocated(toasterMsg);
    }

    public void clickCartButton() {
        cartButton.click();
    }

}
