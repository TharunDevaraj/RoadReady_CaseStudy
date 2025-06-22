# Use Java 17 base image
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy JAR file (adjust name if needed)
COPY target/*.jar app.jar

# Expose the service port (adjust accordingly)
EXPOSE 8282

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
