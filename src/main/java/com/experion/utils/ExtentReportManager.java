package com.experion.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Manages the creation and configuration of ExtentReports test reports
 * Implements singleton pattern to ensure single report instance
 */
public class ExtentReportManager {
    
    // Singleton instance of ExtentReports
    private static ExtentReports extent;

    /*
     * Gets the singleton instance of ExtentReports
     * Creates new instance if one doesn't exist
     * @return ExtentReports instance with configured settings
     */
    public static ExtentReports getInstance() {
        if (extent == null) {
            // Create timestamp for unique report filename
            String timestamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
            String reportName = "Test-Report-" + timestamp + ".html";
            
            // Initialize HTML reporter with path to output directory
            ExtentSparkReporter htmlReporter = new ExtentSparkReporter(
                System.getProperty("user.dir") + "/test-output/" + reportName);

            // Configure report settings
            htmlReporter.config().setDocumentTitle("Automation Report");
            htmlReporter.config().setReportName("SauceDemo Test Report");
            htmlReporter.config().setTheme(Theme.STANDARD);

            // Initialize ExtentReports and attach configured reporter
            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
            
            // Add system/environment info to report
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("User", System.getProperty("user.name"));
        }
        return extent;
    }
}