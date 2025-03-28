package com.experion.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.experion.pages.CartPage;
import com.experion.pages.CheckOutPage;
import com.experion.pages.HomePage;
import com.experion.pages.LoginPage;
import com.experion.utils.ExtentReportManager;
import com.experion.utils.ScreenshotUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

/*
 * Base test class containing common setup and teardown methods
 * for all test classes. Handles:
 * - Test report initialization
 * - Browser management
 * - Screenshot capture on failure
 * - ExtentReports integration
 */
public class BaseTest {
	protected WebDriver driver;
    protected LoginPage loginPage;
    protected HomePage homePage;
    protected CartPage cartPage;
    protected CheckOutPage checkoutPage;
    // WebDriver instance accessible to all test classes
    
    
    // ExtentTest instance for reporting
    protected ExtentTest test;
    
    /*
     * Initializes ExtentReports before any tests run in the suite
     */
    @BeforeSuite
    public void setupSuite() {
        ExtentReportManager.getInstance(); // Initialize report once per suite
    }

    /*
     * Flushes ExtentReports after all tests complete
     */
    @AfterSuite
    public void tearDownSuite() {
        ExtentReportManager.getInstance().flush(); // Generate final report
    }

     /*
     * Configures test environment before each test method
     * @param browser Browser parameter from testng.xml (defaults to chrome)
     * @param result TestNG ITestResult object for test information
     */
    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser, ITestResult result) {
        // Create ExtentTest entry for this test method
        test = ExtentReportManager.getInstance()
                .createTest(result.getMethod().getMethodName())
                .assignCategory(browser.toUpperCase()); // Tag test with browser name

        // Log browser information
        test.log(Status.INFO, "Starting test on " + browser.toUpperCase());

        // Initialize browser based on parameter
        if(browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            test.log(Status.INFO, "Chrome browser launched");
        } 
        else if(browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            test.log(Status.INFO, "Firefox browser launched");
        }

        // Maximize browser window
        driver.manage().window().maximize();
        test.log(Status.INFO, "Browser maximized");
    }

    /*
     * Cleans up test environment after each test method
     * @param result TestNG ITestResult object for test status information
     */
    @AfterMethod
    public void tearDown(ITestResult result) {
        // Log test status in ExtentReport
        if (result.getStatus() == ITestResult.FAILURE) {
            // Log failure with exception
            test.log(Status.FAIL, "Test FAILED: " + result.getThrowable());
            
            // Capture and attach screenshot on failure
            String screenshotPath = ScreenshotUtils.captureScreenshot(driver, result.getName());
            test.addScreenCaptureFromPath(screenshotPath);
        } 
        else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "Test PASSED");
        } 
        else {
            test.log(Status.SKIP, "Test SKIPPED");
        }

        // Quit driver if it exists
        if (driver != null) {
            driver.quit();
            test.log(Status.INFO, "Browser closed");
        }
    }
}