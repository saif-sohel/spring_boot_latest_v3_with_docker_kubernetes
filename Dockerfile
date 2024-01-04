FROM maven:3.8.6 AS build
WORKDIR /app
COPY pom.xml /app
RUN mvn dependency:resolve
COPY . /app
RUN mvn clean package -DskipTests

FROM amazoncorretto:17-alpine-jdk
LABEL maintainer="saifulislamsohel@gmail.com"
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java","-jar","app.jar"]