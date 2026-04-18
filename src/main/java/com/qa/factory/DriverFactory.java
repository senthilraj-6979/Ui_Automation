package com.qa.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

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
                System.setProperty("webdriver.gecko.driver", "/Users/senthilraj/Documents/project/Drivers/firefox/geckodriver");
                TDL_DRIVER.set(new FirefoxDriver());
                break;
            case "chrome":
            default:
                System.setProperty("webdriver.chrome.driver", "/Users/senthilraj/Documents/project/Drivers/chromedriver");
                ChromeOptions options = new ChromeOptions();
                if (headless) {
                    options.addArguments("--headless");
                }
                options.addArguments("--incognito");
                options.addArguments("--remote-allow-origins=*");
                options.addArguments("--disable-notifications");
                TDL_DRIVER.set(new ChromeDriver(options));
                break;
        }

        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        getDriver().manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

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
        }
    }
}
