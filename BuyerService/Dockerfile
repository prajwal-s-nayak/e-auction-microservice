FROM openjdk:11
EXPOSE 8084
ADD ./target/BuyerService-0.0.1-SNAPSHOT.jar /usr/eauction-app/package/BuyerService-0.0.1-SNAPSHOT.jar
WORKDIR /usr/eauction-app/package/
ENTRYPOINT ["java","-jar","BuyerService-0.0.1-SNAPSHOT.jar"]
