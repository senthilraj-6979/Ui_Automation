package com.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WindowType;
import java.util.Set;

public class MultiWindowHandlingPage {
    private WebDriver driver;
    private String googleHandle;
    private String amtrakHandle;

    public MultiWindowHandlingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Check if a window handle is still valid
     */
    private boolean isWindowHandleValid(String handle) {
        try {
            Set<String> handles = driver.getWindowHandles();
            return handles.contains(handle);
        } catch (Exception e) {
            System.err.println("Error validating window handle: " + e.getMessage());
            return false;
        }
    }

    /**
     * Launch Google homepage
     */
    public void launchGoogle() {
        driver.get("https://www.google.com");
        this.googleHandle = driver.getWindowHandle();
        System.out.println("✓ Google window launched - Handle: " + googleHandle);
    }

    /**
     * Open Amtrak in a new window
     */
    public void openAmtrakInNewWindow() {
        try {
            driver.switchTo().newWindow(WindowType.WINDOW);
            driver.get("https://www.amtrak.com");
            this.amtrakHandle = driver.getWindowHandle();
            System.out.println("✓ Amtrak window opened - Handle: " + amtrakHandle);
        } catch (Exception e) {
            System.err.println("Error opening Amtrak window: " + e.getMessage());
            throw new RuntimeException("Failed to open Amtrak window", e);
        }
    }

    /**
     * Verify both windows are open
     */
    public boolean verifyBothWindowsOpen() {
        Set<String> handles = driver.getWindowHandles();
        boolean bothOpen = handles.size() == 2;
        System.out.println("Total windows open: " + handles.size());
        if (!bothOpen) {
            System.err.println("Warning: Expected 2 windows but found " + handles.size());
            System.err.println("Available handles: " + handles);
        }
        return bothOpen;
    }

    /**
     * Switch back to Google window
     */
    public void switchToGoogleWindow() {
        if (googleHandle == null || googleHandle.isEmpty()) {
            throw new RuntimeException("Google window handle not found! Please launch Google first.");
        }

        if (!isWindowHandleValid(googleHandle)) {
            throw new RuntimeException("Google window handle is no longer valid! Window may have been closed.");
        }

        try {
            driver.switchTo().window(googleHandle);
            System.out.println("✓ Switched to Google window");
        } catch (Exception e) {
            System.err.println("Error switching to Google window: " + e.getMessage());
            throw new RuntimeException("Failed to switch to Google window", e);
        }
    }

    /**
     * Switch to Amtrak window
     */
    public void switchToAmtrakWindow() {
        if (amtrakHandle == null || amtrakHandle.isEmpty()) {
            throw new RuntimeException("Amtrak window handle not found! Please open Amtrak window first.");
        }

        if (!isWindowHandleValid(amtrakHandle)) {
            throw new RuntimeException("Amtrak window handle is no longer valid! Window may have been closed.");
        }

        try {
            driver.switchTo().window(amtrakHandle);
            System.out.println("✓ Switched to Amtrak window");
        } catch (Exception e) {
            System.err.println("Error switching to Amtrak window: " + e.getMessage());
            throw new RuntimeException("Failed to switch to Amtrak window", e);
        }
    }

    /**
     * Close Amtrak window and switch back to Google
     */
    public void closeAmtrakWindowAndSwitchToGoogle() {
        // Close Amtrak window if it's still valid
        if (amtrakHandle != null && !amtrakHandle.isEmpty() && isWindowHandleValid(amtrakHandle)) {
            try {
                driver.switchTo().window(amtrakHandle);
                driver.close();
                System.out.println("✓ Closed Amtrak window");
            } catch (Exception e) {
                System.err.println("Error closing Amtrak window: " + e.getMessage());
            }
        }

        // Switch back to Google window
        if (googleHandle != null && !googleHandle.isEmpty() && isWindowHandleValid(googleHandle)) {
            try {
                driver.switchTo().window(googleHandle);
                System.out.println("✓ Switched back to Google window");
            } catch (Exception e) {
                System.err.println("Error switching to Google window: " + e.getMessage());
                throw new RuntimeException("Failed to switch back to Google window", e);
            }
        } else {
            throw new RuntimeException("Google window handle is invalid or null!");
        }
    }

    /**
     * Get current window URL
     */
    public String getCurrentWindowURL() {
        return driver.getCurrentUrl();
    }

    /**
     * Get number of open windows
     */
    public int getNumberOfOpenWindows() {
        return driver.getWindowHandles().size();
    }

    /**
     * Clear stored window handles (call in teardown)
     */
    public void clearWindowHandles() {
        googleHandle = null;
        amtrakHandle = null;
        System.out.println("✓ Window handles cleared");
    }
}

