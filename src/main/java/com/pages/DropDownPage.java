package com.pages;

import com.utilities.UIActionUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DropDownPage {

    private static final Logger logger = LogManager.getLogger(DropDownPage.class);


    private final WebDriver driver;
    private final By tripTypeDropdown = By.xpath("//button[contains(@class,'ff-top-navbar__button')]");
    private static final String NORMALIZED_TEXT_XPATH =
            "translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ-', 'abcdefghijklmnopqrstuvwxyz ')";
    private static final int TRIP_TYPE_TIMEOUT_SECONDS = 15;
    private static final int MAX_TRIP_TYPE_CLICK_ATTEMPTS = 3;

    @FindBy(how = How.ID, using = "languageDropdown")
    private WebElement languageDropdown;


    public DropDownPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void select_tripType()
    {
        logger.info("select_tripType: Attempting to click trip type dropdown");
        UIActionUtility.waitAndGetClickableElement(driver, tripTypeDropdown).click();
    }

    public void selectTripType(String tripType) {
        String normalizedTripType = tripType
                .replace("-", " ")
                .replaceAll("\\s+", " ")
                .trim()
                .toLowerCase();

        By tripTypeSelection = By.xpath("//button[" + NORMALIZED_TEXT_XPATH + "='" + normalizedTripType + "']");
        clickTripTypeWithRetry(tripTypeSelection);
    }

    private void clickTripTypeWithRetry(By tripTypeSelection) {
        RuntimeException lastException = new TimeoutException("Unable to click trip type option: " + tripTypeSelection);

        for (int attempt = 1; attempt <= MAX_TRIP_TYPE_CLICK_ATTEMPTS; attempt++) {
            try {
                ensureTripTypeOptionIsOpen(tripTypeSelection);
                UIActionUtility.waitAndClickElement(driver, tripTypeSelection, TRIP_TYPE_TIMEOUT_SECONDS);
                return;
            } catch (StaleElementReferenceException | ElementClickInterceptedException | TimeoutException exception) {
                lastException = exception;
            }
        }

        throw lastException;
    }

    private void ensureTripTypeOptionIsOpen(By tripTypeSelection) {
        logger.info("ensureTripTypeOptionIsOpen---");
        if (!isTripTypeOptionVisible(tripTypeSelection)) {
            select_tripType();
        }
    }

    private boolean isTripTypeOptionVisible(By tripTypeSelection) {
        try {
            List<WebElement> matchingOptions = driver.findElements(tripTypeSelection);
            for (WebElement matchingOption : matchingOptions) {
                if (matchingOption.isDisplayed()) {
                    return true;
                }
            }
        } catch (StaleElementReferenceException ignored) {
            return false;
        }
        return false;
    }

    public void languageDropdown() {

        logger.info("languageDropdown -------");

        languageDropdown.click();
        List<WebElement> languageOptions = driver.findElements(By.xpath("//ul[@class='dropdown-menu']//a"));
        for (WebElement option : languageOptions) {
            System.out.println("Language option: " + option.getText());
        }
        // Example: Select English
        for (WebElement option : languageOptions) {
            if (option.getText().equalsIgnoreCase("Español")) {
                option.click();
                break;
            }
        }
    }

}
