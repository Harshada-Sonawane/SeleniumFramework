package com.qa.automation.tests;

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
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class BaseTest {

    public static WebDriver driver;
    public LandingPage landingPage;
    protected PageObjectManager pageObjectManager;
    private static boolean useThreadLocal = false;  // Set to true for parallel execution
    public static ThreadLocal<WebDriver> tdriver = new ThreadLocal<>();
    private static Properties properties;


    static {
        try {
            properties = new Properties();
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir") +
                    "/src/test/resources/config.properties");
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public WebDriver initializeDriver() {
        useThreadLocal = Boolean.parseBoolean(properties.getProperty("parallelExecution", "false"));
        System.out.println("*****parallelExecution is set to*****: " + useThreadLocal);
        if (useThreadLocal) {
            if (tdriver.get() == null) {
                tdriver.set(createWebDriver());
            }
            return tdriver.get();
        } else {
            if (driver == null) {
                driver = createWebDriver();
            }
            return driver;
        }
    }


    public WebDriver createWebDriver() {
        String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : properties.getProperty("browser");
        if (browserName == null) {
            throw new RuntimeException("Browser name is not specified in config.properties");
        }
        WebDriver localDriver = null;
        if (browserName.contains("chrome")) {
            ChromeOptions options = new ChromeOptions();
            WebDriverManager.chromedriver().setup();
            if (browserName.contains("headless")) {
                options.addArguments("headless");
            }
            localDriver = new ChromeDriver(options);
            localDriver.manage().window().setSize(new Dimension(1440, 900));
        } else if (browserName.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            localDriver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            localDriver = new EdgeDriver();
        }

        localDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        localDriver.manage().window().maximize();
        return localDriver;
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
        if (useThreadLocal) {
            if (tdriver.get() != null) {
                tdriver.get().quit();
                tdriver.remove();
            }
        } else {
            if (driver != null) {
                driver.quit();
                driver = null;
            }
        }
    }

    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> data = DataReader.getJsonDataToMap(System.getProperty("user.dir") +
                "//src//test//resources//data//PlaceOrder.json");
        return new Object[][]{{data.get(0)}, {data.get(1)}};
    }

    public static void enableThreadLocal() {
        useThreadLocal = true;
    }

    public static void disableThreadLocal() {
        useThreadLocal = false;
    }

    public static WebDriver getDriver() {
        return useThreadLocal ? tdriver.get() : driver;
    }

    @BeforeSuite(alwaysRun = true)
    public void setupThreadLocal() {
        // Read parallelExecution property from config file
        String parallelExecution = properties.getProperty("parallelExecution", "false");

        if ("true".equalsIgnoreCase(parallelExecution)) {
            enableThreadLocal();
            System.out.println("✅ Parallel execution enabled via config.properties!");
        } else {
            disableThreadLocal();
            System.out.println("❌ Parallel execution disabled via config.properties!");
        }
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownThreadLocal() {
        disableThreadLocal();
        System.out.println("🏁 ThreadLocal execution cleaned up after test suite!");
    }

}
