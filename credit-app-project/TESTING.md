# Guía de Testing del Sistema

## 🧪 Scripts de Prueba

### 1. Test del Auth Service

#### Registrar Usuario
```bash
# Registrar un nuevo usuario
curl -X POST http://localhost:8081/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "juan.perez@example.com",
    "password": "Pass1234",
    "nombre": "Juan Pérez",
    "rol": "CLIENTE"
  }'

# Respuesta esperada:
# {
#   "mensaje": "Usuario registrado exitosamente con ID: 1",
#   "token": null,
#   "tipo": null
# }
```

#### Login
```bash
# Autenticar usuario
curl -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "juan.perez@example.com",
    "password": "Pass1234"
  }'

# Respuesta esperada:
# {
#   "token": "anVhbi5wZXJlekBleGFtcGxlLmNvbTo3YzM5ZmQxMi0xYjQzLTRkYWEtODhlOC1mNjNiYzIyZDA1OWE=",
#   "tipo": "Bearer",
#   "mensaje": "Autenticación exitosa"
# }
```

### 2. Test del Core Service

#### Crear Solicitud de Crédito - Riesgo BAJO (Aprobada Automáticamente)
```bash
curl -X POST http://localhost:8082/solicitudes \
  -H "Content-Type: application/json" \
  -d '{
    "documentoCliente": "98765432",
    "nombreCliente": "María González",
    "montoSolicitado": 5000.0,
    "plazoMeses": 12
  }'

# Respuesta esperada (estado: APROBADA, nivelRiesgo: BAJO):
# {
#   "id": 1,
#   "documentoCliente": "98765432",
#   "nombreCliente": "María González",
#   "montoSolicitado": 5000.0,
#   "plazoMeses": 12,
#   "estado": "APROBADA",
#   "scoreRiesgo": 850,
#   "nivelRiesgo": "BAJO",
#   "fechaSolicitud": "2025-12-09T...",
#   "fechaEvaluacion": "2025-12-09T..."
# }
```

#### Crear Solicitud - Riesgo MEDIO (Requiere Revisión Manual)
```bash
curl -X POST http://localhost:8082/solicitudes \
  -H "Content-Type: application/json" \
  -d '{
    "documentoCliente": "45678901",
    "nombreCliente": "Carlos Ruiz",
    "montoSolicitado": 15000.0,
    "plazoMeses": 24
  }'

# Respuesta esperada (estado: PENDIENTE, nivelRiesgo: MEDIO)
```

#### Crear Solicitud - Riesgo ALTO (Rechazada Automáticamente)
```bash
curl -X POST http://localhost:8082/solicitudes \
  -H "Content-Type: application/json" \
  -d '{
    "documentoCliente": "12345678",
    "nombreCliente": "Pedro Sánchez",
    "montoSolicitado": 50000.0,
    "plazoMeses": 48
  }'

# Respuesta esperada (estado: RECHAZADA, nivelRiesgo: ALTO)
```

#### Listar Todas las Solicitudes
```bash
curl http://localhost:8082/solicitudes

# Respuesta esperada: Array con todas las solicitudes
```

#### Buscar Solicitud por ID
```bash
curl http://localhost:8082/solicitudes/1

# Respuesta esperada: Detalles de la solicitud con ID 1
```

#### Buscar Solicitudes por Documento
```bash
curl http://localhost:8082/solicitudes/documento/98765432

# Respuesta esperada: Array con todas las solicitudes del cliente
```

### 3. Test del Risk Service

#### Evaluar Riesgo Directamente
```bash
# Evaluar riesgo de un cliente
curl -X POST http://localhost:8083/evaluate \
  -H "Content-Type: application/json" \
  -d '{
    "documento": "87654321",
    "monto": 10000.0,
    "plazo": 24
  }'

# Respuesta esperada:
# {
#   "documento": "87654321",
#   "score": 650,
#   "nivelRiesgo": "MEDIO"
# }
```

#### Health Check
```bash
curl http://localhost:8083/health

# Respuesta esperada: "Risk Service is running"
```

## 📝 Casos de Prueba Completos

### Caso 1: Flujo Completo - Cliente con Buen Score

```bash
# Paso 1: Registrar usuario
curl -X POST http://localhost:8081/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "cliente.premium@example.com",
    "password": "Secure123",
    "nombre": "Cliente Premium",
    "rol": "CLIENTE"
  }'

# Paso 2: Login
curl -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "cliente.premium@example.com",
    "password": "Secure123"
  }'

# Paso 3: Crear solicitud (será aprobada automáticamente)
curl -X POST http://localhost:8082/solicitudes \
  -H "Content-Type: application/json" \
  -d '{
    "documentoCliente": "99999999",
    "nombreCliente": "Cliente Premium",
    "montoSolicitado": 8000.0,
    "plazoMeses": 18
  }'

# Paso 4: Consultar la solicitud creada
curl http://localhost:8082/solicitudes/1
```

### Caso 2: Múltiples Solicitudes del Mismo Cliente

```bash
# Solicitud 1 - Monto bajo
curl -X POST http://localhost:8082/solicitudes \
  -H "Content-Type: application/json" \
  -d '{
    "documentoCliente": "11111111",
    "nombreCliente": "Ana Torres",
    "montoSolicitado": 3000.0,
    "plazoMeses": 12
  }'

# Solicitud 2 - Monto medio
curl -X POST http://localhost:8082/solicitudes \
  -H "Content-Type: application/json" \
  -d '{
    "documentoCliente": "11111111",
    "nombreCliente": "Ana Torres",
    "montoSolicitado": 15000.0,
    "plazoMeses": 24
  }'

# Solicitud 3 - Monto alto
curl -X POST http://localhost:8082/solicitudes \
  -H "Content-Type: application/json" \
  -d '{
    "documentoCliente": "11111111",
    "nombreCliente": "Ana Torres",
    "montoSolicitado": 60000.0,
    "plazoMeses": 60
  }'

# Consultar todas las solicitudes del cliente
curl http://localhost:8082/solicitudes/documento/11111111
```

### Caso 3: Validación de Errores

```bash
# Error: Usuario ya existe
curl -X POST http://localhost:8081/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "cliente.premium@example.com",
    "password": "Pass123",
    "nombre": "Otro Usuario",
    "rol": "CLIENTE"
  }'
# Respuesta esperada: {"error": "El email ya está registrado"}

# Error: Credenciales inválidas
curl -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "noexiste@example.com",
    "password": "wrong"
  }'
# Respuesta esperada: {"error": "Usuario no encontrado"}

# Error: Datos inválidos en solicitud
curl -X POST http://localhost:8082/solicitudes \
  -H "Content-Type: application/json" \
  -d '{
    "documentoCliente": "",
    "nombreCliente": "",
    "montoSolicitado": -1000.0,
    "plazoMeses": 0
  }'
# Respuesta esperada: {"error": "Datos de solicitud inválidos"}

# Error: Solicitud no encontrada
curl http://localhost:8082/solicitudes/9999
# Respuesta esperada: {"error": "Solicitud no encontrada con ID: 9999"}
```

## 🔍 Verificación de Arquitectura Hexagonal

### Verificar Independencia del Dominio

1. **Revisar que las clases de dominio no tengan dependencias de Spring/JPA:**
   - ✅ `Usuario.java` - Sin anotaciones @Entity
   - ✅ `SolicitudCredito.java` - Sin anotaciones @Entity
   - ✅ `Evaluacion.java` - Sin anotaciones @Entity

2. **Verificar que los puertos sean interfaces puras:**
   - ✅ `UsuarioRepositoryPort` - Interface sin implementación
   - ✅ `SolicitudRepositoryPort` - Interface sin implementación
   - ✅ `RiskServicePort` - Interface sin implementación

3. **Confirmar que los adaptadores implementen los puertos:**
   - ✅ `UsuarioRepositoryAdapter implements UsuarioRepositoryPort`
   - ✅ `SolicitudRepositoryAdapter implements SolicitudRepositoryPort`
   - ✅ `RiskServiceAdapter implements RiskServicePort`

### Verificar Comunicación entre Microservicios

```bash
# Verificar que Core Service llama a Risk Service
# 1. Crear solicitud en Core Service
curl -X POST http://localhost:8082/solicitudes \
  -H "Content-Type: application/json" \
  -d '{
    "documentoCliente": "TEST123",
    "nombreCliente": "Test Usuario",
    "montoSolicitado": 10000.0,
    "plazoMeses": 24
  }'

# 2. Verificar que Risk Service guardó la evaluación
# (Revisar logs de risk-service)
docker compose logs risk-service | grep "TEST123"
```

## 📊 Verificar Base de Datos

```bash
# Conectarse a PostgreSQL
docker exec -it credit-app-project-postgres-1 psql -U postgres

# Verificar base de datos authdb
\c authdb
SELECT * FROM usuarios;

# Verificar base de datos coredb
\c coredb
SELECT * FROM solicitudes;

# Verificar base de datos riskdb
\c riskdb
SELECT * FROM evaluaciones;

# Salir
\q
```

## 🐛 Debug y Logs

```bash
# Ver logs de todos los servicios
docker compose logs -f

# Ver logs de un servicio específico
docker compose logs -f auth-service
docker compose logs -f core-service
docker compose logs -f risk-service

# Ver logs de la base de datos
docker compose logs -f postgres
```

## 📈 Métricas de Testing

### Cobertura de Casos de Uso

- ✅ Registro de usuarios
- ✅ Autenticación de usuarios
- ✅ Creación de solicitudes de crédito
- ✅ Evaluación automática de riesgo
- ✅ Aprobación/Rechazo automático según nivel de riesgo
- ✅ Consulta de solicitudes
- ✅ Comunicación entre microservicios
- ✅ Manejo de errores
- ✅ Validaciones de negocio

### Niveles de Riesgo Testeados

- ✅ **BAJO** (score > 700): Aprobación automática
- ✅ **MEDIO** (500 < score ≤ 700): Requiere revisión manual
- ✅ **ALTO** (score ≤ 500): Rechazo automático

## 🎯 Checklist de Validación

- [ ] Todos los servicios inician correctamente
- [ ] PostgreSQL crea las 3 bases de datos
- [ ] Auth Service registra usuarios correctamente
- [ ] Auth Service autentica usuarios correctamente
- [ ] Core Service crea solicitudes correctamente
- [ ] Core Service consulta solicitudes correctamente
- [ ] Risk Service evalúa riesgo correctamente
- [ ] Core Service se comunica con Risk Service
- [ ] Aprobación automática funciona (riesgo BAJO)
- [ ] Rechazo automático funciona (riesgo ALTO)
- [ ] Manejo de errores funciona correctamente
- [ ] Logs muestran información relevante

---

**Guía de Testing Completa** ✅
