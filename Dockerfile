FROM openjdk:latest

ADD target/IUHStudent-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar", "/IUHStudent-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080