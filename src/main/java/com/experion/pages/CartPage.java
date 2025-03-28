package com.experion.pages;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

/*
 * CartPage class represents the shopping cart page of the application.
 * Contains methods to interact with cart items and perform cart-related operations.
 */
public class CartPage extends BasePage {

    /*
     * Constructor to initialize the CartPage with WebDriver instance
     * @param driver - WebDriver instance
     */
    public CartPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    // Web elements using PageFactory pattern
    
    @FindBy(css = ".shopping_cart_link")
    private WebElement shoppingCart; // Shopping cart button
    
    @FindBy(css = "#checkout")
    private WebElement checkOutButton; // Checkout button
    
    @FindBy(css = ".cart_item")
    private List<WebElement> cartItems; // List of all cart items
    
    @FindBy(css = ".inventory_item_name")
    private List<WebElement> productNamesInCart; // List of product names in cart
    
    @FindBy(css = ".inventory_item_desc")
    private List<WebElement> productDescsInCart; // List of product descriptions in cart
    
    @FindBy(css = ".inventory_item_price")
    private List<WebElement> productPricesInCart; // List of product prices in cart

    /*
     * Clicks on the shopping cart button/icon to view cart contents and checking visibility of cart button
     */
    public void clickOnTheCartButton() {
    	wait.until(ExpectedConditions.visibilityOf(shoppingCart));
        shoppingCart.click();
    }
    
    /*
     * Verifies that user is on the cart page by checking visibility of checkout button
     */
    public void verifyTheUserInCartPage() {
        wait.until(ExpectedConditions.visibilityOf(checkOutButton));
        isDisplayed(checkOutButton);
    }
    
    /*
     * Verifies the products in cart match the expected products list
     * Uses SoftAssert to collect all assertions before failing
     * @param expectedProducts - List of expected products with their details
     * Each product is represented as a list of [name, description, price]
     */
    public void verifyProductsInCart(List<List<String>> expectedProducts) {
        SoftAssert softAssert = new SoftAssert();
        
        // First verify the number of products matches
        softAssert.assertEquals(productNamesInCart.size(), expectedProducts.size(), 
            "Number of products in cart doesn't match expected");
        
        // Compare each product's details with expected values
        for (int i = 0; i < expectedProducts.size(); i++) {
            List<String> expected = expectedProducts.get(i);
            String productInfo = "Product " + (i+1) + ": ";
            
            // Verify product name
            softAssert.assertEquals(productNamesInCart.get(i).getText().trim(), expected.get(0),
                productInfo + "Name mismatch");
                
            // Verify product description    
            softAssert.assertEquals(productDescsInCart.get(i).getText().trim(), expected.get(1),
                productInfo + "Description mismatch");
                
            // Verify product price
            softAssert.assertEquals(productPricesInCart.get(i).getText().trim(), expected.get(2),
                productInfo + "Price mismatch");
        }
        
        // Assert all collected verifications
        softAssert.assertAll();
    }
    
    /*
     * Clicks on the checkout button to proceed to checkout
     */
    public void clickOnTheCheckOutButton() {
    	wait.until(ExpectedConditions.visibilityOf(checkOutButton));
        checkOutButton.click();
    }
}