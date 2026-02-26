package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class LoginPage {

    private WebDriver driver;

//    private By emailId = By.xpath("//input[@data-qa='login-email']");
//    private By password = By.xpath("//input[@data-qa='login-password']");
//    private By lgn_btn = By.xpath("//button[contains(text(),'Login')]");


    @FindBy(xpath = "//input[@data-qa='login-email']")
    WebElement emailId;
    @FindBy(xpath = "//input[@data-qa='login-password']")
    WebElement password;
    @FindBy(xpath = "//button[contains(text(),'Login')]")
    WebElement lgn_btn;

    @FindBy(xpath = "//a")
    List<WebElement> hyperLink;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public void enterEmailId(String uName) {
        emailId.sendKeys(uName);
    }

    public void enterPwd(String pwd) {
        password.sendKeys(pwd);
    }

    public void clickLogin() {
        lgn_btn.click();
    }
}
