# ITestListener - Quick Reference Guide

## 📌 What Was Implemented?

A complete **ITestListener** framework for TestNG with automatic test tracking, metrics collection, and screenshot capture.

## 🎯 New Files Created

| File | Purpose |
|------|---------|
| `TestListener.java` | Handles individual test lifecycle events (pass/fail/skip) |
| `SuiteListener.java` | Handles suite-level events (setup/teardown) |
| `TestExecutionMetrics.java` | Tracks and stores all test metrics |
| `ListenerConfig.java` | Centralized configuration management |

## 🔧 Files Modified

| File | Change |
|------|--------|
| `Runner.java` | Added `@Listeners({TestListener.class})` |
| `FailedRunner.java` | Added `@Listeners({TestListener.class})` |
| `testng.xml` | Added `<listeners>` section with both listeners |

## 📊 How It Works

### Automatic Features (No Code Changes Needed!)

1. **Test Tracking** - Every test start/success/failure is logged
2. **Screenshot Capture** - Fails automatically capture screenshot
3. **Metrics Collection** - Pass/fail counts, times, percentages
4. **Console Logging** - Visual indicators: ✓ ✗ ⊗ ⚠
5. **Report Integration** - Logs to Extent Reports automatically

## 🚀 Running Tests

```bash
# Tests will automatically use listeners
mvn clean test

# Or right-click testng.xml in IDE > Run as > TestNG Suite
```

## 📁 Output Locations

```
target/screenshots/     - Failed test screenshots
target/report/          - HTML reports
target/logs/            - Log files
```

## 🔍 Accessing Metrics

```java
// Get metrics singleton
TestExecutionMetrics metrics = TestExecutionMetrics.getInstance();

// Get specific metrics
int passed = metrics.getPassedTests();
int failed = metrics.getFailedTests();
double percentage = metrics.getPassPercentage();

// Print reports
metrics.printSummary();
metrics.printDetailedResults();
```

## 📋 What Gets Tracked

- ✅ Total tests run
- ✅ Passed count and percentage
- ✅ Failed count and percentage  
- ✅ Skipped count
- ✅ Warning count
- ✅ Execution time per test
- ✅ Average execution time
- ✅ Total execution time
- ✅ Failure reasons

## 🎨 Console Output Symbols

```
✓  = Test Passed
✗  = Test Failed
⊗  = Test Skipped
⚠  = Test Warning
✓  = Action Success
✗  = Action Failed
```

## 📝 Example Console Output

```
================================================================================
TEST SUITE STARTED: Cucumber Tests
Started at: 2026-04-27 10:15:30
================================================================================

────────────────────────────────────────────────────────────────────────────────
TEST STARTED: GoogleSearchTest::verify_page_title
Started at: 2026-04-27 10:15:31
────────────────────────────────────────────────────────────────────────────────

✓ TEST PASSED: verify_page_title
Execution Time: 2450 ms
Status: SUCCESS

✗ TEST FAILED: verify_search_results
Test Class: GoogleSearchTest
Execution Time: 3200 ms
Status: FAILED
Error Message: Element not found
Screenshot captured at: target/screenshots/verify_search_results_2026-04-27_10-15-36.png

================================================================================
TEST EXECUTION METRICS SUMMARY
================================================================================
Total Tests Run:        5
Passed:                 4 (80.00%)
Failed:                 1 (20.00%)
Skipped:                0
Average Execution Time: 2680.00 ms
Total Execution Time:   13400 ms (13.40 seconds)
================================================================================
```

## ⚙️ Configuration Options

Edit `ListenerConfig.java` to customize:

```java
// Screenshot settings
public static final boolean CAPTURE_SCREENSHOT_ON_FAILURE = true;
public static final String SCREENSHOT_DIR = "target/screenshots";

// Report settings
public static final String REPORT_DIR = "target/report";

// Logging settings
public static final boolean LOG_TEST_METRICS = true;
public static final boolean LOG_TO_CONSOLE = true;

// Timeouts
public static final long SCREENSHOT_CAPTURE_TIMEOUT = 10000;
```

## 🔄 Listener Lifecycle

```
Suite Starts
    ↓
SuiteListener.onStart()
    ├─ Initialize Extent Reports
    └─ Create directories
    
For Each Test:
    ├─ TestListener.onTestStart() → Log start
    ├─ Test Execution
    ├─ TestListener.onTestSuccess() → Log pass + metrics
    │  OR
    ├─ TestListener.onTestFailure() → Log fail + screenshot + metrics
    │  OR
    └─ TestListener.onTestSkipped() → Log skip + metrics

Suite Ends
    ↓
TestListener.onFinish()
    ├─ Print metrics summary
    └─ Print detailed results
    ↓
SuiteListener.onFinish()
    ├─ Flush Extent Reports
    └─ Save reports
```

## 🛠️ Troubleshooting

| Issue | Solution |
|-------|----------|
| No screenshots captured | Check WebDriver is not null during failure |
| Listeners not triggering | Verify @Listeners annotation on Runner class |
| No metrics output | Check TestExecutionMetrics is initialized |
| Report not generated | Check target/report/ directory has write permission |

## 📚 Documentation Files

- `ITESTLISTENER_IMPLEMENTATION.md` - Full user guide
- `ITESTLISTENER_SUMMARY.md` - Detailed summary
- `EXCEL_REFACTORING_SUMMARY.md` - Excel implementation (previous)

## 🎓 Example: Using Metrics in Code

```java
public class TestMetricsExporter {
    public static void main(String[] args) {
        // Get metrics after tests complete
        TestExecutionMetrics metrics = TestExecutionMetrics.getInstance();
        
        // Print summary
        metrics.printSummary();
        
        // Get specific values
        int totalTests = metrics.getTotalTests();
        int passed = metrics.getPassedTests();
        int failed = metrics.getFailedTests();
        double passPercentage = metrics.getPassPercentage();
        long totalTime = metrics.getTotalExecutionTime();
        
        // Print custom report
        System.out.println("Test execution completed:");
        System.out.println("  Total: " + totalTests);
        System.out.println("  Passed: " + passed + " (" + passPercentage + "%)");
        System.out.println("  Failed: " + failed);
        System.out.println("  Time: " + totalTime + "ms");
    }
}
```

## ✨ Features at a Glance

| Feature | Status | Notes |
|---------|--------|-------|
| Test lifecycle tracking | ✅ | Automatic |
| Screenshot on failure | ✅ | Timestamp included |
| Metrics collection | ✅ | 10+ metrics tracked |
| Console logging | ✅ | Visual indicators |
| Extent Reports integration | ✅ | Automatic |
| Configuration management | ✅ | Centralized |
| Performance tracking | ✅ | Per-test timing |
| Error logging | ✅ | Failure reasons |
| Directory management | ✅ | Auto-create |
| Cleanup utilities | ✅ | Age-based cleanup |

## 🎯 Next Steps

1. ✅ Run tests: `mvn clean test`
2. ✅ Check console output for metrics
3. ✅ Review `target/screenshots/` for failed test screenshots
4. ✅ Open `target/report/` HTML files in browser
5. ✅ Customize `ListenerConfig` as needed

## 📞 Support

For detailed documentation, see:
- `ITESTLISTENER_IMPLEMENTATION.md` - Comprehensive guide
- `ITESTLISTENER_SUMMARY.md` - Feature overview
- Inline code comments in listener classes

---

**Status:** ✅ **Ready to Use**

The ITestListener framework is fully implemented and ready for use. Just run your tests!

