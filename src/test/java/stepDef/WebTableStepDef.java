package stepDef;


import com.qa.factory.DriverFactory;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import webevents.PageBase;


import java.util.List;


public class WebTableStepDef extends PageBase {

    @Then("Verify the total number of rows and columns")
    public void verify_the_total_number_of_rows_and_columns() {
        List<WebElement> col = DriverFactory.getDriver().findElements(By.xpath("//table[@id='countries']//tr[1]/td"));
        System.out.println("No of Columns--"+col.size());
        List<WebElement> rows = DriverFactory.getDriver().findElements(By.xpath("//table[@id='countries']//tr"));
        int actualRow = rows.size()-1;
        System.out.println("No of Rows--"+actualRow);
    }

    @Then("Select the country {string} by clicking check box")
    public void select_the_record_by_clicking_check_box(String country) {
      WebElement selectCountry = DriverFactory.getDriver().findElement(By.xpath("//strong[contains(text(),"+country+")]//parent" +
              "::td//preceding-sibling::td//input"));
        ((JavascriptExecutor)DriverFactory.getDriver()).executeScript("arguments[0].scrollIntoView(true);", selectCountry);
//        Wait wait = new WebDriverWait(driver, 1000);
//        wait.until(ExpectedConditions.elementToBeClickable(selectCountry));
      if(selectCountry.isDisplayed()) {
          selectCountry.click();
          boolean isSelected = selectCountry.isSelected();
          System.out.println("isSelected------"+isSelected);
          DriverFactory.getDriver().quit();
      }
    }
}
