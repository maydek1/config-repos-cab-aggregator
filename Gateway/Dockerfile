FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/Gateway-0.0.1-SNAPSHOT.jar /app/gateway.jar

ENTRYPOINT ["java", "-jar", "gateway.jar"]