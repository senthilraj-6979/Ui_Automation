package com.pages;

import com.aventstack.extentreports.util.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class PraticeFormPage {

    private WebDriver driver;


    @FindBy(xpath = "//h1[contains(text(),'Practice Form')]")
    WebElement PraticeForm_Lbl;
    @FindBy(xpath = "//input[@id='firstName']")
    WebElement userName_txt;
    @FindBy(xpath = "//input[@id='lastName']")
    WebElement lastName_txt;

    @FindBy(xpath = "//input[@id='userEmail']")
    WebElement email_txt;

    @FindBy(xpath = "//input[@value='Other']")
    WebElement gender;

    @FindBy(how = How.ID, using = "submit")
    WebElement submit_btn;


    public PraticeFormPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public String pageHeader(String pageHeader){
         pageHeader = PraticeForm_Lbl.getText();
        return pageHeader;
    }

    public void enterFirstName(String uName) {
        userName_txt.sendKeys(uName);
    }

    public void enterLastName(String pwd) {
        lastName_txt.sendKeys(pwd);
    }



    public void clickSubmit() {
        submit_btn.click();
    }
}
