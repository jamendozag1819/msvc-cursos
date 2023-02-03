FROM openjdk:17-jdk-alpine as builder

WORKDIR /app/eureka

COPY ./pom.xml /app
COPY ./eureka/.mvn ./.mvn
COPY ./eureka/mvnw .
COPY ./eureka/pom.xml .

#Descarga las dependencias, la bandera codigo y test sirven para saltarse la compilacion y prueba de
#los test y codigo fuente
RUN ./mvnw clean package -Dmaven.tets.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./eureka/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app

RUN mkdir ./logs

COPY --from=builder /app/eureka/target/eureka-0.0.1-SNAPSHOT.jar .

ENV PORT 8761

EXPOSE $PORT

CMD ["java","-jar","eureka-0.0.1-SNAPSHOT.jar"]