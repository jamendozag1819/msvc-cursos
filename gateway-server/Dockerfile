FROM openjdk:17-jdk-alpine as builder

WORKDIR /app/gateway-server

COPY ./pom.xml /app
COPY ./gateway-server/.mvn ./.mvn
COPY ./gateway-server/mvnw .
COPY ./gateway-server/pom.xml .

#Descarga las dependencias, la bandera codigo y test sirven para saltarse la compilacion y prueba de
#los test y codigo fuente
RUN ./mvnw clean package -Dmaven.tets.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./gateway-server/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app

RUN mkdir ./logs

COPY --from=builder /app/gateway-server/target/gateway-server-0.0.1-SNAPSHOT.jar .

ENV PORT 8090

EXPOSE $PORT

CMD ["java","-jar","gateway-server-0.0.1-SNAPSHOT.jar"]