# Use the official Gradle image to build the project
FROM gradle:7.3.3-jdk11 AS build
WORKDIR /app
COPY . .
RUN ./gradlew :order-service:bootJar

# Use the official OpenJDK image to run the application
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/order-service/build/libs/order-service-0.0.1-SNAPSHOT.jar /app/order-service.jar
ENTRYPOINT ["java", "-jar", "/app/order-service.jar"]
