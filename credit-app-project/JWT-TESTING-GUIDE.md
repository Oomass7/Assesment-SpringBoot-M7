# 🔒 Guía de Pruebas JWT con Spring Security

## 📋 Descripción

Esta guía muestra cómo probar la autenticación JWT implementada en los 3 microservicios.

## 🎯 Endpoints Protegidos

### Auth Service (Puerto 8081)
- ✅ **POST** `/auth/register` - Público
- ✅ **POST** `/auth/login` - Público

### Core Service (Puerto 8082)
- 🔒 **POST** `/solicitudes` - Requiere JWT
- 🔒 **GET** `/solicitudes/{id}` - Requiere JWT

### Risk Service (Puerto 8083)
- 🔒 **POST** `/evaluaciones/evaluar` - Requiere JWT

---

## 🧪 Pruebas con cURL

### 1. Registrar un Usuario

```bash
curl -X POST http://localhost:8081/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "usuario@example.com",
    "password": "Password123!",
    "nombre": "Juan Pérez",
    "rol": "USER"
  }'
```

**Respuesta esperada:**
```json
{
  "token": null,
  "tipo": null,
  "mensaje": "Usuario registrado exitosamente con ID: 1"
}
```

### 2. Iniciar Sesión (Obtener JWT)

```bash
curl -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "usuario@example.com",
    "password": "Password123!"
  }'
```

**Respuesta esperada:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c3VhcmlvQGV4YW1wbGUuY29tIiwiaWF0IjoxNzAyMTY0MDAwLCJleHAiOjE3MDIyNTA0MDB9.xxxxxxxxxxx",
  "tipo": "Bearer",
  "mensaje": "Autenticación exitosa"
}
```

🔑 **Importante:** Guarda el token para usarlo en las siguientes peticiones.

### 3. Crear Solicitud de Crédito (CON JWT)

```bash
# Reemplaza <TU_TOKEN_JWT> con el token obtenido en el login
curl -X POST http://localhost:8082/solicitudes \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TU_TOKEN_JWT>" \
  -d '{
    "nombreCliente": "Juan Pérez",
    "monto": 50000.0,
    "plazoMeses": 36,
    "ingresoMensual": 15000.0
  }'
```

**Respuesta esperada (200 OK):**
```json
{
  "id": 1,
  "nombreCliente": "Juan Pérez",
  "monto": 50000.0,
  "plazoMeses": 36,
  "ingresoMensual": 15000.0,
  "estado": "PENDIENTE",
  "nivelRiesgo": "MEDIO",
  "aprobada": false
}
```

### 4. Intentar Acceder SIN JWT (Debe Fallar)

```bash
curl -X POST http://localhost:8082/solicitudes \
  -H "Content-Type: application/json" \
  -d '{
    "nombreCliente": "Juan Pérez",
    "monto": 50000.0,
    "plazoMeses": 36,
    "ingresoMensual": 15000.0
  }' \
  -w "\nHTTP Status: %{http_code}\n"
```

**Respuesta esperada (403 Forbidden):**
```
HTTP Status: 403
```

---

## 🧪 Pruebas con Postman

### Configuración

1. **Crear una Colección** llamada "Credit App JWT"

2. **Crear Variables de Colección:**
   - `baseUrl`: `http://localhost`
   - `authPort`: `8081`
   - `corePort`: `8082`
   - `riskPort`: `8083`
   - `token`: (Se llenará automáticamente)

### Requests

#### 1️⃣ Register User
- **Method:** POST
- **URL:** `{{baseUrl}}:{{authPort}}/auth/register`
- **Body (JSON):**
```json
{
  "email": "test@example.com",
  "password": "Test123!",
  "nombre": "Usuario Test",
  "rol": "USER"
}
```

#### 2️⃣ Login (Guarda el Token)
- **Method:** POST
- **URL:** `{{baseUrl}}:{{authPort}}/auth/login`
- **Body (JSON):**
```json
{
  "email": "test@example.com",
  "password": "Test123!"
}
```
- **Tests (Script):**
```javascript
pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Token is present", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData.token).to.exist;
    pm.collectionVariables.set("token", jsonData.token);
});
```

#### 3️⃣ Create Credit Request (Protected)
- **Method:** POST
- **URL:** `{{baseUrl}}:{{corePort}}/solicitudes`
- **Headers:**
  - `Authorization`: `Bearer {{token}}`
- **Body (JSON):**
```json
{
  "nombreCliente": "María García",
  "monto": 100000,
  "plazoMeses": 48,
  "ingresoMensual": 25000
}
```

#### 4️⃣ Test Without Token (Should Fail)
- **Method:** POST
- **URL:** `{{baseUrl}}:{{corePort}}/solicitudes`
- **Headers:** (Sin Authorization)
- **Body (JSON):** (mismo que arriba)
- **Expected:** 403 Forbidden

---

## 🔍 Verificar JWT

### Decodificar Token (Online)
1. Ve a [jwt.io](https://jwt.io/)
2. Pega tu token en la sección "Encoded"
3. Verifica el payload:
```json
{
  "sub": "usuario@example.com",
  "iat": 1702164000,
  "exp": 1702250400
}
```

### Información del Token
- **sub**: Email del usuario
- **iat**: Timestamp de emisión
- **exp**: Timestamp de expiración (24 horas después de iat)

---

## ⚙️ Configuración JWT

### application.properties (auth-service)
```properties
# JWT Configuration
jwt.secret=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
jwt.expiration=86400000  # 24 horas en milisegundos
```

### Cambiar Tiempo de Expiración
```properties
# 1 hora
jwt.expiration=3600000

# 7 días
jwt.expiration=604800000
```

---

## 🛡️ Características de Seguridad Implementadas

### ✅ Auth Service
- JWT con firma HMAC-SHA256
- BCrypt para encriptar contraseñas
- Spring Security configurado
- Endpoints públicos: `/auth/register`, `/auth/login`
- UserDetailsService personalizado

### ✅ Core Service
- Validación de JWT en cada petición
- Filtro JWT personalizado
- Todos los endpoints protegidos excepto `/actuator/**`

### ✅ Risk Service
- Validación de JWT en cada petición
- Filtro JWT personalizado
- Todos los endpoints protegidos excepto `/actuator/**`

---

## 📊 Flujo de Autenticación

```
┌─────────┐      1. Register/Login      ┌──────────────┐
│         │ ──────────────────────────> │              │
│ Cliente │                             │ Auth Service │
│         │ <────────────────────────── │              │
└─────────┘      2. JWT Token           └──────────────┘
     │
     │ 3. Request + JWT Token
     │
     ├──────────────────────────────────┐
     │                                  │
     v                                  v
┌──────────────┐                  ┌──────────────┐
│              │   4. Validate    │              │
│ Core Service │ ────JWT────────> │ Risk Service │
│              │                  │              │
└──────────────┘                  └──────────────┘
```

---

## 🔧 Troubleshooting

### Error: 403 Forbidden
**Causa:** No se envió el token o es inválido
**Solución:** Verifica el header `Authorization: Bearer <token>`

### Error: 401 Unauthorized
**Causa:** Token expirado o credenciales incorrectas
**Solución:** Realiza login nuevamente para obtener un nuevo token

### Error: Token Signature Invalid
**Causa:** El secreto JWT no coincide entre servicios
**Solución:** Asegúrate que `jwt.secret` sea igual en todos los `application.properties`

### Token No Decodifica
**Causa:** Token mal formado
**Solución:** Copia el token completo sin espacios extras

---

## 📝 Notas de Seguridad

### ⚠️ En Producción:
1. **Cambiar el JWT Secret**: Usa un secreto único y complejo
2. **HTTPS Obligatorio**: Nunca transmitir tokens por HTTP
3. **Rotar Secrets**: Cambiar el secret periódicamente
4. **Refresh Tokens**: Implementar tokens de refresco
5. **Rate Limiting**: Limitar intentos de login
6. **Token Blacklist**: Implementar invalidación de tokens
7. **Auditoría**: Registrar accesos y cambios

### 🔒 Mejoras Recomendadas:
- Implementar refresh tokens
- Agregar roles y permisos más granulares
- Implementar OAuth2/OpenID Connect
- Agregar MFA (Multi-Factor Authentication)
- Implementar CORS correctamente
- Agregar rate limiting

---

## 📚 Recursos Adicionales

- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [JWT.io](https://jwt.io/)
- [JJWT Library](https://github.com/jwtk/jjwt)
- [OWASP JWT Security](https://cheatsheetseries.owasp.org/cheatsheets/JSON_Web_Token_for_Java_Cheat_Sheet.html)

---

**✅ JWT y Spring Security implementados correctamente en todos los microservicios**
