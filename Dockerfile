#  Use a lightweight Java 21 runtime
FROM eclipse-temurin:21-jre

#  Set working directory inside container
WORKDIR /app

# Copy the Spring Boot JAR into container
COPY target/e_commerce_microservices-0.0.1-SNAPSHOT.jar app.jar

#  Expose application port
EXPOSE 8080

#  Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
