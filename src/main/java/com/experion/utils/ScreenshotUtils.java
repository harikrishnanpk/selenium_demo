package com.experion.utils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

/*
 * Utility class for capturing and saving screenshots during test execution
 * Provides methods to take screenshots and save them to a specified location
 */
public class ScreenshotUtils {

    /*
     * Captures a screenshot and saves it to the test-output/screenshots directory
     * @param driver The WebDriver instance used to capture the screenshot
     * @param screenshotName The base name to use for the screenshot file (without extension)
     * @return String The absolute path where the screenshot was saved, or null if failed
     * @throws RuntimeException indirectly through IOException if file operations fail
     */
    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        try {
            //Cast the driver to TakesScreenshot interface
            TakesScreenshot ts = (TakesScreenshot) driver;
            
            //Capture screenshot as temporary file
            File source = ts.getScreenshotAs(OutputType.FILE);
            
            //Define destination path for the screenshot
            String path = System.getProperty("user.dir") + 
                         "/test-output/screenshots/" + 
                         screenshotName + ".png";
            
            //Copy the screenshot to the destination path
            FileUtils.copyFile(source, new File(path));
            
            //Return the path where screenshot was saved
            return path;
            
        } catch (IOException e) {
            // Log error if screenshot capture fails
            System.out.println("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }
}