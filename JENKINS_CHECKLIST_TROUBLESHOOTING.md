# Jenkins Setup Checklist & Troubleshooting Guide

## Pre-Setup Checklist

### System Requirements
- [ ] macOS/Linux/Windows with 4GB+ RAM
- [ ] Java JDK 8+ installed
- [ ] Maven 3.6+ installed
- [ ] Git installed
- [ ] Chrome/Firefox installed
- [ ] ChromeDriver/GeckoDriver installed

### Verify Prerequisites
```bash
# Check Java
java -version

# Check Maven
mvn --version

# Check Git
git --version

# Check ChromeDriver
chromedriver --version
```

---

## Installation Checklist

### Option 1: Local Installation

#### Step 1: Install Jenkins
- [ ] Download Jenkins from https://www.jenkins.io/download/
- [ ] Run Jenkins installer
- [ ] Jenkins running on http://localhost:8080
- [ ] Unlock Jenkins with admin password

#### Step 2: Initial Setup
- [ ] Create first admin user
- [ ] Skip suggested plugins (we'll install specific ones)
- [ ] Go to Dashboard

#### Step 3: Install Required Plugins
- [ ] Go to: Dashboard → Manage Jenkins → Manage Plugins → Available
- [ ] Search and Install:
  - [ ] Pipeline
  - [ ] Pipeline: Declarative Agent API
  - [ ] Git
  - [ ] HTML Publisher
  - [ ] JUnit
  - [ ] Email Extension (optional)
  - [ ] Allure (optional)
  - [ ] Log Parser (optional)
- [ ] Restart Jenkins

#### Step 4: Configure Global Tools
- [ ] Go to: Dashboard → Manage Jenkins → Global Tool Configuration
- [ ] Configure JDK:
  - [ ] Name: `JDK8`
  - [ ] JAVA_HOME set correctly
- [ ] Configure Maven:
  - [ ] Name: `Maven3`
  - [ ] MAVEN_HOME set correctly
- [ ] Save

#### Step 5: Configure Email (Optional)
- [ ] Go to: Dashboard → Manage Jenkins → Configure System
- [ ] Find "E-mail Notification" section
- [ ] Configure SMTP Server
- [ ] Set Default Recipients
- [ ] Test Email Configuration

#### Step 6: Configure Security
- [ ] Manage Jenkins → Configure Global Security
- [ ] Enable CSRF Protection
- [ ] Set authorization strategy
- [ ] Save

### Option 2: Docker Installation

#### Step 1: Setup Docker Environment
- [ ] Docker installed
- [ ] Docker Compose installed (v1.29+)
- [ ] docker-compose.yml in project root

#### Step 2: Start Services
```bash
docker-compose up -d
```
- [ ] Jenkins running on http://localhost:8080
- [ ] Selenium Hub running on http://localhost:4444
- [ ] Chrome node registered
- [ ] MySQL running on port 3306

#### Step 3: Get Initial Password
```bash
docker exec jenkins_ui_automation cat /var/jenkins_home/secrets/initialAdminPassword
```
- [ ] Password copied
- [ ] Jenkins unlocked

#### Step 4: Configure Inside Container
- [ ] Create admin user
- [ ] Install recommended plugins
- [ ] Configure tools (already installed in image if custom Dockerfile used)

---

## Jenkins Job Setup

### Create Pipeline Job
- [ ] Click "New Item"
- [ ] Job Name: `UI_Automation_Pipeline`
- [ ] Select "Pipeline"
- [ ] Click OK

### Configure Job Source
**Option A: Pipeline from SCM (Recommended)**
- [ ] Pipeline section → Definition: "Pipeline script from SCM"
- [ ] SCM: Select "Git"
- [ ] Repository URL: `https://github.com/your-repo/Ui_Automation.git`
- [ ] Credentials: Add GitHub credentials (if private repo)
- [ ] Branch: `*/main` or `*/master`
- [ ] Script Path: `Jenkinsfile`
- [ ] Save

**Option B: Direct Pipeline Script**
- [ ] Pipeline section → Definition: "Pipeline script"
- [ ] Copy Jenkinsfile content into Script box
- [ ] Save

### Configure Build Triggers
- [ ] Check "GitHub hook trigger for GITScm polling" (if using GitHub)
- [ ] Or: Check "Build periodically" and set cron schedule
- [ ] Save

---

## Project Configuration

### Verify Feature Files
- [ ] Feature files in: `src/test/resources/features/`
- [ ] Features tagged with: `@Smoke`, `@Regression`, `@Alert`, etc.
- [ ] Steps implemented in: `src/test/java/stepDef/`

### Verify pom.xml
- [ ] Cucumber dependencies added
- [ ] Selenium dependency version compatible
- [ ] Maven Surefire plugin configured
- [ ] Test reports directory set to `target/report/`

### Verify cucumber.properties
- [ ] Located in: `src/test/resources/cucumber.properties`
- [ ] Contains glue paths
- [ ] Contains feature paths
- [ ] Plugin configuration present

### Verify Runner.java
- [ ] Located in: `src/test/java/runner/Runner.java`
- [ ] Extends `AbstractTestNGCucumberTests`
- [ ] CucumberOptions configured correctly
- [ ] Plugin paths point to correct directories
- [ ] Tags configuration present

---

## First Test Run Checklist

### Pre-Execution
- [ ] All code committed and pushed to Git
- [ ] Feature files have proper tags
- [ ] Step definitions implemented
- [ ] No syntax errors in code
- [ ] ChromeDriver/GeckoDriver available

### Running Build
- [ ] Go to Jenkins job
- [ ] Click "Build Now"
- [ ] Monitor build progress in console
- [ ] Wait for completion

### Post-Execution
- [ ] Check console output for errors
- [ ] Navigate to build results
- [ ] View Cucumber Test Report
- [ ] Check for generated reports
- [ ] Archive artifacts present

---

## Troubleshooting Guide

### Build Not Starting

**Problem: "No executor available"**
```
Solution:
1. Go to Manage Jenkins → Manage Nodes
2. Check master node is online
3. Check executor count > 0
4. Restart Jenkins if needed
```

**Problem: "Job not found"**
```
Solution:
1. Verify job name is correct
2. Check job is created
3. Go to Jenkins Dashboard and select job
4. Verify configuration is saved
```

### Git/SCM Issues

**Problem: "Failed to fetch from repository"**
```bash
# Check Git access
git clone https://github.com/your-repo/Ui_Automation.git

# Verify credentials in Jenkins
# Manage Jenkins → Manage Credentials
# Check GitHub credentials are correct
```

**Problem: "Permission denied (public key)"**
```bash
# Generate SSH key
ssh-keygen -t rsa -b 4096 -C "jenkins@example.com"

# Add to GitHub
# Go to GitHub → Settings → SSH Keys
# Paste public key

# Configure Jenkins with SSH
# Use: git@github.com:your-repo/Ui_Automation.git
```

### Maven Build Issues

**Problem: "Maven command not found"**
```bash
# Check Maven installed
mvn --version

# Add to PATH
export PATH=$PATH:/usr/local/bin/maven/bin

# Or configure in Global Tool Configuration
```

**Problem: "Dependencies not found"**
```bash
# Clear Maven cache
rm -rf ~/.m2/repository

# Run build again (will re-download)
mvn clean install
```

### Selenium/Webdriver Issues

**Problem: "ChromeDriver not found"**
```bash
# Install ChromeDriver
brew install chromedriver

# Or set path in code
System.setProperty("webdriver.chrome.driver", 
    "/path/to/chromedriver");
```

**Problem: "Chrome binary not found"**
```bash
# Install Chrome
brew install google-chrome

# Or update ChromeOptions
ChromeOptions options = new ChromeOptions();
options.setBinary("/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");
driver = new ChromeDriver(options);
```

**Problem: "Connection refused" (Selenium Grid)**
```bash
# Check if Selenium Hub is running
curl http://localhost:4444

# Start Docker containers
docker-compose up -d

# Check hub logs
docker logs selenium_hub
```

### Test Execution Issues

**Problem: "No features found"**
```
Solution:
1. Check features directory: src/test/resources/features/
2. Verify .feature files exist
3. Check cucumber.properties has correct path
4. Verify glue paths in Runner.java
```

**Problem: "Step undefined"**
```
Solution:
1. Check step definition class exists
2. Verify @Given/@When/@Then annotations present
3. Verify method signature matches step text
4. Ensure glue path includes stepDef package
5. Rebuild project: mvn clean compile
```

**Problem: "Tests timeout"**
```
Solution:
1. Increase wait timeouts:
   - In UIActionUtility: Increase WebDriverWait duration
   - In Jenkins: Increase stage timeout
2. Check element locators are correct
3. Verify element is actually present on page
4. Add debug logs to see where it hangs
```

### Report Generation Issues

**Problem: "Report directory not found"**
```
Solution:
1. Create target directory: mkdir -p target/report
2. Verify plugin configuration in pom.xml
3. Check reports plugin in pom.xml:
   <plugin>
     <groupId>org.apache.maven.plugins</groupId>
     <artifactId>maven-surefire-plugin</artifactId>
   </plugin>
```

**Problem: "Cucumber report empty"**
```
Solution:
1. Check if tests actually ran
2. Verify cucumber.json generated in target/report/
3. Check report plugin path in pom.xml
4. Ensure no test skips without reason
```

**Problem: "JUnit results not published"**
```
Solution:
1. Verify testng-results.xml path in Jenkins job
2. Check Surefire plugin creates XML reports
3. Verify junit testResults path matches:
   junit testResults: 'target/surefire-reports/**/*.xml'
```

### Docker-Specific Issues

**Problem: "Docker socket permission denied"**
```bash
# Fix permissions
sudo usermod -aG docker $USER
newgrp docker
sudo systemctl restart docker
```

**Problem: "Port already in use"**
```bash
# Find process using port
lsof -i :8080

# Kill process
kill -9 <PID>

# Or change port in docker-compose.yml
ports:
  - "8081:8080"
```

**Problem: "Container won't start"**
```bash
# Check logs
docker logs jenkins_ui_automation

# Increase memory
# In docker-compose.yml:
environment:
  JAVA_OPTS: "-Xmx3072m -Xms1024m"
```

**Problem: "Selenium containers not healthy"**
```bash
# Check container status
docker ps

# View logs
docker logs selenium_chrome
docker logs selenium_hub

# Restart containers
docker-compose restart
```

---

## Performance Optimization

### Memory Configuration
```bash
# Jenkins JVM Options (in docker-compose.yml)
JAVA_OPTS: "-Xmx2048m -Xms512m"

# Increase if running large test suites
JAVA_OPTS: "-Xmx4096m -Xms1024m"
```

### Test Parallel Execution
```groovy
// In pom.xml or Runner.java
<configuration>
    <parallel>methods</parallel>
    <threadCount>4</threadCount>
</configuration>
```

### Browser Options for Speed
```java
ChromeOptions options = new ChromeOptions();
options.addArguments("--disable-images");     // Faster loading
options.addArguments("--disable-plugins");
options.addArguments("--disable-extensions");
options.addArguments("--start-maximized");
```

---

## Monitoring & Logging

### Enable Debug Logging
```bash
# In Jenkins job
mvn test -X -Dorg.slf4j.simpleLogger.defaultLogLevel=debug
```

### Monitor Jenkins
```bash
# View logs
docker logs -f jenkins_ui_automation

# Check disk usage
docker exec jenkins_ui_automation du -sh /var/jenkins_home

# Monitor container stats
docker stats jenkins_ui_automation
```

### Archive Logs
```groovy
// In Jenkinsfile post section
archiveArtifacts artifacts: '**/*.log', allowEmptyArchive: true
```

---

## Maintenance Tasks

### Weekly
- [ ] Review failed test logs
- [ ] Check Jenkins disk space: `df -h`
- [ ] Review build history

### Monthly
- [ ] Update Jenkins plugins
- [ ] Update browser drivers (Chrome, Firefox)
- [ ] Clean up old builds: `Manage Jenkins → Manage Plugins`
- [ ] Backup Jenkins configuration

### Quarterly
- [ ] Update Java/Maven/Git
- [ ] Audit security settings
- [ ] Review and optimize pipeline
- [ ] Update test suites

---

## Quick Reference

| Task | Command |
|------|---------|
| Start Jenkins | `docker-compose up -d` |
| Stop Jenkins | `docker-compose down` |
| View logs | `docker logs -f jenkins_ui_automation` |
| Run tests | `mvn test` |
| Run tests with tags | `mvn test -Dcucumber.filter.tags="@Alert"` |
| Clean build | `mvn clean compile` |
| Skip tests | `mvn clean install -DskipTests` |

---

## Getting Help

- **Jenkins Docs**: https://www.jenkins.io/doc/
- **Cucumber Docs**: https://cucumber.io/docs/
- **Selenium Docs**: https://www.selenium.dev/documentation/
- **Stack Overflow**: Tag questions with `jenkins`, `selenium`, `cucumber`
- **Jenkins Community**: https://www.jenkins.io/community/

---

