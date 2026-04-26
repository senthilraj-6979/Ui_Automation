package com.pages;

import com.utilities.UIActionUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class iFrame_PassengerDisabilityPage {
    private final WebDriver driver;
    private final By passengerDisabilityPage = By.xpath("//*[@class='info-tooltip-icon']");
    private final By addOns = By.xpath("//button[contains(text(),'Products')]");
    private final By passengerDisabilityPageHeading = By.xpath("//h4[contains(text(),'Passenger with Disability or Assistance Needed?')]");
    private final By passengerDisabilityFrame = By.xpath("//img[@class='close-button info-icon']");
    private final By travellerDropdown = By.xpath("//button[contains(@class,'traveler-dropdown-button')]");
    private final By adultIncrement = By.xpath("//div[@id='passengertype-adult']//button[contains(@class,'increment')]");
    private final By passengerCloseButton = By.xpath("//button[@class='close pull-right']");
    private static final int DEFAULT_TIMEOUT_SECONDS = 15;

    Set<String> winsList;

    public iFrame_PassengerDisabilityPage(WebDriver driver) {
        this.driver = driver;
    }

    public void click_iFrame_PassengerDisabilityPage() {
        UIActionUtility.waitAndClickElement(driver, passengerDisabilityPage, DEFAULT_TIMEOUT_SECONDS);
    }

    public void switchToiFrame() {
        System.out.println("Old window----" + driver.getCurrentUrl());
        System.out.println("Parent title----" + driver.getTitle());

        try {
            new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT_SECONDS)).until(ExpectedConditions.numberOfWindowsToBe(2));
        } catch (TimeoutException ignored) {
            // Continue with whatever windows are currently available.
        }

        winsList = driver.getWindowHandles();
        for (String list : winsList) {
            String winTitle = driver.switchTo().window(list).getTitle();
            if (winTitle.contains("Passenger with Disability or Assistance Needed?")) {
                driver.switchTo().window(list);
                return;
            }
        }
    }

    public void verifyHeading() {
        String expectedTitle = "Passenger with Disability or Assistance Needed?";
        String actualTitle = UIActionUtility.waitAndGetVisibleElement(driver, passengerDisabilityPageHeading, DEFAULT_TIMEOUT_SECONDS).getText();
        if (actualTitle.equals(expectedTitle)) {
            System.out.println("Heading is correct: " + actualTitle);
        } else {
            System.out.println("Heading is incorrect. Expected: " + expectedTitle + ", but got: " + actualTitle);
        }
    }

    public void clickTravellerDropdown(){
        UIActionUtility.waitAndClickElement(driver, travellerDropdown, DEFAULT_TIMEOUT_SECONDS);
    }

    public void clickAdultIncrement(){
        UIActionUtility.waitAndClickElement(driver, adultIncrement, DEFAULT_TIMEOUT_SECONDS);
        UIActionUtility.waitAndClickElement(driver, adultIncrement, DEFAULT_TIMEOUT_SECONDS);
        UIActionUtility.waitAndClickElement(driver, passengerCloseButton, DEFAULT_TIMEOUT_SECONDS);
    }

    public void clickTripTypeDropdown() {
        UIActionUtility.waitAndClickElement(driver, addOns, DEFAULT_TIMEOUT_SECONDS);
    }

    public void closeiFrame() {
        UIActionUtility.waitAndClickElement(driver, passengerDisabilityFrame, DEFAULT_TIMEOUT_SECONDS);
    }
}
