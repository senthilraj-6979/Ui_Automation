# Jenkins Pipeline Quick Start

## Option 1: Get Started in 5 Minutes

### Step 1: Install Jenkins
```bash
# macOS
brew install jenkins

# Start Jenkins
brew services start jenkins

# Access Jenkins at http://localhost:8080
```

### Step 2: Install Required Plugins
Open Jenkins → Manage Jenkins → Manage Plugins

Search and install:
- Pipeline
- HTML Publisher
- JUnit
- Git
- Email Extension

### Step 3: Create a New Job
1. Click **New Item**
2. Enter: `UI_Automation_Pipeline`
3. Select **Pipeline**
4. In Pipeline section, select **Pipeline script from SCM**
5. Select **Git** as SCM
6. Enter repo URL: `https://github.com/your-repo/Ui_Automation.git`
7. Set Script Path: `Jenkinsfile`
8. Click **Save**

### Step 4: Run Build
1. Click **Build Now**
2. Wait for completion
3. View report: Click build → **Cucumber Test Report**

---

## Option 2: Copy-Paste Pipeline

If you don't have a Git repo yet:

1. Create New Item → Pipeline
2. Select **Definition**: "Pipeline script"
3. Copy this into the Script field:

```groovy
pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out code...'
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }
        
        stage('Run Tests') {
            steps {
                sh 'mvn test -Dcucumber.filter.tags="@Alert"'
            }
        }
        
        stage('Report') {
            steps {
                publishHTML([
                    reportDir: 'target/report',
                    reportFiles: 'cucumber-reports.html',
                    reportName: 'Test Report'
                ])
            }
        }
    }
    
    post {
        always {
            junit testResults: 'target/surefire-reports/**/*.xml', 
                  allowEmptyResults: true
        }
    }
}
```

4. Click **Save**
5. Click **Build Now**

---

## Common Commands

### Run All Tests
```bash
mvn test
```

### Run Specific Tag
```bash
mvn test -Dcucumber.filter.tags="@Alert"
```

### Run Multiple Tags (OR logic)
```bash
mvn test -Dcucumber.filter.tags="@Alert or @LoginPage"
```

### Run Multiple Tags (AND logic)
```bash
mvn test -Dcucumber.filter.tags="@Regression and @Critical"
```

### Skip Tests
```bash
mvn clean compile -DskipTests
```

### Run with Specific Runner
```bash
mvn test -Dtest=Runner
```

---

## Jenkins Build Workflow

```
Code Committed
    ↓
Webhook/Manual Trigger
    ↓
✓ Git Checkout
    ↓
✓ Maven Build
    ↓
✓ Run Cucumber Tests
    ↓
✓ Generate HTML Report
    ↓
✓ Publish Results
    ↓
✓ Archive Artifacts
    ↓
Build Complete
```

---

## Troubleshooting Quick Fixes

### Maven command not found
```bash
brew install maven
# Then configure in Jenkins Global Tool Configuration
```

### ChromeDriver not found
```bash
brew install chromedriver
# Add to PATH or configure in code
```

### Tests not executing
1. Check if feature files exist: `src/test/resources/features/`
2. Verify tags in feature files match your test command
3. Check if glue paths are correct in Runner.java

### Report not generating
1. Ensure `target/report/` directory is created
2. Check pom.xml has cucumber-core dependency
3. Verify cucumber.properties file exists

---

## Configuration Templates

### Basic Jenkinsfile (No SCM)
```groovy
pipeline {
    agent any
    
    stages {
        stage('Build & Test') {
            steps {
                sh 'mvn clean test'
            }
        }
        
        stage('Report') {
            steps {
                publishHTML([
                    reportDir: 'target/report',
                    reportFiles: 'cucumber-reports.html',
                    reportName: 'Cucumber Report'
                ])
            }
        }
    }
}
```

### Jenkinsfile with Parameters
```groovy
pipeline {
    agent any
    
    parameters {
        choice(name: 'TEST_TAG', choices: ['@Alert', '@LoginPage', 'ALL'])
    }
    
    stages {
        stage('Run Tests') {
            steps {
                script {
                    def tag = params.TEST_TAG == 'ALL' ? 
                        '@Alert or @LoginPage' : params.TEST_TAG
                    sh "mvn test -Dcucumber.filter.tags='${tag}'"
                }
            }
        }
    }
}
```

---

## Email Notifications

Add to your Jenkinsfile post section:

```groovy
post {
    success {
        emailext(
            subject: "Build Success: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
            body: "Tests passed! Report: ${env.BUILD_URL}",
            to: "your-email@company.com"
        )
    }
    
    failure {
        emailext(
            subject: "Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
            body: "Tests failed. Details: ${env.BUILD_URL}console",
            to: "your-email@company.com"
        )
    }
}
```

---

## Scheduling Builds

In Jenkins job configuration:

**Build Triggers** → Check **Build periodically**

Cron examples:
```
0 9 * * *      # Daily at 9 AM
0 */4 * * *    # Every 4 hours
0 18 * * 1-5   # Weekdays at 6 PM
H H * * *      # Once daily (random time)
H/30 * * * *   # Every 30 minutes
```

---

## Test Organization with Tags

Update your feature files:

```gherkin
@Smoke @Alert @Critical
Feature: Alert Handling
  
  @AlertTest1
  Scenario: Handle simple alert
    ...

@Regression @Alert
Scenario: Handle multiple alerts
  ...

@LoginPage @Smoke
Feature: Login

@Skip
Scenario: Future test
  ...
```

Run specific tests:
```bash
mvn test -Dcucumber.filter.tags="@Smoke"           # Smoke tests
mvn test -Dcucumber.filter.tags="@Critical"        # Critical tests
mvn test -Dcucumber.filter.tags="@Regression"      # Regression tests
mvn test -Dcucumber.filter.tags="not @Skip"        # Skip marked tests
```

---

## File Locations

```
Project Root/
├── Jenkinsfile              ← Main pipeline
├── Jenkinsfile.advanced     ← Advanced features
├── JENKINS_SETUP_GUIDE.md   ← Full documentation
├── pom.xml                  ← Maven config
├── src/
│   └── test/
│       ├── java/
│       │   ├── runner/Runner.java
│       │   └── stepDef/
│       └── resources/
│           ├── features/    ← Feature files (.feature)
│           ├── config/      ← Configuration files
│           └── testdata/    ← Test data files
└── target/
    └── report/              ← Generated reports
```

---

## Next Steps

1. ✅ Install Jenkins
2. ✅ Install plugins
3. ✅ Create new Pipeline job
4. ✅ Configure Git or paste pipeline
5. ✅ Click Build Now
6. ✅ View test report
7. ✅ Customize as needed

---

## Support Links

- Jenkins Home: http://localhost:8080
- Cucumber Tags: https://cucumber.io/docs/cucumber/api/
- Maven Plugins: https://maven.apache.org/plugins/
- Selenium Setup: https://www.selenium.dev/documentation/

---

## Quick Command Reference

| Command | Purpose |
|---------|---------|
| `mvn clean compile` | Build without tests |
| `mvn test` | Run all tests |
| `mvn test -Dtest=Runner` | Run specific test class |
| `mvn clean test` | Clean and run tests |
| `mvn verify` | Run tests and verification |
| `mvn -DskipTests clean package` | Package without tests |

---

