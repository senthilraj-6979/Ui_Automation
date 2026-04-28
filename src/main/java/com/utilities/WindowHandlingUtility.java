package com.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import java.util.*;


/**
 * Utility class for handling multiple windows/tabs in Selenium
 * Provides common methods for window/tab operations with validation
 */
public class WindowHandlingUtility {
    private WebDriver driver;
    private Map<String, String> windowHandles = new HashMap<>();
    private static final long WINDOW_SWITCH_TIMEOUT = 5000; // 5 seconds

    public WindowHandlingUtility(WebDriver driver) {
        this.driver = driver;
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
     * Store window handle with a name/key
     */
    public void storeWindowHandle(String name) {
        String handle = driver.getWindowHandle();
        windowHandles.put(name, handle);
        System.out.println("✓ Stored window handle for '" + name + "': " + handle);
    }

    /**
     * Open new tab and optionally navigate to URL
     */
    public String openNewTab(String url, String windowName) {
        driver.switchTo().newWindow(WindowType.TAB);
        if (url != null && !url.isEmpty()) {
            driver.get(url);
        }
        String tabHandle = driver.getWindowHandle();
        windowHandles.put(windowName, tabHandle);
        System.out.println("✓ Opened new tab '" + windowName + "' - Handle: " + tabHandle);
        return tabHandle;
    }

    /**
     * Open new window and optionally navigate to URL
     */
    public String openNewWindow(String url, String windowName) {
        driver.switchTo().newWindow(WindowType.WINDOW);
        if (url != null && !url.isEmpty()) {
            driver.get(url);
        }
        String windowHandle = driver.getWindowHandle();
        windowHandles.put(windowName, windowHandle);
        System.out.println("✓ Opened new window '" + windowName + "' - Handle: " + windowHandle);
        return windowHandle;
    }

    /**
     * Switch to window/tab by name
     */
    public void switchToWindow(String windowName) {
        String handle = windowHandles.get(windowName);
        if (handle == null || handle.isEmpty()) {
            throw new RuntimeException("Window '" + windowName + "' not found in stored handles");
        }
        if (!isWindowHandleValid(handle)) {
            throw new RuntimeException("Window '" + windowName + "' handle is no longer valid");
        }
        try {
            driver.switchTo().window(handle);
            System.out.println("✓ Switched to window '" + windowName + "'");
        } catch (Exception e) {
            throw new RuntimeException("Failed to switch to window '" + windowName + "'", e);
        }
    }

    /**
     * Switch to window by title (substring match)
     */
    public void switchToWindowByTitle(String titleSubstring) {
        Set<String> handles = driver.getWindowHandles();
        for (String handle : handles) {
            driver.switchTo().window(handle);
            if (driver.getTitle().contains(titleSubstring)) {
                System.out.println("✓ Switched to window with title containing: " + titleSubstring);
                return;
            }
        }
        throw new RuntimeException("Window with title containing '" + titleSubstring + "' not found");
    }

    /**
     * Switch to window by URL (substring match)
     */
    public void switchToWindowByUrl(String urlSubstring) {
        Set<String> handles = driver.getWindowHandles();
        for (String handle : handles) {
            driver.switchTo().window(handle);
            if (driver.getCurrentUrl().contains(urlSubstring)) {
                System.out.println("✓ Switched to window with URL containing: " + urlSubstring);
                return;
            }
        }
        throw new RuntimeException("Window with URL containing '" + urlSubstring + "' not found");
    }

    /**
     * Close specific window/tab by name and switch to another
     */
    public void closeWindowAndSwitch(String windowToClose, String windowToSwitchTo) {
        String handleToClose = windowHandles.get(windowToClose);
        if (handleToClose != null && isWindowHandleValid(handleToClose)) {
            driver.switchTo().window(handleToClose);
            driver.close();
            System.out.println("✓ Closed window '" + windowToClose + "'");
        }
        switchToWindow(windowToSwitchTo);
    }

    /**
     * Close all windows except the one specified
     */
    public void closeAllWindowsExcept(String windowNameToKeep) {
        String handleToKeep = windowHandles.get(windowNameToKeep);
        if (handleToKeep == null) {
            throw new RuntimeException("Window '" + windowNameToKeep + "' not found");
        }

        Set<String> allHandles = driver.getWindowHandles();
        for (String handle : allHandles) {
            if (!handle.equals(handleToKeep)) {
                driver.switchTo().window(handle);
                driver.close();
                System.out.println("✓ Closed window with handle: " + handle);
            }
        }
        switchToWindow(windowNameToKeep);
        System.out.println("✓ All windows closed except '" + windowNameToKeep + "'");
    }

    /**
     * Get URL of specific window
     */
    public String getWindowUrl(String windowName) {
        String originalHandle = driver.getWindowHandle();
        switchToWindow(windowName);
        String url = driver.getCurrentUrl();
        driver.switchTo().window(originalHandle);
        return url;
    }

    /**
     * Get title of specific window
     */
    public String getWindowTitle(String windowName) {
        String originalHandle = driver.getWindowHandle();
        switchToWindow(windowName);
        String title = driver.getTitle();
        driver.switchTo().window(originalHandle);
        return title;
    }

    /**
     * Get total number of open windows/tabs
     */
    public int getWindowCount() {
        return driver.getWindowHandles().size();
    }

    /**
     * Get all stored window names
     */
    public Set<String> getStoredWindowNames() {
        return new HashSet<>(windowHandles.keySet());
    }

    /**
     * Wait for new window to open
     */
    public String waitForNewWindow(String originalHandle) {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < WINDOW_SWITCH_TIMEOUT) {
            Set<String> handles = driver.getWindowHandles();
            for (String handle : handles) {
                if (!handle.equals(originalHandle)) {
                    return handle;
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        throw new RuntimeException("New window did not open within " + WINDOW_SWITCH_TIMEOUT + "ms");
    }

    /**
     * Wait for window to close
     */
    public void waitForWindowToClose(String handleToClose) {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < WINDOW_SWITCH_TIMEOUT) {
            Set<String> handles = driver.getWindowHandles();
            if (!handles.contains(handleToClose)) {
                System.out.println("✓ Window closed: " + handleToClose);
                return;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        throw new RuntimeException("Window did not close within " + WINDOW_SWITCH_TIMEOUT + "ms");
    }

    /**
     * Clear all stored window handles
     */
    public void clearWindowHandles() {
        windowHandles.clear();
        System.out.println("✓ All window handles cleared");
    }

    /**
     * Get current window handle
     */
    public String getCurrentWindowHandle() {
        return driver.getWindowHandle();
    }

    /**
     * Get window handle by name
     */
    public String getWindowHandle(String windowName) {
        return windowHandles.get(windowName);
    }

    /**
     * Print all stored windows (useful for debugging)
     */
    public void printAllStoredWindows() {
        System.out.println("\n=== Stored Windows ===");
        for (Map.Entry<String, String> entry : windowHandles.entrySet()) {
            boolean isValid = isWindowHandleValid(entry.getValue());
            System.out.println("  Name: " + entry.getKey() + ", Handle: " + entry.getValue() +
                             ", Valid: " + isValid);
        }
        System.out.println("Total windows: " + driver.getWindowHandles().size());
        System.out.println("=====================\n");
    }

    /**
     * Switch to first window
     */
    public void switchToFirstWindow() {
        List<String> handles = new ArrayList<>(driver.getWindowHandles());
        if (handles.isEmpty()) {
            throw new RuntimeException("No windows available");
        }
        driver.switchTo().window(handles.get(0));
        System.out.println("✓ Switched to first window");
    }

    /**
     * Switch to last window
     */
    public void switchToLastWindow() {
        List<String> handles = new ArrayList<>(driver.getWindowHandles());
        if (handles.isEmpty()) {
            throw new RuntimeException("No windows available");
        }
        driver.switchTo().window(handles.get(handles.size() - 1));
        System.out.println("✓ Switched to last window");
    }

    /**
     * Switch to window by index
     */
    public void switchToWindowByIndex(int index) {
        List<String> handles = new ArrayList<>(driver.getWindowHandles());
        if (index < 0 || index >= handles.size()) {
            throw new RuntimeException("Window index " + index + " is out of range");
        }
        driver.switchTo().window(handles.get(index));
        System.out.println("✓ Switched to window at index: " + index);
    }
}


