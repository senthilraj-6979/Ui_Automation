package stepDef;

import com.pages.HomePage;
import com.qa.factory.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import webevents.PageBase;

public class HomePageStepDef extends PageBase {

    private HomePage bookingPage = new HomePage(DriverFactory.getDriver());

    @Given("Lanuch the amtrak URL {string}")
    public void lanuch_(String url) {
        DriverFactory.getDriver().get(url);
    }

    @When("User click on AcceptAll cookie button")
    public void user_click_on_accept_all_button() {
        bookingPage.clickAcceptAllCookies();
    }

    @Then("User click guest rewards option")
    public void user_click_guest_rewards_option() {
        bookingPage.clickGuestRewards();
    }

    @Then("User click signInbutton")
    public void user_click_signIn_option() {
        bookingPage.clickSignIn();
    }

    @Then("User click one-way option")
    public void user_click_oneway_option() {
        bookingPage.clickOneway();
        bookingPage.selectOneway();
    }

    @Then("User enters from station {string}")
    public void user_enter_from_station(String fromStation) throws InterruptedException {
        bookingPage.enterFromStation(fromStation);

    }

    @Then("User enters to station {string}")
    public void user_enter_to_station(String toStation) throws InterruptedException {
        bookingPage.enterToStation(toStation);

    }

    @Then("User clicks departure date")
    public void user_clicks_departure_date() throws InterruptedException {
        bookingPage.enterClicksDepartDateField();

    }

    @Then("User selects departure date")
    public void user_selects_departure_date() throws InterruptedException {
        bookingPage.selectDepartDate();

    }

    @Then("Close calender clicking done button")
    public void click_doneButton() throws InterruptedException {
        bookingPage.clickDone();

    }

    @Then("Click Find Trains")
    public void click_findTrains() throws InterruptedException {
        bookingPage.clickFindTrains();

    }
    @Then("User clicks {string} language dropdown")
    public void click_dropDown(String language) throws InterruptedException {
        bookingPage.languageDropdown(language);
    }

    @Then("User clicks tripType dropdown")
    public void select_oneway() throws InterruptedException {
        bookingPage.clickOnewayDefault();
    }

    @Then("User selects Multi City trip")
    public void select_tripType() throws InterruptedException {
        bookingPage.selectMultiCity();
    }


    @Then("User selects {string} trip")
    public void select_roundTrip(String tripType) throws InterruptedException {
        bookingPage.roundTrip(tripType);
    }

    @Then("User clicks use points option")
    public void click_pointsOption() throws InterruptedException {
        bookingPage.enableUserPoints();
    }

    @Then("User click Need Assistance checkbox")
    public void select_needAssistance() throws InterruptedException {
        bookingPage.assistanceCheckbox();
    }

    @Then("Verify AGR popup is getting display")
    public void verify_AGRPopup() throws InterruptedException {
        bookingPage.agrPopup();
    }

}
