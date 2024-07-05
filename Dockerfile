FROM maven:3-openjdk-17-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
#COPY config/* /home/app/config/
#COPY keys/* /home/app/keys/
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip=true

#
# Package stage
#
FROM amazoncorretto:17

WORKDIR /app

COPY --from=build /home/app/target/AfriPay.jar .

ENTRYPOINT ["java", "-jar","AfriPay.jar"]