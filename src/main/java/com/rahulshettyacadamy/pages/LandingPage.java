package com.rahulshettyacadamy.pages;

import com.rahulshettyacadamy.utilities.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage extends WaitUtils {

    WebDriver driver;

    public LandingPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "userEmail")
    WebElement userName;

    @FindBy(id = "userPassword")
    WebElement userPassword;

    @FindBy(id = "login")
    WebElement loginBtn;

    @FindBy(css = "#toast-container")
    WebElement toasterMsg;

    public void login(String username, String password) {
        userName.sendKeys(username);
        userPassword.sendKeys(password);
        loginBtn.click();
    }

    public void openUrl() {
        driver.get("https://rahulshettyacademy.com/client");
    }

    public String getErrorMsgInvalidLogin() {
        return toasterMsg.getText().trim();
    }

}
