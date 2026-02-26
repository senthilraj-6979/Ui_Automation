package stepDef;

import com.pages.LoginPage;
import com.qa.factory.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class LoginPageStepDef {

    private LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
    @Given("User is on login page")
    public void user_is_on_login_page() {
        DriverFactory.getDriver().get("https://www.automationexercise.com/login");
        String title = loginPage.getTitle();
        Assert.assertNotNull(title);
    }

    @When("User enters username {string}")
    public void user_enters_username(String userName) {
        loginPage.enterEmailId(userName);
    }
    @When("User enters password {string}")
    public void user_enters_password(String password) {
        loginPage.enterPwd(password);
    }
    @When("User clicks on Login Button")
    public void user_clicks_on_login_button() {
        loginPage.clickLogin();
    }
    @Then("User gets the title of the page")
    public void user_gets_the_title_of_the_page() {

    }
    @Then("Page title should be {string}")
    public void page_title_should_be(String string) {

    }
}
