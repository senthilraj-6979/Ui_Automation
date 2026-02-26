package stepDef;

import com.pages.TrainsStatusPage;
import com.qa.factory.DriverFactory;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import webevents.PageBase;

public class TrainStatusStepDef extends PageBase {

    private TrainsStatusPage trainsStatusPage = new TrainsStatusPage(DriverFactory.getDriver());


    @Then("User clicks TrainStatus option")
    public void user_click_guest_rewards_option() {
        trainsStatusPage.clickTrainStatus();
    }


    @Then ("User enters FROM station {string} at TRAIN STATUS")
    public void user_enter_from_station(String departStation) throws InterruptedException {
        trainsStatusPage.trainStatusFromSationCode(departStation);
    }

    @Then ("User enters TO station {string} at TRAIN STATUS")
    public void user_enter_to_station(String arrivalStation) throws InterruptedException {
        trainsStatusPage.trainStatusToSationCode(arrivalStation);
    }

    @Then("Select train status calender")
    public void select_train_status_calender() throws InterruptedException {
        trainsStatusPage.select_train_status_Calender();
    }

    @Then("Select train status date")
    public void select_train_status_Date() throws InterruptedException {
        trainsStatusPage.select_train_status_Date();
    }

    @Then("Click Train Status button")
    public void trainStatusButton(){
        trainsStatusPage.clickCheckStatus();
    }

    @Then("User clicks TrainNumber option")
    public void selectTrainNumber(){
        trainsStatusPage.selectTrainNumber();
    }

    @Then("Select TrainNumber option")
    public void selectTrainNumberDropdown(){
        trainsStatusPage.selectTrainNumberOption();
    }

    @Then("Enter TrainNumber {string}")
    public void enterTrainNumber(String trainNumber){
        trainsStatusPage.enterTrainName(trainNumber);
    }

    @Then("Enter StationNumber {string}")
    public void enterStationNumber(String stationNumber){
        trainsStatusPage.enterStationCode(stationNumber);
    }
}
