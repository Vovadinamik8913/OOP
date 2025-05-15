FROM maven:3.9.9 as builder
WORKDIR /usr/src
COPY ../ .
RUN mvn clean package -pl bot -am -DskipTests

FROM openjdk:21-jdk
WORKDIR /bot
COPY --from=builder /usr/src/bot/target/bot.jar app.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "app.jar"]
