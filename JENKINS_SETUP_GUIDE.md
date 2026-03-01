# Jenkins Pipeline Setup Guide for Selenium UI Automation

## Overview
This guide explains how to set up a Jenkins pipeline for your Selenium + Cucumber UI Automation project.

## Prerequisites

### Required Software
- **Jenkins** (2.350+)
- **Maven** (3.6+)
- **Java** (JDK 8+)
- **Git** (for SCM)
- **Chrome/Firefox** (for Selenium tests)

### Required Jenkins Plugins
1. **Pipeline** (Declarative & Scripted)
2. **Git** (for source code management)
3. **Maven Integration** (for Maven builds)
4. **JUnit Plugin** (for test result reporting)
5. **HTML Publisher Plugin** (for Cucumber report)
6. **Email Extension Plugin** (for notifications - optional)
7. **Allure Plugin** (for advanced reporting - optional)
8. **Log Parser Plugin** (for log analysis - optional)

### Installation Steps for Jenkins Plugins

1. Go to **Manage Jenkins** → **Manage Plugins**
2. Click the **Available** tab
3. Search for each plugin and install:
   ```
   - Pipeline: Declarative Agent API
   - Pipeline: Model Definition
   - HTML Publisher
   - JUnit
   - Git
   - Email Extension
   - Allure
   ```
4. Restart Jenkins after installation

## Jenkins Configuration

### 1. Configure Java and Maven

**Step 1: Install JDK**
1. Go to **Manage Jenkins** → **Global Tool Configuration**
2. Scroll to **JDK** section
3. Click **Add JDK**
4. Set Name: `JDK8`
5. Set JAVA_HOME path (e.g., `/usr/libexec/java_home -v 1.8`)
6. Save

**Step 2: Install Maven**
1. In **Global Tool Configuration**, find **Maven** section
2. Click **Add Maven**
3. Set Name: `Maven3`
4. Set MAVEN_HOME (e.g., `/usr/local/Cellar/maven/3.8.1/libexec`)
5. Save

### 2. Install ChromeDriver

For UI tests, you need ChromeDriver:

```bash
# On macOS using Homebrew
brew install chromedriver

# Verify installation
which chromedriver
chromedriver --version

# Or download from: https://chromedriver.chromium.org/
```

Add ChromeDriver to PATH or configure in your project.

### 3. Create Jenkins Pipeline Job

**Option A: Using Jenkinsfile from SCM (Recommended)**

1. Click **New Item**
2. Enter job name: `UI_Automation_Pipeline`
3. Select **Pipeline**
4. Click **OK**
5. Configure Pipeline:
   - **Definition**: Select "Pipeline script from SCM"
   - **SCM**: Select "Git"
   - **Repository URL**: `https://github.com/your-repo/Ui_Automation.git`
   - **Branch Specifier**: `*/main` or `*/master`
   - **Script Path**: `Jenkinsfile` (default)
6. Click **Save**

**Option B: Using Jenkinsfile Name Variants**

If you want to use advanced pipeline:
- Change **Script Path** to: `Jenkinsfile.advanced`

**Option C: Creating Manual Pipeline Job**

1. Click **New Item**
2. Enter job name: `UI_Automation_Pipeline`
3. Select **Pipeline**
4. Click **OK**
5. Under **Pipeline** section, select **Definition**: "Pipeline script"
6. Copy-paste the content of `Jenkinsfile` in the **Script** field
7. Click **Save**

## Jenkinsfile Options

### 1. **Jenkinsfile** (Recommended - Basic)
Simple declarative pipeline for basic test execution.

**Use when:**
- Starting with Jenkins
- Need simple build/test/report workflow
- Testing with basic tags

```groovy
// Run tests with specific tags
mvn test -Dcucumber.filter.tags="@Alert or @LoginPage"
```

### 2. **Jenkinsfile.scripted** (Advanced - Flexible)
Scripted pipeline with more flexibility and error handling.

**Use when:**
- Need custom logic and conditions
- Want colored console output
- Need advanced error handling

### 3. **Jenkinsfile.advanced** (Production)
Full-featured pipeline with parameters, multiple test suites, and advanced reporting.

**Use when:**
- Running in production
- Need parameterized builds
- Want multiple test suite options
- Need email notifications

## How to Run Tests

### Option 1: Run All Tests
```groovy
mvn test -Dcucumber.filter.tags="@Alert or @LoginPage or @homePage"
```

### Option 2: Run Specific Test Suite
```groovy
// Smoke Tests Only
mvn test -Dcucumber.filter.tags="@Alert or @LoginPage"

// Regression Tests
mvn test -Dcucumber.filter.tags="@Alert or @LoginPage or @homePage or @DropDown"

// Single Feature Tag
mvn test -Dcucumber.filter.tags="@Alert"
```

### Option 3: Exclude Tests
```groovy
mvn test -Dcucumber.filter.tags="not @Skip"
```

## Configure Test Tags in Your Project

Update your feature files with tags:

```gherkin
@Alert @Smoke @Regression
Feature: Alert Handling
  
  @AlertTest1
  Scenario: Accept Simple Alert
    Given User navigates to alerts page
    When User clicks alert button
    Then User accepts the alert

@LoginPage @Smoke
Feature: Login Functionality
  
  Scenario: Valid Login
    Given User is on login page
    When User enters valid credentials
    Then User is logged in
```

## Running Jenkins Pipeline

### Method 1: Manual Trigger
1. Open Jenkins job: `UI_Automation_Pipeline`
2. Click **Build Now** button
3. View build progress in real-time

### Method 2: Parameterized Build
1. Open Jenkins job: `UI_Automation_Pipeline` (advanced version)
2. Click **Build with Parameters**
3. Select test suite:
   - ALL
   - SMOKE
   - REGRESSION
   - SANITY
   - ALERT_TESTS
   - LOGIN_TESTS
   - FORM_TESTS
4. Click **Build**

### Method 3: Scheduled Builds
1. Go to job configuration
2. Scroll to **Build Triggers**
3. Check **Build periodically**
4. Set schedule using cron:
   ```
   # Daily at 9 AM
   0 9 * * *
   
   # Every 2 hours
   0 */2 * * *
   
   # Weekdays at 6 PM
   0 18 * * 1-5
   ```
5. Save

### Method 4: Webhook (GitHub Integration)
1. Go to your GitHub repository settings
2. Add Webhook:
   - **Payload URL**: `http://jenkins-server:8080/github-webhook/`
   - **Content type**: `application/json`
   - **Events**: Push events
3. Jenkins will trigger automatically on code push

## Test Reports

### Accessing Reports

After test execution:

1. **Cucumber HTML Report**
   - Click build number
   - Click **Cucumber Test Report** link
   - View detailed test results

2. **Test Results**
   - Jenkins home page shows test trend graph
   - Failed test details available

3. **Artifacts**
   - Click **Artifacts** to download reports
   - Screenshots automatically captured

### Report Contents

- Feature execution summary
- Scenario pass/fail status
- Step-by-step execution details
- Error messages and stack traces
- Execution time for each scenario

## Troubleshooting

### Issue 1: Plugin Not Found
**Error:** `No such element: plugin not installed`

**Solution:**
```
1. Manage Jenkins → Manage Plugins
2. Install required plugins
3. Restart Jenkins
```

### Issue 2: Maven Not Found
**Error:** `mvn: command not found`

**Solution:**
```bash
# Install Maven
brew install maven

# Configure in Global Tool Configuration
# Set MAVEN_HOME: /usr/local/Cellar/maven/3.8.1/libexec
```

### Issue 3: ChromeDriver Issues
**Error:** `WebDriver not found` or `Chrome binary not found`

**Solution:**
```bash
# Install ChromeDriver
brew install chromedriver

# Grant execute permission
chmod +x /usr/local/bin/chromedriver

# Update your code to use system chromedriver
System.setProperty("webdriver.chrome.driver", 
    "/usr/local/bin/chromedriver");
```

### Issue 4: Tests Timeout
**Error:** `TimeoutException: waiting for element...`

**Solution:**
1. Increase timeout in pipeline: Change `timeout(time: 1, unit: 'HOURS')` to `timeout(time: 2, unit: 'HOURS')`
2. Check element locators - they might be incorrect
3. Add proper waits in your code using UIActionUtility

### Issue 5: Report Not Generated
**Error:** `Cucumber report not found`

**Solution:**
1. Verify `cucumber.properties` in `src/test/resources/`
2. Check plugin configuration in pom.xml
3. Ensure reports directory is writable

## Email Notifications

To enable email notifications:

1. Configure mail server:
   - **Manage Jenkins** → **Configure System**
   - Find **E-mail Notification** section
   - Set SMTP server, port, and sender email

2. Uncomment email code in Jenkinsfile:

```groovy
emailext(
    subject: "Build Status: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
    body: "Test execution completed. Details: ${env.BUILD_URL}",
    to: 'your-email@company.com'
)
```

## Advanced Features

### 1. Parallel Test Execution

Add to Jenkinsfile:
```groovy
stage('Run Tests in Parallel') {
    parallel {
        stage('Alert Tests') {
            steps {
                sh 'mvn test -Dcucumber.filter.tags="@Alert"'
            }
        }
        stage('Login Tests') {
            steps {
                sh 'mvn test -Dcucumber.filter.tags="@LoginPage"'
            }
        }
    }
}
```

### 2. Artifact Management

Archive reports:
```groovy
archiveArtifacts artifacts: 'target/report/**/*,target/screenshots/**/*'
```

### 3. Build Notifications

Slack integration:
```groovy
slackSend(
    channel: '#automation-team',
    message: "Build ${env.BUILD_NUMBER} completed with status: ${currentBuild.result}"
)
```

## Pipeline Workflow Summary

```
┌─────────────┐
│   Checkout  │  Clone code from Git
└──────┬──────┘
       │
┌──────▼──────┐
│    Build    │  Maven clean compile
└──────┬──────┘
       │
┌──────▼──────┐
│  Run Tests  │  Execute Cucumber tests
└──────┬──────┘
       │
┌──────▼──────────────────┐
│ Generate Test Reports   │  Create HTML/JSON reports
└──────┬──────────────────┘
       │
┌──────▼──────────────────┐
│ Archive Artifacts       │  Store reports & screenshots
└──────┬──────────────────┘
       │
┌──────▼──────────────────┐
│ Notifications (Optional)│  Send email alerts
└──────────────────────────┘
```

## Best Practices

1. **Use Jenkinsfile in SCM** - Track pipeline changes in Git
2. **Use Tags Effectively** - Organize tests by @Smoke, @Regression, etc.
3. **Set Timeouts** - Prevent hanging builds
4. **Archive Reports** - Keep historical data
5. **Use Parameters** - Run different test suites without job reconfiguration
6. **Monitor Trends** - Use Jenkins trend graphs to track stability
7. **Clean Up** - Use `buildDiscarder` to limit stored builds
8. **Document Tests** - Add descriptions to scenarios

## Quick Reference

| Component | Configuration |
|-----------|---------------|
| Pipeline Type | Declarative (recommended) |
| Trigger | GitHub Webhook or Manual |
| Build Tool | Maven 3.6+ |
| Java Version | JDK 8+ |
| Browser | Chrome with Chromedriver |
| Report Format | HTML + JSON (Cucumber) |
| Timeout | 1-2 hours |

## Support & Troubleshooting

- **Jenkins Documentation**: https://www.jenkins.io/doc/
- **Cucumber Documentation**: https://cucumber.io/docs/cucumber/
- **Selenium Documentation**: https://www.selenium.dev/documentation/
- **Maven Documentation**: https://maven.apache.org/

## Next Steps

1. ✅ Create Jenkins job with Jenkinsfile
2. ✅ Configure Java and Maven in Global Tool Configuration
3. ✅ Install ChromeDriver
4. ✅ Update feature files with proper tags
5. ✅ Run first build manually
6. ✅ Configure email notifications
7. ✅ Set up scheduled builds or webhooks
8. ✅ Monitor and adjust as needed

