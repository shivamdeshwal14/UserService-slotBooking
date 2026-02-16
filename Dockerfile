# Use a Maven + JDK image to build the app
FROM maven:3.9.4-eclipse-temurin-17 AS build

# Set working directory inside the container
WORKDIR /app

# Copy all project files
COPY . .

# Make Maven wrapper executable
RUN chmod +x ./mvnw

# Build the Spring Boot app (skip tests to speed up)
RUN ./mvnw clean package -DskipTests

# Use a lightweight JDK image for running the app
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java","-jar","app.jar"]
