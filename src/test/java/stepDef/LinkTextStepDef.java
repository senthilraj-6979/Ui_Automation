package stepDef;

import com.pages.LinkTextPage;
import com.qa.factory.DriverFactory;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;

public class LinkTextStepDef {

    LinkTextPage link = new LinkTextPage(DriverFactory.getDriver());

    @When("User click on Home option")
    public void user_click_on_home_option() throws InterruptedException {
        link.clickHome();
    }

    @Then("User click on Cart option")
    public void user_click_on_cart_option() throws InterruptedException {
        link.clickCart();
     }

    @Then("Count the total number of links")
    public void count_no_of_links() throws IOException {
        link.countHyperLink();
    }
}
