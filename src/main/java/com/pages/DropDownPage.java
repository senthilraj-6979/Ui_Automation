package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DropDownPage {
    WebDriver driver;

    private WebElement country = driver.findElement(By.name("country"));
}
