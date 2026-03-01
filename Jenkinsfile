// Jenkins Declarative Pipeline for Selenium UI Automation with Cucumber
// Version: 1.2.0 - Fixed junit context, simplified pipeline, removed tool references
pipeline {
    agent any

    options {
        buildDiscarder(logRotator(numToKeepStr: '30'))
        timestamps()
        timeout(time: 1, unit: 'HOURS')
    }

    stages {
        stage('Checkout') {
            steps {
                echo '========== Checkout Code =========='
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo '========== Building Project =========='
                sh 'mvn clean compile'
            }
        }

        stage('Run Tests') {
            steps {
                echo '========== Running Cucumber Tests =========='
                sh '''
                    mvn test -Dcucumber.filter.tags="@Alert or @LoginPage or @homePage" || true
                '''
            }
        }

        stage('Generate Reports') {
            steps {
                echo '========== Generating Test Reports =========='
                publishHTML([
                    reportDir: 'target/report',
                    reportFiles: 'cucumber-reports.html',
                    reportName: 'Cucumber Report',
                    keepAll: true,
                    allowMissing: true
                ])
            }
        }

        stage('Publish Test Results') {
            steps {
                echo '========== Publishing Test Results =========='
                junit testResults: 'target/surefire-reports/**/*.xml',
                      allowEmptyResults: true,
                      skipPublishingChecks: true
            }
        }

        stage('Archive Artifacts') {
            steps {
                echo '========== Archiving Artifacts =========='
                archiveArtifacts artifacts: 'target/report/**/*,target/screenshots/**/*',
                                 allowEmptyArchive: true
            }
        }
    }

    post {
        always {
            echo '========== Pipeline Complete =========='
        }
        success {
            echo "✓ Build Successful!"
        }
        failure {
            echo "✗ Build Failed - Check logs above"
        }
        unstable {
            echo "⚠ Build Unstable - Some tests may have failed"
        }
    }
}

