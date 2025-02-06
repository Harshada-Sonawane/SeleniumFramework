package com.rahulshettyacadamy.tests;

import com.rahulshettyacadamy.Base.BaseTest;
import com.rahulshettyacadamy.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class PlaceOrderTest extends BaseTest {

    String productName = "QWERTY";

    @Test()
    public void testPlaceOrder() throws IOException, InterruptedException {
        LandingPage landingPage = launchApplication();
        landingPage.login("anshika@gmail.com", "Iamking@000");
        ProductsPage productsPage = pageObjectManager.getProductsPage();
        productsPage.clickAddToCart(productName);
        productsPage.clickCartHeader();
        MyCartPage myCartPage = pageObjectManager.getMyCartPage();
        Assert.assertTrue(myCartPage.verifyProductAddedToMyCart(productName));
        myCartPage.clickCheckoutButton();
        CheckOutPage checkOutPage = pageObjectManager.getCheckOutPage();
        checkOutPage.selectCountry("india");
        checkOutPage.clickPlaceOrderButton();
        OrderHistoryPage orderHistoryPage = pageObjectManager.getOrderHistoryPage();
        Assert.assertTrue(orderHistoryPage.verifyOrderPlaced("THANKYOU FOR THE ORDER."));
    }

    @Test(dependsOnMethods = "testPlaceOrder")
    public void testOrderHistory() throws IOException {
        LandingPage landingPage = launchApplication();
        landingPage.login("anshika@gmail.com", "Iamking@000");
        OrdersPage ordersPage = pageObjectManager.getOrdersPage();
        ordersPage.clickOrdersHeader();
        Assert.assertTrue(ordersPage.verifyOrderDisplay(productName));
    }
}
