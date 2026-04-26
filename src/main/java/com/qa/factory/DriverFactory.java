package com.qa.factory;

//import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.util.concurrent.TimeUnit;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> TDL_DRIVER = new ThreadLocal<>();

    public WebDriver init_driver(String browser) {
        return init_driver(browser, false);
    }

    public WebDriver init_driver(String browser, boolean headless) {
        String browserName = browser == null ? "chrome" : browser.trim().toLowerCase();

        switch (browserName) {
            case "firefox":
                System.out.println("Initializing Firefox WebDriver...");
                System.setProperty("webdriver.gecko.driver", "/usr/local/Cellar/geckodriver/0.36.0/bin/geckodriver");
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("--headless");
                }
                firefoxOptions.addArguments("--incognito");
                TDL_DRIVER.set(new FirefoxDriver(firefoxOptions));
                System.out.println("✓ Firefox WebDriver initialized successfully");
                break;

            case "safari":
                System.out.println("Initializing Safari WebDriver...");
                SafariOptions safariOptions = new SafariOptions();
                // Note: SafariDriver does not require explicit path on macOS
                TDL_DRIVER.set(new SafariDriver(safariOptions));
                System.out.println("✓ Safari WebDriver initialized successfully");
                break;

            case "chrome":
            default:
                System.out.println("Initializing Chrome WebDriver...");
                System.setProperty("webdriver.chrome.driver", "/Users/senthilraj/Documents/project/Drivers/chromedriver");
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless");
                }
                chromeOptions.addArguments("--incognito");
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.addArguments("--disable-notifications");
                TDL_DRIVER.set(new ChromeDriver(chromeOptions));
                System.out.println("✓ Chrome WebDriver initialized successfully");
                break;
        }

        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        getDriver().manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

        System.out.println("Browser: " + browserName + " | Headless: " + headless);

        return getDriver();
    }

    public static WebDriver getDriver() {
        return TDL_DRIVER.get();
    }

    public static void quitDriver() {
        WebDriver driver = TDL_DRIVER.get();
        if (driver != null) {
            driver.quit();
            TDL_DRIVER.remove();
            System.out.println("✓ WebDriver quit successfully");
        }
    }
}
