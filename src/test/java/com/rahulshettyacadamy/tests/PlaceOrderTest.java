package com.rahulshettyacadamy.tests;

import com.rahulshettyacadamy.Base.BaseTest;
import com.rahulshettyacadamy.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class PlaceOrderTest extends BaseTest {

    @Test()
    public void testPlaceOrder() throws IOException {
        String productName = "QWERTY";
        LandingPage landingPage= launchApplication();
        landingPage.login("anshika@gmail.com", "Iamking@000");
        ProductsPage productsPage = pageObjectManager.getProductsPage();
        productsPage.clickAddToCart(productName);
        productsPage.clickCartButton();
        MyCartPage myCartPage = pageObjectManager.getMyCartPage();
        Assert.assertTrue(myCartPage.verifyProductAddedToMyCart(productName));
        myCartPage.clickCheckoutButton();
        CheckOutPage checkOutPage = pageObjectManager.getCheckOutPage();
        checkOutPage.selectCountry("india");
        checkOutPage.clickPlaceOrderButton();
        OrderHistoryPage orderHistoryPage = pageObjectManager.getOrderHistoryPage();
        Assert.assertTrue(orderHistoryPage.verifyOrderPlaced("THANKYOU FOR THE ORDER."));
    }
}
