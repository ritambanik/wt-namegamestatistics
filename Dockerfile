FROM openjdk:8-jdk-alpine
LABEL maintainer="ritam.banik@gmail.com"
VOLUME /tmp
EXPOSE 8083
ARG JAR_FILE=build/libs/namegame-statistics-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} namegame-statistics-service.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/namegame-statistics-service.jar"]
