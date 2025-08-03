# Usar Eclipse Temurin 21 con Maven incluido
FROM maven:3.9.4-eclipse-temurin-21 AS build

# Crear directorio de trabajo
WORKDIR /app

# Copiar archivo pom.xml
COPY pom.xml .

# Copiar código fuente
COPY src src

# Construir la aplicación
RUN mvn clean package -DskipTests

# Etapa de runtime
FROM eclipse-temurin:21-jre

# Crear directorio de trabajo
WORKDIR /app

# Copiar el WAR construido (no JAR)
COPY --from=build /app/target/ms-catalogo-0.0.1-SNAPSHOT.war app.jar

# Exponer el puerto del microservicio de catálogo
EXPOSE 8080

# Ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]
