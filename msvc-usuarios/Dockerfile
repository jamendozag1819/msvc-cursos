FROM openjdk:17-jdk-alpine as builder

WORKDIR /app/msvc-usuarios

COPY ./pom.xml /app
COPY ./msvc-usuarios/.mvn ./.mvn
COPY ./msvc-usuarios/mvnw .
COPY ./msvc-usuarios/pom.xml .

#Descarga las dependencias, la bandera codigo y test sirven para saltarse la compilacion y prueba de
#los test y codigo fuente
RUN ./mvnw clean package -Dmaven.tets.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./msvc-usuarios/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app

RUN mkdir ./logs

COPY --from=builder /app/msvc-usuarios/target/msvc-usuarios-0.0.1-SNAPSHOT.jar .

ENV PORT 8001

EXPOSE $PORT

CMD ["java","-jar","msvc-usuarios-0.0.1-SNAPSHOT.jar"]