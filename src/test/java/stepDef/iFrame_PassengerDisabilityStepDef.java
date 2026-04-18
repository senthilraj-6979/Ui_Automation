package stepDef;

import com.pages.HomePage;
import com.pages.iFrame_PassengerDisabilityPage;
import com.qa.factory.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import webevents.PageBase;

public class iFrame_PassengerDisabilityStepDef  extends PageBase {
    private iFrame_PassengerDisabilityPage passengerDisabilityPage() {
        return new iFrame_PassengerDisabilityPage(DriverFactory.getDriver());
    }

    @Then("User clicks on Passenger Disability link")
    public void user_click_oneway_option() {
            passengerDisabilityPage().click_iFrame_PassengerDisabilityPage();
    }

    @Then("User switch to iFrame and switch to frame")
    public void verify_iFrame(){
        passengerDisabilityPage().switchToiFrame();

    }

    @Then("Verify heading of the page is Passenger with Disability or Assistance Needed?")
    public void verify_heading_of_the_page_is_passenger_with_disability_or_assistance_needed() {
        passengerDisabilityPage().verifyHeading();

    }

    @Then("Close the popup")
        public void close_popup(){
           passengerDisabilityPage().closeiFrame();
    }

    @Then("Click Traveller dropdown")
    public void click_traveller_dropdown() throws InterruptedException {
        passengerDisabilityPage().clickTravellerDropdown();

    }

    @Then("Click and increment the number of passengers")
    public void click_on_adult_increment_button() throws InterruptedException {
        passengerDisabilityPage().clickAdultIncrement();
    }

    @Then("Then Click on trip type dropdown ")
    public void click_on_tripType_dropdown() throws InterruptedException {
        passengerDisabilityPage().clickTripTypeDropdown();
    }

}
