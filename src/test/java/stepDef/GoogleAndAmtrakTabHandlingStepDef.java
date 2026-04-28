package stepDef;

import com.pages.WindowHandlingTestsNewWindowPage;
import com.qa.factory.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.cucumber.java.After;
import org.testng.Assert;

public class GoogleAndAmtrakTabHandlingStepDef {

    private WindowHandlingTestsNewWindowPage tabHandlingPage;

    private WindowHandlingTestsNewWindowPage getTabHandlingPage() {
        if (tabHandlingPage == null) {
            tabHandlingPage = new WindowHandlingTestsNewWindowPage(DriverFactory.getDriver());
        }
        return tabHandlingPage;
    }

    @Given("User launches Google in first tab")
    public void user_launches_google_in_first_tab() {
        System.out.println("\n=== Launching Google in First Tab ===");
        getTabHandlingPage().launchGoogle();
        System.out.println("✓ Google launched in first tab successfully");
    }

    @When("User opens Amtrak website in a new tab")
    public void user_opens_amtrak_website_in_new_tab() {
        System.out.println("\n=== Opening Amtrak in New Tab ===");
        getTabHandlingPage().openAmtrakInNewTab();
        System.out.println("✓ Amtrak opened in new tab");
    }

    @Then("User verifies Google and Amtrak tabs are both open")
    public void user_verifies_both_tabs_open() {
        System.out.println("\n=== Verifying Both Tabs Are Open ===");
        boolean bothTabsOpen = getTabHandlingPage().verifyBothTabsOpen();
        Assert.assertTrue(bothTabsOpen, "Both tabs should be open");
        int totalTabs = getTabHandlingPage().getNumberOfTabs();
        System.out.println("✓ Both tabs verified - Total tabs: " + totalTabs);
    }

    @And("User validates Google tab URL contains google domain")
    public void user_validates_google_url() {
        System.out.println("\n=== Validating Google Tab URL ===");
        getTabHandlingPage().switchToGoogleTab();
        String currentURL = getTabHandlingPage().getCurrentTabURL();
        System.out.println("Current Google URL: " + currentURL);
        Assert.assertTrue(currentURL.contains("google"), "URL should contain 'google'");
        System.out.println("✓ Google URL validated");
    }

    @And("User validates Amtrak tab URL contains amtrak domain")
    public void user_validates_amtrak_url() {
        System.out.println("\n=== Validating Amtrak Tab URL ===");
        getTabHandlingPage().switchToAmtrakTab();
        String currentURL = getTabHandlingPage().getCurrentTabURL();
        System.out.println("Current Amtrak URL: " + currentURL);
        Assert.assertTrue(currentURL.contains("amtrak"), "URL should contain 'amtrak'");
        System.out.println("✓ Amtrak URL validated");
    }

    @Then("User closes the Amtrak tab and returns to Google")
    public void user_closes_amtrak_and_returns_to_google() {
        System.out.println("\n=== Closing Amtrak Tab and Returning to Google ===");
        getTabHandlingPage().closeAmtrakTabAndSwitchToGoogle();
        System.out.println("✓ Amtrak tab closed and returned to Google");
    }

    @And("User confirms only Google tab remains open")
    public void user_confirms_only_google_tab_remains() {
        System.out.println("\n=== Confirming Only Google Tab Remains ===");
        int remainingTabs = getTabHandlingPage().getNumberOfTabs();
        Assert.assertEquals(remainingTabs, 1, "Only 1 tab should remain");
        String currentURL = getTabHandlingPage().getCurrentTabURL();
        System.out.println("✓ Only 1 tab remaining - URL: " + currentURL);
    }

    @After("@GoogleAndAmtrakTabs")
    public void tearDown() {
        System.out.println("\n=== Cleaning Up Tab Handles ===");
        if (tabHandlingPage != null) {
            tabHandlingPage.clearHandles();
        }
        tabHandlingPage = null;
        System.out.println("✓ Cleanup completed\n");
    }
}

