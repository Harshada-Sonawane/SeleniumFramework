package com.rahulshettyacadamy.pages;

import com.rahulshettyacadamy.utilities.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MyCartPage extends WaitUtils {

    WebDriver driver;

    public MyCartPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".cartSection h3")
    List<WebElement> cartProducts;

    @FindBy(css = ".totalRow button")
    WebElement checkoutButton;

    public List<WebElement> getCartProductsList() {
        return cartProducts;
    }

    public boolean verifyProductAddedToMyCart(String productName) {
        return getCartProductsList().stream().anyMatch(cartProduct ->
                cartProduct.getText().equalsIgnoreCase(productName));

    }

    public void clickCheckoutButton() {
        checkoutButton.click();
    }


}
