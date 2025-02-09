package com.automation.tests;

import com.automation.Base.BaseTest;
import com.automation.pages.LandingPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class ErrorValidationTest extends BaseTest {

    @Test()
    public void testInvalidLogin() throws IOException {
        LandingPage landingPage = launchApplication();
        landingPage.login("test@test.com", "test");
        Assert.assertEquals(landingPage.getErrorMsgInvalidLogin(), "Incorrect email or password.");
    }
}
