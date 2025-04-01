package com.experion.tests;

import static org.testng.Assert.assertNotEquals;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import org.testng.annotations.Test;
import com.experion.config.ConfigManager;
import com.experion.pages.CartPage;
import com.experion.pages.CheckOutPage;
import com.experion.pages.HomePage;
import com.experion.pages.LoginPage;
import com.experion.testdata.CartPageTestData;
import com.experion.testdata.CheckoutPageTestData;
import com.experion.testdata.HomepageTestData;
import com.experion.testdata.LoginTestData;

/*
 * Automated test scenarios for the application
 * Contains end-to-end test cases for:
 * - Product sorting functionality
 * - Add to cart operations
 * - Checkout workflow
 */
public class AutomatedScenario extends BaseTest {

    /*
     * Performs standard login with valid credentials
     * Reusable method to avoid code duplication in test cases
     */
    private void performStandardLogin() {
        // Initialize page objects
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);
        
        // Navigate to application URL
        driver.get(ConfigManager.getBaseUrl());
        
        // Enter credentials and login
        loginPage.enterUsername(LoginTestData.STANDARD_USERNAME);
        loginPage.enterPassword(LoginTestData.VALID_PASSWORD);
        loginPage.clickLogin();
        
        // Verify successful login
        homePage.verifyUserInHomePage();
    }

    
    // PRODUCT SORTING TEST CASES
    

    /*
     * Verifies default product sorting (A to Z)
     */
    @Test
    public void VerifyTheDefaultSortingByNameAscending() {
        performStandardLogin();
        HomePage homePage = new HomePage(driver);
        
        // Verify default sorting
        homePage.clickOnTheSortDropdown();
        homePage.isSortedAscendingByName();
    }

    /*
     * Verifies descending name sorting (Z to A)
     */
    @Test
    public void VerifyTheDescendingSortingByName() {
        performStandardLogin();
        HomePage homePage = new HomePage(driver);
        
        // Change sorting to Z-A
        homePage.clickOnTheSortDropdown();
        List<String> initialProducts = homePage.getAllProductNames();
        homePage.selectSorting(HomepageTestData.NAME_ZTOA_SORTING);
        
        // Verify sorting changed
        List<String> sortedProducts = homePage.getAllProductNames();
        assertNotEquals(initialProducts, sortedProducts, 
            "Product list should change after sorting");
        
        // Verify correct descending order
        homePage.isSortedDescendingByName();
    }

    /*
     * Verifies price sorting (low to high)
     */
    @Test
    public void VerifyTheAscendingSortingByPrice() {
        performStandardLogin();
        HomePage homePage = new HomePage(driver);
        
        // Change sorting to price low-high
        homePage.clickOnTheSortDropdown();
        List<String> initialProducts = homePage.getAllProductPrice();
        homePage.selectSorting(HomepageTestData.PRICE_LOWTOHIGH_SORTING);
        
        // Verify sorting changed
        List<String> sortedProducts = homePage.getAllProductPrice();
        assertNotEquals(initialProducts, sortedProducts, 
            "Product list should change after sorting");
        
        // Verify correct price order
        homePage.isSortedAscendingByPrice();
    }

    /*
     * Verifies price sorting (high to low)
     */
    @Test
    public void VerifyTheDescendingSortingByPrice() {
        performStandardLogin();
        HomePage homePage = new HomePage(driver);
        
        // Change sorting to price high-low
        homePage.clickOnTheSortDropdown();
        List<String> initialProducts = homePage.getAllProductPrice();
        homePage.selectSorting(HomepageTestData.PRICE_HIGHTOLOW_SORTING);
        
        // Verify sorting changed
        List<String> sortedProducts = homePage.getAllProductPrice();
        assertNotEquals(initialProducts, sortedProducts, 
            "Product list should change after sorting");
        
        // Verify correct price order
        homePage.isSortedDescendingByPrice();
    }

    // CART OPERATIONS TEST CASES
   
    /*
     * Verifies adding products to cart
     * @throws InterruptedException if thread sleep is interrupted
     */
    @Test 
    public void VerifyTheAddToCartFeature()  {
        performStandardLogin();
        HomePage homePage = new HomePage(driver);
        CartPage cartPage = new CartPage(driver);
        
        // Sort products Z-A
        homePage.clickOnTheSortDropdown();
        List<String> initialProducts = homePage.getAllProductNames();
        homePage.selectSorting(HomepageTestData.NAME_ZTOA_SORTING);
        List<String> sortedProducts = homePage.getAllProductNames();
        assertNotEquals(initialProducts, sortedProducts, 
            "Product list should change after sorting");
        homePage.isSortedDescendingByName();
        
        // Add products to cart
        homePage.clickAddToCartForItem(HomepageTestData.First_Product_Name);
        homePage.clickAddToCartForItem(HomepageTestData.Second_Product_Name);
        
        // Verify cart contents
        cartPage.clickOnTheCartButton();
        cartPage.verifyTheUserInCartPage();
        cartPage.verifyProductsInCart(Arrays.asList(
            Arrays.asList(
                CartPageTestData.First_Product_Name, 
                CartPageTestData.First_Product_Desc, 
                CartPageTestData.First_Product_Price),
            Arrays.asList(
                CartPageTestData.Second_Product_Name,
                CartPageTestData.Second_Product_Desc,
                CartPageTestData.Second_Product_Price)
        ));
    }

  
    // CHECKOUT WORKFLOW TEST CASES

    /*
     * Verifies complete checkout workflow
     * @throws InterruptedException if thread sleep is interrupted
     */
    @Test 
    public void VerifyTheCheckOut() throws InterruptedException {
        performStandardLogin();
        HomePage homePage = new HomePage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckOutPage checkoutPage = new CheckOutPage(driver);
        
        // Sort products Z-A
        homePage.clickOnTheSortDropdown();
        List<String> initialProducts = homePage.getAllProductNames();
        homePage.selectSorting(HomepageTestData.NAME_ZTOA_SORTING);
        List<String> sortedProducts = homePage.getAllProductNames();
        assertNotEquals(initialProducts, sortedProducts, 
            "Product list should change after sorting");
        homePage.isSortedDescendingByName();
        
        // Add products to cart
        homePage.clickAddToCartForItem(HomepageTestData.First_Product_Name);
        homePage.clickAddToCartForItem(HomepageTestData.Second_Product_Name);
        
        // Verify cart contents
        cartPage.clickOnTheCartButton();
        cartPage.verifyTheUserInCartPage();
        cartPage.verifyProductsInCart(Arrays.asList(
            Arrays.asList(
                CartPageTestData.First_Product_Name, 
                CartPageTestData.First_Product_Desc, 
                CartPageTestData.First_Product_Price),
            Arrays.asList(
                CartPageTestData.Second_Product_Name,
                CartPageTestData.Second_Product_Desc,
                CartPageTestData.Second_Product_Price)
        ));
        
        // Proceed to checkout
        cartPage.clickOnTheCheckOutButton();
        
        // Enter shipping information
        checkoutPage.enterTheFirstName(CheckoutPageTestData.User1_FirstName);
        checkoutPage.enterTheSecondName(CheckoutPageTestData.User1_LastName);
        checkoutPage.enterThePostalCode(CheckoutPageTestData.User1_Zipcode);
        checkoutPage.clickOnTheContinueButton();
        
        // Verify order summary
        Thread.sleep(Duration.ofSeconds(2));
        checkoutPage.verifyTheTotalAmountWithoutTax("39.98");
        
        // Complete purchase
        Thread.sleep(Duration.ofSeconds(2));
        checkoutPage.clickOnTheFinishButton();
        checkoutPage.validateFinalMessage(CheckoutPageTestData.Finale_Message_For_Checkout);
    }
}