# Innovatech Chile - Backend Microservicios ⚙️

Este repositorio contiene la API Backend de Innovatech Chile, construida con Java Spring Boot, conectada a una base de datos PostgreSQL y desplegada en una subred privada de AWS.

## 🏗️ Arquitectura y Contenedorización

El proyecto implementa un Dockerfile Multi-stage:
1. Build Stage: Utiliza Maven para compilar y empaquetar el código fuente en un artefacto .jar.
2. Production Stage: Utiliza eclipse-temurin:17-jre-alpine ejecutado bajo un **usuario no root** (llamado 'spring') creado explícitamente, optimizando el tamaño y la seguridad.

## 💾 Persistencia de Datos

Para garantizar la continuidad operativa, implementamos Named Volumes (postgres_data) para la base de datos. Se eligió este tipo de volumen porque es administrado por Docker, garantizando que la información no se pierda incluso si el contenedor se reinicia o destruye.

## 🚀 Cómo ejecutar localmente

1. Ejecuta el stack desde la raíz de este proyecto con el comando: docker-compose up --build -d
2. El backend se comunicará internamente con la BD mediante la red aislada de Docker (innovatech_net).

## 🛡️ Pipeline CI/CD y Despliegue Seguro

El flujo de GitHub Actions se activa en la rama 'deploy'. Dado que este servicio opera en una Subred Privada de AWS (sin acceso desde internet), el pipeline utiliza la instancia pública del Frontend como Bastion Host (Proxy de salto) para conectarse de forma segura por SSH y actualizar el contenedor de la API.