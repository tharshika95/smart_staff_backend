# Base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the application JAR file
COPY target/smart_staff-0.0.1-SNAPSHOT.jar app.jar

# Copy the 'uploads' directory with the images into the container
COPY uploads /app/uploads

# Ensure write permissions for the uploads directory
RUN chmod -R 777 /app/uploads

# Expose the port the app runs on
EXPOSE 8082

# Set environment variables for database configuration
ENV DB_HOST=host.docker.internal
ENV DB_PORT=3306
ENV DB_NAME=smart_staff
ENV DB_USER=root
ENV DB_PASSWORD=

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
