package com.pages;

import com.utilities.UIActionUtility;
import io.cucumber.java.bs.A;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.Set;


public class MouseAction {
    private WebDriver driver;

    Set<String> windowHandles;

    @FindBy(how = How.XPATH, using = "//button[contains(text(),'Products')]")
    private WebElement addOns;


    public MouseAction(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public void mouse_hover() {

        driver.getWindowHandles();

        windowHandles = driver.getWindowHandles();
//        windows = new ArrayList<String>(windowHandles);
//        driver.switchTo().window(windows.get(1));


        String Parent_Window = driver.getWindowHandle();
        System.out.println("PARENT WINDOW-------" + Parent_Window);

        for (String Child_Window : driver.getWindowHandles()) {
            driver.switchTo().window(Child_Window);
        }

        System.out.println("Old window----" + driver.getCurrentUrl());
        System.out.println("Parent title----" + driver.getTitle());

        Actions mouse = new Actions(driver);
        mouse.moveToElement(addOns).perform();
        UIActionUtility.clickElement(addOns);
    }


}
