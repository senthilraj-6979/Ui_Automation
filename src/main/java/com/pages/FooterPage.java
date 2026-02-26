package com.pages;

import com.aventstack.extentreports.util.Assert;
import com.utilities.UIActionUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FooterPage {


    private WebDriver driver;

    Set<String> windowHandles;
    Set<String> winsList;
    List<String> windows;

    @FindBy(how = How.XPATH, using = "//i[@class='fab fa-facebook']")
    private WebElement faceBook;

    @FindBy(how = How.XPATH, using = "//form[@id='login_popup_cta_form']//input[@name='email']")
    private WebElement faceBookEmail;

    @FindBy(how = How.XPATH, using = "//div[@aria-label='Close']")
    private WebElement closeFaceBookFrame;

    @FindBy(how = How.XPATH, using = "//i[@class='fab fa-twitter']")
    private WebElement twitter;


    public FooterPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickFacebookIcon() {
        UIActionUtility.isElementDisplayed(faceBook);
        UIActionUtility.clickElement(faceBook);
        UIActionUtility.isElementDisplayed(faceBook);
    }

    public void windowsSwitch() {
        driver.getWindowHandles();
        System.out.println("Old window----" + driver.getCurrentUrl());
        System.out.println("Parent title----" + driver.getTitle());
//        windowHandles = driver.getWindowHandles();
//        windows = new ArrayList<String>(windowHandles);
//        driver.switchTo().window(windows.get(1));

        winsList = driver.getWindowHandles();
        for (String list : winsList) {
            String winTitle = driver.switchTo().window(list).getTitle();

            if (winTitle.contains("Washington")) {
                driver.switchTo().window(list);
                System.out.println("NEW Windows Title-------" + winTitle);
             }

        }
      //  driver.switchTo().defaultContent();
      //s  System.out.println("Windows Title-------");

    }

    public void enterFBuserID(String fbLoginID) {

        UIActionUtility.waitForSeconds(2);
        UIActionUtility.isElementDisplayed(faceBookEmail);
        UIActionUtility.typeText(faceBookEmail, fbLoginID);
        UIActionUtility.waitForSeconds(2);
    }

    public void closeFacebookFrame() {

        UIActionUtility.isElementDisplayed(closeFaceBookFrame);
        UIActionUtility.clickElement(closeFaceBookFrame);
        UIActionUtility.waitForSeconds(2);

    }

    public void closeFacebookWindow() {
        windowHandles = driver.getWindowHandles();
        //   windows.clear();
        //   windows = new ArrayList<String>(windowHandles);
        driver.close();
        driver.switchTo().defaultContent();
    }

    public void clickTwitterIcon() {
        UIActionUtility.isElementDisplayed(twitter);
        UIActionUtility.clickElement(twitter);

    }

}


