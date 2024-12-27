pipeline {
    agent any
    environment {
        DOCKER_HUB_CREDENTIALS = credentials('smart-staff-docker-hub-credentials') // Jenkins Docker Hub credentials ID
        DOCKER_IMAGE = 'tharshika801/smart-staff-backend-app'
        TAG = 'latest'
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/tharshika95/smart_staff_backend.git'
            }
        }
        stage('Build and Test') {
            steps {
                sh './mvnw clean package -DskipTests'
            }
        }
        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${DOCKER_IMAGE}:${TAG} ."
            }
        }
        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', DOCKER_CREDENTIALS) {
                                            sh "docker push ${IMAGE_NAME}:${TAG}"
                    }
                }
            }
        }
        stage('Run Docker Image Locally') {
            steps {
                sh "docker stop smart-staff-backend-app || true && docker rm smart-staff-backend-app || true"
                sh "docker run -d --name smart-staff-backend-app -p 8082:8082 ${DOCKER_IMAGE}:${env.BUILD_NUMBER}"
            }
        }
    }
    post {
        success {
            echo 'Build and deployment successful!'
        }
        failure {
            echo 'Build or deployment failed.'
        }
         always {
                    cleanWs() // Clean workspace after build
         }
    }
}
