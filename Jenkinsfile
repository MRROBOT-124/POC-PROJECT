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
        stage('SCM') {
            steps {
                git 'https://github.com/MRROBOT-124/POC-PROJECT.git'
            }
        }
        stage('SonarQube analysis') {
            steps {
                tools {
                    sonarQube 'SonarQube Scanner 2.8'
                }
                withSonarQubeEnv() { // Will pick the global server connection you have configured
                  sh './gradlew sonarqube'
                }
            }
        }
        stage("Quality Gate") {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    // Parameter indicates whether to set pipeline to UNSTABLE if Quality Gate fails
                    // true = set pipeline to UNSTABLE, false = don't
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Build') {
            steps {
                echo "STARTING GRADLE BUILD"
                sh './gradlew build'
                echo "GRADLE BUILD COMPLETED SUCCESSFULLY"
            }
        }


    }
}