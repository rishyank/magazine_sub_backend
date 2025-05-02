# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set environment variable to prevent prompts in terminal
ENV DEBIAN_FRONTEND=noninteractive

# Set the working directory inside the container
WORKDIR /app

# Copy the application JAR (change `myapp.jar` to your actual JAR file)
COPY target/*.jar magazine-0.0.1-SNAPSHOT.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8950


# Run the JAR file
ENTRYPOINT ["java", "-jar", "magazine-0.0.1-SNAPSHOT.jar"]
