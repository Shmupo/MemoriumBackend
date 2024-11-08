# Dockerfile-consumer

# Use an OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the application JAR file (replace 'consumer.jar' with the actual JAR name if different)
COPY target/order-consumer-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 9002

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]