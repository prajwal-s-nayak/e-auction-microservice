FROM openjdk:11
EXPOSE 8082
ADD ./target/SellerService-0.0.1-SNAPSHOT.jar /usr/eauction-app/package/SellerService-0.0.1-SNAPSHOT.jar
WORKDIR /usr/eauction-app/package/
ENTRYPOINT ["java","-jar","SellerService-0.0.1-SNAPSHOT.jar"]
