# Use the official OpenJDK 21 slim image (Debian-based, Ubuntu-compatible)
FROM openjdk:21-jdk-slim

# Set a working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/LMSUsers-0.0.1-SNAPSHOT.jar /app/app.jar

# Informative only; it has no influence in the port effectively exposed by the container.
#EXPOSE 8080

# Set the command to run the JAR file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
