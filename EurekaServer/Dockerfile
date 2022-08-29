FROM openjdk:11
EXPOSE 8761
ADD ./target/EurekaServer-0.0.1-SNAPSHOT.jar /usr/eauction-app/package/EurekaServer-0.0.1-SNAPSHOT.jar
WORKDIR /usr/eauction-app/package/
ENTRYPOINT ["java","-jar","EurekaServer-0.0.1-SNAPSHOT.jar"]
