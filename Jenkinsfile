
pipeline {
    agent any

    environment {
        IMAGE_NAME = "leave-service:2024SL70006-${BUILD_NUMBER}"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                sh 'mvn clean verify'
            }
        }

        stage('SonarQube Code Inspection') {
            steps {
                withCredentials([
                    string(
                        credentialsId: 'sonarqube-token',
                        variable: 'SONAR_TOKEN'
                    )
                ]) {
                    sh '''
                    mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.11.0.3922:sonar \
                    -Dsonar.host.url=http://localhost:9000 \
                    -Dsonar.login="$SONAR_TOKEN"
                    '''
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh '''
                docker build -t "$IMAGE_NAME" .
                docker images
                '''
            }
        }

        stage('Deploy to Staging') {
            steps {
                sh '''
                docker rm -f leave-service-staging || true

                docker run -d \
                --name leave-service-staging \
                -e APP_ENV=staging \
                -p 8085:8080 \
                "$IMAGE_NAME"
                '''
            }
        }

        stage('Verify Staging') {
            steps {
                sh '''
                sleep 5

                curl --fail --retry 10 --retry-delay 2 \
                http://localhost:8085/

                docker ps
                '''
            }
        }

        stage('Approval and Deploy to Production') {
            steps {

                input message: 'Staging verified. Approve production deployment?',
                      ok: 'Approve Production'

                sh '''
                docker rm -f leave-service-production || true

                docker run -d \
                --name leave-service-production \
                -e APP_ENV=production \
                -p 8086:8080 \
                "$IMAGE_NAME"

                sleep 5

                curl --fail --retry 10 --retry-delay 2 \
                http://localhost:8086/

                docker ps
                '''
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true,
                  testResults: 'target/surefire-reports/*.xml'
        }
    }
}

