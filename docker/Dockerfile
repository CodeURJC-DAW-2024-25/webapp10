#################################################
# Base image for the build container
#################################################
FROM maven:3.9.9-eclipse-temurin-21 AS builder

# Set the working directory where commands will be executed
WORKDIR /project

# Copy project dependencies
COPY ../backend/pom.xml ./

# Download project dependencies
RUN mvn clean package

# Copy project source code
COPY ../backend/src ./src

# Compile project
RUN mvn package -o -DskipTests=true

#################################################
# Base image for the application container
#################################################
FROM openjdk:21-jdk-slim

# Install necessary dependencies for font management
RUN apt-get update && apt-get install -y \
    libfreetype6 \
    libfontconfig1 \
    && rm -rf /var/lib/apt/lists/*

# Set the working directory where the JAR is located
WORKDIR /usr/app/

# Copy the JAR from the build container
COPY --from=builder /project/target/*.jar /usr/app/

# Specify the port that the container exposes
EXPOSE 8443

# Command to run when docker run is executed
CMD [ "java", "-jar", "backend-0.0.1-SNAPSHOT.jar" ]
