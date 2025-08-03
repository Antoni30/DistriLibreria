# Usar OpenJDK 21 como imagen base
FROM openjdk:21-jdk-slim

# Información del mantenedor
LABEL maintainer="ec.edu.espe"

# Crear directorio de trabajo
WORKDIR /app

# Copiar archivos de Maven wrapper
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn

# Copiar archivo pom.xml
COPY pom.xml .

# Dar permisos de ejecución al wrapper de Maven
RUN chmod +x mvnw

# Descargar dependencias (esto se cachea si no cambia el pom.xml)
RUN ./mvnw dependency:go-offline -B

# Copiar código fuente
COPY src src

# Construir la aplicación
RUN ./mvnw clean package -DskipTests

# Exponer el puerto del API Gateway
EXPOSE 8080

# Ejecutar la aplicación
CMD ["java", "-jar", "target/apiGateway-0.0.1-SNAPSHOT.jar"]
