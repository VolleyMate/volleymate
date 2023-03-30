FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/spring-petclinic-2.2.0.BUILD-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]