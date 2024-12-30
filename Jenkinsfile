pipeline {
    agent any
    tools {
        jdk 'openjdk17' // Use the correct name from Global Tool Configuration
        maven 'maven'   // Use the correct name from Global Tool Configuration
    }
    environment {
        DOCKER_HUB_CREDENTIALS = credentials('smart-staff-docker-hub-credentials') // Jenkins Docker Hub credentials ID
        TAG = 'latest'
        VERSION = getVersion(GITBRANCH)
        DOCKER_IMAGE = getTagName(VERSION, BUILD_NUMBER)                                                   // Docker image name
        DOCKER_CONTAINER_NAME = 'smart-staff-backend-app'                       // Docker container name
    }
    stages {
        stage('Build and Test') {
            steps {
                echo 'Building the project and running tests...'
                bat '.\\mvnw clean package -DskipTests'
            }
        }
        stage('Build Docker Image') {
            steps {
                echo 'Building Docker image...'
                bat "docker build -t ${DOCKER_IMAGE}:${TAG} ."
            }
        }
        stage('Push Docker Image to Docker Hub') {
            steps {
                echo 'Pushing Docker image to Docker Hub...'
                script {
                    docker.withRegistry('https://index.docker.io/v1/', DOCKER_HUB_CREDENTIALS) {
                        bat "docker push ${DOCKER_IMAGE}:${TAG}"
                    }
                }
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
    }
}

def getVersion(String gitBranch) {
    def parts = gitBranch.tokenize('/')
    return parts[-1]  // Return the last part of the branch name
}

def getTagName(String version, String buildId) {
    def tagname = version + '-' + buildId
    return tagname  // Return the tag name
}