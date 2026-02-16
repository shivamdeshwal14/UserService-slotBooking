# Use Java 21
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy project
COPY . .

# Build the app
RUN ./mvnw clean package -DskipTests

# Expose port
EXPOSE 8080

# Run app
CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]
