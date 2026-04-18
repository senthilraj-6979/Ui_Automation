package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "@target/rerun.txt",
        glue = {"stepDef", "webevents"},
        plugin = {
                "pretty",
                "html:target/report/cucumber-rerun-report.html",
                "json:target/report/cucumber-rerun.json",
                "reporting.ExtentCucumberPlugin"
        }
)
public class FailedRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}

