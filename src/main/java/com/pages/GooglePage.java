package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GooglePage {
    private final WebDriver driver;

    @FindBy(xpath = "//textarea[@class='gLFyf']")
    WebElement googleSearchInput;


    public GooglePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void enterSearchTerm(String searchTerm) {
        googleSearchInput.sendKeys(searchTerm);
    }

    public void clickSearchButton() {
        Actions actions = new Actions(driver);
        WebElement button = driver.findElement(By.id("gbqfbb"));
        actions.moveToElement(button).click().perform();
    }

}
