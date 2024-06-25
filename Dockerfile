FROM openjdk:21
VOLUME /tmp
EXPOSE 8060
ARG JAR_FILE=target/pharma-connect-payment-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java","-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}","-jar","/app.jar"]
