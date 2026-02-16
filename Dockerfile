# =========================
# Build stage with Maven + JDK 21
# =========================
FROM maven:3.9.4-eclipse-temurin-21 AS build

# Set working directory inside the container
WORKDIR /app

# Copy project files
COPY . .

# Make Maven wrapper executable
RUN chmod +x ./mvnw

# Build the Spring Boot app (skip tests for faster builds)
RUN ./mvnw clean package -DskipTests

# =========================
# Runtime stage with lightweight JDK 21
# =========================
FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose Spring Boot port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java","-jar","app.jar"]
