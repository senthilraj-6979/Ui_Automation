package stepDef;


import com.qa.factory.DriverFactory;
import com.qa.util.ConfigReader;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Parameters;
import webevents.PageBase;

import java.util.Properties;

public class Hooks extends PageBase {

    private DriverFactory driverFactory;
    private WebDriver driver;
    private ConfigReader confReader;
    Properties prop;

    @Before(order=0)
    public void getProperty() {
        confReader = new ConfigReader();
        prop = confReader.init_prop();
    }

    @Before(order=1)
    public void launchBrowser() {
        String browserName = prop.getProperty("browser");
        driverFactory = new DriverFactory();
        driver = driverFactory.init_driver(browserName);
    }

    @After(order=1)
    public void afterScenario(){
        System.out.println("This will run after the Scenario");
        if(driver!=null) {
            driver.quit();
        }
    }

//    @After(order=1)
//    public void tearDown(Scenario scenario){
//        String screenShot = scenario.toString().replaceAll(" ","_");
//        byte[] sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
//        scenario.attach(sourcePath,"image/png",screenShot);
//        }
    }

