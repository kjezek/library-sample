FROM maven:3.3-jdk-8

ADD src/ src/
ADD pom.xml .

RUN mvn clean install

FROM openjdk:8-oracle
COPY --from=0 /target/backend-1.0-SNAPSHOT.jar /backend.jar

CMD java -jar backend.jar