package stepDef;

import com.pages.PraticeFormPage;
import com.qa.factory.DriverFactory;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import webevents.PageBase;

public class PraticeFormStepDef extends PageBase {

    PraticeFormPage praticeFormPage = new PraticeFormPage(DriverFactory.getDriver());

    @When("Verify {string} page loaded")
    public void verify_page_loaded(String title) {
        String actualHeader = praticeFormPage.pageHeader(title);
        Assert.assertEquals(actualHeader, title, "Page header mismatch");
    }
    @Then("User enters first name {string}")
    public void user_enters_firstName(String userName) {
        praticeFormPage.enterFirstName(userName);
    }

    @Then("User enters last name {string}")
    public void user_enters_lastName(String lastName) {
    praticeFormPage.enterLastName(lastName);
}


    @Then("Select gender as {string}")
        public void selectGender(String lastName) {
    praticeFormPage.selectGender(lastName);
}
@Then("User enters mobile number {string}")
    public void user_enters_mobile_number(String mobileNumber) {
    praticeFormPage.enterPhone_no(mobileNumber);
}

@Then("User enters date of birth {string}")
    public void user_enters_date_of_birth(String dob) {
    praticeFormPage.enterDOB(dob);
}

@Then("User clicks upload picture {string}")
    public void upload_picture(String path) {
    praticeFormPage.uploadPicture(path);
}


@Then("User selects hobby {string}")
    public void select_hobby(String hobby) {
    praticeFormPage.selectHobby(hobby);
}

@Then("User selects state {string}")
    public void user_selects_state(String state) throws InterruptedException {
    praticeFormPage.selectState(state);
}

@Then("User selects city {string}")
    public void user_selects_city(String city) throws InterruptedException {
    praticeFormPage.selectCity(city);
}

    }
