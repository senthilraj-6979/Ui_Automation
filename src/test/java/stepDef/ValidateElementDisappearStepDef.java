package stepDef;

import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import webevents.PageBase;

public class ValidateElementDisappearStepDef extends PageBase {


    @When("Verify the element is disappeared")
    public void verify_the_element_is_disappeared() {
        driver.findElement(By.xpath("//a[@href='/codingground.htm']")).click();
        // explicit wait of invisibility condition
        WebDriverWait w = new WebDriverWait(driver,5);
        // invisibilityOfElementLocated condition
        w.until(ExpectedConditions.
                invisibilityOfElementLocated(By.xpath("//a[@href='/codingground.htm']")));
        // get page title of next page
        System.out.println("Page title after click:" + driver.getTitle());
        driver.quit();
    }
}
