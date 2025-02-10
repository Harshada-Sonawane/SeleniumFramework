package com.automation.pages;

import com.automation.utilities.WaitUtils;
import org.openqa.selenium.WebDriver;

public class BasePage {
    protected WebDriver driver;
    protected WaitUtils waitUtils;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }
}

