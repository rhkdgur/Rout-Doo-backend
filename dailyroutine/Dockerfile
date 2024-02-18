FROM openjdk:17.0.2-jdk-slim-buster AS builder


WORKDIR /app
COPY gradlew settings.gradle build.gradle ./
COPY gradle ./gradle
COPY src/main ./src/main
RUN ./gradlew clean bootJar

FROM openjdk:17.0.2-slim-buster

WORKDIR /app
COPY  --from=builder /app/build/libs/dailyroutine.jar app.jar

#ENV PROFILE="dev"

ENTRYPOINT ["java", "-jar" ,"app.jar" , "-Dspring.profiles.active=dev"]