# Usar Maven con Eclipse Temurin 21 para construir
FROM maven:3.9.4-eclipse-temurin-21 AS build

# Crear directorio de trabajo
WORKDIR /app

# Copiar archivo pom.xml primero (para cache de dependencias)
COPY pom.xml .

# Descargar dependencias
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY src src

# Construir la aplicación
RUN mvn clean package -DskipTests

# Etapa de runtime - usar Eclipse Temurin JRE
FROM eclipse-temurin:21-jre

# Información del mantenedor
LABEL maintainer="ec.edu.espe"

# Crear directorio de trabajo
WORKDIR /app

# Copiar el JAR desde la etapa de build
COPY --from=build /app/target/ms-autenticacion-0.0.1-SNAPSHOT.war app.jar

# Exponer el puerto del microservicio de autenticación
EXPOSE 8080

# Ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]
