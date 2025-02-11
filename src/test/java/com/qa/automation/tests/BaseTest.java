package com.qa.automation.tests;

import com.qa.automation.managers.DriverManager;
import com.qa.automation.managers.PageObjectManager;
import com.qa.automation.pages.LandingPage;
import com.qa.automation.utilities.DataReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class BaseTest {

    public WebDriver driver;
    public LandingPage landingPage;
    protected PageObjectManager pageObjectManager;

    public WebDriver initializeDriver() throws IOException {
        if (DriverManager.getDriver() == null) {
            Properties properties = new Properties();
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir") +
                    "//src//test//resources//config.properties");
            properties.load(fis);
            String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : properties.getProperty("browser");
            if (browserName == null) {
                throw new RuntimeException("Browser name is not specified in config.properties");
            }
            if (browserName.contains("chrome")) {
                ChromeOptions options = new ChromeOptions();
                WebDriverManager.chromedriver().setup();
                if (browserName.contains("headless")) {
                    options.addArguments("headless");
                }
                driver = new ChromeDriver(options);
                driver.manage().window().setSize(new Dimension(1440,900));
            } else if (browserName.equalsIgnoreCase("firefox")) {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            } else if (browserName.equalsIgnoreCase("edge")) {
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize();
            DriverManager.setDriver(driver); // Store in DriverManager
        }
        return DriverManager.getDriver();
    }

    @BeforeMethod(alwaysRun = true)
    public LandingPage launchApplication() throws IOException {
        driver = initializeDriver(); // Ensures driver is not null
        pageObjectManager = new PageObjectManager(driver);
        landingPage = pageObjectManager.getLandingPage();
        landingPage.openUrl();
        return landingPage;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (DriverManager.getDriver() != null) {
            DriverManager.getDriver().quit();
            DriverManager.removeDriver(); // Remove the instance from ThreadLocal
        }
    }

    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> data = DataReader.getJsonDataToMap(System.getProperty("user.dir") +
                "//src//test//resources//data//PlaceOrder.json");
        return new Object[][]{{data.get(0)}, {data.get(1)}};
    }
}
