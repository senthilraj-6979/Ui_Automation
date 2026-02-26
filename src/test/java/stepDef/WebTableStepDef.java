package stepDef;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import webevents.PageBase;

import java.time.Duration;
import java.util.List;

public class WebTableStepDef extends PageBase {

    @Then("Verify the total number of rows and columns")
    public void verify_the_total_number_of_rows_and_columns() {
        List<WebElement> col = driver.findElements(By.xpath("//table[@id='countries']//tr[1]/td"));
        System.out.println("No of Columns--"+col.size());
        List<WebElement> rows = driver.findElements(By.xpath("//table[@id='countries']//tr"));
        int actualRow = rows.size()-1;
        System.out.println("No of Rows--"+actualRow);
    }

    @Then("Select the country {string} by clicking check box")
    public void select_the_record_by_clicking_check_box(String country) {
      WebElement selectCountry = driver.findElement(By.xpath("//strong[contains(text(),"+country+")]//parent" +
              "::td//preceding-sibling::td//input"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", selectCountry);
//        Wait wait = new WebDriverWait(driver, 1000);
//        wait.until(ExpectedConditions.elementToBeClickable(selectCountry));
      if(selectCountry.isDisplayed()) {
          selectCountry.click();
          boolean isSelected = selectCountry.isSelected();
          System.out.println("isSelected------"+isSelected);
          driver.quit();
      }
    }
}
