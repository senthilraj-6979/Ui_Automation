# Jenkins Pipeline - Quick Reference Card

## 🎯 1-Minute Overview

```
┌─────────────────────────────────────────┐
│  Code Push → Jenkins → Tests → Reports  │
│                                         │
│  Automated, Fast, Beautiful Reports    │
└─────────────────────────────────────────┘
```

---

## 🚀 Getting Started

### Option A: Docker (Recommended) ⭐
```bash
docker-compose up -d
# Jenkins: http://localhost:8080
# Password: docker logs jenkins_ui_automation | grep initialAdminPassword
```

### Option B: Local Installation
```bash
brew install jenkins
brew services start jenkins
# Jenkins: http://localhost:8080
```

---

## 📋 5-Minute Setup Checklist

- [ ] Jenkins running
- [ ] Unlock Jenkins with password
- [ ] Install plugins (Pipeline, Git, HTML Publisher, JUnit)
- [ ] Configure Java & Maven in Global Tool Configuration
- [ ] Create Pipeline job: UI_Automation_Pipeline
- [ ] Point to Jenkinsfile
- [ ] Click Build Now
- [ ] View test report

**Total Time**: 5-10 minutes

---

## 📁 File Structure

```
Project Root/
├── Jenkinsfile              ← Use this one (recommended)
├── Jenkinsfile.advanced     ← For advanced features
├── docker-compose.yml       ← For Docker setup
├── src/test/resources/
│   ├── features/            ← .feature files with tests
│   ├── config/              ← Configuration
│   └── testdata/            ← Test data
└── target/report/           ← Generated reports
```

---

## 🔑 Pipeline Stages (6 Steps)

```
1. CHECKOUT     → Git clone & branch switch
2. BUILD        → Maven compile
3. RUN TESTS    → Execute Cucumber scenarios
4. REPORTS      → Generate HTML/JSON
5. ARCHIVE      → Store artifacts
6. NOTIFY       → Email/Slack alerts
```

---

## 💻 Commands Cheat Sheet

| Command | Purpose |
|---------|---------|
| `mvn clean compile` | Build without tests |
| `mvn test` | Run all tests |
| `mvn test -Dcucumber.filter.tags="@Alert"` | Run @Alert tests only |
| `mvn test -DskipTests` | Skip tests |
| `mvn test -X` | Debug mode |
| `docker-compose up -d` | Start all services |
| `docker-compose down` | Stop all services |
| `docker logs -f jenkins_ui_automation` | View Jenkins logs |

---

## 🧪 Running Tests from Jenkins

```
1. Open Jenkins Dashboard
2. Click: UI_Automation_Pipeline
3. Click: Build Now
4. Wait for completion
5. Click build number
6. Click: Cucumber Test Report
7. View results!
```

---

## 🏷️ Test Tags (Feature Organization)

Add to `.feature` files:

```gherkin
@Smoke @Critical
Feature: Login
  
  @LoginTest1
  Scenario: Valid credentials
    ...

@Regression
Scenario: Invalid credentials
  ...
```

Run specific tests:
```bash
mvn test -Dcucumber.filter.tags="@Smoke"           # Smoke tests
mvn test -Dcucumber.filter.tags="@Critical"        # Critical only
mvn test -Dcucumber.filter.tags="not @Skip"        # Skip some
mvn test -Dcucumber.filter.tags="@A or @B"        # A or B
mvn test -Dcucumber.filter.tags="@A and @B"       # A and B
```

---

## 📊 Reports Location

After test execution:

```
Jenkins Dashboard
    ↓
UI_Automation_Pipeline
    ↓
Build #X
    ↓
Cucumber Test Report
    ↓
View detailed results with screenshots!
```

---

## 🔧 Configuration Files

### cucumber.properties
```properties
cucumber.glue=stepDef,webevents
cucumber.features=src/test/resources/features
cucumber.plugin=pretty,html:target/report/cucumber-reports.html,json:target/report/cucumber.json
```

### pom.xml (Key Dependencies)
```xml
<!-- Selenium -->
<artifactId>selenium-java</artifactId>
<version>3.141.5</version>

<!-- Cucumber -->
<artifactId>cucumber-java</artifactId>
<version>7.11.1</version>

<!-- TestNG -->
<artifactId>testng</artifactId>
<version>7.1.0</version>
```

---

## 🐳 Docker Services

```bash
# Start
docker-compose up -d

# Services:
http://localhost:8080      ← Jenkins
http://localhost:4444      ← Selenium Hub
localhost:3306             ← MySQL
localhost:6379             ← Redis

# Stop
docker-compose down

# View logs
docker logs -f jenkins_ui_automation
docker logs -f selenium_hub
```

---

## 🚨 Troubleshooting (Top 5 Issues)

| Issue | Fix |
|-------|-----|
| **Tests not found** | Check `src/test/resources/features/` exists with .feature files |
| **ChromeDriver error** | `brew install chromedriver` |
| **Port in use** | Change port in docker-compose.yml |
| **Build timeout** | Increase timeout in Jenkinsfile: `timeout(time: 2, unit: 'HOURS')` |
| **Report missing** | Check `target/report/` created and cucumber.json exists |

---

## 📈 Monitoring

### Build Trends
```
Jenkins Dashboard
    ↓
Click job name
    ↓
See build history & trend graph
```

### Recent Builds Status
- **✓ Green** = All tests passed
- **⚠ Yellow** = Some tests failed (unstable)
- **✗ Red** = Build failed

---

## 🎯 Key Features

✅ **Automated**: Trigger on code push (webhook)
✅ **Fast**: Maven + Selenium optimized
✅ **Beautiful Reports**: HTML with screenshots
✅ **Scalable**: Run tests in parallel
✅ **Trackable**: Build history & trends
✅ **Notifiable**: Email alerts
✅ **Containerized**: Docker ready
✅ **Cloud Ready**: Kubernetes deployment files

---

## 🔐 Credentials Setup

### GitHub (If using private repo)
```
1. Go to: Manage Jenkins → Manage Credentials
2. Add GitHub username/password or SSH key
3. Configure in Pipeline job SCM section
```

### Email Notifications
```
1. Go to: Manage Jenkins → Configure System
2. Find: E-mail Notification
3. Configure SMTP (Gmail, Outlook, etc.)
4. Test send email
```

---

## 📚 Documentation Quick Links

| Document | When to Read |
|----------|--------------|
| README_JENKINS.md | Overview (this folder) |
| JENKINS_QUICK_START.md | 5-minute setup |
| JENKINS_SETUP_GUIDE.md | Complete details |
| JENKINS_DOCKER_SETUP.md | Docker/Kubernetes |
| JENKINS_CHECKLIST_TROUBLESHOOTING.md | Problem solving |
| JENKINS_ARCHITECTURE_DIAGRAMS.md | Understanding flow |

---

## 🎓 Learning Path

```
Day 1: Read README_JENKINS.md (overview)
Day 2: Read JENKINS_QUICK_START.md (setup)
Day 3: Setup Jenkins & create job
Day 4: Run first test
Day 5: Customize for your needs
```

---

## ⚡ Power Tips

### Speed Up Builds
```bash
# Skip tests during build
mvn clean compile -DskipTests

# Parallel test execution (update pom.xml)
<threadCount>4</threadCount>
```

### Debug Failing Tests
```bash
# Run single test
mvn test -Dcucumber.filter.tags="@SpecificTag"

# Enable debug mode
mvn test -X

# Keep browser open for debugging
ChromeOptions options = new ChromeOptions();
options.setHeadless(false);
```

### Better Reports
```groovy
// In Jenkinsfile - use Allure plugin
allure includeProperties: false,
       jdk: '',
       results: [[path: 'target/allure-results']]
```

---

## 🆚 Jenkinsfile Comparison

| Feature | Basic | Scripted | Advanced |
|---------|-------|----------|----------|
| **File** | Jenkinsfile | Jenkinsfile.scripted | Jenkinsfile.advanced |
| **Complexity** | Simple | Medium | Full-featured |
| **Parameters** | ❌ | ❌ | ✅ |
| **Recommended** | ✅ | ⭐ | For production |
| **Lines** | ~60 | ~70 | ~250 |

---

## 🌐 Webhook Setup (GitHub Auto-Trigger)

### 1. GitHub Settings
```
Repo → Settings → Webhooks → Add webhook

Payload URL:
http://your-jenkins-url:8080/github-webhook/

Content type: application/json

Events: Push events
```

### 2. Jenkins Configuration
```
Job → Configure → Build Triggers

✓ GitHub hook trigger for GITScm polling
```

Now tests run automatically on every push! 🚀

---

## 📞 Get Help

1. **Check this card** (you're reading it!)
2. **Read relevant doc** (see table above)
3. **Check Jenkins logs**: `docker logs jenkins_ui_automation`
4. **View pipeline console**: Click build → Console Output
5. **Review troubleshooting**: JENKINS_CHECKLIST_TROUBLESHOOTING.md

---

## 🎉 Success Indicators

You'll know it's working when:

✅ Jenkins Dashboard shows your job
✅ Build starts automatically or manually
✅ Console shows "mvn test" output
✅ Tests execute (see Chrome browser activity)
✅ Build completes in ~5-10 minutes
✅ "Cucumber Test Report" link appears
✅ Detailed test results visible with pass/fail
✅ Screenshots captured for failed tests

---

## 🚀 Ready to Go!

Your Jenkins pipeline is fully configured and ready to use.

**Next Step**: Open JENKINS_QUICK_START.md for 5-minute setup

---

**Quick Links**:
- Jenkins: http://localhost:8080
- Selenium Hub: http://localhost:4444
- Docker Compose: `docker-compose up -d`
- Documentation: See file list in README_JENKINS.md

**Questions?** Check JENKINS_CHECKLIST_TROUBLESHOOTING.md

---

*Last Updated: February 28, 2026*
*All files in project root directory*

