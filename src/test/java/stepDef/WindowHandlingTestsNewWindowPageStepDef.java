package stepDef;

import com.pages.WindowHandlingTestsNewWindowPage;
import com.qa.factory.DriverFactory;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WindowHandlingTestsNewWindowPageStepDef {
    private static final Logger logger = LogManager.getLogger(WindowHandlingTestsNewWindowPageStepDef.class);

    public WindowHandlingTestsNewWindowPageStepDef() {
        logger.info("WindowHandlingTestsNewWindowPageStepDef initialized");
    }

    private WindowHandlingTestsNewWindowPage windowHandlingTestsNewWindowPage() {
        try {
            logger.debug("Creating WindowHandlingTestsNewWindowPage instance");
            WindowHandlingTestsNewWindowPage page = new WindowHandlingTestsNewWindowPage(DriverFactory.getDriver());
            logger.debug("✓ WindowHandlingTestsNewWindowPage instance created successfully");
            return page;
        } catch (Exception e) {
            logger.error("Error creating WindowHandlingTestsNewWindowPage instance: " + e.getMessage(), e);
            throw new RuntimeException("Failed to create WindowHandlingTestsNewWindowPage instance", e);
        }
    }

    @Then("Switch to new window {string}")
    public void switch_to_new_window(String newURL) {
        try {
            logger.info("Switching to new window with URL: " + newURL);
            windowHandlingTestsNewWindowPage().switchTONewHandle(newURL);
            logger.info("✓ Successfully switched to new window - URL: " + newURL);
        } catch (Exception e) {
            logger.error("Error switching to new window with URL: " + newURL + " - " + e.getMessage(), e);
            throw new RuntimeException("Failed to switch to new window with URL: " + newURL, e);
        }
    }

    @Then("Switch to old window")
    public void switch_to_old_window() {
        try {
            logger.info("Switching back to old/original window");
            windowHandlingTestsNewWindowPage().switchTOOldHandle();
            logger.info("✓ Successfully switched back to old window");
        } catch (Exception e) {
            logger.error("Error switching to old window: " + e.getMessage(), e);
            throw new RuntimeException("Failed to switch to old window", e);
        }
    }
}