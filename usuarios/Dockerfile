# Usar Maven con Eclipse Temurin 21 para construir
FROM maven:3.9.4-eclipse-temurin-21 AS build

# Crear directorio de trabajo
WORKDIR /app

# Copiar archivo pom.xml primero (para cache de dependencias)
COPY pom.xml .

# Descargar dependencias con más verbose para debug
RUN mvn dependency:resolve -B -X || mvn dependency:resolve -B

# Copiar código fuente
COPY src src

# Construir la aplicación con más información de debug
RUN mvn clean package -DskipTests -B -X

# Etapa de runtime - usar Eclipse Temurin JRE
FROM eclipse-temurin:21-jre

# Información del mantenedor
LABEL maintainer="ec.edu.espe"

# Crear directorio de trabajo
WORKDIR /app

# Copiar el JAR desde la etapa de build (con nombre correcto basado en pom.xml)
COPY --from=build /app/target/ms-usuarios-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto del microservicio de usuarios
EXPOSE 8080

# Ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]
