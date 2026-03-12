package com.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.Set;

public class iFrame_PassengerDisabilityPage {
    private WebDriver driver;

    @FindBy(how = How.XPATH, using = "//*[@class='info-tooltip-icon']")
    private WebElement passengerDisabilityPage;

    @FindBy(how = How.XPATH, using = "//button[contains(text(),'Products')]")
    private WebElement addOns;

    @FindBy(how =How.XPATH,using = "//h4[contains(text(),'Passenger with Disability or Assistance Needed?')]")
    private WebElement passengerDisabilityPageHeading;


    @FindBy(how = How.XPATH, using = "//img[@class='close-button info-icon']")
    private WebElement passengerDisabilityFrame;

    @FindBy(how = How.XPATH, using = "//button[contains(@class,'traveler-dropdown-button')]")
    private WebElement travellerDropdown;

    @FindBy(how=How.XPATH, using = "//div[@id='passengertype-adult']//button[contains(@class,'increment')]")
    private WebElement adultIncrement;

    @FindBy(how=How.XPATH, using = "//button[@class='close pull-right']")
    private WebElement passengerCloeButton;

    Set<String> winsList;

    public iFrame_PassengerDisabilityPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void click_iFrame_PassengerDisabilityPage() {
        passengerDisabilityPage.click();
    }

    public void switchToiFrame() {

          // driver.getWindowHandles();
            System.out.println("Old window----" + driver.getCurrentUrl());
            System.out.println("Parent title----" + driver.getTitle());

            winsList = driver.getWindowHandles();
            for (String list : winsList) {
                String winTitle = driver.switchTo().window(list).getTitle();
                if (winTitle.contains("Passenger with Disability or Assistance Needed?")) {
                    driver.switchTo().window(list);
                }
            }

        }

        public void verifyHeading() {
            String expectedTitle = "Passenger with Disability or Assistance Needed?";
            String actualTitle = passengerDisabilityPageHeading.getText();
            if (actualTitle.equals(expectedTitle)) {
                System.out.println("Heading is correct: " + actualTitle);
            } else {
                System.out.println("Heading is incorrect. Expected: " + expectedTitle + ", but got: " + actualTitle);
            }
        }

        public void clickTravellerDropdown(){
            travellerDropdown.click();
        }

        public void clickAdultIncrement(){
            adultIncrement.click();
            adultIncrement.click();
            passengerCloeButton.click();
        }

        public void clickTripTypeDropdown() throws InterruptedException {
            addOns.click();
            Thread.sleep(2000);
        }


        public void closeiFrame() {
            passengerDisabilityFrame.click();
        }

    }

