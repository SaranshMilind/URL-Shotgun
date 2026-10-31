# Build the Spring Boot application with Java 25 and Maven
FROM maven:3.9-eclipse-temurin-25 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn -B clean package -DskipTests

# Run the packaged application
FROM eclipse-temurin:25-jre

WORKDIR /app

COPY --from=build /app/target/URL-Shortener-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar app.jar"]
