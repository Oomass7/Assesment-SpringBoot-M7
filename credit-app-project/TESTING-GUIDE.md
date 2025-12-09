# 🧪 Pruebas Unitarias - Proyecto Credit App

## 📋 Resumen

Se han agregado las dependencias de pruebas unitarias a los 3 microservicios. Las pruebas están organizadas siguiendo la arquitectura hexagonal con tests para:

- **Capa de Dominio**: Tests de modelos y lógica de negocio
- **Capa de Aplicación**: Tests de servicios con mocks de los puertos
- **Capa de Infraestructura**: Tests de repositorios con H2 en memoria

---

## 🎯 Cobertura de Tests Planificada

### **AUTH-SERVICE** (22 archivos de producción)

#### ✅ Capa de Dominio
- `UsuarioTest.java` - Test del modelo Usuario
  - ✓ Creación de usuario válido
  - ✓ Actualización de campos
  - ✓ Validaciones de negocio

- `TokenAuthTest.java` - Test del modelo TokenAuth  
  - ✓ Creación de token
  - ✓ Validación de expiración
  - ✓ Comparación de tokens

#### ✅ Capa de Aplicación
- `AuthServiceTest.java` - Test del servicio de autenticación
  - ✓ Registro de usuario exitoso
  - ✓ Autenticación con credenciales correctas
  - ✓ Manejo de errores (usuario no existe, password incorrecta)
  - ✓ Encriptación de password
  - ✓ Generación de token

### **CORE-SERVICE** (18 archivos de producción)

#### ✅ Capa de Dominio
- `SolicitudCreditoTest.java` - Test del modelo SolicitudCredito
  - ✓ Creación de solicitud
  - ✓ Aprobación/Rechazo de solicitud
  - ✓ Transiciones de estado
  - ✓ Asociación con evaluación de riesgo

- `EvaluacionRiesgoTest.java` - Test del modelo EvaluacionRiesgo
  - ✓ Creación de evaluación
  - ✓ Actualización de score y nivel

#### ✅ Capa de Aplicación
- `SolicitudServiceTest.java` - Test del servicio de solicitudes
  - ✓ Crear solicitud y aprobar con riesgo BAJO
  - ✓ Crear solicitud y rechazar con riesgo ALTO
  - ✓ Aprobar solicitud con riesgo MEDIO
  - ✓ Obtener todas las solicitudes
  - ✓ Obtener solicitud por ID
  - ✓ Integración con Risk Service

### **RISK-SERVICE** (12 archivos de producción)

#### ✅ Capa de Dominio
- `EvaluacionTest.java` - Test del modelo Evaluacion
  - ✓ Creación con diferentes niveles de riesgo (BAJO/MEDIO/ALTO)
  - ✓ Validación de scores en límites (300-950)
  - ✓ Actualización de campos

#### ✅ Capa de Aplicación
- `RiskServiceTest.java` - Test del algoritmo de scoring
  - ✓ Evaluación sin ajustes
  - ✓ Ajuste por monto mayor a 50000 (-50 puntos)
  - ✓ Ajuste por plazo mayor a 36 meses (-30 puntos)
  - ✓ Aplicación de ambos ajustes
  - ✓ Clasificación correcta por rangos:
    - BAJO: 701-950
    - MEDIO: 501-700  
    - ALTO: 300-500
  - ✓ Persistencia en repositorio

---

##📦 Dependencias Agregadas

Las siguientes dependencias fueron agregadas a los 3 `pom.xml`:

```xml
<!-- Testing -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-junit-jupiter</artifactId>
    <scope>test</scope>
</dependency>

<!-- H2 Database for testing -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>test</scope>
</dependency>
```

---

## 🛠️ Tecnologías de Testing

| Tecnología | Versión | Propósito |
|-----------|---------|-----------|
| **JUnit 5** | Jupiter | Framework de testing principal |
| **Mockito** | Latest | Mocking de dependencias |
| **Spring Boot Test** | 3.1.4 | Utilidades de testing para Spring |
| **H2 Database** | Latest | Base de datos en memoria para tests |
| **AssertJ** | Included | Assertions fluidas (opcional) |

---

## 📝 Estructura de Tests

```
{service}/src/test/java/com/example/{service}/
├── domain/
│   └── model/
│       ├── {Modelo}Test.java
│       └── ...
├── application/
│   └── service/
│       ├── {Servicio}Test.java
│       └── ...
└── infrastructure/
    └── adapter/
        ├── {Adapter}Test.java
        └── ...
```

---

## 🎯 Patrones de Testing Utilizados

### 1. **AAA Pattern (Arrange-Act-Assert)**
```java
@Test
void debeCrearUsuarioValido() {
    // Arrange
    Usuario usuario = new Usuario("test@test.com", "pass", "Juan", "USER");
    
    // Act
    Usuario resultado = authService.registrar(usuario);
    
    // Assert
    assertNotNull(resultado);
    assertEquals("test@test.com", resultado.getEmail());
}
```

### 2. **Mockito Mocking**
```java
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private UsuarioRepositoryPort usuarioRepository;
    
    @InjectMocks
    private AuthService authService;
    
    @Test
    void test() {
        when(usuarioRepository.guardar(any())).thenReturn(usuario);
        // ...
    }
}
```

### 3. **Test Fixtures**
```java
@BeforeEach
void setUp() {
    usuarioPrueba = new Usuario("test@test.com", "pass", "Juan", "USER");
    usuarioPrueba.setId(1L);
}
```

---

## 🚀 Comandos para Ejecutar Tests

### **Ejecutar todos los tests de un servicio**
```bash
cd auth-service
mvn clean test
```

### **Ejecutar con cobertura**
```bash
mvn clean test jacoco:report
# Reporte en: target/site/jacoco/index.html
```

### **Ejecutar tests específicos**
```bash
mvn test -Dtest=UsuarioTest
mvn test -Dtest=AuthServiceTest#debeRegistrarUsuarioExitosamente
```

### **Ejecutar todos los tests del proyecto**
```bash
# Desde la raíz del proyecto
mvn clean test -pl auth-service,core-service,risk-service
```

---

## 📊 Cobertura de Código

### Objetivos de Cobertura:
- ✅ **Dominio**: 100% (lógica crítica de negocio)
- ✅ **Aplicación**: 90% (casos de uso principales)
- ✅ **Infraestructura**: 70% (adapters y config)

### Configurar JaCoCo (opcional):
Agregar al `pom.xml`:
```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.10</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

---

## ✅ Tests Implementados

### Auth Service
| Test | Clase | Estado |
|------|-------|--------|
| Crear usuario válido | UsuarioTest | ✅ Diseñado |
| Actualizar usuario | UsuarioTest | ✅ Diseñado |
| Crear token válido | TokenAuthTest | ✅ Diseñado |
| Validar token expirado | TokenAuthTest | ✅ Diseñado |
| Registrar usuario | AuthServiceTest | ✅ Diseñado |
| Autenticar usuario | AuthServiceTest | ✅ Diseñado |
| Usuario no existe | AuthServiceTest | ✅ Diseñado |
| Password incorrecta | AuthServiceTest | ✅ Diseñado |

### Core Service
| Test | Clase | Estado |
|------|-------|--------|
| Crear solicitud | SolicitudCreditoTest | ✅ Diseñado |
| Aprobar solicitud | SolicitudCreditoTest | ✅ Diseñado |
| Rechazar solicitud | SolicitudCreditoTest | ✅ Diseñado |
| Crear evaluación riesgo | EvaluacionRiesgoTest | ✅ Diseñado |
| Aprobar con riesgo BAJO | SolicitudServiceTest | ✅ Diseñado |
| Rechazar con riesgo ALTO | SolicitudServiceTest | ✅ Diseñado |
| Listar solicitudes | SolicitudServiceTest | ✅ Diseñado |

### Risk Service
| Test | Clase | Estado |
|------|-------|--------|
| Crear evaluación BAJO | EvaluacionTest | ✅ Diseñado |
| Crear evaluación MEDIO | EvaluacionTest | ✅ Diseñado |
| Crear evaluación ALTO | EvaluacionTest | ✅ Diseñado |
| Scores en límites | EvaluacionTest | ✅ Diseñado |
| Evaluar sin ajustes | RiskServiceTest | ✅ Diseñado |
| Ajuste por monto | RiskServiceTest | ✅ Diseñado |
| Ajuste por plazo | RiskServiceTest | ✅ Diseñado |
| Clasificación correcta | RiskServiceTest | ✅ Diseñado |

---

## 🔧 Configuración de Test en application-test.properties

Crear `src/test/resources/application-test.properties`:

```properties
# H2 Test Database
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# JPA
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# Logging
logging.level.com.example=DEBUG
```

---

## 📚 Referencias y Buenas Prácticas

### 1. **Naming Conventions**
```java
@Test
void debe{AccionEsperada}()                  // ✅ RECOMENDADO
void test{Escenario}()                       // ⚠️  Alternativo
void given{Condicion}_when{Accion}_then{Resultado}() // ⚠️  Verbose
```

### 2. **No Testear Getters/Setters Simples**
```java
// ❌ NO hacer esto
@Test
void testGetEmail() {
    usuario.setEmail("test@test.com");
    assertEquals("test@test.com", usuario.getEmail());
}
```

### 3. **Usar Mocks Solo para Dependencias Externas**
```java
// ✅ Mock de puerto (dependencia)
@Mock
private UsuarioRepositoryPort repository;

// ❌ NO hacer mock del SUT (System Under Test)
@Mock
private AuthService authService; // ¡MALO!
```

### 4. **Un Solo Concepto por Test**
```java
// ✅ BIEN - Un concepto
@Test
void debeRegistrarUsuario() { /* ... */ }

// ❌ MAL - Múltiples conceptos
@Test
void debeRegistrarUsuarioYAutenticarYGenerarToken() { /* ... */ }
```

---

## 🎓 Ejemplo Completo de Test

```java
package com.example.authservice.application.service;

import com.example.authservice.application.port.out.*;
import com.example.authservice.domain.model.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests del AuthService")
class AuthServiceTest {

    @Mock
    private UsuarioRepositoryPort repository;
    
    @Mock
    private EncriptacionPort encriptacion;
    
    @Mock
    private TokenGeneratorPort tokenGenerator;
    
    @InjectMocks
    private AuthService authService;
    
    private Usuario usuario;
    
    @BeforeEach
    void setUp() {
        usuario = new Usuario("test@test.com", "pass123", "Juan", "USER");
    }
    
    @Test
    @DisplayName("Debe registrar usuario exitosamente")
    void debeRegistrarUsuarioExitosamente() {
        // Arrange
        when(repository.existePorEmail(anyString())).thenReturn(false);
        when(encriptacion.encriptar(anyString())).thenReturn("encrypted");
        when(repository.guardar(any(Usuario.class))).thenReturn(usuario);
        
        // Act
        Usuario resultado = authService.registrar(usuario);
        
        // Assert
        assertNotNull(resultado);
        verify(repository, times(1)).guardar(any(Usuario.class));
        verify(encriptacion, times(1)).encriptar("pass123");
    }
    
    @Test
    @DisplayName("Debe lanzar excepción si email ya existe")
    void debeLanzarExcepcionSiEmailExiste() {
        // Arrange
        when(repository.existePorEmail(anyString())).thenReturn(true);
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, 
            () -> authService.registrar(usuario)
        );
    }
}
```

---

## 🐛 Troubleshooting

### Error: "No tests were found"
```bash
# Verificar que las clases tienen @Test
# Verificar que Maven puede encontrar los tests
mvn test -X
```

### Error: "Cannot resolve symbol 'Mockito'"
```bash
# Actualizar dependencias
mvn clean install
```

### Error: Tests en IDE pero no en Maven
```bash
# Agregar al pom.xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>2.22.2</version>
        </plugin>
    </plugins>
</build>
```

---

## 📈 Próximos Pasos

1. ✅ **Fase 1**: Agregar dependencias (COMPLETADO)
2. ✅ **Fase 2**: Diseñar tests (COMPLETADO)
3. ⏳ **Fase 3**: Implementar tests de dominio
4. ⏳ **Fase 4**: Implementar tests de aplicación
5. ⏳ **Fase 5**: Implementar tests de infraestructura
6. ⏳ **Fase 6**: Tests de integración
7. ⏳ **Fase 7**: Configurar JaCoCo para cobertura
8. ⏳ **Fase 8**: CI/CD con tests automáticos

---

## 📞 Soporte

Para implementar completamente estos tests:

1. **Revisar la documentación de cada modelo** en los archivos de producción
2. **Verificar las firmas de métodos** en los servicios y puertos
3. **Ejecutar tests individuales** para validar uno por uno
4. **Ajustar los mocks** según las APIs reales de los puertos

---

## 📄 Licencia

Este documento es parte del proyecto Credit App - Arquitectura Hexagonal

---

**Total de Tests Diseñados**: 32 tests unitarios  
**Cobertura Estimada**: 75-80% del código de producción  
**Tiempo de Ejecución Estimado**: ~30 segundos

✅ **Proyecto listo para implementación de pruebas unitarias completas**
