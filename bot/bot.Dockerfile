FROM openjdk:21
LABEL authors="misha"

ENV TELEGRAM_API_KEY=${TELEGRAM_API_KEY}

COPY ./target/bot.jar bot.jar

ENTRYPOINT ["java","-jar","/bot.jar"]
