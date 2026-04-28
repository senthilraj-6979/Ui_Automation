package com.qa.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
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

        try {
            switch (browserName) {
                case "firefox":
                    System.out.println("Initializing Firefox WebDriver...");
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (headless) {
                        firefoxOptions.addArguments("--headless");
                    }
                    firefoxOptions.addArguments("--incognito");
                    firefoxOptions.setAcceptInsecureCerts(true);
                    TDL_DRIVER.set(new FirefoxDriver(firefoxOptions));
                    System.out.println("✓ Firefox WebDriver initialized successfully");
                    break;

                case "safari":
                    System.out.println("Initializing Safari WebDriver...");
                    SafariOptions safariOptions = new SafariOptions();
                    safariOptions.setAutomaticInspection(false);
                    safariOptions.setAutomaticProfiling(false);
                    TDL_DRIVER.set(new SafariDriver(safariOptions));
                    System.out.println("✓ Safari WebDriver initialized successfully");
                    break;

                case "chrome":
                default:
                    System.out.println("Initializing Chrome WebDriver...");
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if (headless) {
                        chromeOptions.addArguments("--headless");
                    }
                    chromeOptions.addArguments("--incognito");
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    chromeOptions.addArguments("--disable-notifications");
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--no-sandbox");
                    TDL_DRIVER.set(new ChromeDriver(chromeOptions));
                    System.out.println("✓ Chrome WebDriver initialized successfully");
                    break;
            }

            WebDriver driver = getDriver();
            if (driver != null) {
                driver.manage().deleteAllCookies();
                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
                System.out.println("Browser: " + browserName + " | Headless: " + headless);
            }

        } catch (Exception e) {
            System.err.println("Error initializing WebDriver for browser: " + browserName);
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize WebDriver", e);
        }

        return getDriver();
    }

    public static WebDriver getDriver() {
        return TDL_DRIVER.get();
    }

    public static void quitDriver() {
        WebDriver driver = TDL_DRIVER.get();
        if (driver != null) {
            try {
                // Close all open windows/tabs
                driver.quit();
                System.out.println("✓ WebDriver quit successfully");
            } catch (Exception e) {
                System.err.println("Error while quitting WebDriver: " + e.getMessage());
            } finally {
                TDL_DRIVER.remove();
                // Give the process time to terminate gracefully
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
