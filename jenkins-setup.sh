#!/bin/bash

# Jenkins Pipeline Configuration Script for Ui_Automation Project
# This script helps configure Jenkins environment variables and tools

echo "=========================================="
echo "Jenkins Configuration Script"
echo "=========================================="
echo ""

# Check if Jenkins is running
echo "Checking Jenkins connection..."
if curl -s http://localhost:8080 > /dev/null; then
    echo "✓ Jenkins is running on http://localhost:8080"
else
    echo "✗ Jenkins is not running on port 8080"
    echo "  Start Jenkins with: brew services start jenkins"
    exit 1
fi

echo ""
echo "=========================================="
echo "System Requirements Check"
echo "=========================================="

# Check Maven
if command -v mvn &> /dev/null; then
    MAVEN_VERSION=$(mvn --version | head -1)
    echo "✓ Maven installed: $MAVEN_VERSION"
    MAVEN_HOME=$(mvn -v | grep "Maven home" | awk '{print $NF}')
else
    echo "✗ Maven not found"
    echo "  Install with: brew install maven"
fi

# Check Java
if command -v java &> /dev/null; then
    JAVA_VERSION=$(java -version 2>&1 | head -1)
    echo "✓ Java installed: $JAVA_VERSION"
    JAVA_HOME=$(/usr/libexec/java_home)
else
    echo "✗ Java not found"
    echo "  Install with: brew install java"
fi

# Check Git
if command -v git &> /dev/null; then
    GIT_VERSION=$(git --version)
    echo "✓ Git installed: $GIT_VERSION"
else
    echo "✗ Git not found"
    echo "  Install with: brew install git"
fi

# Check ChromeDriver
if command -v chromedriver &> /dev/null; then
    CHROMEDRIVER_VERSION=$(chromedriver --version)
    echo "✓ ChromeDriver installed: $CHROMEDRIVER_VERSION"
else
    echo "✗ ChromeDriver not found"
    echo "  Install with: brew install chromedriver"
fi

echo ""
echo "=========================================="
echo "Environment Variables"
echo "=========================================="
echo "JAVA_HOME=$JAVA_HOME"
echo "MAVEN_HOME=$MAVEN_HOME"
echo ""

echo "=========================================="
echo "Configuration Summary"
echo "=========================================="
echo ""
echo "To complete Jenkins setup:"
echo ""
echo "1. Go to: http://localhost:8080"
echo "2. Manage Jenkins → Global Tool Configuration"
echo "3. Configure JDK:"
echo "   - Name: JDK8"
echo "   - JAVA_HOME: $JAVA_HOME"
echo "4. Configure Maven:"
echo "   - Name: Maven3"
echo "   - MAVEN_HOME: $MAVEN_HOME"
echo "5. Manage Jenkins → Manage Plugins"
echo "   - Install: Pipeline, HTML Publisher, JUnit, Git, Email Extension"
echo ""
echo "6. Create new Pipeline job:"
echo "   - Name: UI_Automation_Pipeline"
echo "   - Pipeline script from SCM"
echo "   - Git repository URL"
echo "   - Script path: Jenkinsfile"
echo ""
echo "=========================================="
echo ""

