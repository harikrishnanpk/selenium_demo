package com.experion.testdata;

/*
 * Contains all test data related to login functionality
 * Provides centralized storage of credentials for different test scenarios
 */
public class LoginTestData {
    
    
    //Standard valid username for normal testing 
    public static final String STANDARD_USERNAME = "standard_user";
    
    // Username for performance testing scenarios 
    public static final String PERFORMANCE_USERNAME = "performance_glitch_user";
    
    // Valid password that works with all functional accounts 
    public static final String VALID_PASSWORD = "secret_sauce";
    // Username for locked account (should show error when used) 
    public static final String LOCKED_USERNAME = "locked_out_user";

    
    // Non-existent username for negative testing 
    public static final String INVALID_USERNAME = "invalid_user";
    
    // Incorrect password for negative testing 
    public static final String INVALID_PASSWORD = "wrong_password";

    
}