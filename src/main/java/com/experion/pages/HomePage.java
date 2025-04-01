package com.experion.pages;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/*
 * HomePage class represents the main product listing page of the application.
 * Contains methods to interact with products, sorting functionality, and cart operations.
 */
public class HomePage extends BasePage {
    
    // Web elements using PageFactory pattern
    
    @FindBy(xpath = "//div[@class='app_logo']")
    private WebElement appLogo_homepage; // Application logo on homepage
    
    @FindBy(xpath = "//select[@class='product_sort_container']")
    private WebElement homePage_Sort_Dropdown; // Product sorting dropdown
    
    @FindBy(css = ".inventory_item_name")
    private List<WebElement> productNames; // List of all product name elements
    
    @FindBy(css = ".inventory_item_price")
    private List<WebElement> productPrice; // List of all product price elements
    
    /*
     * Constructor to initialize the HomePage with WebDriver instance
     * @param driver - WebDriver instance
     */
    public HomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    /*
     * Gets the 'Add to Cart' button WebElement for a specific product
     * @param itemName - Name of the product to locate
     * @return WebElement of the 'Add to Cart' button for the specified product
     */
    public WebElement getAddToCartButtonForItem(String itemName) {
        String xpath = String.format("//div[@class='inventory_item'][.//div[contains(text(),'%s')]]//button", itemName);
        return driver.findElement(By.xpath(xpath));
    }
    
    /*
     * Verifies user is on the home page by checking visibility of app logo
     */
    public void verifyUserInHomePage() {
    	wait.until(ExpectedConditions.visibilityOf(appLogo_homepage));
        isDisplayed(appLogo_homepage);
    }
    
    /*
     * Clicks on the sorting dropdown to open it
     */
    public void clickOnTheSortDropdown() {
    	wait.until(ExpectedConditions.visibilityOf(homePage_Sort_Dropdown));
        click(homePage_Sort_Dropdown);
    }
    
    /*
     * Selects a sorting option from the dropdown
     * @param options - The visible text of the option to select (e.g., "Name (A to Z)")
     */
    public void selectSorting(String options) {
    	wait.until(ExpectedConditions.visibilityOf(homePage_Sort_Dropdown));
        selectByVisibleText(homePage_Sort_Dropdown, options);
    }
    
    /**
     * Gets all product names as a List of Strings
     * @return List of product names
     */
    public List<String> getAllProductNames() {
        return productNames.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());    
    }
    
    /*
     * Gets all product prices as a List of Strings (including $ sign)
     * @return List of product price strings
     */
    public List<String> getAllProductPrice() {
        return productPrice.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());    
    }
    
    /*
     * Verifies if products are sorted in descending order (Z to A) by name
     * @return true if sorted correctly, false otherwise
     * @throws AssertionError if sorting violation is found
     */
    public boolean isSortedDescendingByName() {
        List<String> currentNames = getAllProductNames();
        System.out.println("Current product order: " + currentNames); 
        
        for (int i = 0; i < currentNames.size() - 1; i++) {
            String current = currentNames.get(i);
            String next = currentNames.get(i + 1);
            
            // Compare current with next in descending order (Z to A)
            if (current.compareToIgnoreCase(next) < 0) {
                System.err.println("Sorting violation at position " + i + 
                    ": " + current + " should come before " + next + " in descending order");
                Assert.fail("Sorting violation detected: " + current + " should come before " + next);
                return false; 
            }
        }
        return true;
    }
    
    /*
     * Verifies if products are sorted in ascending order (A to Z) by name
     * @return true if sorted correctly, false otherwise
     * @throws AssertionError if sorting violation is found
     */
    public boolean isSortedAscendingByName() {
        List<String> currentNames = getAllProductNames();
        System.out.println("Current product order: " + currentNames);

        for (int i = 0; i < currentNames.size() - 1; i++) {
            String current = currentNames.get(i);
            String next = currentNames.get(i + 1);

            // Compare current with next in ascending order (A to Z)
            if (current.compareToIgnoreCase(next) > 0) {
                System.err.println("Sorting violation at position " + i + 
                    ": " + current + " should come after " + next + " in ascending order");
                Assert.fail("Sorting violation detected: " + current + " should come after " + next);
                return false;
            }
        }
        return true;
    }
    
    /*
     * Verifies if products are sorted in ascending order by price (low to high)
     * @return true if sorted correctly, false otherwise
     * @throws AssertionError if sorting violation is found
     */
    public boolean isSortedAscendingByPrice() {
        List<Double> prices = getAllProductPrices();
        System.out.println("Current product prices: " + prices);

        for (int i = 0; i < prices.size() - 1; i++) {
            double current = prices.get(i);
            double next = prices.get(i + 1);

            if (current > next) {
                System.err.printf("Sorting violation at position %d: $%.2f should come before $%.2f%n", 
                                i, current, next);
                Assert.fail(String.format("Prices not sorted ascending: $%.2f > $%.2f", current, next));
                return false;
            }
        }
        return true;
    }

    /*
     * Verifies if products are sorted in descending order by price (high to low)
     * @return true if sorted correctly, false otherwise
     * @throws AssertionError if sorting violation is found
     */
    public boolean isSortedDescendingByPrice() {
        List<Double> prices = getAllProductPrices();
        System.out.println("Current product prices: " + prices);

        for (int i = 0; i < prices.size() - 1; i++) {
            double current = prices.get(i);
            double next = prices.get(i + 1);

            if (current < next) {
                System.err.printf("Sorting violation at position %d: $%.2f should come after $%.2f%n", 
                                i, current, next);
                Assert.fail(String.format("Prices not sorted descending: $%.2f < $%.2f", current, next));
                return false;
            }
        }
        return true;
    }

    /*
     * Helper method to extract product prices as List of Doubles
     * @return List of product prices as Double values
     */
    private List<Double> getAllProductPrices() {
        return productPrice.stream()
                .map(WebElement::getText)
                .map(price -> price.replace("$", ""))
                .map(Double::parseDouble)
                .collect(Collectors.toList());
    }
    
    /*
     * Clicks the 'Add to Cart' button for a specific product
     * @param itemName - Name of the product to add to cart
     */
    public void clickAddToCartForItem(String itemName) {
        getAddToCartButtonForItem(itemName).click();
    }
}