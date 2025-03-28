package com.experion.pages;
import java.time.Duration;
import java.util.NoSuchElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import io.netty.handler.timeout.TimeoutException;

/*
 * CheckOutPage class represents the checkout page of the application
 * Contains all the web elements and methods to interact with the checkout page
 */
public class CheckOutPage extends BasePage{

    // WebDriver instance and WebDriverWait for explicit waits
    public CheckOutPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    // WebElements using PageFactory pattern
    
    @FindBy(css ="#first-name")
    private WebElement firstNameTextbox; // Textbox for entering first name
    
    @FindBy(css ="#last-name")
    private WebElement lastNameTextbox; // Textbox for entering last name
    
    @FindBy(css = "#postal-code")
    private WebElement postalCodeTextbox; // Textbox for entering postal code
    
    @FindBy(css = "#cancel")
    private WebElement cancelButton; // Button to cancel checkout
    
    @FindBy(css = "#continue")
    private WebElement continueButton; // Button to continue checkout
    
    @FindBy(xpath = "//h3[@data-test='error']")
    private WebElement errorMessageInCheckout; // Element for error message display
    
    @FindBy(xpath = "//span[contains(text(),'Checkout: Overview')]")
    private WebElement checkOutOverview; // Header for checkout overview page
    
    @FindBy(xpath = "//button[@id='finish']")
    private WebElement finishButton; // Button to finish checkout
    
    @FindBy(xpath = "//h2[@class='complete-header']")
    private WebElement finalMessage; // Final confirmation message after checkout
    
    /*
     * Enters first name in the first name textbox
     * @param firstName - First name to be entered
     */
    public void enterTheFirstName(String firstName) {
        wait.until(ExpectedConditions.visibilityOf(firstNameTextbox));
        firstNameTextbox.sendKeys(firstName);
    }
    
    /*
     * Enters last name in the last name textbox
     * @param secondName - Last name to be entered
     */
    public void enterTheSecondName(String secondName) {
        wait.until(ExpectedConditions.visibilityOf(lastNameTextbox));
        lastNameTextbox.sendKeys(secondName);
    }
    
    /*
     * Enters postal code in the postal code textbox
     * @param postalCode - Postal code to be entered
     */
    public void enterThePostalCode(String postalCode) {
        wait.until(ExpectedConditions.visibilityOf(postalCodeTextbox));
        postalCodeTextbox.sendKeys(postalCode);
    }
    
    /*
     * Verifies the error message displayed on the checkout page
     * @param expectedMessage - Expected error message to verify
     */
    public void verifyErrorMessage(String expectedMessage) {
        wait.until(ExpectedConditions.visibilityOf(errorMessageInCheckout));
        String actualErrorMessage = errorMessageInCheckout.getText();
        Assert.assertEquals(actualErrorMessage,expectedMessage, "Error message in checkout page does not match!");
    }
    
    /*
     * Clicks on the cancel button to cancel the checkout process
     */
    public void clickOnTheCancelButton() {
        wait.until(ExpectedConditions.visibilityOf(cancelButton));
        cancelButton.click();
    }
    
    /*
     * Clicks on the continue button to proceed with checkout
     */
    public void clickOnTheContinueButton() {
        wait.until(ExpectedConditions.visibilityOf(continueButton));
        continueButton.click();
    }
    
    /*
     * Verifies that user is on the checkout overview page
     */
    public void verifyUserInCheckoutOverview() {
        wait.until(ExpectedConditions.visibilityOf(checkOutOverview));
        checkOutOverview.isDisplayed();
    }
    
    /*
     * Checks if the item total displayed matches the expected amount
     * @param expectedAmount - Expected item total amount
     * @return true if amount matches, false otherwise
     */
    public boolean isItemTotalCorrect(String expectedAmount) {
        String xpath = String.format("//div[normalize-space()='Item total: $%s']", expectedAmount);
        try {
            return driver.findElement(By.xpath(xpath)).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    
    /*
     * Verifies the total amount (without tax) matches the expected value
     * Provides detailed logging if verification fails
     * @param expectedAmount - Expected total amount
     * @return true if amount matches, false otherwise
     */
    public boolean verifyTheTotalAmountWithoutTax(String expectedAmount) {
        try {
            // First try exact match with parameterized XPath
            String exactXpath = String.format("//div[normalize-space()='Item total: $%s']", expectedAmount);
            new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(exactXpath)));
            System.out.println("Item total verified: $" + expectedAmount);
            return true;
            
        } catch (TimeoutException e) {
            // If exact match fails, find and compare actual value
            try {
                WebElement totalElement = driver.findElement(
                    By.xpath("//div[contains(., 'Item total: $')]"));
                String actualText = totalElement.getText().trim();
                String actualAmount = actualText.replace("Item total: $", "").trim();
                
                System.out.println("ITEM TOTAL MISMATCH");
                System.out.println("Expected: $" + expectedAmount);
                System.out.println("Actual:   $" + actualAmount);
                return false;
                
            } catch (NoSuchElementException ex) {
                System.out.println("ITEM TOTAL NOT FOUND");
                System.out.println("Expected: $" + expectedAmount);
                return false;
            }
        }
    }
    
    /*
     * Clicks on the finish button to complete the checkout process
     */
    public void clickOnTheFinishButton() {
        wait.until(ExpectedConditions.visibilityOf(finishButton));
        finishButton.click();
    }
    
    /*
     * Validates the final confirmation message after successful checkout
     * @param expectedText - Expected confirmation message
     */
    public void validateFinalMessage(String expectedText) {
        String actualText = wait.until(ExpectedConditions.visibilityOf(finalMessage))
            .getText()
            .trim();
        
        Assert.assertEquals(actualText, expectedText, 
            String.format("Final message validation failed%nExpected: '%s'%nActual:   '%s'", 
                          expectedText, actualText));
    }
}