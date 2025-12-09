# Sistema de Gestión de Créditos - Microservicios con Arquitectura Hexagonal

## 📋 Descripción

Sistema distribuido de gestión de solicitudes de crédito implementado con **arquitectura de microservicios** y **arquitectura hexagonal (puertos y adaptadores)** en cada servicio.

## 🏗️ Arquitectura Hexagonal

La arquitectura hexagonal (también conocida como Ports & Adapters) separa la lógica de negocio del dominio de los detalles de infraestructura, permitiendo:

- **Independencia de frameworks**: La lógica de negocio no depende de Spring, JPA, etc.
- **Testabilidad**: Fácil testeo unitario del dominio sin necesidad de infraestructura
- **Flexibilidad**: Los adaptadores pueden cambiarse sin afectar el dominio
- **Mantenibilidad**: Separación clara de responsabilidades

### Estructura de Capas

```
📦 microservicio/
├── 📂 domain/                    # NÚCLEO - Sin dependencias externas
│   ├── model/                    # Entidades de dominio
│   └── exception/                # Excepciones de negocio
├── 📂 application/               # CASOS DE USO
│   ├── port/
│   │   ├── in/                   # Puertos de entrada (interfaces)
│   │   └── out/                  # Puertos de salida (interfaces)
│   └── service/                  # Implementación de casos de uso
└── 📂 infrastructure/            # ADAPTADORES
    ├── adapter/
    │   ├── rest/                 # Adaptador REST (entrada)
    │   ├── persistence/          # Adaptador JPA (salida)
    │   ├── http/                 # Cliente HTTP (salida)
    │   └── security/             # Seguridad (salida)
    └── config/                   # Configuración de Spring
```

## 🎯 Microservicios

### 1. **Auth Service** (Puerto 8081)
Servicio de autenticación y gestión de usuarios.

**Dominio:**
- `Usuario`: Entidad de dominio con lógica de validación
- `TokenAuth`: Value Object para tokens

**Casos de Uso (Puertos de Entrada):**
- `RegistrarUsuarioUseCase`: Registrar nuevos usuarios
- `AutenticarUsuarioUseCase`: Autenticar usuarios y generar tokens

**Adaptadores de Salida:**
- `UsuarioRepositoryPort` → `UsuarioRepositoryAdapter` (JPA)
- `EncriptacionPort` → `EncriptacionAdapter` (SHA-256)
- `TokenGeneratorPort` → `TokenGeneratorAdapter` (Base64)

**Endpoints:**
- `POST /auth/register` - Registrar usuario
- `POST /auth/login` - Autenticar usuario

### 2. **Core Service** (Puerto 8082)
Servicio principal de gestión de solicitudes de crédito.

**Dominio:**
- `SolicitudCredito`: Entidad con lógica de aprobación/rechazo
- `EvaluacionRiesgo`: Value Object para resultado de evaluación

**Casos de Uso (Puertos de Entrada):**
- `CrearSolicitudUseCase`: Crear solicitud y evaluar riesgo
- `ConsultarSolicitudUseCase`: Consultar solicitudes

**Adaptadores de Salida:**
- `SolicitudRepositoryPort` → `SolicitudRepositoryAdapter` (JPA)
- `RiskServicePort` → `RiskServiceAdapter` (HTTP Client)

**Endpoints:**
- `POST /solicitudes` - Crear solicitud
- `GET /solicitudes` - Listar todas
- `GET /solicitudes/{id}` - Buscar por ID
- `GET /solicitudes/documento/{documento}` - Buscar por documento

### 3. **Risk Service** (Puerto 8083)
Servicio de evaluación de riesgo crediticio.

**Dominio:**
- `Evaluacion`: Entidad con algoritmo de cálculo de riesgo

**Casos de Uso (Puertos de Entrada):**
- `EvaluarRiesgoUseCase`: Evaluar riesgo del cliente

**Adaptadores de Salida:**
- `EvaluacionRepositoryPort` → `EvaluacionRepositoryAdapter` (JPA)

**Endpoints:**
- `POST /evaluate` - Evaluar riesgo
- `GET /health` - Health check

## 🔄 Flujo de Comunicación

```
Cliente → Core Service → Risk Service
           ↓
        PostgreSQL (coredb, riskdb, authdb)
```

## 🚀 Ejecución

### Prerequisitos
- Docker y Docker Compose
- Java 17+
- Maven 3.8+

### Iniciar todos los servicios

```bash
docker compose up --build
```

### Servicios disponibles:
- **Auth Service**: http://localhost:8081
- **Core Service**: http://localhost:8082
- **Risk Service**: http://localhost:8083
- **PostgreSQL**: localhost:5432

## 📊 Base de Datos

Cada microservicio tiene su propia base de datos (Database per Service Pattern):

- `authdb` - Usuarios y tokens
- `coredb` - Solicitudes de crédito
- `riskdb` - Historial de evaluaciones

## 🧪 Ejemplos de Uso

### 1. Registrar Usuario
```bash
curl -X POST http://localhost:8081/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "usuario@example.com",
    "password": "password123",
    "nombre": "Juan Pérez",
    "rol": "CLIENTE"
  }'
```

### 2. Login
```bash
curl -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "usuario@example.com",
    "password": "password123"
  }'
```

### 3. Crear Solicitud de Crédito
```bash
curl -X POST http://localhost:8082/solicitudes \
  -H "Content-Type: application/json" \
  -d '{
    "documentoCliente": "12345678",
    "nombreCliente": "Juan Pérez",
    "montoSolicitado": 10000.0,
    "plazoMeses": 24
  }'
```

### 4. Consultar Solicitud
```bash
curl http://localhost:8082/solicitudes/1
```

### 5. Evaluar Riesgo (llamada interna desde core-service)
```bash
curl -X POST http://localhost:8083/evaluate \
  -H "Content-Type: application/json" \
  -d '{
    "documento": "12345678",
    "monto": 10000.0,
    "plazo": 24
  }'
```

## 🎨 Beneficios de la Arquitectura Hexagonal

1. **Dominio Puro**: Las entidades de dominio (`Usuario`, `SolicitudCredito`, `Evaluacion`) no tienen anotaciones de JPA ni Spring
2. **Inversión de Dependencias**: El dominio define interfaces (puertos) que la infraestructura implementa
3. **Testabilidad**: Se pueden testear casos de uso con mocks de los puertos
4. **Intercambiabilidad**: Los adaptadores pueden cambiarse fácilmente (ej: de PostgreSQL a MongoDB)
5. **Claridad**: Separación explícita entre lógica de negocio e infraestructura

## 📁 Estructura Completa del Proyecto

```
credit-app-project/
├── auth-service/
│   ├── src/main/java/com/example/authservice/
│   │   ├── domain/
│   │   │   ├── model/
│   │   │   │   ├── Usuario.java
│   │   │   │   └── TokenAuth.java
│   │   │   └── exception/
│   │   ├── application/
│   │   │   ├── port/in/
│   │   │   ├── port/out/
│   │   │   └── service/
│   │   │       └── AuthService.java
│   │   └── infrastructure/
│   │       ├── adapter/
│   │       │   ├── rest/
│   │       │   ├── persistence/
│   │       │   └── security/
│   │       └── config/
│   ├── Dockerfile
│   └── pom.xml
├── core-service/
│   ├── src/main/java/com/example/coreservice/
│   │   ├── domain/
│   │   ├── application/
│   │   └── infrastructure/
│   ├── Dockerfile
│   └── pom.xml
├── risk-service/
│   ├── src/main/java/com/example/riskservice/
│   │   ├── domain/
│   │   ├── application/
│   │   └── infrastructure/
│   ├── Dockerfile
│   └── pom.xml
├── docker-compose.yml
├── init-databases.sql
└── README.md
```

## 🔧 Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.1.4**
- **Spring Data JPA**
- **PostgreSQL 15**
- **Docker & Docker Compose**
- **Maven**

## 📝 Notas Importantes

- La encriptación actual usa SHA-256 para demostración. En producción usar BCrypt o Argon2.
- Los tokens actuales son simples Base64. En producción usar JWT con firma digital.
- Cada microservicio es independiente y puede desplegarse por separado.
- La comunicación entre servicios es síncrona con REST (puede mejorarse con mensajería asíncrona).

## 🏆 Patrones Implementados

- ✅ Arquitectura Hexagonal (Ports & Adapters)
- ✅ Microservicios
- ✅ Database per Service
- ✅ Repository Pattern
- ✅ Dependency Inversion Principle
- ✅ Use Case Pattern
- ✅ DTO Pattern
- ✅ Mapper Pattern

---

**Desarrollado con Arquitectura Hexagonal y Microservicios** 🚀

