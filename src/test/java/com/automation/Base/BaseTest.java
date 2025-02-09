package com.automation.Base;

import com.automation.managers.PageObjectManager;
import com.automation.pages.LandingPage;
import com.automation.utilities.DataReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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
        if (driver == null) {
            Properties properties = new Properties();
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir") +
                    "//src//main//java//resources//config.properties");
            properties.load(fis);
            String browserName = properties.getProperty("browser");
            if (browserName == null) {
                throw new RuntimeException("Browser name is not specified in config.properties");
            }
            if (browserName.equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            } else if (browserName.equalsIgnoreCase("firefox")) {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            } else if (browserName.equalsIgnoreCase("edge")) {
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize();
        }
        return driver;
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
        if (driver != null) {
            driver.quit(); //Closes all browser windows
            driver = null; //Ensures a fresh driver for the next test
        }
    }

    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> data = DataReader.getJsonDataToMap(System.getProperty("user.dir") +
                "//src//test//java//com//automation//data//PlaceOrder.json");
        return new Object[][]{{data.get(0)}, {data.get(1)}};
    }
}
