package stepDef;


import com.qa.factory.DriverFactory;
import com.qa.util.ConfigReader;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import webevents.PageBase;

import java.util.Properties;

public class Hooks extends PageBase {

    private DriverFactory driverFactory;
    private ConfigReader confReader;
    Properties prop;

    @Before(order=0)
    public void getProperty() {
        confReader = new ConfigReader();
        prop = confReader.init_prop();
    }

    @Before(order=1)
    public void launchBrowser() {
        try {
            String browserName = resolveValue("browser", "BROWSER", prop.getProperty("browser"), "chrome");
            boolean headless = Boolean.parseBoolean(resolveValue("headless", "HEADLESS", prop.getProperty("headless"), "false"));

            driverFactory = new DriverFactory();
            driverFactory.init_driver(browserName, headless);
            System.out.println("✓ Launching browser: " + browserName + " | headless=" + headless);
        } catch (Exception e) {
            System.err.println("✗ Failed to launch browser: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Browser initialization failed", e);
        }
    }

    @After(order=1)
    public void afterScenario(){
        System.out.println("Cleaning up after the Scenario...");
        try {
            DriverFactory.quitDriver();
        } catch (Exception e) {
            System.err.println("✗ Error during browser cleanup: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String resolveValue(String systemPropertyName, String environmentVariableName, String configValue, String defaultValue) {
        String systemValue = System.getProperty(systemPropertyName);
        if (systemValue != null && !systemValue.trim().isEmpty()) {
            return systemValue.trim();
        }

        String environmentValue = System.getenv(environmentVariableName);
        if (environmentValue != null && !environmentValue.trim().isEmpty()) {
            return environmentValue.trim();
        }

        if (configValue != null && !configValue.trim().isEmpty()) {
            return configValue.trim();
        }

        return defaultValue;
    }
}
