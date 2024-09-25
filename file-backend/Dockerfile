FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/file-0.0.1-SNAPSHOT.jar app.jar

RUN apk add --no-cache mysql-client

COPY init_db.sh /docker-entrypoint-initdb.d/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
