FROM openjdk:21-slim as build

WORKDIR /app

# Copiar archivos de Maven
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

RUN chmod +x mvnw

# Intentar descargar dependencias con reintentos
RUN ./mvnw dependency:go-offline -B || \
    ./mvnw dependency:go-offline -B || \
    ./mvnw dependency:go-offline -B

COPY src src

# Limpiar y construir el proyecto
RUN ./mvnw clean package -DskipTests

FROM openjdk:21-slim

WORKDIR /app

COPY --from=build /app/target/*.war app.war

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.war"]