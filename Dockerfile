FROM openjdk:17
ARG JAR_FILE=target/github-proxy-0.1.jar
ADD ${JAR_FILE} github-proxy-0.1.jar
ENTRYPOINT ["java","-jar","/github-proxy-0.1.jar","--server.port=8080"]