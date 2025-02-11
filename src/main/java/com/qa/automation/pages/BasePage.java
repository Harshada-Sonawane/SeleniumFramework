package com.qa.automation.pages;

import com.qa.automation.utilities.WaitUtils;
import org.openqa.selenium.WebDriver;

public class BasePage {
    protected WebDriver driver;
    protected WaitUtils waitUtils;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }
}

