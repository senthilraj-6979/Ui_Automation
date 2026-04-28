# ITestListener Implementation Guide

## Overview

This project now includes a comprehensive TestNG `ITestListener` implementation for handling test lifecycle events and tracking test execution metrics. The listener framework provides:

- ✅ Automatic test lifecycle tracking (start, success, failure, skip)
- ✅ Screenshot capture on test failure
- ✅ Test execution metrics and statistics
- ✅ Integration with Extent Reports
- ✅ Detailed console logging
- ✅ Suite-level setup and teardown

## Components

### 1. **TestListener** (ITestListener)
**File:** `src/test/java/reporting/TestListener.java`

Implements `ITestListener` interface to handle individual test lifecycle events.

**Features:**
- `onStart()` - Called when test suite starts
- `onFinish()` - Called when test suite finishes
- `onTestStart()` - Called before each test
- `onTestSuccess()` - Called when test passes
- `onTestFailure()` - Called when test fails (captures screenshot)
- `onTestSkipped()` - Called when test is skipped
- `onTestFailedButWithinSuccessPercentage()` - Called for tests with warnings

**Key Capabilities:**
- Tracks execution time for each test
- Captures screenshots on failure with timestamp
- Logs to console with clear formatting
- Integrates with ExtentReportManager
- Records metrics using TestExecutionMetrics

### 2. **SuiteListener** (ISuiteListener)
**File:** `src/test/java/reporting/SuiteListener.java`

Implements `ISuiteListener` interface for suite-level events.

**Features:**
- `onStart()` - Initialize Extent Reports before suite execution
- `onFinish()` - Flush and finalize reports after suite execution

### 3. **TestExecutionMetrics**
**File:** `src/test/java/reporting/TestExecutionMetrics.java`

Singleton class that tracks and collects test execution metrics.

**Methods:**
```java
// Record test results
recordTestPass(String testName, long executionTime)
recordTestFailure(String testName, long executionTime, String failureReason)
recordTestSkip(String testName)
recordTestWarning(String testName)

// Get metrics
getTotalTests()
getPassedTests()
getFailedTests()
getSkippedTests()
getWarningTests()
getPassPercentage()
getFailPercentage()
getAverageExecutionTime()
getTotalExecutionTime()
getExecutionTime(String testName)
getFailureReason(String testName)

// Display metrics
printSummary()
printDetailedResults()
reset()
```

## Integration Points

### 1. **Runner Class**
**File:** `src/test/java/runner/Runner.java`

```java
@Listeners({TestListener.class})
public class Runner extends AbstractTestNGCucumberTests {
    // ...
}
```

### 2. **FailedRunner Class**
**File:** `src/test/java/runner/FailedRunner.java`

```java
@Listeners({TestListener.class})
public class FailedRunner extends AbstractTestNGCucumberTests {
    // ...
}
```

### 3. **TestNG XML Configuration**
**File:** `testng.xml`

```xml
<listeners>
    <listener class-name="reporting.SuiteListener"/>
    <listener class-name="reporting.TestListener"/>
</listeners>
```

## Output Examples

### Console Output - Test Suite Start
```
================================================================================================
TEST SUITE STARTED: Cucumber Tests
Started at: 2026-04-27 10:15:30
================================================================================================
```

### Console Output - Test Start
```
--------------------------------------------------------------------------------
TEST STARTED: GoogleSearchTest::verify_google_page_title
Started at: 2026-04-27 10:15:31
--------------------------------------------------------------------------------
```

### Console Output - Test Success
```
✓ TEST PASSED: verify_google_page_title
Execution Time: 2450 ms
Status: SUCCESS
```

### Console Output - Test Failure with Screenshot
```
✗ TEST FAILED: verify_google_search_results
Test Class: GoogleSearchTest
Execution Time: 3200 ms
Status: FAILED
Error Message: Element not found: By.xpath: //button[@class='search-btn']
Screenshot captured at: target/screenshots/verify_google_search_results_2026-04-27_10-15-34.png
```

### Console Output - Test Metrics Summary
```
================================================================================================
TEST EXECUTION METRICS SUMMARY
================================================================================================
Total Tests Run:        10
Passed:                 8 (80.00%)
Failed:                 2 (20.00%)
Skipped:                0
Warnings:               0
Average Execution Time: 2650.50 ms
Total Execution Time:   26505 ms (26.51 seconds)
================================================================================================
```

## Files Generated

### Screenshots
- **Location:** `target/screenshots/`
- **Format:** `{TestName}_{timestamp}.png`
- **Example:** `verify_google_search_2026-04-27_10-15-34.png`

### Reports
- **Location:** `target/report/`
- **Extent Report:** `test-report-{timestamp}.html`
- **Cucumber Report:** `cucumber-reports.html`

## Usage Instructions

### Running Tests with Listeners

**Via Maven:**
```bash
mvn clean test
```

**Via TestNG XML:**
```bash
mvn clean test -Dsuites=testng.xml
```

**Via IDE (Right-click on testng.xml):**
- Select "Run as TestNG Suite"

### Accessing Metrics Programmatically

```java
// Get metrics instance
TestExecutionMetrics metrics = TestExecutionMetrics.getInstance();

// Get specific metrics
int passedCount = metrics.getPassedTests();
double passPercentage = metrics.getPassPercentage();
long executionTime = metrics.getExecutionTime("testName");

// Print summary
metrics.printSummary();
metrics.printDetailedResults();
```

## Key Features

### 1. **Automatic Screenshot Capture**
- Screenshots are automatically captured on test failure
- Saved with timestamp for easy identification
- Path included in console output and Extent Report

### 2. **Test Execution Metrics**
- Pass/Fail percentages
- Average execution time
- Total execution time
- Individual test execution times
- Failure reasons tracking

### 3. **Console Logging**
- Clear visual formatting with separators
- Status indicators: ✓ (pass), ✗ (fail), ⊗ (skip), ⚠ (warning)
- Timestamp for all events
- Test class and method names

### 4. **Extent Report Integration**
- Automatic test log creation
- Pass/Fail/Skip/Warning status tracking
- Screenshot attachment on failure
- Detailed error messages

### 5. **Suite Level Tracking**
- Suite start/finish timestamps
- Automatic report initialization
- System information capture (OS, Java version, Browser, etc.)

## Best Practices

1. **Screenshot Directory**
   - Ensure `target/screenshots/` directory has write permissions
   - Clean up old screenshots periodically to save disk space

2. **Extent Reports**
   - Close/flush reports properly in `onFinish()` method
   - Check `target/report/` for HTML reports

3. **Metrics Usage**
   - Access metrics after all tests complete
   - Use `printSummary()` for quick overview
   - Use `printDetailedResults()` for detailed analysis

4. **Error Handling**
   - All listener methods have try-catch blocks
   - Errors are logged but don't stop test execution
   - Check console logs for any listener errors

## Troubleshooting

### Screenshots Not Captured
- **Check:** WebDriver is not null during test failure
- **Solution:** Ensure browser is launched before test execution

### Extent Report Not Generated
- **Check:** `target/report/` directory exists
- **Solution:** Check file permissions and disk space

### Metrics Not Recording
- **Check:** TestExecutionMetrics singleton is initialized
- **Solution:** Verify TestListener is registered in @Listeners annotation

### Listeners Not Triggering
- **Check:** @Listeners annotation is present on Runner class
- **Check:** Listeners are registered in testng.xml
- **Solution:** Add listeners if missing and rebuild project

## Configuration Options

### Screenshot Directory
Located in `TestListener.java`:
```java
private static final String SCREENSHOT_DIR = "target/screenshots";
```

### Report Directory
Located in `SuiteListener.java`:
```java
private static final String REPORT_DIR = "target/report";
```

## Performance Considerations

- Screenshot capture adds ~500ms-1000ms per failed test
- Metrics tracking is lightweight (~1-2ms per test)
- Report flushing happens once at suite end (~1-2 seconds)
- Console logging is minimal overhead

## Future Enhancements

Potential additions:
- [ ] Custom report themes
- [ ] Video recording on failure
- [ ] Performance benchmarking
- [ ] Retry failed tests automatically
- [ ] Email report delivery
- [ ] Dashboard visualization
- [ ] Database logging

## Support & Documentation

For more information:
- TestNG Listeners: https://testng.org/doc/documentation-main.html#listeners
- Extent Reports: https://www.extentreports.com/
- Selenium Screenshots: https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/TakesScreenshot.html

