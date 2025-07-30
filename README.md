# DistriLibreria

DistriLibreria es un sistema distribuido desarrollado en Java que permite la gestión de libros, autores y papers, con funcionalidades de catálogo y notificaciones. El proyecto está dividido en varios microservicios, utilizando Spring Boot y JPA para la persistencia de datos.

## Características principales

- **Gestión de libros:** Permite crear, actualizar, eliminar y listar libros. Cada libro tiene asociados datos como título, autor, año de publicación, editorial, ISBN, género, número de páginas y un resumen.
- **Gestión de autores:** Permite la administración de autores, incluyendo su creación y consulta.
- **Gestión de papers:** Permite registrar y gestionar papers académicos.
- **Catálogo distribuido:** Incluye un microservicio de catálogo para centralizar y consultar las publicaciones registradas.
- **Notificaciones:** Se envían notificaciones al registrar nuevos libros.
- **API Gateway:** Integra un microservicio API Gateway (GatwAid) para unificar el acceso a los servicios y enrutar las solicitudes a los microservicios correspondientes. La ruta principal del Gateway es el puerto **8000**.
- **Eureka Service Discovery:** Incorpora Eureka para el descubrimiento de servicios, permitiendo que los microservicios se registren y descubran dinámicamente.

## Estructura del proyecto

- `publicaciones/`: Microservicio principal de gestión de publicaciones (libros, autores y papers).
- `ms-catalogo/`: Microservicio encargado del catálogo global de publicaciones.
- `gatwaid/` (API Gateway): Encargado de enrutar las solicitudes de los clientes hacia los microservicios, expuesto en el puerto **8000**.
- `eureka/`: Microservicio de Eureka Service Discovery.
- Cada microservicio utiliza repositorios JPA para la gestión de datos y controladores REST para exponer sus funcionalidades.

## Endpoints principales

- `/libros`: CRUD de libros.
- `/autores`: CRUD de autores.
- `/papers`: CRUD de papers.
- Los microservicios se comunican mediante colas (RabbitMQ) para sincronizar el catálogo y enviar notificaciones.
- Todas las rutas de los servicios pasan por el API Gateway en el puerto **8000**.

## Tecnologías utilizadas

- Java
- Spring Boot
- Spring Data JPA
- Spring Cloud Gateway (GatwAid)
- Eureka Server
- RabbitMQ (mensajería)
- Lombok
- Maven

## Instalación y uso

1. Clona el repositorio:
   ```bash
   git clone https://github.com/Antoni30/DistriLibreria.git
   ```
2. Configura las bases de datos, el broker de mensajes (RabbitMQ), el servidor Eureka y el Gateway.
3. Compila y ejecuta cada microservicio desde su directorio correspondiente:
   ```bash
   cd eureka
   mvn spring-boot:run
   cd ../gatwaid
   mvn spring-boot:run
   cd ../publicaciones
   mvn spring-boot:run
   cd ../ms-catalogo
   mvn spring-boot:run
   ```
4. Accede a los endpoints REST a través del API Gateway (por ejemplo: `http://localhost:8000/libros`, `http://localhost:8000/autores`, etc.) usando tu cliente favorito (Postman, navegador, etc.).

## Contribución

¡Las contribuciones son bienvenidas! Por favor abre un issue o pull request con sugerencias, mejoras o reportes de bugs.

## Licencia

Este proyecto aún no tiene licencia especificada.
