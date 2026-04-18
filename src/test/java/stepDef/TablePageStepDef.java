package stepDef;

import com.pages.TablePage;
import com.qa.factory.DriverFactory;
import com.utilities.UIActionUtility;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import webevents.PageBase;

import java.sql.Driver;

public class TablePageStepDef extends PageBase {

    private TablePage tablePage() {
        return new TablePage(DriverFactory.getDriver());
    }

    @Then("Click on the Add button")
    public void click_on_add_button() throws InterruptedException {
        tablePage().clickAddButton();
    }

    @Then("Verify {string} displayed")
    public void move_registration_page(String formName) throws InterruptedException {
        tablePage().isRegistrationFormDisplayed();
    }

    @And("User enters email {string}")
    public void user_enters_email(String email) {
        tablePage().enterEmail(email);
    }

    @And("User enters age {string}")
    public void user_enters_age(String age) {
        tablePage().enterAge(age);
    }

    @And("User enters salary {string}")
    public void user_enters_salary(String salary) {
        tablePage().enterSalary(salary);
    }

    @And("User enters department {string}")
    public void user_enters_department(String department) {
        tablePage().enterDepartment(department);
    }

    @And("Click on the Submit button")
    public void click_on_submit_button() {
        tablePage().clickSubmitButton();
    }

    @And("Verify {string} added to the table")
    public void verify_new_record_added(String name) throws InterruptedException {
        tablePage().verifyNewRecordAdded(name);
    }

    @And("Click on the Delete button for {string}")
    public void click_on_delete_button(String name) throws InterruptedException {
        tablePage().deleteRecord(name);
}
}
