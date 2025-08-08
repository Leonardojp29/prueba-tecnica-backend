Este repositorio contiene la solución a la prueba técnica para el puesto de desarrollador Backend. El proyecto está dividido en dos partes:

- prueba-java-api/: API RESTful construida con Java y Spring Boot.
- prueba-laravel-cliente/: Cliente web construido con Laravel que consume la API.

---

Requisitos Previos

Asegúrate de tener instalado en tu máquina:

Para la API en Java
- [Java 17+](https://adoptopenjdk.net/)
- [Maven 3.8+](https://maven.apache.org/)
- [MySQL Server](https://dev.mysql.com/downloads/mysql/)

Para el cliente en Laravel
- [PHP 8.2+](https://www.php.net/)
- [Composer](https://getcomposer.org/)
- [Node.js y NPM](https://nodejs.org/)
- [Laravel CLI (opcional)](https://laravel.com/docs/installation)
- [MySQL Server](si aún no lo tienes)

---

Cómo levantar los proyectos

### 1. Clona el repositorio
git clone https://github.com/Leonardojp29/prueba-tecnica-backend.git
cd prueba-tecnica-backend

## 2. Levantar la API (Java Spring Boot)
cd prueba-java-api/prueba-java-api

## 3. Configura la base de datos en src/main/resources/application.properties:

spring.datasource.url=jdbc:usuario
spring.datasource.password=contraseña

## 4. Asegúrate de que la base de datos prueba exista y ejecuta el proyecto

## 5. Levantar el cliente Laravel
cd ../../prueba-laravel-cliente

## 6. Instala las dependencias
composer install
npm install && npm run dev

## 7. Configura en .env la URL de la API:
API_URL=http://localhost:8080/api

## 8. Levanta el servidor local:
php artisan serve

El cliente estara disponible en:
http://localhost:8000



Funcionalidades:

Desde la interfaz web puedes:

Ver artículos (tanto publicados como no publicados)
Crear nuevos artículos
Editar artículos existentes
Eliminar artículos






