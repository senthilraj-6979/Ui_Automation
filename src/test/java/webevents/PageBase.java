package webevents;

import com.qa.factory.DriverFactory;
import org.openqa.selenium.WebDriver;

public class PageBase {

    protected WebDriver getDriver() {
        return DriverFactory.getDriver();
    }
}
