FROM gradle:8.14-jdk21-alpine AS builder
WORKDIR /app
COPY build.gradle settings.gradle /app/
COPY . /app/
RUN gradle build -x test

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
