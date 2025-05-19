FROM openjdk:17-alpine
VOLUME /tmp
EXPOSE 8081
ADD ../WalletManagement/target/WalletManagement-1.0-SNAPSHOT.jar WalletManagement/server.jar
ENTRYPOINT ["java","-jar","WalletManagement/app.jar"]
