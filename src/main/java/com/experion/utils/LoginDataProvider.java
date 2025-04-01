package com.experion.utils;
import org.testng.annotations.DataProvider;

/**
 * Provides test data for login functionality tests
 * Contains data provider methods that supply different sets of login credentials
 */
public class LoginDataProvider {

    /*
     * Data provider method that supplies valid login credentials
     * @return 2D array of test data containing username/password combinations
     * @DataProvider annotation marks this as a TestNG data source
     * @name "loginData" identifies this provider for test methods
     */
    @DataProvider(name = "validLoginData")
    public Object[][] getLoginData() {
        return new Object[][] {
            // Each inner array represents one test case with username and password
            {"standard_user", "secret_sauce"},       // Standard user credentials
            {"performance_glitch_user", "secret_sauce"}, // Performance test user
            {"visual_user", "secret_sauce"},        // Visual test user
        };
    }
}