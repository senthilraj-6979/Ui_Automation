package stepDef;

import com.qa.factory.DriverFactory;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import webevents.PageBase;

public class ValidateElementDisappearStepDef extends PageBase {


    @When("Verify the element is disappeared")
    public void verify_the_element_is_disappeared() {
        DriverFactory.getDriver().findElement(By.xpath("//a[@href='/codingground.htm']")).click();
        // explicit wait of invisibility condition
        WebDriverWait w = new WebDriverWait(DriverFactory.getDriver(),5);
        // invisibilityOfElementLocated condition
        w.until(ExpectedConditions.
                invisibilityOfElementLocated(By.xpath("//a[@href='/codingground.htm']")));
        // get page title of next page
        System.out.println("Page title after click:" + DriverFactory.getDriver().getTitle());
        DriverFactory.getDriver().quit();
    }
}
