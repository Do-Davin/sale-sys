FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY target/sale-system.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
