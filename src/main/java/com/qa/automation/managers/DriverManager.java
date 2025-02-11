package com.qa.automation.managers;

import org.openqa.selenium.WebDriver;

public class DriverManager {
    private static ThreadLocal<WebDriver> tdriver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return tdriver.get();
    }

    public static void setDriver(WebDriver driver) {
        tdriver.set(driver);
    }

    public static void removeDriver() {
        tdriver.remove();
    }
}
