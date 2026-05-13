# ==========================================
# Etapa 1: Construcción (Build Stage)
# ==========================================
FROM maven:3.9-eclipse-temurin-17-alpine AS builder

WORKDIR /app

# Copiamos el POM y descargamos dependencias para optimizar la caché de Docker
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiamos el código fuente y empaquetamos el proyecto saltando los tests
COPY src ./src
RUN mvn clean package -DskipTests

# ==========================================
# Etapa 2: Producción (Production Stage)
# ==========================================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Creación de grupo y usuario NO ROOT (Requisito de la rúbrica)
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copiamos solo el artefacto final (el .jar) desde la Etapa 1
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]