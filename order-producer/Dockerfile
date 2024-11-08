# Dockerfile-producer

# Use an OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the application JAR file (replace 'consumer.jar' with the actual JAR name if different)
COPY target/order-producer-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 9003

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]