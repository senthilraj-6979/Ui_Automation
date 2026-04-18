package reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.qa.factory.DriverFactory;
import com.qa.util.ConfigReader;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

public final class ExtentReportManager {

    private static final String REPORT_DIRECTORY = "target/report/extent";
    private static final String REPORT_FILE_PATH = REPORT_DIRECTORY + "/extent-report.html";
    private static final ThreadLocal<ExtentTest> SCENARIO_TEST = new ThreadLocal<>();

    private static ExtentReports extentReports;

    private ExtentReportManager() {
    }

    public static synchronized ExtentReports getExtentReports() {
        if (extentReports == null) {
            createReportDirectory();

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(REPORT_FILE_PATH);
            sparkReporter.config().setDocumentTitle("UI Automation Extent Report");
            sparkReporter.config().setReportName("Cucumber Selenium Execution");
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");

            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);
            extentReports.setSystemInfo("OS", System.getProperty("os.name"));
            extentReports.setSystemInfo("Java", System.getProperty("java.version"));
            extentReports.setSystemInfo("Browser", resolveBrowser());
            extentReports.setSystemInfo("Generated On", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        }
        return extentReports;
    }

    public static void createScenarioTest(String scenarioName, Collection<String> tags) {
        ExtentTest extentTest = getExtentReports().createTest(scenarioName);
        if (tags != null && !tags.isEmpty()) {
            extentTest.assignCategory(tags.toArray(new String[0]));
        }
        SCENARIO_TEST.set(extentTest);
    }

    public static ExtentTest getScenarioTest() {
        return SCENARIO_TEST.get();
    }

    public static void clearScenarioTest() {
        SCENARIO_TEST.remove();
    }

    public static synchronized void flushReport() {
        if (extentReports != null) {
            extentReports.flush();
        }
    }

    public static String captureScreenshotBase64() {
        WebDriver driver = DriverFactory.getDriver();
        if (driver instanceof TakesScreenshot) {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        }
        return null;
    }

    public static String getReportFilePath() {
        return REPORT_FILE_PATH;
    }

    private static void createReportDirectory() {
        try {
            Files.createDirectories(Paths.get(REPORT_DIRECTORY));
        } catch (IOException e) {
            throw new RuntimeException("Unable to create Extent report directory", e);
        }
    }

    private static String resolveBrowser() {
        try {
            Properties properties = new ConfigReader().init_prop();
            if (properties == null) {
                return "unknown";
            }
            return properties.getProperty("browser", "unknown");
        } catch (Exception e) {
            return "unknown";
        }
    }
}

