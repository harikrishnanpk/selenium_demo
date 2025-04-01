package com.experion.managers;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Manages the creation and configuration of ExtentReports test reports.
 * Implements the singleton pattern to ensure a single report instance is used across tests.
 */
public class ReportManager {
    private static ExtentReports extent;

    /*
     * Initializes the report with time stamp filename and default configuration, if it doesn't already exist.
     * @return Configured ExtentReports instance ready for test logging
     */
    public static ExtentReports getInstance() {
        if (extent == null) {
            // Create unique report filename with time stamp to prevent overwrites
            String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            String reportName = "Test-Report-" + timestamp + ".html";
            
            // Configure HTML reporter with path, title, and visual settings
            ExtentSparkReporter htmlReporter = new ExtentSparkReporter(
                System.getProperty("user.dir") + "/test-output/" + reportName);
            
            // Set report metadata and visual preferences
            htmlReporter.config().setDocumentTitle("Automation Report");
            htmlReporter.config().setReportName("Functional Testing");
            htmlReporter.config().setTheme(Theme.STANDARD);
            
            // Initialize and configure the main report instance
            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
            
            // Add system/environment information to report header
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("User", "Nandhakumar KR ");
        }
        return extent;
    }
}