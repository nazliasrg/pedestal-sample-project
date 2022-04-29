FROM openjdk:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/pedestal-sample-project-0.0.1-SNAPSHOT-standalone.jar /pedestal-sample-project/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/pedestal-sample-project/app.jar"]
