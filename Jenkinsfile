pipeline {
    agent any
    tools {
        jdk 'openjdk17' // Use the correct name from Global Tool Configuration
        maven 'maven'   // Use the correct name from Global Tool Configuration
    }
    environment {
        DOCKER_HUB_CREDENTIALS = 'smart-staff-docker-hub-credentials' // Jenkins Docker Hub credentials ID
        TAG = 'latest'
        VERSION = getVersion(GIT_BRANCH)
        DOCKER_IMAGE_NAME = getTagName(VERSION, BUILD_NUMBER)                                                   // Docker image name
        DOCKER_CONTAINER_NAME = 'smart-staff-backend-app'                       // Docker container name
        PORT = '8082'                                                           // Exposed port
        HOST_PORT = '8082'
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
                bat "docker build -t ${DOCKER_IMAGE_NAME}:${TAG} ."
            }
        }
        stage('Push Docker Image to Docker Hub') {
            steps {
                echo 'Pushing Docker image to Docker Hub...'
                withCredentials([usernamePassword(credentialsId: DOCKER_HUB_CREDENTIALS, usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    bat """
                    docker login -u %DOCKER_USER% -p %DOCKER_PASS%
                    docker tag ${DOCKER_IMAGE_NAME} %DOCKER_USER%/${DOCKER_IMAGE_NAME}:latest
                    docker push %DOCKER_USER%/${DOCKER_IMAGE_NAME}:latest
                    """
                }
            }
        }
        stage('Deploy') {
            steps {
                // Stop and remove the existing container, then run a new one
                bat """
                docker stop ${DOCKER_CONTAINER_NAME} || exit /b 0
                docker rm ${DOCKER_CONTAINER_NAME} || exit /b 0
                docker run -d --name ${DOCKER_CONTAINER_NAME} -p ${HOST_PORT}:${PORT} ${DOCKER_IMAGE_NAME}
                """
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