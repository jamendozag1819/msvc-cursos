FROM openjdk:17-jdk-alpine as builder

WORKDIR /app/msvc-cursos

COPY ./pom.xml /app
COPY ./msvc-cursos/.mvn ./.mvn
COPY ./msvc-cursos/mvnw .
COPY ./msvc-cursos/pom.xml .

#Descarga las dependencias, la bandera codigo y test sirven para saltarse la compilacion y prueba de
#los test y codigo fuente
#RUN ./mvnw clean package -Dmaven.tets.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
RUN ./mvnw dependency:go-offline

COPY ./msvc-cursos/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app

RUN mkdir ./logs

COPY --from=builder /app/msvc-cursos/target/msvc-cursos-0.0.1-SNAPSHOT.jar .

EXPOSE 8002

CMD ["java","-jar","msvc-cursos-0.0.1-SNAPSHOT.jar"]