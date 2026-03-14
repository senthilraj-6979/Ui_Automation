package reporting;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.Result;
import io.cucumber.plugin.event.Status;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestCaseStarted;
import io.cucumber.plugin.event.TestRunFinished;
import io.cucumber.plugin.event.TestRunStarted;
import io.cucumber.plugin.event.TestStepFinished;

public class ExtentCucumberPlugin implements ConcurrentEventListener {

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestRunStarted.class, event -> ExtentReportManager.getExtentReports());
        publisher.registerHandlerFor(TestCaseStarted.class, this::handleScenarioStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::handleStepFinished);
        publisher.registerHandlerFor(TestCaseFinished.class, this::handleScenarioFinished);
        publisher.registerHandlerFor(TestRunFinished.class, this::handleTestRunFinished);
    }

    private void handleScenarioStarted(TestCaseStarted event) {
        ExtentReportManager.createScenarioTest(event.getTestCase().getName(), event.getTestCase().getTags());
        ExtentReportManager.getScenarioTest().info("Scenario started");
    }

    private void handleStepFinished(TestStepFinished event) {
        if (!(event.getTestStep() instanceof PickleStepTestStep)) {
            return;
        }

        ExtentTest scenarioTest = ExtentReportManager.getScenarioTest();
        if (scenarioTest == null) {
            return;
        }

        PickleStepTestStep pickleStep = (PickleStepTestStep) event.getTestStep();
        String stepName = pickleStep.getStep().getKeyword() + pickleStep.getStep().getText();
        Result result = event.getResult();
        Status status = result.getStatus();

        switch (status) {
            case PASSED:
                scenarioTest.pass(stepName);
                break;
            case FAILED:
                logFailedStep(scenarioTest, stepName, result.getError());
                break;
            case SKIPPED:
                scenarioTest.skip(stepName);
                break;
            case PENDING:
            case UNDEFINED:
            case AMBIGUOUS:
                scenarioTest.warning(stepName + " - " + status.name());
                if (result.getError() != null) {
                    scenarioTest.warning(result.getError());
                }
                break;
            default:
                scenarioTest.info(stepName + " - " + status.name());
                break;
        }
    }

    private void logFailedStep(ExtentTest scenarioTest, String stepName, Throwable error) {
        String screenshotBase64 = ExtentReportManager.captureScreenshotBase64();

        if (screenshotBase64 != null && !screenshotBase64.trim().isEmpty()) {
            try {
                scenarioTest.fail(stepName,
                        MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotBase64, "Failure Screenshot").build());
            } catch (Exception exception) {
                scenarioTest.fail(stepName);
            }
        } else {
            scenarioTest.fail(stepName);
        }

        if (error != null) {
            scenarioTest.fail(error);
        }
    }

    private void handleScenarioFinished(TestCaseFinished event) {
        ExtentTest scenarioTest = ExtentReportManager.getScenarioTest();
        if (scenarioTest != null) {
            scenarioTest.info("Scenario finished with status: " + event.getResult().getStatus().name());
        }
        ExtentReportManager.clearScenarioTest();
    }

    private void handleTestRunFinished(TestRunFinished event) {
        ExtentReportManager.flushReport();
    }
}

