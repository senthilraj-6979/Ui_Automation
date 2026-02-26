package stepDef;

import com.pages.LoginPage;
import com.pages.PraticeFormPage;
import com.qa.factory.DriverFactory;
import io.cucumber.java.en.When;
import org.testng.Assert;
import webevents.PageBase;

public class PraticeFormStepDef extends PageBase {

    PraticeFormPage praticeFormPage = new PraticeFormPage(DriverFactory.getDriver());

    @When("Verify {string} page loaded")
    public void verify_page_loaded(String title) {
        praticeFormPage.pageHeader();

    }
    @When("User enters usermane {string}")
    public void user_enters_usermane(String userName) {
        praticeFormPage.enterFirstName(userName);
    }
}
