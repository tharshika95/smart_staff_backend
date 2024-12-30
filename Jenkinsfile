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
        stage('Run Docker Image Locally') {
            steps {
                echo 'Running Docker container locally...'
                script {
                    // Stop and remove the existing container if it's running
                    bat """
                    docker ps -q --filter "name=${DOCKER_CONTAINER_NAME}" | findstr . && docker stop ${DOCKER_CONTAINER_NAME} || echo "No running container to stop"
                    docker ps -a -q --filter "name=${DOCKER_CONTAINER_NAME}" | findstr . && docker rm ${DOCKER_CONTAINER_NAME} || echo "No container to remove"
                    """

                    // Run the new container
                    bat "docker run -d --name ${DOCKER_CONTAINER_NAME} -p 8888:8888 ${DOCKER_IMAGE}:${TAG}"
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