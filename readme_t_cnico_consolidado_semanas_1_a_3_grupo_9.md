# 🚀 Plataforma Cloud de Capacitación Segura con Inteligencia Artificial (`capacitaciones.api`)

**Universidad Privada del Norte (UPN) — Facultad de Ingeniería**

**Carrera:** Ingeniería de Sistemas Computacionales

**Curso:** Soluciones Web y Aplicaciones Distribuidas

**Docente:** Ing. Eber Cisneros Canales

**Repositorio Oficial:** `https://github.com/WEPORXS/capacitaciones-api-upn-grupo9.git`

## 👥 Integrantes del Equipo — Grupo 9

| Integrante | Rol en el Proyecto | Responsabilidad Principal (Semanas 1 a 3) | 
| ----- | ----- | ----- | 
| **Villanueva Portilla, David Armando** | Arquitecto de Datos / DBA | Diseño del modelo relacional `capacitalab` en MySQL 8, integridad referencial `InnoDB` y diagrama EER. | 
| **Garcia Aguilar, Jesus Alberto** | Backend Configuration Lead | Configuración de Spring Boot 4 (Java 21), `pom.xml`, conexión JDBC en `application.properties` (puerto `8088`). | 
| **Silva Quintana, Joel Alberto** | Persistencia JPA & Repositorios | Mapeo de las 4 entidades JPA (`@Entity`, `@ManyToOne`, `@OneToMany`) e interfaces `JpaRepository`. | 
| **Salazar Carbajal, Miguel Angel** | Lógica de Negocio & Validaciones | Implementación de `CapacitacionServiceImpl`, DTOs (`@Valid`), reglas `409 Conflict` y `GlobalExceptionHandler`. | 
| **Criollo Cueva, Oscar Aurelio** | Seguridad JWT, CORS & QA | Configuración de `SecurityConfig` (JWT + CORS `localhost:4200`), `CapacitacionController` y pruebas en Postman. | 

## 📌 Evolución del Proyecto por Semanas (Semanas 1 a 3)

### 🔹 Semana 1: Concepción Backend-First, Requerimientos y Modelo Relacional

* Identificación de la problemática corporativa en la elaboración artesanal de cursos y calificación manual de evaluaciones.

* Definición de **13 Requerimientos Funcionales (RF)** y **12 Requerimientos No Funcionales (RNF)** bajo una arquitectura **N-Capas (MVC)**.

* Diseño del esquema relacional **`capacitalab`** en MySQL 8 compuesto por 4 tablas principales: `perfil`, `capacitacion`, `pregunta` y `evaluacion`.

### 🔹 Semana 2: Implementación Inicial del Backend y Persistencia JPA

* Creación del proyecto `capacitaciones.api` en **Java 21** y **Spring Boot 4** ejecutándose en Apache Tomcat embebido sobre el puerto **`8088`**.

* Mapeo completo de las 4 entidades JPA (`Capacitacion`, `Perfil`, `Pregunta`, `Evaluacion`) y sus 4 interfaces `JpaRepository`.

* Exposición de operaciones CRUD iniciales en `/api/v1/capacitaciones` separando entidades de los contratos DTO (`CapacitacionRequest` y `CapacitacionResponse`).

### 🔹 Semana 3: Consolidación CRUD, Validaciones, Manejo de Excepciones, CORS y Seguridad JWT

* **Reglas de negocio consolidadas:** Verificación de no duplicidad de código (`existsByCodigoIgnoreCase` -> `409 Conflict`), estado inicial `BORRADOR` y eliminación lógica (`activo = false` -> `204 No Content`).

* **Manejo centralizado de errores (`@RestControllerAdvice`):** `GlobalExceptionHandler` estandariza las respuestas `400 Bad Request`, `404 Not Found` y `409 Conflict` sin exponer trazas internas del servidor.

* **Seguridad Stateless con Spring Security y JWT:** Protección de endpoints mediante `JwtAuthenticationFilter` y `JwtService`, habilitando el endpoint `/api/v1/auth/login` y respondiendo `401 Unauthorized` ante accesos sin token.

* **Preparación para Angular (CORS):** Configuración explícita de `CorsConfigurationSource` autorizando el origen `http://localhost:4200` y los métodos `GET`, `POST`, `PUT`, `DELETE` y `OPTIONS`.

## 📂 Estructura de Paquetes (`com.upn.capacitaciones.api`)

```
com.upn.capacitaciones.api
├── capacitacion
│   ├── controller
│   │   └── CapacitacionController.java       # Endpoints REST (/api/v1/capacitaciones)
│   ├── dto
│   │   ├── CapacitacionRequest.java          # DTO de entrada con Jakarta Validation (@NotBlank, @NotNull, @Size)
│   │   └── CapacitacionResponse.java         # DTO de salida desacoplado de la entidad JPA
│   ├── entity
│   │   ├── Capacitacion.java                 # Entidad principal (@ManyToOne Perfil, @OneToMany Pregunta)
│   │   ├── Perfil.java                       # Entidad de usuarios y roles (ADMIN, CREADOR, ALUMNO)
│   │   ├── Pregunta.java                     # Entidad de banco de preguntas por capacitación
│   │   └── Evaluacion.java                   # Entidad de exámenes rendidos y calificados
│   ├── repository
│   │   ├── CapacitacionRepository.java       # JpaRepository + existsByCodigoIgnoreCase
│   │   ├── PerfilRepository.java             # JpaRepository<Perfil, Long>
│   │   ├── PreguntaRepository.java           # JpaRepository<Pregunta, Long>
│   │   └── EvaluacionRepository.java         # JpaRepository<Evaluacion, Long>
│   └── service
│       ├── CapacitacionService.java          # Contrato de negocio
│       └── impl
│           └── CapacitacionServiceImpl.java  # Lógica @Transactional, conversión DTO y baja lógica
├── common
│   ├── exception
│   │   ├── GlobalExceptionHandler.java       # Interceptor @RestControllerAdvice (400, 404, 409)
│   │   ├── ResourceNotFoundException.java    # Excepción para recursos inexistentes (404)
│   │   └── DuplicateResourceException.java   # Excepción para códigos duplicados (409)
│   └── security
│       ├── SecurityConfig.java               # Cadena de filtros Spring Security + CORS (localhost:4200)
│       ├── JwtService.java                   # Generación y firma HMAC-SHA256 de Tokens JWT
│       ├── JwtAuthenticationFilter.java      # Validación de cabecera Authorization: Bearer <token>
│       └── AuthController.java               # Endpoint público POST /api/v1/auth/login
└── Application.java                          # Clase principal de arranque de Spring Boot

```

## 🗄️ Modelo de Base de Datos Relacional (MySQL 8 — `capacitalab`)

* **`perfil` (1) ── (N) `capacitacion`:** Un instructor creador gestiona múltiples capacitaciones (`creador_id`).

* **`capacitacion` (1) ── (N) `pregunta`:** Una capacitación contiene un banco de múltiples preguntas (`capacitacion_id`).

* **`capacitacion` (1) ── (N) `evaluacion`:** Una capacitación registra múltiples intentos de evaluación (`capacitacion_id`).

* **`perfil` (1) ── (N) `evaluacion`:** Un alumno rinde múltiples evaluaciones automatizadas (`alumno_id`).

## 🌐 Contrato de Endpoints REST y Seguridad (Semana 3)

**URL Base del Servidor:** `http://localhost:8088`

**Origen CORS Permitido (Frontend Angular):** `http://localhost:4200`

| N.° | Método | Ruta / Endpoint | Seguridad | Propósito | Códigos HTTP de Respuesta | 
| ----- | ----- | ----- | ----- | ----- | ----- | 
| 1 | `POST` | `/api/v1/auth/login` | Público | Autentica credenciales y emite Token JWT firmado | `200 OK`, `401 Unauthorized` | 
| 2 | `GET` | `/api/v1/capacitaciones` | Bearer JWT | Lista todas las capacitaciones activas (`activo = true`) | `200 OK`, `401 Unauthorized` | 
| 3 | `GET` | `/api/v1/capacitaciones/{id}` | Bearer JWT | Obtiene el detalle de una capacitación por su ID | `200 OK`, `401`, `404 Not Found` | 
| 4 | `POST` | `/api/v1/capacitaciones` | Bearer JWT | Registra una capacitación validando `@Valid` y unicidad | `201 Created`, `400`, `401`, `409 Conflict` | 
| 5 | `PUT` | `/api/v1/capacitaciones/{id}` | Bearer JWT | Actualiza datos o publica capacitación (`BORRADOR` -> `PUBLICADO`) | `200 OK`, `400`, `401`, `404 Not Found` | 
| 6 | `DELETE` | `/api/v1/capacitaciones/{id}` | Bearer JWT | Ejecuta eliminación lógica (`activo = false`) | `204 No Content`, `401`, `404 Not Found` | 

## 🧪 Matriz de Pruebas Verificadas en Postman (Casos Exitosos y Fallidos)

| ID Prueba | Escenario Probado | Método y Endpoint | Entrada / Condición | Resultado Obtenido | 
| ----- | ----- | ----- | ----- | ----- | 
| **CP-01** | Solicitud sin Token JWT | `GET /api/v1/capacitaciones` | Sin cabecera `Authorization` | **`401 Unauthorized`** | 
| **CP-02** | Login y emisión de JWT | `POST /api/v1/auth/login` | `{"email": "admin@upn.edu.pe", "password": "Upn2026*"}` | **`200 OK`** (Retorna Bearer Token) | 
| **CP-03** | Registro válido de curso | `POST /api/v1/capacitaciones` | Header `Bearer <token>` + JSON válido (`CAP-001`) | **`201 Created`** + Header `Location` | 
| **CP-04** | Consulta general activa | `GET /api/v1/capacitaciones` | Header `Bearer <token>` | **`200 OK`** + Lista JSON desde MySQL | 
| **CP-05** | Consulta por ID existente | `GET /api/v1/capacitaciones/1` | Header `Bearer <token>` + `id = 1` | **`200 OK`** + Objeto JSON | 
| **CP-06** | Actualización y publicación | `PUT /api/v1/capacitaciones/1` | Header `Bearer <token>` + `estado: "PUBLICADO"` | **`200 OK`** + Registro actualizado | 
| **CP-07** | Eliminación lógica | `DELETE /api/v1/capacitaciones/1` | Header `Bearer <token>` + `id = 1` | **`204 No Content`** (`activo = 0` en BD) | 
| **CP-08** | Datos inválidos (`@Valid`) | `POST /api/v1/capacitaciones` | JSON con `titulo: ""` vacío | **`400 Bad Request`** (`validationErrors`) | 
| **CP-09** | ID inexistente / Duplicado | `GET /capacitaciones/999` / `POST` | `id = 999` o `codigo: "CAP-001"` repetido | **`404 Not Found`** / **`409 Conflict`** | 

## ⚙️ Instrucciones de Ejecución Paso a Paso

1. **Clonar el repositorio:**

   ```
   git clone https://github.com/WEPORXS/capacitaciones-api-upn-grupo9.git
   cd capacitaciones-api-upn-grupo9
   
   ```

2. **Verificar el esquema en MySQL 8 (Puerto `3306`):**
   Asegurarse de contar con el esquema `capacitalab` activo en MySQL Workbench y verificar el usuario/contraseña en `src/main/resources/application.properties`.

3. **Compilar y ejecutar el servidor Spring Boot (Puerto `8088`):**

   ```
   ./mvnw spring-boot:run
   
   ```

## 🌍 Ficha de Impacto y Alineación con el ODS 9

El proyecto contribuye directamente al **Objetivo de Desarrollo Sostenible N.° 9 (Industria, Innovación e Infraestructura)**:

* **Meta 9.1 (Infraestructuras fiables, sostenibles y resilientes):** Implementa una arquitectura desacoplada N-Capas con persistencia transaccional en MySQL, manejo controlado de excepciones y seguridad stateless mediante tokens JWT.

* **Meta 9.4 (Modernización tecnológica y eficiencia de recursos):** Sustituye el uso de exámenes impresos en papel y hojas de cálculo aisladas por una plataforma web distribuida que automatiza la creación y evaluación de capacitaciones corporativas.