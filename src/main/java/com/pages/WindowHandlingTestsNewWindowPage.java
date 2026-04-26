package com.pages;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WindowType;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public class WindowHandlingTestsNewWindowPage {
    private WebDriver driver;
    private String googleHandle;
    private String amtrakHandle;

    public WindowHandlingTestsNewWindowPage(WebDriver driver) {
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
            return false;
        }
    }

    /**
     * Launch Google in the current tab
     */
    public void launchGoogle() {
        driver.get("https://www.google.com");
        this.googleHandle = driver.getWindowHandle();
        System.out.println("✓ Google launched - Handle: " + googleHandle);
    }

    /**
     * Open a new tab and navigate to specified URL
     */
    public void openNewTabWithURL(String url) {
        try {
            driver.switchTo().newWindow(WindowType.TAB);
            driver.get(url);
            this.amtrakHandle = driver.getWindowHandle();
            System.out.println("✓ New tab opened - Handle: " + amtrakHandle);
        } catch (Exception e) {
            System.err.println("Error opening new tab: " + e.getMessage());
            throw new RuntimeException("Failed to open new tab with URL: " + url, e);
        }
    }

    /**
     * Open new tab and navigate to Amtrak
     */
    public void openAmtrakInNewTab() {
        openNewTabWithURL("https://www.amtrak.com");
    }

    /**
     * Verify both tabs are open
     */
    public boolean verifyBothTabsOpen() {
        Set<String> handles = driver.getWindowHandles();
        boolean bothOpen = handles.size() == 2;
        System.out.println("Total tabs open: " + handles.size());
        if (!bothOpen) {
            System.err.println("Warning: Expected 2 tabs but found " + handles.size());
            System.err.println("Available handles: " + handles);
        }
        return bothOpen;
    }

    /**
     * Switch to Google tab with validation
     */
    public void switchToGoogleTab() {
        if (googleHandle == null || googleHandle.isEmpty()) {
            throw new RuntimeException("Google tab handle not found! Please launch Google first.");
        }

        if (!isWindowHandleValid(googleHandle)) {
            throw new RuntimeException("Google tab handle is no longer valid! Tab may have been closed.");
        }

        try {
            driver.switchTo().window(googleHandle);
            System.out.println("✓ Switched to Google tab");
        } catch (Exception e) {
            System.err.println("Error switching to Google tab: " + e.getMessage());
            throw new RuntimeException("Failed to switch to Google tab", e);
        }
    }

    /**
     * Switch to Amtrak tab with validation
     */
    public void switchToAmtrakTab() {
        if (amtrakHandle == null || amtrakHandle.isEmpty()) {
            throw new RuntimeException("Amtrak tab handle not found! Please open Amtrak tab first.");
        }

        if (!isWindowHandleValid(amtrakHandle)) {
            throw new RuntimeException("Amtrak tab handle is no longer valid! Tab may have been closed.");
        }

        try {
            driver.switchTo().window(amtrakHandle);
            System.out.println("✓ Switched to Amtrak tab");
        } catch (Exception e) {
            System.err.println("Error switching to Amtrak tab: " + e.getMessage());
            throw new RuntimeException("Failed to switch to Amtrak tab", e);
        }
    }

    /**
     * Get current tab URL
     */
    public String getCurrentTabURL() {
        return driver.getCurrentUrl();
    }

    /**
     * Get number of open tabs
     */
    public int getNumberOfTabs() {
        return driver.getWindowHandles().size();
    }

    /**
     * Close Amtrak tab and switch back to Google
     */
    public void closeAmtrakTabAndSwitchToGoogle() {
        // Close Amtrak tab if it's still valid
        if (amtrakHandle != null && !amtrakHandle.isEmpty() && isWindowHandleValid(amtrakHandle)) {
            try {
                driver.switchTo().window(amtrakHandle);
                driver.close();
                System.out.println("✓ Closed Amtrak tab");
            } catch (Exception e) {
                System.err.println("Error closing Amtrak tab: " + e.getMessage());
            }
        }

        // Switch back to Google tab
        if (googleHandle != null && !googleHandle.isEmpty() && isWindowHandleValid(googleHandle)) {
            try {
                driver.switchTo().window(googleHandle);
                System.out.println("✓ Switched back to Google tab");
            } catch (Exception e) {
                System.err.println("Error switching to Google tab: " + e.getMessage());
                throw new RuntimeException("Failed to switch back to Google tab", e);
            }
        } else {
            throw new RuntimeException("Google tab handle is invalid or null!");
        }
    }

    /**
     * Clear handles for cleanup
     */
    public void clearHandles() {
        googleHandle = null;
        amtrakHandle = null;
        System.out.println("✓ Tab handles cleared");
    }

    /**
     * Original method - kept for backward compatibility
     */
    public void switchTONewHandle(String newURL) {
        String initialHandle = driver.getWindowHandle();

        // Open a new window and navigate to another URL
        driver.switchTo().newWindow(WindowType.WINDOW);
        driver.get(newURL);

        Dimension size = driver.manage().window().getSize();
        int width1 = size.getWidth();
        int height1 = size.getHeight();

        System.out.println("width--"+width1);
        System.out.println("height--"+height1);

        // Ensure there are two windows open
        //assertThat(driver.getWindowHandles()).hasSize(2);

        // Switch back to the original window (parent window)
        driver.switchTo().window(initialHandle);

    }
}
