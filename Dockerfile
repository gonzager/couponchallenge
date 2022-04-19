#
# Build stage
#

FROM maven:3.6.0-jdk-11-slim AS build
WORKDIR /app
COPY src ./src
COPY pom.xml .
RUN mvn -f pom.xml clean package

#
# Package stage
#

FROM openjdk:11
WORKDIR /app
COPY --from=build /app/target/coupon.jar .
EXPOSE 8080
ENTRYPOINT ["java","-jar","coupon.jar"]










