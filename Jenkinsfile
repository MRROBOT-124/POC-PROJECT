pipeline {
    agent "JenkinsAgent"
    options {
//      PREPEND TIMESTAMP FOR EVERY LOG IN CONSOLE
        timestamp()

        buildDiscarder(logRotator(numToKeepStr: '20'))
    }

    stages {
        stage('Build') {
            steps {
                sh 'gradle build'
            }
        }
    }
}