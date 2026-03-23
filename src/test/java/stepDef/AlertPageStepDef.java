package stepDef;

import com.pages.AlertPage;
import com.qa.factory.DriverFactory;
import com.sun.javafx.tools.packager.Log;
import com.utilities.UIActionUtility;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.log4j.Log4j;
import webevents.PageBase;


public class AlertPageStepDef extends PageBase {

        AlertPage alertPage = new AlertPage(DriverFactory.getDriver());

        @Given("User launches the URL {string}")
        public void user_launches_the_url(String url) {
            Log.info("Inside AlertPageStepDef - User launches the URL: " + url);
            DriverFactory.getDriver().get(url);
        }

        @When("Click on the Alert option")
        public void click_on_alert_option() {
            alertPage.clickAlertLink();
        }

        @Then("Click on the first alert button")
        public void click_on_the_alert_button() throws InterruptedException {
            alertPage.clickAlertButton();
        }

        @Then("Click Ok and accept the alert")
        public void click_ok_close_alert_popup() {
            // Alert is already showing from previous step
            // Just accept/close it
            alertPage.acceptSimpleAlert();
        }

        @Then("Click on the timer alert button")
        public void click_on_timer_alert_button() throws InterruptedException {
            alertPage.timerAltertButton();
         }

        // Alternative: Click button AND handle alert in one step
        @Then("Click alert button and accept popup")
        public void click_alert_button_and_accept() throws InterruptedException {
            alertPage.clickAlertButtonAndAccept();
        }

        @Then("Click on the confirm alert button")
        public void click_on_the_confirm_alert_button() {
            alertPage.clickConfirmButton();
            // Verify alert appears
            try {
                Thread.sleep(1000);  // Wait for dialog to appear
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Then("Click Ok and accept the confirm alert")
        public void click_ok_and_accept_confirm_alert() {
            alertPage.acceptConfirmAlert();
        }

        @Then("Click on the prompt alert button")
        public void click_on_the_prompt_alert_button() {
            alertPage.clickPromptButton();
            // Verify alert appears
            try {
                Thread.sleep(1000);  // Wait for dialog to appear
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Then("User enters {string} in prompt and accepts")
        public void user_enters_text_in_prompt(String text) {
            alertPage.sendTextAndAcceptPrompt(text);
        }


}
