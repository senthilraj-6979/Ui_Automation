package com.pages;

import com.utilities.UIActionUtility;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {


    private WebDriver driver;

    @FindBy(how = How.XPATH, using = "//*[@id='onetrust-accept-btn-handler']")
    private WebElement acceptAllBtn;

    @FindBy(how = How.XPATH, using = "//*[@id='guest-reward-desktop']")
    private WebElement guestRewardDesktop;

    @FindBy(how = How.CSS, using = "a#header-sign-in")
    private WebElement signInButton;

    @FindBy(how = How.XPATH, using = "//*[@amt-auto-test-id='fare-finder-travel-selection']")
    private WebElement travelType;

    @FindBy(how = How.XPATH, using = "//*[@amt-auto-test-id='fare-finder-oneway-trip-tab']")
    private WebElement onewayoption;

    @FindBy(how = How.XPATH, using = "//*[@amt-auto-test-id='fare-finder-from-station-field-page']")
    private WebElement fromStation;

    @FindBy(how = How.ID, using = "mat-input-0")
    private WebElement depart;

    @FindBy(how = How.ID, using = "mat-input-1")
    private WebElement arrival;

    @FindBy(how = How.ID, using = "mat-input-2")
    private WebElement departdatefield;

    @FindBy(how = How.XPATH, using = "//*[@amt-auto-test-id='fare-finder-to-station-field-page']")
    private WebElement toStation;

    @FindBy(how = How.XPATH, using = "//span[@class='calendar-day today ng-star-inserted']")
    private WebElement departDate;

    @FindBy(how = How.XPATH, using = "//button[contains(text(),'Done')]")
    private WebElement doneButton;

    @FindBy(how = How.XPATH, using = "//button[contains(text(),'FIND TRAINS')]")
    private WebElement findTrain;

    @FindBy(how = How.ID, using = "languageDropdown")
    private WebElement languageDropdown;

    @FindBy(how = How.XPATH, using = "//ul[@id='language-content']//descendant::li//a)")
    private List<WebElement> language;

    @FindBy(how = How.XPATH, using = "//ul[@id='language-content']//descendant::li//a[contains(text(),'Español')")
    private WebElement spanish;

    @FindBy(how = How.XPATH, using = "//ul[@id='language-content']//descendant::li//a[contains(text(),'中文')]")
    private WebElement chinese;

    @FindBy(how = How.XPATH, using = "//ul[@id='language-content']//descendant::li//a[contains(text(),'Español')]")
    private WebElement french;

    @FindBy(how = How.XPATH, using = "//button[@amt-auto-test-id='fare-finder-travel-selection']")
    private WebElement oneway;

    @FindBy(how = How.XPATH, using = "//button[contains(text(),'Multi-City')]")
    private WebElement multiCity;

    @FindBy(how = How.XPATH, using = "//button[contains(text(),'Round-Trip')")
    private WebElement roundTrip;

    @FindBy(how = How.XPATH, using = "//*[@amt-auto-test-id='fare-finder-redeem-points']//input")
    private WebElement usePoints;

    @FindBy(how = How.ID, using = "am-check-0")
    private WebElement assistanceCheckbox;

    @FindBy(how = How.XPATH, using = "//div[@class='agr-popup-content']")
    private WebElement agrPopup;


    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickAcceptAllCookies() {
        UIActionUtility.isElementDisplayed(acceptAllBtn);
        acceptAllBtn.click();
    }

    public void clickGuestRewards() {
        UIActionUtility.clickElement(guestRewardDesktop);
    }

    public void clickSignIn() {
        UIActionUtility.clickElement(signInButton);
    }

    public void clickOneway() {
        UIActionUtility.clickElement(travelType);
    }

    public void selectOneway() {
        UIActionUtility.clickElement(onewayoption);
    }

    public void enterFromStation(String from) throws InterruptedException {
        UIActionUtility.clickElement(fromStation);
        UIActionUtility.typeText(depart, from);
    }

    public void enterToStation(String to) throws InterruptedException {
        UIActionUtility.clickElement(toStation);
        UIActionUtility.typeText(arrival, to);
    }

    public void enterClicksDepartDateField() throws InterruptedException {
        UIActionUtility.clickElement(departdatefield);
    }

    public void selectDepartDate() throws InterruptedException {
        UIActionUtility.clickElement(departDate);
    }

    public void clickDone() throws InterruptedException {
        UIActionUtility.clickElement(doneButton);
    }

    public void clickFindTrains() throws InterruptedException {
        UIActionUtility.clickElement(findTrain);
    }

    public void languageDropdown(String selectedLanguage) throws InterruptedException {
        UIActionUtility.clickElement(languageDropdown);
        System.out.println("**language****  " + selectedLanguage.toLowerCase());
        //   for (WebElement language : languageList) {
        switch (selectedLanguage.toLowerCase()) {
            case "chinese":
                UIActionUtility.clickElement(chinese);
                break;
            case "español":
                UIActionUtility.clickElement(spanish);
                break;
            case "français":
                UIActionUtility.clickElement(french);
                break;
        }
    }

    public void clickOnewayDefault() throws InterruptedException {
        UIActionUtility.clickElement(oneway);
    }

    public void selectMultiCity() throws InterruptedException {
        UIActionUtility.isElementDisplayed(multiCity);
        UIActionUtility.clickElement(multiCity);
    }

    public void roundTrip(String tripType) throws InterruptedException {

        //button[contains(text(),'Multi-City')]

        driver.findElement(By.xpath("//button[contains(text(),'"+tripType+"')]")).click();
        Thread.sleep(1000);


    }


    public void enableUserPoints() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click()", usePoints);
    }
    public void assistanceCheckbox() throws InterruptedException {
        UIActionUtility.clickElement(assistanceCheckbox);
        Thread.sleep(2000);
    }


    public void agrPopup() throws InterruptedException {
        UIActionUtility.isElementDisplayed(agrPopup);
        Thread.sleep(1000);

    }
}


