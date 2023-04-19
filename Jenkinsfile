pipeline {
    agent any
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
    }
}