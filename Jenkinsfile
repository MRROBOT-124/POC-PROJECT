pipeline {
    agent {
        label "JenkinsAgent"
    }
    options {
//      PREPEND TIMESTAMP FOR EVERY LOG IN CONSOLE
        timestamps()

        buildDiscarder(logRotator(numToKeepStr: '20'))
    }

    stages {

        stage('Build') {
            steps {
                echo "STARTING GRADLE BUILD"
                sh './gradlew build'
                echo "GRADLE BUILD COMPLETED SUCCESSFULLY"
            }
        }


        stage('Test') {
            steps {
                echo "STARTING GRADLE TESTS"
                sh './gradlew test'
                echo 'GRADLE TEST COMPLETED SUCCESSFULLY'
            }
        }

        stage('SonarQube analysis') {
            steps {
                echo "STARTING SONARQUBE SCAN"
                withSonarQubeEnv(installationName: 'Sonar') {
                    // Will pick the global server connection you have configured
                    sh './gradlew sonar'
                }
                echo "SONARQUBE SCAN SUCCESSFULLY COMPLETED"
            }
        }
    }
}