FROM openjdk:latest

ARG PROFILE

ENV PROFILE_VAR=$PROFILE

VOLUME /tmp

ADD target/IUHStudent-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=$PROFILE_VAR","-jar", "app.jar", "/IUHStudent-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080