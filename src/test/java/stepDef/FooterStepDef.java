package stepDef;

import com.pages.FooterPage;
import com.pages.LoginPage;
import com.qa.factory.DriverFactory;
import com.utilities.UIActionUtility;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class FooterStepDef {

    private FooterPage footerPage() {
        return new FooterPage(DriverFactory.getDriver());
    }

    @Given("User clicks facebook icon")
    public void user_click_faceBook() {
        UIActionUtility.waitForSeconds(2);
        footerPage().clickFacebookIcon();
    }

    @When("User navigate to facebook page")
    public void user_navigate_faceBook() {
        footerPage().windowsSwitch();
    }

    @Then("User facebook loginId {string}")
    public void user_faceBook_loginId(String loginID) {
        footerPage().enterFBuserID(loginID);;

    }

    @Then("User closes facebook frame")
    public void user_faceBook_frame_close() {
        footerPage().closeFacebookFrame();

    }

    @Then("User closes facebook window and back to amtrak page")
    public void user_close_faceBook() {
        footerPage().closeFacebookWindow();

    }

    @Then("User clicks twitter icon")
    public void click_twitter_icon() {
        footerPage().clickTwitterIcon();

    }

    @Then("User navigate to twitter page")
    public void user_navigate_twitters() {
        footerPage().windowsSwitch();

    }
}
