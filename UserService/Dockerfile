FROM openjdk:11
EXPOSE 8081
ADD ./target/UserService-0.0.1-SNAPSHOT.jar /usr/eauction-app/package/UserService-0.0.1-SNAPSHOT.jar
WORKDIR /usr/eauction-app/package/
ENTRYPOINT ["java","-jar","UserService-0.0.1-SNAPSHOT.jar"]
