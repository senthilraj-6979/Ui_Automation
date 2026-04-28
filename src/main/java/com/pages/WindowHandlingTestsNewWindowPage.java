package com.pages;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WindowType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class WindowHandlingTestsNewWindowPage {
    private static final Logger logger = LogManager.getLogger(WindowHandlingTestsNewWindowPage.class);
    private WebDriver driver;
    private String googleHandle;
    private String amtrakHandle;

    public WindowHandlingTestsNewWindowPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        logger.info("WindowHandlingTestsNewWindowPage initialized");
    }

    /**
     * Check if a window handle is still valid
     */
    private boolean isWindowHandleValid(String handle) {
        try {
            Set<String> handles = driver.getWindowHandles();
            boolean isValid = handles.contains(handle);
            logger.debug("Validating window handle: " + handle + " - Valid: " + isValid);
            return isValid;
        } catch (Exception e) {
            logger.error("Error validating window handle: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Launch Google in the current tab
     */
    public void launchGoogle() {
        try {
            logger.info("Launching Google...");
            driver.get("https://www.google.com");
            this.googleHandle = driver.getWindowHandle();
            logger.info("✓ Google launched successfully - Handle: " + googleHandle);
        } catch (Exception e) {
            logger.error("Failed to launch Google: " + e.getMessage(), e);
            throw new RuntimeException("Failed to launch Google", e);
        }
    }

    /**
     * Open a new tab and navigate to specified URL
     */
    public void openNewTabWithURL(String url) {
        try {
            logger.info("Opening new tab with URL: " + url);
            driver.switchTo().newWindow(WindowType.TAB);
            logger.debug("New tab created");

            driver.get(url);
            this.amtrakHandle = driver.getWindowHandle();
            logger.info("✓ New tab opened successfully - URL: " + url + " - Handle: " + amtrakHandle);
        } catch (Exception e) {
            logger.error("Error opening new tab with URL: " + url + " - " + e.getMessage(), e);
            throw new RuntimeException("Failed to open new tab with URL: " + url, e);
        }
    }

    /**
     * Open new tab and navigate to Amtrak
     */
    public void openAmtrakInNewTab() {
        logger.info("Opening Amtrak in new tab");
        openNewTabWithURL("https://www.amtrak.com");
    }

    /**
     * Verify both tabs are open
     */
    public boolean verifyBothTabsOpen() {
        try {
            Set<String> handles = driver.getWindowHandles();
            boolean bothOpen = handles.size() == 2;
            logger.info("Verifying both tabs open - Total tabs: " + handles.size());

            if (!bothOpen) {
                logger.warn("⚠ Expected 2 tabs but found " + handles.size());
                logger.debug("Available handles: " + handles);
            } else {
                logger.info("✓ Both tabs are open as expected");
            }
            return bothOpen;
        } catch (Exception e) {
            logger.error("Error verifying tabs: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Switch to Google tab with validation
     */
    public void switchToGoogleTab() {
        try {
            logger.info("Attempting to switch to Google tab...");

            if (googleHandle == null || googleHandle.isEmpty()) {
                logger.error("Google tab handle not found! Please launch Google first.");
                throw new RuntimeException("Google tab handle not found! Please launch Google first.");
            }

            if (!isWindowHandleValid(googleHandle)) {
                logger.error("Google tab handle is no longer valid! Tab may have been closed.");
                throw new RuntimeException("Google tab handle is no longer valid! Tab may have been closed.");
            }

            driver.switchTo().window(googleHandle);
            logger.info("✓ Successfully switched to Google tab - URL: " + driver.getCurrentUrl());
        } catch (Exception e) {
            logger.error("Error switching to Google tab: " + e.getMessage(), e);
            throw new RuntimeException("Failed to switch to Google tab", e);
        }
    }

    /**
     * Switch to Amtrak tab with validation
     */
    public void switchToAmtrakTab() {
        try {
            logger.info("Attempting to switch to Amtrak tab...");

            if (amtrakHandle == null || amtrakHandle.isEmpty()) {
                logger.error("Amtrak tab handle not found! Please open Amtrak tab first.");
                throw new RuntimeException("Amtrak tab handle not found! Please open Amtrak tab first.");
            }

            if (!isWindowHandleValid(amtrakHandle)) {
                logger.error("Amtrak tab handle is no longer valid! Tab may have been closed.");
                throw new RuntimeException("Amtrak tab handle is no longer valid! Tab may have been closed.");
            }

            driver.switchTo().window(amtrakHandle);
            logger.info("✓ Successfully switched to Amtrak tab - URL: " + driver.getCurrentUrl());
        } catch (Exception e) {
            logger.error("Error switching to Amtrak tab: " + e.getMessage(), e);
            throw new RuntimeException("Failed to switch to Amtrak tab", e);
        }
    }

    /**
     * Get current tab URL
     */
    public String getCurrentTabURL() {
        String url = driver.getCurrentUrl();
        logger.debug("Current tab URL: " + url);
        return url;
    }

    /**
     * Get number of open tabs
     */
    public int getNumberOfTabs() {
        int tabCount = driver.getWindowHandles().size();
        logger.debug("Total open tabs: " + tabCount);
        return tabCount;
    }

    /**
     * Close Amtrak tab and switch back to Google
     */
    public void closeAmtrakTabAndSwitchToGoogle() {
        try {
            logger.info("Closing Amtrak tab and switching back to Google...");

            // Close Amtrak tab if it's still valid
            if (amtrakHandle != null && !amtrakHandle.isEmpty() && isWindowHandleValid(amtrakHandle)) {
                try {
                    driver.switchTo().window(amtrakHandle);
                    driver.close();
                    logger.info("✓ Closed Amtrak tab");
                } catch (Exception e) {
                    logger.warn("Error closing Amtrak tab: " + e.getMessage());
                }
            }

            // Switch back to Google tab
            if (googleHandle != null && !googleHandle.isEmpty() && isWindowHandleValid(googleHandle)) {
                try {
                    driver.switchTo().window(googleHandle);
                    logger.info("✓ Switched back to Google tab - URL: " + driver.getCurrentUrl());
                } catch (Exception e) {
                    logger.error("Error switching to Google tab: " + e.getMessage(), e);
                    throw new RuntimeException("Failed to switch back to Google tab", e);
                }
            } else {
                logger.error("Google tab handle is invalid or null!");
                throw new RuntimeException("Google tab handle is invalid or null!");
            }
        } catch (Exception e) {
            logger.error("Error in closeAmtrakTabAndSwitchToGoogle: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Clear handles for cleanup
     */
    public void clearHandles() {
        try {
            googleHandle = null;
            amtrakHandle = null;
            logger.info("✓ Tab handles cleared for cleanup");
        } catch (Exception e) {
            logger.error("Error clearing handles: " + e.getMessage(), e);
        }
    }

    /**
     * Original method - kept for backward compatibility
     */
    public void switchTONewHandle(String newURL) {
        try {
            logger.info("Opening new window with URL: " + newURL);
            // Open a new window and navigate to another URL
            driver.switchTo().newWindow(WindowType.WINDOW);
            driver.get(newURL);
            logger.info("✓ New window opened with URL: " + newURL);

            Dimension size = driver.manage().window().getSize();
            int width1 = size.getWidth();
            int height1 = size.getHeight();
            logger.debug("Window size - Width: " + width1 + ", Height: " + height1);
        } catch (Exception e) {
            logger.error("Error switching to new handle with URL: " + newURL + " - " + e.getMessage(), e);
            throw new RuntimeException("Failed to switch to new handle", e);
        }
    }

    public void switchTOOldHandle(){
        try {
            logger.info("Switching back to original window...");
            String initialHandle = driver.getWindowHandle();
            driver.switchTo().window(initialHandle);
            logger.info("✓ Switched to original window - URL: " + driver.getCurrentUrl());
        } catch (Exception e) {
            logger.error("Error switching to old handle: " + e.getMessage(), e);
            throw new RuntimeException("Failed to switch to old handle", e);
        }
    }
}
