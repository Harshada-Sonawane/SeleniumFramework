package com.qa.automation.tests;

import com.qa.automation.pages.*;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

public class PlaceOrderTest extends BaseTest {

    String productName = "BANARSI SAREE";

    @Test(dataProvider = "getData",groups = {"Purchase"})
    @Description("Verify place order functionality")
    public void testPlaceOrder(HashMap<String,String> input) throws InterruptedException {
        landingPage.login(input.get("email"), input.get("password"));
        ProductsPage productsPage = pageObjectManager.getProductsPage();
        productsPage.clickAddToCart(input.get("productName"));
        productsPage.clickCartHeader();
        MyCartPage myCartPage = pageObjectManager.getMyCartPage();
        Assert.assertTrue(myCartPage.verifyProductAddedToMyCart(input.get("productName")));
        myCartPage.clickCheckoutButton();
        CheckOutPage checkOutPage = pageObjectManager.getCheckOutPage();
        checkOutPage.selectCountry("india");
        checkOutPage.clickPlaceOrderButton();
        OrderHistoryPage orderHistoryPage = pageObjectManager.getOrderHistoryPage();
        Assert.assertTrue(orderHistoryPage.verifyOrderPlaced("THANKYOU FOR THE ORDER."));
    }

    @Test(dependsOnMethods = "testPlaceOrder",enabled = false)
    @Description("Verify history of placed order")
    public void testOrderHistory() {
        landingPage.login("anshika@gmail.com", "Iamking@000");
        OrdersPage ordersPage = pageObjectManager.getOrdersPage();
        ordersPage.clickOrdersHeader();
        Assert.assertTrue(ordersPage.verifyOrderDisplay(productName));
    }
}
