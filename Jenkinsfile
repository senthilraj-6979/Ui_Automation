// Jenkins Declarative Pipeline for Selenium UI Automation with Cucumber
pipeline {
    agent any

    options {
        // Keep last 30 builds
        buildDiscarder(logRotator(numToKeepStr: '30'))
        // Add timestamps to console output
        timestamps()
        // Timeout after 1 hour
        timeout(time: 1, unit: 'HOURS')
    }

    environment {
        // Set Maven path (optional, if not in system PATH)
        MAVEN_HOME = tool name: 'Maven3', type: 'maven'
        PATH = "${MAVEN_HOME}/bin:${PATH}"
        // Set Java version if needed
        JAVA_HOME = tool name: 'JDK8', type: 'jdk'
    }

    stages {
        stage('Checkout') {
            steps {
                echo '========== Checkout Code =========='
                checkout scm
                sh 'git --version'
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
                script {
                    try {
                        // Run all tests
                        sh 'mvn test -Dcucumber.filter.tags="@Alert or @LoginPage or @homePage"'
                    } catch (Exception e) {
                        echo "Test execution failed: ${e.message}"
                        // Continue to reporting even if tests fail
                        currentBuild.result = 'UNSTABLE'
                    }
                }
            }
        }

        stage('Generate Reports') {
            steps {
                echo '========== Generating Test Reports =========='
                script {
                    // Cucumber HTML Report
                    publishHTML([
                        reportDir: 'target/report',
                        reportFiles: 'cucumber-reports.html',
                        reportName: 'Cucumber Report',
                        keepAll: true
                    ])

                    // Allure Report (optional - requires Allure plugin)
                    // allure includeProperties: false,
                    //        jdk: '',
                    //        results: [[path: 'target/allure-results']]
                }
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
            echo '========== Test Execution Summary =========='
            // Archive test results
            junit testResults: 'target/surefire-reports/**/*.xml',
                   skipPublishingChecks: true,
                   allowEmptyResults: true

            // Clean workspace
            cleanWs(
                deleteDirs: true,
                patterns: [[pattern: 'target/**', type: 'INCLUDE']]
            )
        }

        success {
            echo "✓ Pipeline executed successfully!"
            // Send success notification
            // emailext(
            //     subject: "Build SUCCESS: ${env.JOB_NAME} - ${env.BUILD_NUMBER}",
            //     body: "Build was successful. Check console output at ${env.BUILD_URL}",
            //     to: "${env.CHANGE_AUTHOR_EMAIL}"
            // )
        }

        failure {
            echo "✗ Pipeline failed!"
            // Send failure notification
            // emailext(
            //     subject: "Build FAILED: ${env.JOB_NAME} - ${env.BUILD_NUMBER}",
            //     body: "Build failed. Check console output at ${env.BUILD_URL}",
            //     to: "${env.CHANGE_AUTHOR_EMAIL}"
            // )
        }

        unstable {
            echo "⚠ Build is unstable - some tests may have failed"
        }
    }
}

