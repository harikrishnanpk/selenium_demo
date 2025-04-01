package com.experion.tests;

import com.experion.config.ConfigManager;
import com.experion.pages.HomePage;
import com.experion.pages.LoginPage;
import com.experion.testdata.LoginTestData;
import com.experion.utils.LoginDataProvider;

import static org.testng.Assert.assertNotEquals;
import java.util.List;
import org.testng.annotations.Test;

/**
 * Test class containing all login-related test scenarios
 * Validates both positive and negative login workflows
 */
public class LoginTests extends BaseTest {

    // TEST CONFIGURATION
    private String baseUrl = ConfigManager.getBaseUrl();
    
    /*
     * Validates successful login and basic sorting functionality
     * Test Steps:
     * 1. Navigate to base URL
     * 2. Login with valid credentials
     * 3. Verify homepage loads
     * 4. Test product sorting functionality
     */
    @Test(dataProvider = "validLoginData", dataProviderClass = LoginDataProvider.class)
    public void successfulLoginTest(String userName,String password) throws InterruptedException {
        // Initialize pages
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);
        
        // Login workflow
        loginPage.enterUsername(userName);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
        
        // Homepage verification
        homePage.verifyUserInHomePage();
        
        // Sorting validation
        homePage.clickOnTheSortDropdown();
        List<String> initialProducts = homePage.getAllProductNames();
        homePage.selectSorting("Name (Z to A)");
        List<String> sortedProducts = homePage.getAllProductNames();
        
        // Assertions
        assertNotEquals(initialProducts, sortedProducts, 
            "Product list should change after sorting");
        homePage.isSortedDescendingByName();
    }

    
    /*
     * Validates login failure with invalid password
     * Test Steps:
     * 1. Navigate to base URL
     * 2. Attempt login with valid username but invalid password
     * 3. Verify appropriate error message displays
     */
    @Test()
    public void loginWithInvalidPassword() {
        // Initialize pages
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver);
        
        // Failed login attempt
        loginPage.enterUsername(LoginTestData.STANDARD_USERNAME);
        loginPage.enterPassword(LoginTestData.INVALID_PASSWORD);
        loginPage.clickLogin();
        
        // Error message validation
        loginPage.verifyErrorMessage(
            "Epic sadface: Username and password do not match any user in this service");
    }
}