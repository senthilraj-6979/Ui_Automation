package stepDef;

import com.pages.MultiWindowHandlingPage;
import com.qa.factory.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.After;
import org.testng.Assert;

public class MultiWindowHandlingStepDef {

    private MultiWindowHandlingPage multiWindowHandlingPage;

    private MultiWindowHandlingPage getMultiWindowHandlingPage() {
        if (multiWindowHandlingPage == null) {
            multiWindowHandlingPage = new MultiWindowHandlingPage(DriverFactory.getDriver());
        }
        return multiWindowHandlingPage;
    }

    @Given("User launches Google homepage")
    public void user_launches_google_homepage() {
        System.out.println("=== Launching Google homepage ===");
        getMultiWindowHandlingPage().launchGoogle();
        System.out.println("✓ Google homepage launched");
    }

    @Then("User opens Amtrak in a new window")
    public void user_opens_amtrak_in_new_window() {
        System.out.println("=== Opening Amtrak in new window ===");
        getMultiWindowHandlingPage().openAmtrakInNewWindow();
        System.out.println("✓ Amtrak opened in new window");
    }

    @Then("User verifies both windows are open")
    public void user_verifies_both_windows_open() {
        System.out.println("=== Verifying both windows are open ===");
        boolean bothWindowsOpen = getMultiWindowHandlingPage().verifyBothWindowsOpen();
        Assert.assertTrue(bothWindowsOpen, "Both windows should be open");
        System.out.println("✓ Both windows verified - Total windows: " +
                           getMultiWindowHandlingPage().getNumberOfOpenWindows());
    }

    @Then("User switches back to Google window")
    public void user_switches_back_to_google_window() {
        System.out.println("=== Switching to Google window ===");
        getMultiWindowHandlingPage().switchToGoogleWindow();
        String currentURL = getMultiWindowHandlingPage().getCurrentWindowURL();
        System.out.println("✓ Switched to Google window - URL: " + currentURL);
        Assert.assertTrue(currentURL.contains("google"), "Should be on Google page");
    }

    @Then("User switches to Amtrak window")
    public void user_switches_to_amtrak_window() {
        System.out.println("=== Switching to Amtrak window ===");
        getMultiWindowHandlingPage().switchToAmtrakWindow();
        String currentURL = getMultiWindowHandlingPage().getCurrentWindowURL();
        System.out.println("✓ Switched to Amtrak window - URL: " + currentURL);
        Assert.assertTrue(currentURL.contains("amtrak"), "Should be on Amtrak page");
    }

    @Then("User closes Amtrak window")
    public void user_closes_amtrak_window() {
        System.out.println("=== Closing Amtrak window ===");
        getMultiWindowHandlingPage().closeAmtrakWindowAndSwitchToGoogle();
        System.out.println("✓ Amtrak window closed, switched back to Google");
        int remainingWindows = getMultiWindowHandlingPage().getNumberOfOpenWindows();
        Assert.assertEquals(remainingWindows, 1, "Should have only 1 window open");
    }

    @After
    public void tearDown() {
        System.out.println("=== Cleaning up window handles ===");
        if (multiWindowHandlingPage != null) {
            multiWindowHandlingPage.clearWindowHandles();
        }
        multiWindowHandlingPage = null;
        System.out.println("✓ Cleanup completed");
    }
}

