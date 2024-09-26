FROM maven:3.8.7-eclipse-temurin-17 AS builder

WORKDIR /app

COPY ./pom.xml ./ 

COPY ./src ./src

RUN mvn clean package -DskipTests

RUN ls /app/target

FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY --from=builder /app/target/file-0.0.1-SNAPSHOT.jar app.jar

COPY init_db.sh /docker-entrypoint-initdb.d/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
