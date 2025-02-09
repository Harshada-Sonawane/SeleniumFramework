package com.automation.managers;

import com.automation.pages.*;
import org.openqa.selenium.WebDriver;

public class PageObjectManager {
    private final WebDriver driver;
    private LandingPage landingPage;
    private ProductsPage productsPage;
    private MyCartPage myCartPage;
    private CheckOutPage checkOutPage;
    private OrderHistoryPage orderHistoryPage;
    private OrdersPage ordersPage;

    public PageObjectManager(WebDriver driver) {
        this.driver = driver;
    }

    public LandingPage getLandingPage() {
        return (landingPage == null) ? landingPage = new LandingPage(driver) : landingPage;
    }

    public ProductsPage getProductsPage() {
        return (productsPage == null) ? productsPage = new ProductsPage(driver) : productsPage;
    }

    public MyCartPage getMyCartPage() {
        return (myCartPage == null) ? myCartPage = new MyCartPage(driver) : myCartPage;
    }

    public CheckOutPage getCheckOutPage() {
        return (checkOutPage == null) ? checkOutPage = new CheckOutPage(driver) : checkOutPage;
    }

    public OrderHistoryPage getOrderHistoryPage() {
        return (orderHistoryPage == null) ? orderHistoryPage = new OrderHistoryPage(driver) : orderHistoryPage;
    }

    public OrdersPage getOrdersPage() {
        return (ordersPage == null) ? ordersPage = new OrdersPage(driver) : ordersPage;
    }
}
