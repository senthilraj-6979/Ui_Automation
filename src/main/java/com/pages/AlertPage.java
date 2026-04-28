package com.pages;

import com.utilities.UIActionUtility;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AlertPage {
    private WebDriver driver;

    // Correct @FindBy syntax with linkText
    @FindBy(linkText = "Alerts")
    private WebElement alertLnk;

    @FindBy(id = "alertButton")
    private WebElement alertBtn;

    @FindBy(id = "timerAlertButton")
    private WebElement timerAlertBtn;

    @FindBy(id = "confirmButton")
    private WebElement confirmBtn;

    @FindBy(id = "promtButton")
    private WebElement promptBtn;

    // Constructor to initialize WebDriver and PageFactory
    public AlertPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ============= Navigation Methods =============

    public void clickAlertLink() {
        UIActionUtility.waitForElementToBeClickable(driver, alertLnk);
        alertLnk.click();
    }

    public void clickAlertButton() {
        UIActionUtility.waitForElementToBeClickable(driver, alertBtn);
        alertBtn.click();
        // No sleep here - alert will appear immediately
    }

    public void clickPromptButton() {
        UIActionUtility.waitForElementToBeClickable(driver, promptBtn);
        promptBtn.click();
        // Add small delay to allow dialog to appear
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clickConfirmButton() {
        UIActionUtility.waitForElementToBeClickable(driver, confirmBtn);
        confirmBtn.click();
        // Add small delay to allow dialog to appear
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void timerAltertButton() throws InterruptedException {
        UIActionUtility.waitForElementToBeClickable(driver, timerAlertBtn);
        timerAlertBtn.click();
    }

    // ============= Alert Handling Methods =============

    /**
     * Handle Simple Alert - Click OK button
     * JavaScript Alert: alert("message")
     */
    public void acceptSimpleAlert() {
        Alert alert = waitForAlertAndGetIt(15);
        alert.accept();
        // Small delay to ensure alert is fully closed
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Handle Confirm Alert - Click OK button
     * JavaScript Confirm: confirm("message")
     */
    public void acceptConfirmAlert() {
        Alert alert = waitForAlertAndGetIt(15);  // Increased timeout to 15 seconds
        alert.accept();
    }

    /**
     * Handle Confirm Alert - Click Cancel button (Dismiss)
     * JavaScript Confirm: confirm("message")
     */
    public void dismissConfirmAlert() {
        Alert alert = waitForAlertAndGetIt(10);
        alert.dismiss();
    }

    /**
     * Handle Prompt Alert - Enter text and Click OK
     * JavaScript Prompt: prompt("message")
     * @param text Text to enter in the prompt
     */
    public void sendTextAndAcceptPrompt(String text) {
        Alert alert = waitForAlertAndGetIt(15);  // Wait for alert

        try {
            // Send the text to the prompt
            alert.sendKeys(text);

            System.out.println("Text entered: " + text);

            // Small delay to ensure text is entered
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // Ignore
            }

            // Click OK button using accept
            alert.accept();

            System.out.println("Prompt alert accepted");

        } catch (org.openqa.selenium.UnhandledAlertException e) {
            System.err.println("Unhandled alert exception: " + e.getMessage());
            try {
                driver.switchTo().alert().accept();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (Exception e) {
            System.err.println("Error entering text in prompt: " + e.getMessage());
            System.err.println("Exception type: " + e.getClass().getName());
            e.printStackTrace();

            // Try to accept anyway
            try {
                Alert retryAlert = driver.switchTo().alert();
                retryAlert.accept();
                System.out.println("Alert accepted via retry");
            } catch (Exception e2) {
                System.err.println("Failed to accept alert in retry: " + e2.getMessage());
                e2.printStackTrace();
            }
        }
    }

    /**
     * Handle Prompt Alert - Click Cancel without entering text
     * JavaScript Prompt: prompt("message")
     */
    public void dismissPromptAlert() {
        Alert alert = waitForAlertAndGetIt(10);
        alert.dismiss();
    }


    /**
     * Wait for an alert to appear
     * @param timeoutInSeconds Timeout in seconds
     * @return Alert object
     */
    private Alert waitForAlertAndGetIt(int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.until(ExpectedConditions.alertIsPresent());
        return driver.switchTo().alert();
    }


    // ============= Button Click Methods with Alert Handling =============

    /**
     * Click Alert button and immediately handle the alert
     */
    public void clickAlertButtonAndAccept() {
        clickAlertButton();
        acceptSimpleAlert();
    }
}
