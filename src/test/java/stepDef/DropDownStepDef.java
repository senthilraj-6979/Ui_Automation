package stepDef;

import com.utilities.UIActionUtility;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import webevents.PageBase;

import javax.swing.*;
import java.util.List;
import java.util.Set;

public class DropDownStepDef extends PageBase {

    @Given("Lanuch the URL {string}")
    public void lanuch_(String url) {
        driver = new ChromeDriver();
        driver.get(url);
        driver.manage().window().maximize();
    }

    @When("User select click and select the value from drop down")
    public void user_select_click_and_select_the_value_from_drop_down() throws InterruptedException {
        Thread.sleep(1500);
        WebElement country = driver.findElement(By.name("country"));
        Select drpCountry = new Select(country);

        List<WebElement> dropList = drpCountry.getOptions();
        for (WebElement dList : dropList) {
            System.out.println("List values --" + dList.getText());
            if (dList.getText().endsWith("INDIA")) {
                dList.click();
            }
        }
        drpCountry.selectByVisibleText("ANTARCTICA");

        //Selecting Items in a Multiple SELECT elements
        driver.get("http://jsbin.com/osebed/2");
        Select fruits = new Select(driver.findElement(By.id("fruits")));
        fruits.selectByVisibleText("Banana");
        fruits.selectByVisibleText("Grape");
        fruits.selectByIndex(1);
        driver.quit();
    }

    @When("User select radio button from drop down")
    public void user_select_radio_button_from_drop_down() throws InterruptedException {
        WebElement readioBtn = driver.findElement(By.id("vfb-7-1"));
        Assert.assertTrue(readioBtn.isDisplayed());
        readioBtn.click();
        readioBtn.isSelected();
    }

    @Then("User select checkboxes")
    public void selectCheckbox() {
        System.out.println("URL----" + driver.getCurrentUrl());
        WebElement checkBox = driver.findElement(By.xpath("//strong[contains(text(),'Checkbox')]//preceding-sibling::input"));
        WebElement checkBox1 = driver.findElement(By.id("vfb-6-0"));
        checkBox1.click();
        Assert.assertTrue(checkBox1.isSelected());
        driver.quit();

    }

    @Then("User zoom {string} screen")
    public void xoomScreen(int zoomPercent) {

        try {
            JavascriptExecutor js = null;
            //     js.executeScript("document.body.style.zoom='"+zoomPercent+"%';");
            js.executeScript("document.body.style.zoom = '300';");
            System.out.println("Screen zoom " + zoomPercent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}