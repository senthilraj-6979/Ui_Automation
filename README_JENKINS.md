# Jenkins Pipeline Setup for Ui_Automation 🚀

Complete Jenkins CI/CD pipeline configuration for your Selenium + Cucumber UI Automation project.

## 📦 What's Included

```
Ui_Automation/
├── 📄 Jenkinsfile                           ✅ Main pipeline (recommended)
├── 📄 Jenkinsfile.scripted                  ✅ Flexible scripted pipeline
├── 📄 Jenkinsfile.advanced                  ✅ Full-featured with parameters
├── 🐳 docker-compose.yml                    ✅ Complete Docker setup
├── 📋 JENKINS_SETUP_SUMMARY.md              📖 Start here!
├── ⚡ JENKINS_QUICK_START.md                📖 5-minute setup
├── 📚 JENKINS_SETUP_GUIDE.md                📖 Complete guide (600+ lines)
├── 🔧 JENKINS_DOCKER_SETUP.md               📖 Docker & Kubernetes
├── 🐛 JENKINS_CHECKLIST_TROUBLESHOOTING.md  📖 Fixes & solutions
├── 🏗️ JENKINS_ARCHITECTURE_DIAGRAMS.md      📖 Visual architecture
└── 🔨 jenkins-setup.sh                      ✅ Setup verification script
```

## 🚀 Quick Start (Choose One)

### ⚡ Super Quick (Docker) - 2 minutes

```bash
cd /Users/senthilraj/IdeaProjects/Ui_Automation
docker-compose up -d
# Access: http://localhost:8080
# Password: docker logs jenkins_ui_automation | grep "initialAdminPassword"
```

### 🔧 Manual Setup - 5 minutes

```bash
brew install jenkins
brew services start jenkins
# Access: http://localhost:8080
# Follow JENKINS_QUICK_START.md for job creation
```

## 📖 Documentation Map

```
START HERE
    ↓
JENKINS_SETUP_SUMMARY.md (This file - Overview)
    ↓
    ├─→ JENKINS_QUICK_START.md (5-min guide)
    │
    ├─→ JENKINS_SETUP_GUIDE.md (Complete setup)
    │   └─→ Covers: Installation, Configuration, Job Creation
    │
    ├─→ JENKINS_DOCKER_SETUP.md (Docker/Kubernetes)
    │   └─→ Covers: Docker Compose, Dockerfile, K8s, JCasC
    │
    ├─→ JENKINS_CHECKLIST_TROUBLESHOOTING.md (Problems?)
    │   └─→ Covers: Installation, Build, Tests, Reports, Docker
    │
    └─→ JENKINS_ARCHITECTURE_DIAGRAMS.md (Understanding)
        └─→ Covers: Flow, Components, Timeline, Metrics
```

## 📋 Installation Checklist

### Prerequisites
- [ ] Java JDK 8+ installed
- [ ] Maven 3.6+ installed
- [ ] Git installed
- [ ] Chrome/ChromeDriver installed
- [ ] Docker (optional, but recommended)

### Jenkins Setup
- [ ] Jenkins installed and running
- [ ] Initial admin user created
- [ ] Required plugins installed
- [ ] Java & Maven tools configured

### Project Configuration
- [ ] Feature files have tags (@Alert, @LoginPage, etc.)
- [ ] Step definitions implemented
- [ ] pom.xml has required dependencies
- [ ] cucumber.properties configured
- [ ] Runner.java configured

### Job Configuration
- [ ] Pipeline job created
- [ ] Git repository configured (or Jenkinsfile pasted)
- [ ] Script path set to Jenkinsfile
- [ ] First build executed successfully

## 🎯 Pipeline Features

### Automation
✅ Automatic code checkout
✅ Maven compilation
✅ Cucumber test execution with Selenium
✅ Test filtering by tags
✅ Parallel test execution
✅ Screenshot capture on failure
✅ HTML report generation
✅ Test result publishing

### Integration
✅ GitHub webhook integration
✅ Email notifications
✅ Docker support
✅ Kubernetes deployment ready
✅ Slack notifications (optional)
✅ Allure reporting (optional)

### Management
✅ Build history tracking
✅ Artifact archiving
✅ Scheduled builds (cron)
✅ Build parameters
✅ Multiple test suites
✅ Environment configuration

## 📊 Pipeline Stages

```
CODE PUSHED TO GIT
        ↓
    CHECKOUT
        ├─ Clone repository
        └─ Verify branch
        ↓
    BUILD
        ├─ Maven clean
        ├─ Compile code
        └─ Resolve dependencies
        ↓
    TESTS
        ├─ Parse features
        ├─ Execute scenarios
        ├─ Capture screenshots
        └─ Generate JSON
        ↓
    REPORTS
        ├─ Generate HTML
        ├─ Publish results
        └─ Archive artifacts
        ↓
    NOTIFY
        ├─ Email results
        ├─ Slack message
        └─ Build status
        ↓
    COMPLETE (✓ / ⚠ / ✗)
```

## 🔑 Key Files

### Jenkinsfiles (Choose One)

**Jenkinsfile** (Start with this)
- Simple, clean, recommended
- Suitable for most use cases
- ~60 lines

**Jenkinsfile.scripted**
- More flexible
- Custom logic & error handling
- ~70 lines

**Jenkinsfile.advanced**
- Full-featured production ready
- Parameterized builds
- Test suite selection
- Email notifications
- ~250 lines

### Configuration Files

**docker-compose.yml**
- Jenkins + Selenium Grid
- Chrome & Firefox browsers
- MySQL database
- Redis cache
- Grafana & Prometheus (optional)

**jenkins-setup.sh**
- Verifies system requirements
- Checks Java, Maven, Git, ChromeDriver
- Displays configuration hints

## 🐳 Docker Setup (Easiest)

Complete stack with one command:

```bash
docker-compose up -d

# Services started:
# ✓ Jenkins           → http://localhost:8080
# ✓ Selenium Hub      → http://localhost:4444
# ✓ Chrome Node       → Ready for tests
# ✓ Firefox Node      → Ready for tests
# ✓ MySQL             → localhost:3306
# ✓ Redis             → localhost:6379
# ✓ Grafana           → http://localhost:3000
# ✓ Prometheus        → http://localhost:9090
```

Get admin password:
```bash
docker exec jenkins_ui_automation cat /var/jenkins_home/secrets/initialAdminPassword
```

## 🧪 Running Tests

### From Jenkins
```
1. Click "Build Now"
2. Monitor progress
3. View Cucumber Report (after completion)
```

### From Command Line
```bash
# All tests
mvn test

# Specific tag
mvn test -Dcucumber.filter.tags="@Alert"

# Multiple tags (OR)
mvn test -Dcucumber.filter.tags="@Smoke or @Critical"

# Multiple tags (AND)
mvn test -Dcucumber.filter.tags="@Regression and @Critical"

# Skip tests
mvn clean compile -DskipTests
```

## 📈 Viewing Reports

After test execution:

```
Jenkins Dashboard
    ↓
Click Build Number
    ↓
Click "Cucumber Test Report"
    ↓
View:
  ├─ Feature summaries
  ├─ Scenario results
  ├─ Step details
  ├─ Error messages
  └─ Screenshots
```

## 🔍 Test Organization with Tags

Add to feature files:

```gherkin
@Smoke @Alert @Critical
Feature: Alert Handling
  
  @AlertTest1
  Scenario: Handle simple alert
    Given User navigates to alerts page
    When User clicks alert button
    Then User accepts the alert

@Regression @Skip
Scenario: Future test
  ...
```

Run specific tests:
```bash
mvn test -Dcucumber.filter.tags="@Smoke"           # Smoke tests
mvn test -Dcucumber.filter.tags="@Regression"      # Regression tests
mvn test -Dcucumber.filter.tags="@Critical"        # Critical only
mvn test -Dcucumber.filter.tags="not @Skip"        # Skip marked tests
```

## 🛠️ Common Tasks

### View Jenkins Logs
```bash
# Docker
docker logs -f jenkins_ui_automation

# Local
tail -f ~/.jenkins/logs/all.log
```

### Clean Jenkins Workspace
```bash
# Docker
docker exec jenkins_ui_automation rm -rf /var/jenkins_home/workspace/UI_Automation_Pipeline/*

# Local - go to job directory and clear
```

### Update ChromeDriver
```bash
brew upgrade chromedriver
# Or download from: https://chromedriver.chromium.org/
```

### Configure Email Notifications
```
1. Manage Jenkins → Configure System
2. E-mail Notification section
3. Configure SMTP server
4. Test configuration
5. Uncomment email code in Jenkinsfile
```

## 🐛 Quick Troubleshooting

| Issue | Solution |
|-------|----------|
| Tests not found | Check `src/test/resources/features/` directory |
| ChromeDriver error | `brew install chromedriver` |
| Maven not found | `brew install maven` then configure in Global Tool Config |
| Port in use | Change port in docker-compose.yml or `kill -9 <PID>` |
| Report missing | Check `target/report/` exists and cucumber.json generated |
| Build hangs | Increase timeout in Jenkinsfile |

See **JENKINS_CHECKLIST_TROUBLESHOOTING.md** for detailed solutions.

## 🎓 Learning Path

1. **Day 1**: Read JENKINS_QUICK_START.md
2. **Day 2**: Setup Jenkins (Docker or Local)
3. **Day 3**: Create first job and run build
4. **Day 4**: Configure email notifications
5. **Day 5**: Setup GitHub webhook
6. **Week 2**: Add test tags and organize suites
7. **Week 3**: Customize for your team needs

## 📚 Resources

- **Jenkins Docs**: https://www.jenkins.io/doc/
- **Cucumber Docs**: https://cucumber.io/docs/cucumber/
- **Selenium Docs**: https://www.selenium.dev/documentation/
- **Maven Docs**: https://maven.apache.org/
- **Docker Docs**: https://docs.docker.com/

## 💬 FAQ

**Q: Which Jenkinsfile should I use?**
A: Start with `Jenkinsfile` (basic). Use `Jenkinsfile.advanced` for parameterized builds and options.

**Q: Do I need Docker?**
A: No, but it's highly recommended. It simplifies setup significantly.

**Q: Can I run tests in parallel?**
A: Yes! Update pom.xml or Runner.java with threadCount configuration.

**Q: How do I schedule builds?**
A: In Jenkins job → Build Triggers → Build periodically (cron syntax)

**Q: Can I integrate with GitHub?**
A: Yes! Configure GitHub webhook: Settings → Webhooks → `http://jenkins:8080/github-webhook/`

**Q: How do I add email notifications?**
A: Configure SMTP in Jenkins, then uncomment email section in Jenkinsfile.

## ✨ What's Next?

After successful setup:
- [ ] Customize pipeline for your needs
- [ ] Add more feature files with tests
- [ ] Configure email notifications
- [ ] Setup scheduled builds
- [ ] Integrate with GitHub
- [ ] Monitor build trends
- [ ] Share with team
- [ ] Automate deployments

## 🚀 You're Ready!

Your Jenkins pipeline is now ready to:

✅ Automatically run tests on every code push
✅ Generate beautiful test reports
✅ Track test trends and metrics
✅ Notify your team of build status
✅ Scale to your team's needs
✅ Integrate with your existing workflow

**Start with**: JENKINS_QUICK_START.md (5 minutes)

---

## 📞 Need Help?

1. Check **JENKINS_CHECKLIST_TROUBLESHOOTING.md** first
2. Review relevant documentation file
3. Check Jenkins logs: `docker logs jenkins_ui_automation`
4. Review pipeline console output for errors
5. Stack Overflow with tags: `jenkins`, `selenium`, `cucumber`

---

**Created**: February 28, 2026
**Project**: Ui_Automation
**Framework**: Selenium + Cucumber + TestNG
**Status**: ✅ Ready to use

Happy Automating! 🎉

