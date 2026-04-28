package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepDef", "webevents"},
        plugin = {
                "pretty",
                "html:target/report/cucumber-reports.html",
                "json:target/report/cucumber.json",
                "reporting.ExtentCucumberPlugin",
                "rerun:target/rerun.txt"
        },
        tags = "@Regression"
)
public class Runner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
