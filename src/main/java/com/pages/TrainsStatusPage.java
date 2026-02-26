package com.pages;

import com.utilities.UIActionUtility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class TrainsStatusPage {


    private WebDriver driver;
    @FindBy(how = How.XPATH, using = "//li[@class='nav-item']//a[contains(text(),'TRAIN STATUS')]")
    private WebElement trainStatus;

    @FindBy(how = How.ID, using = "mat-input-6")
    private WebElement fromStation;
    @FindBy(how = How.ID, using = "mat-input-7")
    private WebElement toStation;

    @FindBy(how = How.XPATH, using = "//img[@class='cal-icon']")
    private WebElement clickCalendar;

    @FindBy(how = How.XPATH, using = "//div[(@class='ngb-dp-day ng-star-inserted')  and not(contains(@class,'disabled'))]//span[contains(text(),'27')]")
    private WebElement selectDate;

    @FindBy(how=How.XPATH, using="//button[contains(text(),'CHECK STATUS')]")
    private WebElement checkStatus;

    @FindBy(how=How.XPATH, using="//span[@class='dropdown-btn1-icon1']")
    private WebElement trainNumber;

    @FindBy(how=How.XPATH, using="//div[contains(@class,'dropdown am-dropdown--expanded')]//descendant::div[contains(text(),'Train Number')]")
    private WebElement selectTrainNumber;

    @FindBy(how=How.NAME, using="trainNoField")
    private WebElement trainName;

    @FindBy(how=How.ID, using="mat-input-13")
    private WebElement station;

    public TrainsStatusPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickTrainStatus() {
        UIActionUtility.isElementDisplayed(trainStatus);
        UIActionUtility.clickElement(trainStatus);
        UIActionUtility.isElementEnabled(trainStatus);
    }

    public void trainStatusFromSationCode(String departCode) throws InterruptedException {
        UIActionUtility.isElementDisplayed(fromStation);
        UIActionUtility.typeText(fromStation, departCode);
    }

    public void trainStatusToSationCode(String arrivalCode) throws InterruptedException {
        UIActionUtility.isElementDisplayed(toStation);
        UIActionUtility.typeText(toStation, arrivalCode);
    }


    public void select_train_status_Calender() {
        UIActionUtility.isElementDisplayed(clickCalendar);
        UIActionUtility.clickElement(clickCalendar);
    }


    public void select_train_status_Date() {
        UIActionUtility.isElementDisplayed(selectDate);
        UIActionUtility.clickElement(selectDate);
       }

    public void clickCheckStatus(){
        UIActionUtility.isElementDisplayed(checkStatus);
        UIActionUtility.clickElement(checkStatus);
    }

    public void selectTrainNumber(){
        UIActionUtility.isElementDisplayed(trainNumber);
        UIActionUtility.clickElement(trainNumber);
        UIActionUtility.isElementEnabled(trainNumber);
    }

    public void selectTrainNumberOption(){
        UIActionUtility.isElementDisplayed(selectTrainNumber);
        UIActionUtility.clickElement(selectTrainNumber);
      }

    public void enterTrainName(String trainNo){
        UIActionUtility.isElementDisplayed(trainName);
        UIActionUtility.typeText(trainName,trainNo);
    }

    public void enterStationCode(String stationCode){
        UIActionUtility.isElementDisplayed(station);
        UIActionUtility.typeText(station,stationCode);
    }

}
