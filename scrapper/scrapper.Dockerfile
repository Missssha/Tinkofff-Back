FROM openjdk:21
LABEL authors="misha"

COPY ./target/scrapper.jar scrapper.jar

ENTRYPOINT ["java","-jar","/scrapper.jar"]
