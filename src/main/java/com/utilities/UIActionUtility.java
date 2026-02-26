package com.utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

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
}
