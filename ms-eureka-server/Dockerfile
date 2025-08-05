# Etapa 1: Compilación
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copiar todo el proyecto
COPY . .

# Compilar el proyecto sin ejecutar tests
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final
FROM eclipse-temurin:21-jdk-alpine

# Crear un usuario no root por seguridad
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

WORKDIR /home/spring

# Copiar el JAR desde la etapa de construcción
COPY --from=builder /app/target/ms-eureka-server-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto donde corre Eureka (8761)
EXPOSE 8761

# Ejecutar el microservicio
ENTRYPOINT ["java", "-jar", "app.jar"]
