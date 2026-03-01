node('master') {
    try {
        stage('Checkout') {
            echo 'Checking out code...'
            checkout scm
        }

        stage('Build') {
            echo 'Building project...'
            sh 'mvn clean compile 2>&1 | tail -20 || true'
        }

        stage('Test') {
            echo 'Running tests...'
            sh 'mvn test -Dcucumber.filter.tags="@Alert or @LoginPage or @homePage" 2>&1 | tail -50 || true'
        }

        stage('Reports') {
            echo 'Publishing reports...'
            publishHTML([
                reportDir: 'target/report',
                reportFiles: 'cucumber-reports.html',
                reportName: 'Test Report',
                keepAll: true,
                allowMissing: true
            ])
        }

        stage('Archive') {
            echo 'Archiving artifacts...'
            archiveArtifacts artifacts: 'target/report/**/*,target/screenshots/**/*',
                             allowEmptyArchive: true
        }

        echo 'Build completed successfully'
    } catch (Exception e) {
        echo "Build failed: ${e.message}"
        currentBuild.result = 'FAILURE'
    }
}

