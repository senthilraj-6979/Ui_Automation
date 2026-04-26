package stepDef;

import com.pages.FooterPage;
import com.pages.GooglePage;
import com.qa.factory.DriverFactory;
import com.utilities.UIActionUtility;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GooglePageStepDef {

    private GooglePage googlePage() {
        return new GooglePage(DriverFactory.getDriver());
    }

    @When("Enter search text {string}")
    public void enter_search_text(String serachText) {
        googlePage().enterSearchTerm(serachText);
    }


   @Then("Click on search button")
        public void click_on_search_button() throws InterruptedException {
        googlePage().clickSearchButton();
    }
}
