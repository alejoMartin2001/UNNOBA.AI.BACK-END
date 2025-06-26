# UNNOBA AI - Back-End

Este es el back-end para el proyecto UNNOBA AI, desarrollado con Spring Boot.

## Requisitos Previos

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/downloads/) versión 17 o superior.

## Instalación y Ejecución

Sigue estos pasos para levantar el servidor de back-end localmente.

### 1. Navegar al Directorio del Back-End

Abre una terminal y sitúate en la carpeta del back-end del proyecto.

```bash
cd UNNOBA.AI.BACK-END/
```

### 2. Ejecutar la Aplicación con Maven Wrapper

Este proyecto incluye un [Maven Wrapper](https://github.com/takari/maven-wrapper) (`mvnw`), el cual descargará la versión correcta de Maven y todas las dependencias necesarias automáticamente. No necesitas tener Maven instalado en tu sistema.

Para iniciar la aplicación, ejecuta el siguiente comando en la raíz del directorio `UNNOBA.AI.BACK-END`:

```bash
./mvnw spring-boot:run
```

Si estás en un sistema operativo basado en Unix (Linux o macOS) y obtienes un error de "permiso denegado", dale permisos de ejecución al wrapper primero:

```bash
chmod +x mvnw
```

### 3. ¡Listo!

El servidor se estará ejecutando en `http://localhost:8080`.
