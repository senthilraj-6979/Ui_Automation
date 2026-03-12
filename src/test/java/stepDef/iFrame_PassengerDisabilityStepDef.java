package stepDef;

import com.pages.HomePage;
import com.pages.iFrame_PassengerDisabilityPage;
import com.qa.factory.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import webevents.PageBase;

public class iFrame_PassengerDisabilityStepDef  extends PageBase {
    private iFrame_PassengerDisabilityPage iFrame_PassengerDisabilityPage = new iFrame_PassengerDisabilityPage(DriverFactory.getDriver());

    @Then("User clicks on Passenger Disability link")
    public void user_click_oneway_option() {
            iFrame_PassengerDisabilityPage.click_iFrame_PassengerDisabilityPage();
    }

    @Then("User switch to iFrame and switch to frame")
    public void verify_iFrame(){
        iFrame_PassengerDisabilityPage.switchToiFrame();

    }

    @Then("Verify heading of the page is Passenger with Disability or Assistance Needed?")
    public void verify_heading_of_the_page_is_passenger_with_disability_or_assistance_needed() {
        iFrame_PassengerDisabilityPage.verifyHeading();

    }

    @Then("Close the popup")
        public void close_popup(){
           iFrame_PassengerDisabilityPage.closeiFrame();
    }

    @Then("Click Traveller dropdown")
    public void click_traveller_dropdown() throws InterruptedException {
        iFrame_PassengerDisabilityPage.clickTravellerDropdown();

    }

    @Then("Click and increment the number of passengers")
    public void click_on_adult_increment_button() throws InterruptedException {
        iFrame_PassengerDisabilityPage.clickAdultIncrement();
    }

    @Then("Then Click on trip type dropdown ")
    public void click_on_tripType_dropdown() throws InterruptedException {
        iFrame_PassengerDisabilityPage.clickTripTypeDropdown();
    }

}


