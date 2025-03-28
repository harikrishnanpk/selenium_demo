package com.experion.pages;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/*
 * LoginPage class represents the login page of the application.
 * Contains all web elements and methods to interact with the login functionality.
 */
public class LoginPage extends BasePage {
    
    // Web elements using PageFactory pattern
    
    @FindBy(id = "user-name")
    private WebElement usernameField; // Input field for username
    
    @FindBy(id = "password")
    private WebElement passwordField; // Input field for password
    
    @FindBy(id = "login-button")
    private WebElement loginButton; // Login submission button
    
    @FindBy(xpath = "//h3[@data-test='error']")
    private WebElement errorMessage; // Error message element displayed on login failure
    
    /*
     * Constructor to initialize the LoginPage with WebDriver instance
     * @param driver - WebDriver instance
     */
    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /*
     * Enters username into the username field after waiting for it to be visible
     * @param username - The username to be entered
     */
    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOf(usernameField));
        type(usernameField, username);
    }

    /*
     * Enters password into the password field after waiting for it to be visible
     * @param password - The password to be entered
     */
    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordField));
        type(passwordField, password);
    }

    /*
     * Clicks the login button after waiting for it to be visible
     * Submits the login form
     */
    public void clickLogin() {
        wait.until(ExpectedConditions.visibilityOf(loginButton));
        click(loginButton);
    }
    
    /*
     * Verifies the error message displayed on failed login attempt
     * @param expectedMessage - The expected error message text to verify against
     * @throws AssertionError if the actual error message doesn't match expected
     */
    public void verifyErrorMessage(String expectedMessage) {
        // Wait for error message to be visible
        wait.until(ExpectedConditions.visibilityOf(errorMessage));
        
        // Get the actual error message text
        String actualErrorMessage = errorMessage.getText();
        
        // Assert that the actual message matches the expected message
        Assert.assertEquals(actualErrorMessage, expectedMessage, 
            "Error message does not match!");
    }
}