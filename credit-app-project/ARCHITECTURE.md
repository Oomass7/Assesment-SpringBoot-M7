# Arquitectura del Sistema - Diagramas

## 🏛️ Arquitectura General de Microservicios

```
┌─────────────────────────────────────────────────────────────────┐
│                         CLIENTES                                │
│                    (Aplicaciones Frontend)                       │
└────────────┬────────────────┬────────────────┬──────────────────┘
             │                │                │
             ▼                ▼                ▼
    ┌────────────────┐ ┌────────────────┐ ┌────────────────┐
    │  Auth Service  │ │  Core Service  │ │  Risk Service  │
    │   (Puerto:     │ │   (Puerto:     │ │   (Puerto:     │
    │     8081)      │ │     8082)      │ │     8083)      │
    └────────┬───────┘ └────────┬───────┘ └────────┬───────┘
             │                  │                  │
             │                  │                  │
             │                  └──────────────────┘
             │                  (Comunicación HTTP)
             │
             ▼                  ▼                  ▼
    ┌────────────────┐ ┌────────────────┐ ┌────────────────┐
    │   PostgreSQL   │ │   PostgreSQL   │ │   PostgreSQL   │
    │    (authdb)    │ │    (coredb)    │ │    (riskdb)    │
    └────────────────┘ └────────────────┘ └────────────────┘
```

## 🎯 Arquitectura Hexagonal - Detalle por Servicio

### AUTH SERVICE

```
┌───────────────────────────────────────────────────────────────────┐
│                        AUTH SERVICE                                │
├───────────────────────────────────────────────────────────────────┤
│                                                                    │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │              CAPA DE ADAPTADORES (Entrada)               │   │
│  │                                                           │   │
│  │  ┌─────────────────────────────────────────────────┐    │   │
│  │  │      AuthRestController (REST API)              │    │   │
│  │  │  POST /auth/register                            │    │   │
│  │  │  POST /auth/login                               │    │   │
│  │  └──────────────────┬──────────────────────────────┘    │   │
│  └─────────────────────┼───────────────────────────────────┘   │
│                        │                                         │
│  ┌─────────────────────▼───────────────────────────────────┐   │
│  │           CAPA DE APLICACIÓN (Casos de Uso)            │   │
│  │                                                         │   │
│  │  ┌────────────────────────────────────────────────┐   │   │
│  │  │         AuthService                            │   │   │
│  │  │  - RegistrarUsuarioUseCase                     │   │   │
│  │  │  - AutenticarUsuarioUseCase                    │   │   │
│  │  └──────────────┬─────────────────────────────────┘   │   │
│  └─────────────────┼──────────────────────────────────────┘   │
│                    │                                            │
│  ┌─────────────────▼──────────────────────────────────────┐   │
│  │              CAPA DE DOMINIO (Lógica de Negocio)       │   │
│  │                                                         │   │
│  │  ┌────────────────┐  ┌──────────────┐                 │   │
│  │  │    Usuario     │  │  TokenAuth   │                 │   │
│  │  │  - esValido()  │  │  - esValido()│                 │   │
│  │  │  - activar()   │  │              │                 │   │
│  │  └────────────────┘  └──────────────┘                 │   │
│  └─────────────┬───────────────────────────────────────────┘   │
│                │                                                │
│  ┌─────────────▼──────────────────────────────────────────┐   │
│  │         CAPA DE ADAPTADORES (Salida)                   │   │
│  │                                                         │   │
│  │  ┌──────────────────┐  ┌──────────────────────┐       │   │
│  │  │ UsuarioRepository│  │  EncriptacionAdapter │       │   │
│  │  │   Adapter (JPA)  │  │  TokenGeneratorAdap. │       │   │
│  │  └────────┬─────────┘  └──────────────────────┘       │   │
│  └───────────┼────────────────────────────────────────────┘   │
│              │                                                  │
└──────────────┼──────────────────────────────────────────────────┘
               ▼
        ┌─────────────┐
        │  PostgreSQL │
        │   (authdb)  │
        └─────────────┘
```

### CORE SERVICE

```
┌───────────────────────────────────────────────────────────────────┐
│                        CORE SERVICE                                │
├───────────────────────────────────────────────────────────────────┤
│                                                                    │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │              CAPA DE ADAPTADORES (Entrada)               │   │
│  │                                                           │   │
│  │  ┌─────────────────────────────────────────────────┐    │   │
│  │  │   SolicitudRestController (REST API)            │    │   │
│  │  │  POST   /solicitudes                            │    │   │
│  │  │  GET    /solicitudes                            │    │   │
│  │  │  GET    /solicitudes/{id}                       │    │   │
│  │  │  GET    /solicitudes/documento/{doc}            │    │   │
│  │  └──────────────────┬──────────────────────────────┘    │   │
│  └─────────────────────┼───────────────────────────────────┘   │
│                        │                                         │
│  ┌─────────────────────▼───────────────────────────────────┐   │
│  │           CAPA DE APLICACIÓN (Casos de Uso)            │   │
│  │                                                         │   │
│  │  ┌────────────────────────────────────────────────┐   │   │
│  │  │         SolicitudService                       │   │   │
│  │  │  - CrearSolicitudUseCase                       │   │   │
│  │  │  - ConsultarSolicitudUseCase                   │   │   │
│  │  └──────────────┬─────────────────────────────────┘   │   │
│  └─────────────────┼──────────────────────────────────────┘   │
│                    │                                            │
│  ┌─────────────────▼──────────────────────────────────────┐   │
│  │              CAPA DE DOMINIO (Lógica de Negocio)       │   │
│  │                                                         │   │
│  │  ┌──────────────────────┐  ┌──────────────────┐       │   │
│  │  │  SolicitudCredito    │  │ EvaluacionRiesgo │       │   │
│  │  │  - esValida()        │  │ (Value Object)   │       │   │
│  │  │  - aprobar()         │  │                  │       │   │
│  │  │  - rechazar()        │  │                  │       │   │
│  │  │  - asignarEvaluacion│  │                  │       │   │
│  │  └──────────────────────┘  └──────────────────┘       │   │
│  └─────────────┬───────────────────────────────────────────┘   │
│                │                                                │
│  ┌─────────────▼──────────────────────────────────────────┐   │
│  │         CAPA DE ADAPTADORES (Salida)                   │   │
│  │                                                         │   │
│  │  ┌──────────────────┐  ┌──────────────────────┐       │   │
│  │  │ SolicitudReposit.│  │  RiskServiceAdapter  │       │   │
│  │  │  Adapter (JPA)   │  │   (HTTP Client)      │───────┼───┐
│  │  └────────┬─────────┘  └──────────────────────┘       │   │
│  └───────────┼────────────────────────────────────────────┘   │
│              │                                                  │
└──────────────┼──────────────────────────────────────────────────┘
               ▼                                        │
        ┌─────────────┐                                │
        │  PostgreSQL │                                │
        │   (coredb)  │                                │
        └─────────────┘                                │
                                                       │
        ┌──────────────────────────────────────────────┘
        │
        ▼
┌───────────────────────┐
│    Risk Service       │
│  POST /evaluate       │
└───────────────────────┘
```

### RISK SERVICE

```
┌───────────────────────────────────────────────────────────────────┐
│                        RISK SERVICE                                │
├───────────────────────────────────────────────────────────────────┤
│                                                                    │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │              CAPA DE ADAPTADORES (Entrada)               │   │
│  │                                                           │   │
│  │  ┌─────────────────────────────────────────────────┐    │   │
│  │  │     RiskRestController (REST API)               │    │   │
│  │  │  POST /evaluate                                 │    │   │
│  │  │  GET  /health                                   │    │   │
│  │  └──────────────────┬──────────────────────────────┘    │   │
│  └─────────────────────┼───────────────────────────────────┘   │
│                        │                                         │
│  ┌─────────────────────▼───────────────────────────────────┐   │
│  │           CAPA DE APLICACIÓN (Casos de Uso)            │   │
│  │                                                         │   │
│  │  ┌────────────────────────────────────────────────┐   │   │
│  │  │         RiskService                            │   │   │
│  │  │  - EvaluarRiesgoUseCase                        │   │   │
│  │  └──────────────┬─────────────────────────────────┘   │   │
│  └─────────────────┼──────────────────────────────────────┘   │
│                    │                                            │
│  ┌─────────────────▼──────────────────────────────────────┐   │
│  │              CAPA DE DOMINIO (Lógica de Negocio)       │   │
│  │                                                         │   │
│  │  ┌──────────────────────────────────────────────┐     │   │
│  │  │         Evaluacion                           │     │   │
│  │  │  - esValida()                                │     │   │
│  │  │  - calcularRiesgo()                          │     │   │
│  │  │    * Algoritmo de scoring                    │     │   │
│  │  │    * Clasificación por nivel                 │     │   │
│  │  └──────────────────────────────────────────────┘     │   │
│  └─────────────┬───────────────────────────────────────────┘   │
│                │                                                │
│  ┌─────────────▼──────────────────────────────────────────┐   │
│  │         CAPA DE ADAPTADORES (Salida)                   │   │
│  │                                                         │   │
│  │  ┌──────────────────────────────────────────────┐     │   │
│  │  │  EvaluacionRepositoryAdapter (JPA)           │     │   │
│  │  └────────┬─────────────────────────────────────┘     │   │
│  └───────────┼────────────────────────────────────────────┘   │
│              │                                                  │
└──────────────┼──────────────────────────────────────────────────┘
               ▼
        ┌─────────────┐
        │  PostgreSQL │
        │   (riskdb)  │
        └─────────────┘
```

## 🔄 Flujo de Proceso: Crear Solicitud de Crédito

```
┌─────────┐
│ Cliente │
└────┬────┘
     │
     │ 1. POST /solicitudes
     │    {documento, nombre, monto, plazo}
     ▼
┌────────────────┐
│  Core Service  │
│                │
│ ┌────────────┐ │
│ │   REST     │ │ 2. Recibe request
│ │ Controller │ │    Convierte DTO → Domain
│ └─────┬──────┘ │
│       │        │
│ ┌─────▼──────┐ │
│ │ Solicitud  │ │ 3. Ejecuta caso de uso
│ │  Service   │ │    - Valida solicitud
│ └─────┬──────┘ │    - Guarda en DB
│       │        │
└───────┼────────┘
        │
        │ 4. Llamada HTTP a Risk Service
        │    POST /evaluate
        │    {documento, monto, plazo}
        ▼
┌────────────────┐
│  Risk Service  │
│                │
│ ┌────────────┐ │
│ │   REST     │ │ 5. Recibe evaluación
│ │ Controller │ │
│ └─────┬──────┘ │
│       │        │
│ ┌─────▼──────┐ │
│ │   Risk     │ │ 6. Calcula riesgo
│ │  Service   │ │    - Algoritmo de scoring
│ └─────┬──────┘ │    - Clasifica nivel
│       │        │
│ ┌─────▼──────┐ │
│ │Repository  │ │ 7. Guarda evaluación
│ └─────┬──────┘ │
└───────┼────────┘
        │
        │ 8. Retorna resultado
        │    {documento, score, nivelRiesgo}
        ▼
┌────────────────┐
│  Core Service  │
│                │
│ ┌────────────┐ │
│ │ Solicitud  │ │ 9. Procesa resultado
│ │  Service   │ │    - Asigna evaluación
│ └─────┬──────┘ │    - Aprueba/Rechaza según nivel
│       │        │    - Actualiza en DB
│ ┌─────▼──────┐ │
│ │Repository  │ │ 10. Persiste solicitud actualizada
│ └─────┬──────┘ │
│       │        │
│ ┌─────▼──────┐ │
│ │   REST     │ │ 11. Retorna respuesta
│ │ Controller │ │     Convierte Domain → DTO
│ └─────┬──────┘ │
└───────┼────────┘
        │
        │ 12. Response JSON
        │     {id, estado, scoreRiesgo, nivelRiesgo, ...}
        ▼
┌─────────┐
│ Cliente │
└─────────┘
```

## 📊 Patrones y Principios Aplicados

### Arquitectura Hexagonal
```
        ┌──────────────────────────────────────┐
        │      ADAPTADORES DE ENTRADA          │
        │  (REST Controllers, Message Queue)   │
        └────────────────┬─────────────────────┘
                         │
        ┌────────────────▼─────────────────────┐
        │         PUERTOS DE ENTRADA           │
        │      (Use Case Interfaces)           │
        └────────────────┬─────────────────────┘
                         │
        ┌────────────────▼─────────────────────┐
        │       CAPA DE APLICACIÓN             │
        │      (Servicios/Use Cases)           │
        └────────────────┬─────────────────────┘
                         │
        ┌────────────────▼─────────────────────┐
        │         DOMINIO (CORE)               │
        │    Entidades + Lógica de Negocio     │
        │   ❌ SIN dependencias externas       │
        └────────────────┬─────────────────────┘
                         │
        ┌────────────────▼─────────────────────┐
        │         PUERTOS DE SALIDA            │
        │    (Repository/Service Interfaces)   │
        └────────────────┬─────────────────────┘
                         │
        ┌────────────────▼─────────────────────┐
        │      ADAPTADORES DE SALIDA           │
        │   (JPA, HTTP Client, External APIs)  │
        └──────────────────────────────────────┘
```

### Principios SOLID Aplicados

- ✅ **S**ingle Responsibility: Cada clase tiene una única responsabilidad
- ✅ **O**pen/Closed: Abierto a extensión (nuevos adaptadores), cerrado a modificación
- ✅ **L**iskov Substitution: Los adaptadores son intercambiables
- ✅ **I**nterface Segregation: Interfaces específicas (puertos) por funcionalidad
- ✅ **D**ependency Inversion: El dominio no depende de la infraestructura

---

**Documentación de Arquitectura** 📐
