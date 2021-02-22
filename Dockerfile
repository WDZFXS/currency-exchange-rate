FROM fabric8/java-alpine-openjdk11-jre
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} currency-exchange-rate.jar
ENTRYPOINT ["java","-jar","currency-exchange-rate.jar"]