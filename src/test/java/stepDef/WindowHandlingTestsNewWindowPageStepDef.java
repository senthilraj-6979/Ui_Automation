package stepDef;

import com.pages.LoginPage;
import com.pages.WindowHandlingTestsNewWindowPage;
import com.qa.factory.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import webevents.PageBase;

public class WindowHandlingTestsNewWindowPageStepDef  {
    private WindowHandlingTestsNewWindowPage windowHandlingTestsNewWindowPage() {
        return new WindowHandlingTestsNewWindowPage(DriverFactory.getDriver());
    }

    @Then("Switch to new window {string}")
    public void switch_to_new_window(String newURL) {
        windowHandlingTestsNewWindowPage().switchTONewHandle(newURL);
    }
}
