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
                echo 'Checking out code...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Building project...'
                sh 'mvn clean compile 2>&1 | tail -20 || true'
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                sh 'mvn test -Dcucumber.filter.tags="@Alert or @LoginPage or @homePage" 2>&1 | tail -50 || true'
            }
        }

        stage('Reports') {
            steps {
                echo 'Publishing reports...'
                publishHTML([
                    reportDir: 'target/report',
                    reportFiles: 'cucumber-reports.html',
                    reportName: 'Test Report',
                    keepAll: true,
                    allowMissing: true
                ])
            }
        }

        stage('Archive') {
            steps {
                echo 'Archiving artifacts...'
                archiveArtifacts artifacts: 'target/report/**/*,target/screenshots/**/*',
                                 allowEmptyArchive: true
            }
        }
    }

    post {
        always {
            echo 'Build complete'
        }
    }
}

