# Etapa 1: Construcción (Build)
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /app
# Copiamos los archivos de configuración y el código fuente
COPY pom.xml .
COPY src ./src
# Compilamos el proyecto y generamos el .jar
RUN mvn clean package -DskipTests

# Etapa 2: Producción (Run)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Solo copiamos el .jar compilado de la etapa anterior
COPY --from=builder /app/target/backend-innovatech.jar ./app.jar
# Exponemos el puerto del backend (ej. 8080)
EXPOSE 8080
# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]