FROM maven:3.9.6 AS build
WORKDIR /app
COPY pom.xml ./
COPY src ./src
RUN mvn clean package -DskipTests

FROM amazoncorretto:17-alpine-jdk
LABEL maintainer="saifulislamsohel@gmail.com"

COPY --from=build /app/target/*.jar app.jar
COPY keystore.p12 /app/keystore.p12

EXPOSE 8443

CMD ["java", "-Dserver.ssl.key-store=/app/keystore.p12", "-Dserver.ssl.key-store-password=1234", "-Dserver.ssl.key-store-type=PKCS12", "-Dserver.ssl.key-alias=tomcat", "-jar", "app.jar"]