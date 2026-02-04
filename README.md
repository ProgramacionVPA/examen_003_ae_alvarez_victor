# Inventory Config Service

Microservicio para la gestión de configuraciones de inventario, desarrollado con Kotlin y Spring Boot. Implementa seguridad con AWS Cognito y auditoría automática de cambios.

## 📋 Requisitos Previos

* Java 17 (JDK)
* Docker y Docker Compose (Recomendado para la Base de Datos)
* PostgreSQL (Si no se usa Docker)
* Cuenta de AWS con User Pool de Cognito configurado

## 🚀 Puesta en Marcha

### 1. Base de Datos (PostgreSQL)
El proyecto incluye un archivo `docker-compose.yaml` para levantar la base de datos automáticamente.

```bash
# En la raíz del proyecto:
docker-compose up -d


BD: inventory_db

Puerto: 5432

Usuario/Pass: postgres / password

2. Configuración de AWS Cognito
El microservicio requiere un User Pool de AWS Cognito. Asegúrese de que el archivo src/main/resources/application.yaml tenga el ID correcto:

YAML
aws:
  cognito:
    user_pool_id: us-east-2_XXXXXXX  # Reemplazar con su User Pool ID real
    region: us-east-2
3. Ejecutar el Microservicio
Desde la terminal en la raíz del proyecto:

Bash
./gradlew bootRun
O ejecutando la clase principal InventoryConfigServiceApplication desde IntelliJ IDEA.

🔒 Seguridad y Auditoría (Claims)
El sistema implementa seguridad basada en Roles y Auditoría de datos:

Rol de Administrador (ROLE_admin):

Se utiliza el claim estándar cognito:groups.

El usuario debe pertenecer al grupo admin (minúscula) en AWS Cognito.

Spring Security lo mapea automáticamente a ROLE_admin.

Auditoría (updatedBy):

Para cumplir con el requisito de auditoría, se extrae el username (o sub) del JWT.

Este valor se guarda automáticamente en el campo updatedBy de la base de datos en cada operación de escritura (POST, PUT, PATCH).

🧪 Guía de Pruebas (Endpoints)
Se incluye una colección de Postman (Examen_Arquitectura_Alvarez_Victor.json) en el repositorio.

Nivel 1: Público (Sin Token)
GET /public/health: Retorna estado "OK". Sirve para verificar que el servicio inició.

Nivel 2: Autenticado (Cualquier usuario logueado)
GET /api/rules: Lista todas las reglas de inventario.

GET /api/rules/{id}: Obtiene el detalle de una regla específica.

Nivel 3: Administrador (Grupo admin)
Requiere un Token JWT válido de un usuario perteneciente al grupo admin.

POST /api/rules: Crea una nueva regla.

Body: { "name": "...", "description": "...", "isActive": true }

PUT /api/rules/{id}: Actualiza una regla existente.

PATCH /api/rules/{id}/toggle: Invierte el estado (Activo/Inactivo) de la regla.

👤 Autor
Victor Paul Alvarez Alvarez Examen de Arquitectura Empresarial - 2026