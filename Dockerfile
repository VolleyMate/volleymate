FROM openjdk:8-alpine
# #Required for starting application up.
RUN apk update && apk add /bin/sh
RUN mkdir -p /opt/app
ENV PROJECT_HOME /opt/app
COPY target/spring-petclinic-2.2.0.BUILD-SNAPSHOT.jar $PROJECT_HOME/spring-petclinic-2.2.0.BUILD-SNAPSHOT.jar
WORKDIR $PROJECT_HOME
CMD ["java" ,"-jar","./spring-petclinic-2.2.0.BUILD-SNAPSHOT.jar"]