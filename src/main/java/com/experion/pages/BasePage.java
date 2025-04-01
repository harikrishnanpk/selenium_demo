package com.experion.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/*
 * Abstract base class providing common page object functionality and utilities.
 * Implements core Selenium operations with built-in wait handling to ensure
 * reliable test execution. All page classes should extend this base class.
 */
public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    /*
     * Initializes the base page with driver instance and sets up:
     * - Explicit wait mechanism (10 second timeout)
     * - PageFactory element initialization
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    /*
     * Clicks an element after ensuring it's clickable 
     * @param element Target WebElement to click
     */
    protected void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    /*
     * Enters text into a field after ensuring it's visible
     * @param element Target input WebElement
     * @param text Text to input
     */
    protected void type(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element)).sendKeys(text);
    }

    /*
     * Retrieves visible text from an element
     * @param element Target WebElement
     * @return Visible text content
     */
    protected String getText(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getText();
    }

    /*
     * Verifies element visibility
     * @param element Target WebElement
     * @return true if element is visible, false otherwise
     */
    protected boolean isDisplayed(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();     
    }
    
    /*
     * Selects dropdown option by visible text
     * @param element Dropdown WebElement (must be <select> tag)
     * @param option Visible text of option to select
     */
    public void selectByVisibleText(WebElement element, String option) {
        Select dropdown = new Select(element);
        dropdown.selectByVisibleText(option);
    }
}