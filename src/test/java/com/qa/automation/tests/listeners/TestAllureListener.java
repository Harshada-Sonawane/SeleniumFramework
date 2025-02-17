package com.qa.automation.tests.listeners;

import com.qa.automation.tests.BaseTest;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestAllureListener implements ITestListener {

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    // Capture screenshot for Allure report
    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshotPNG(WebDriver driver) {
        try {
            if (driver instanceof TakesScreenshot) {
                return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            }
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
        return new byte[0];
    }

    // Save log message in Allure report
    @Attachment(value = "{0}", type = "text/plain")
    public static String saveTextLog(String message) {
        return message;
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        System.out.println("🚀 Test execution started: " + iTestContext.getName());
        //iTestContext.setAttribute("WebDriver", BaseTest.getDriver());
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        System.out.println("🎯 Test started: " + getTestMethodName(iTestResult));

        WebDriver driver = BaseTest.getDriver(); // Retrieve driver only when test starts

        if (driver == null) {
            System.out.println("❌ WebDriver is null in TestAllureListener!");
        } else {
            iTestResult.setAttribute("WebDriver", driver);
            System.out.println("✅ WebDriver is set for: " + getTestMethodName(iTestResult));
        }
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        System.out.println("❌ Test failed: " + getTestMethodName(iTestResult));
        WebDriver driver = BaseTest.getDriver();  // ✅ Get the driver correctly

        if (driver != null) {
            System.out.println("📸 Capturing screenshot for failed test: " + getTestMethodName(iTestResult));
            saveScreenshotPNG(driver);
        } else {
            System.err.println("⚠️ Driver is null, cannot capture screenshot!");
        }

        saveTextLog(getTestMethodName(iTestResult) + " failed and screenshot taken!");
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("⏭️ Test skipped: " + getTestMethodName(iTestResult));
        saveTextLog(getTestMethodName(iTestResult) + " was skipped.");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        System.out.println("✅ Test passed: " + getTestMethodName(iTestResult));
        saveTextLog(getTestMethodName(iTestResult) + " passed successfully!");
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        System.out.println("🏁 Test execution finished: " + iTestContext.getName());
    }
}
