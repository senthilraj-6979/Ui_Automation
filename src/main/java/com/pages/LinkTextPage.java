package com.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

public class LinkTextPage {
    WebDriver driver;
    
    @FindBy(linkText="Cart")
    WebElement cart;
    @FindBy(linkText="Home")
    WebElement home;
    @FindBy(xpath="//a")
    List<WebElement> hyperLink;

    public LinkTextPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickHome() {
        if (home.isSelected()) {
            home.click();
        }
    }

    public void clickCart() {
        if (cart.isSelected()) {
            cart.click();
        }
    }

    public void countHyperLink() throws IOException {
        for(int index=0;index< hyperLink.size();index++){
            System.out.println(hyperLink.get(index).getText());
        }
        int brokenCount=0;
        for(WebElement element:hyperLink){
            String url = element.getAttribute("href");
            System.out.println("url-----------"+url);
            try{
                URL validate = new URL(url);
                HttpURLConnection hURL = (HttpURLConnection) validate.openConnection();
                hURL.connect();
                if(url!=null && hURL.getResponseCode()>=400){
                    brokenCount++;
                    System.out.println("broken-->"+hURL);
                }
                else {
                    System.out.println(hURL);
                }
        //        driver.quit();
            }
           catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("BrokenCount+----"+ brokenCount);
    }
}
