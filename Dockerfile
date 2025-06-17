# Use a lightweight JDK base image
FROM eclipse-temurin:17-jre

# Set work directory
WORKDIR /app

# Copy the built jar (adjust the jar name as needed)
COPY build/libs/jobOrchestrator-1.0-SNAPSHOT.jar app.jar

# Expose the API port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]