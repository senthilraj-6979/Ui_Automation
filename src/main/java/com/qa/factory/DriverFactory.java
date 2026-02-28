package com.qa.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

public class DriverFactory {

    public static ThreadLocal<WebDriver> tdlDriver = new ThreadLocal<>();

    public WebDriver init_driver(String browser) {
        System.setProperty("webdriver.chrome.driver", "/Users/senthilraj/Documents/project/Drivers/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        options.addArguments("incognito");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable notifications");
        DesiredCapabilities cp = new DesiredCapabilities();
        cp.setCapability(ChromeOptions.CAPABILITY, true);
        options.merge(cp);

        if (browser.equalsIgnoreCase("chrome")) {
            tdlDriver.set(new ChromeDriver());
        } else if (browser.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", "/Users/senthilraj/Documents/project/Drivers/firefox/geckodriver");
            tdlDriver.set(new FirefoxDriver());
        } else {
            System.out.println("Please pass correct driver");
        }
        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        getDriver().manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

        return getDriver();
    }

    public static synchronized WebDriver getDriver(){
        return tdlDriver.get();
    }
}
