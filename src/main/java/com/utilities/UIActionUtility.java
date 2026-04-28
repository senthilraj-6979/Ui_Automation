package com.utilities;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class UIActionUtility {

    private static WebDriver driver;
    JavascriptExecutor js;

    public UIActionUtility(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public synchronized void setJs(JavascriptExecutor js) {
        this.js = js;
    }

    public synchronized JavascriptExecutor getJs() {
        return js;
    }

    public static boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean isElementEnabled(WebElement element) {
        try {
            return element.isEnabled();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean clickElement(WebElement element) {
        try {
            element.click();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void typeText(WebElement element, String text) {
        clickElement(element);
        element.clear();
        element.sendKeys(text);
    }

    public static void selectDropdownValue(WebElement element, String selectType, String value) {
        try {
            Select select = new Select(element);

            switch (selectType.toLowerCase()) {
                case "visibletext":
                    select.selectByVisibleText(value);
                case "index":
                    select.selectByIndex(Integer.parseInt(value));
                case "value":
                    select.selectByValue(value);
            }

        } catch (Exception e) {

        }
    }

    public boolean javaScriptClick(WebElement element) throws InterruptedException {
        try {
            getJs().executeScript("arguments[0].click():", element);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getCurrentUrl() {
        String currrentUrl = null;
        try {
            currrentUrl = driver.getCurrentUrl();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currrentUrl;
    }

    public static void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean switchToWindow(String title, List<String> winsList) {
        for (String list : winsList) {
            String winTitle = driver.switchTo().window(list).getTitle();
            if (winTitle.contains(title)) {
                System.out.println("*** Right Window ***");
            }
            return true;
        }
        return false;
    }

    public static void switchToParentWindow(String parentId) {
        driver.switchTo().window(parentId);
    }

    public static void closeAllWindows(){


    }

    /**
     * Wait for element to be clickable and return it
     * @param driver WebDriver instance
     * @param locator By locator (e.g., By.xpath, By.id)
     * @param timeoutInSeconds Timeout in seconds
     * @return Clickable WebElement
     */
    public static WebElement waitAndGetClickableElement(WebDriver driver, By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        return driver.findElement(locator);
    }

    /**
     * Wait for element to be clickable and return it (default 10 seconds timeout)
     * @param driver WebDriver instance
     * @param locator By locator
     * @return Clickable WebElement
     */
    public static WebElement waitAndGetClickableElement(WebDriver driver, By locator) {
        return waitAndGetClickableElement(driver, locator, 10);
    }

    /**
     * Wait for element to be clickable and click a freshly located element.
     * Useful when the DOM refreshes and older references go stale.
     * @param driver WebDriver instance
     * @param locator By locator
     * @param timeoutInSeconds Timeout in seconds
     */
    public static void waitAndClickElement(WebDriver driver, By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.elementToBeClickable(locator));
        driver.findElement(locator).click();
    }

    /**
     * Wait for element to be clickable and click a freshly located element (default 10 seconds timeout).
     * @param driver WebDriver instance
     * @param locator By locator
     */
    public static void waitAndClickElement(WebDriver driver, By locator) {
        waitAndClickElement(driver, locator, 10);
    }

    /**
     * Wait for element to be visible and return it
     * @param driver WebDriver instance
     * @param locator By locator
     * @param timeoutInSeconds Timeout in seconds
     * @return Visible WebElement
     */
    public static WebElement waitAndGetVisibleElement(WebDriver driver, By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElement(locator);
    }

    /**
     * Wait for element to be visible and return it (default 10 seconds timeout)
     * @param driver WebDriver instance
     * @param locator By locator
     * @return Visible WebElement
     */
    public static WebElement waitAndGetVisibleElement(WebDriver driver, By locator) {
        return waitAndGetVisibleElement(driver, locator, 10);
    }

    /**
     * Wait for element to be present and return it
     * @param driver WebDriver instance
     * @param locator By locator
     * @param timeoutInSeconds Timeout in seconds
     * @return Present WebElement
     */
    public static WebElement waitAndGetPresentElement(WebDriver driver, By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return driver.findElement(locator);
    }

    /**
     * Wait for element to be present and return it (default 10 seconds timeout)
     * @param driver WebDriver instance
     * @param locator By locator
     * @return Present WebElement
     */
    public static WebElement waitAndGetPresentElement(WebDriver driver, By locator) {
        return waitAndGetPresentElement(driver, locator, 10);
    }

    /**
     * Wait for element to be clickable using WebElement
     * @param driver WebDriver instance
     * @param element WebElement
     * @param timeoutInSeconds Timeout in seconds
     * @return Clickable WebElement
     */
    public static WebElement waitForElementToBeClickable(WebDriver driver, WebElement element, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Wait for element to be clickable using WebElement (default 10 seconds timeout)
     * @param driver WebDriver instance
     * @param element WebElement
     * @return Clickable WebElement
     */
    public static WebElement waitForElementToBeClickable(WebDriver driver, WebElement element) {
        return waitForElementToBeClickable(driver, element, 10);
    }



}
