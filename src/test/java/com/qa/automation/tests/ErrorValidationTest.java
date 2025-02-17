package com.qa.automation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class ErrorValidationTest extends BaseTest {

    @Test(groups = "Error")
    public void testInvalidLogin() throws IOException {
        landingPage.login("test@test.com", "test");
        Assert.assertEquals(landingPage.getErrorMsgInvalidLogin(), "Incorrect email or password.");
    }
}
