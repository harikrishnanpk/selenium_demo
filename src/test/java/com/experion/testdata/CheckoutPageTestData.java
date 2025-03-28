package com.experion.testdata;

/*
 * Contains test data for checkout process validations
 * Stores user information and expected system messages
 */
public class CheckoutPageTestData {

    // Standard test user first name for checkout forms 
    public static final String User1_FirstName = "Nandhakumar";
    
    // Standard test user last name for checkout forms 
    public static final String User1_LastName = "KR";
    
    // Valid postal code for test transactions 
    public static final String User1_Zipcode = "560098";

    /*
     * Expected success message after completing checkout 
     * Must match UI text exactly including punctuation
     */
    public static final String Finale_Message_For_Checkout = "Thank you for your order!";

}