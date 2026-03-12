package com.pages;

import com.utilities.UIActionUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class DropDownPage {
    private final WebDriver driver;
    private final By tripTypeDropdown = By.xpath("//button[contains(@class,'ff-top-navbar__button')]");
    private static final String NORMALIZED_TEXT_XPATH =
            "translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ-', 'abcdefghijklmnopqrstuvwxyz ')";

    @FindBy(how = How.ID, using = "languageDropdown")
    private WebElement languageDropdown;


    public DropDownPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void select_tripType() {
        UIActionUtility.waitAndGetClickableElement(driver, tripTypeDropdown).click();
    }

    public void selectTripType(String tripType) {
        String normalizedTripType = tripType
                .replace("-", " ")
                .replaceAll("\\s+", " ")
                .trim()
                .toLowerCase();

        By tripTypeSelection = By.xpath("//button[" + NORMALIZED_TEXT_XPATH + "='" + normalizedTripType + "']");

        if (!isTripTypeOptionVisible(tripTypeSelection)) {
            select_tripType();
        }
        UIActionUtility.waitAndGetClickableElement(driver, tripTypeSelection, 50).click();
    }

    private boolean isTripTypeOptionVisible(By tripTypeSelection) {
        List<WebElement> matchingOptions = driver.findElements(tripTypeSelection);
        for (WebElement matchingOption : matchingOptions) {
            if (matchingOption.isDisplayed()) {
                return true;
            }
        }
        return false;
    }

    public void languageDropdown() throws InterruptedException {
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

