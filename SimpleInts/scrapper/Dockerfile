FROM maven:3.9.9 as builder
WORKDIR /usr/src
COPY ../ .
RUN mvn clean package -pl scrapper -am -DskipTests

FROM openjdk:21-jdk
WORKDIR /app
COPY --from=builder /usr/src/scrapper/target/scrapper.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
