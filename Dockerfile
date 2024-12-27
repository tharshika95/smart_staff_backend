# Base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the application JAR file
COPY target/smart_staff-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on
EXPOSE 8082

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
