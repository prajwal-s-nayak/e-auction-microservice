FROM openjdk:11
EXPOSE 8083
ADD ./target/API_Gateway-0.0.1-SNAPSHOT.jar /usr/eauction-app/package/API_Gateway-0.0.1-SNAPSHOT.jar
WORKDIR /usr/eauction-app/package/
ENTRYPOINT ["java","-jar","API_Gateway-0.0.1-SNAPSHOT.jar"]
