# 🧪 Cómo Ejecutar Pruebas Unitarias

## ✅ ¡Tests Implementados y Funcionando!

Se han creado **6 pruebas unitarias** que están **PASANDO** exitosamente:
- ✅ 2 tests en `auth-service`
- ✅ 2 tests en `core-service`
- ✅ 2 tests en `risk-service`

---

## 📝 Resumen de Tests Creados

### Auth Service (2 tests)
- ✅ `debeCrearUsuarioValido` - Valida creación de usuario
- ✅ `debeActualizarEmail` - Valida actualización de email

### Core Service (2 tests)
- ✅ `debeCrearSolicitudValida` - Valida creación de solicitud de crédito
- ✅ `debeAprobarSolicitud` - Valida aprobación de solicitud

### Risk Service (2 tests)
- ✅ `debeCrearEvaluacionConRiesgoBajo` - Valida evaluación con riesgo BAJO
- ✅ `debeCrearEvaluacionConRiesgoAlto` - Valida evaluación con riesgo ALTO

---

## 🚀 Comandos para Ejecutar Tests

### **1. Ejecutar tests de UN solo servicio**
```bash
# Auth Service
cd auth-service
mvn test

# Core Service  
cd core-service
mvn test

# Risk Service
cd risk-service
mvn test
```

### **2. Ejecutar TODOS los tests del proyecto**
```bash
# Desde la raíz del proyecto
mvn test
```

### **3. Ejecutar tests con información detallada**
```bash
mvn test -X  # Debug mode
mvn test --debug  # Más detalles
```

### **4. Ejecutar solo una clase de test específica**
```bash
mvn test -Dtest=UsuarioTest
mvn test -Dtest=SolicitudCreditoTest
mvn test -Dtest=EvaluacionTest
```

### **5. Ejecutar un método específico**
```bash
mvn test -Dtest=UsuarioTest#debeCrearUsuarioValido
mvn test -Dtest=SolicitudCreditoTest#debeAprobarSolicitud
```

### **6. Saltar tests (cuando quieres compilar sin ejecutar tests)**
```bash
mvn clean package -DskipTests
```

### **7. Ejecutar tests en modo "watch" (re-ejecutar al cambiar código)**
```bash
# Instala primero el plugin
mvn io.kokuwa.maven:k3s-maven-plugin:watch

# O usa este wrapper script:
while true; do
    mvn test
    sleep 2
done
```

---

## 📊 Output de Tests Exitosos

Cuando ejecutas `mvn test`, verás algo como esto:

```
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running com.example.authservice.domain.model.UsuarioTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.03 s

Results:
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0

-------------------------------------------------------
BUILD SUCCESS
-------------------------------------------------------
```

---

## 🔍 Ver Reportes Detallados

Maven genera reportes automáticamente:

```bash
# Los reportes están en:
auth-service/target/surefire-reports/
core-service/target/surefire-reports/
risk-service/target/surefire-reports/

# Ver reporte TXT
cat auth-service/target/surefire-reports/com.example.authservice.domain.model.UsuarioTest.txt

# Ver reporte XML
cat auth-service/target/surefire-reports/TEST-com.example.authservice.domain.model.UsuarioTest.xml
```

---

## 📈 Generar Reportes HTML con Surefire

Agrega al `pom.xml`:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-report-plugin</artifactId>
    <version>2.22.2</version>
</plugin>
```

Luego ejecuta:
```bash
mvn surefire-report:report
# Reporte en: target/site/surefire-report.html
```

---

## 📊 Generar Reporte de Cobertura con JaCoCo

### 1. Agregar plugin al `pom.xml`:
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

### 2. Ejecutar tests con cobertura:
```bash
mvn clean test jacoco:report
```

### 3. Ver reporte HTML:
```bash
# El reporte estará en:
firefox target/site/jacoco/index.html
```

---

## 🐛 Troubleshooting

### Error: "No tests were found"
```bash
# Solución 1: Limpia y recompila
mvn clean compile test-compile test

# Solución 2: Verifica que el plugin surefire esté configurado
mvn help:effective-pom | grep surefire
```

### Tests no se ejecutan en IntelliJ/Eclipse
```bash
# Actualiza el proyecto Maven
mvn clean install
# Luego recarga el proyecto en el IDE
```

### Error: "Cannot find symbol Test"
```bash
# Verifica que las dependencias de test estén en el pom.xml
mvn dependency:tree | grep junit
```

---

## 🎯 Buenas Prácticas

### 1. **Ejecuta tests ANTES de hacer commit**
```bash
git add .
mvn test && git commit -m "feat: add new feature"
```

### 2. **Ejecuta tests en modo continuo durante desarrollo**
```bash
# Terminal 1: Deja corriendo los tests
watch -n 2 mvn test

# Terminal 2: Edita tu código
```

### 3. **Ejecuta tests con perfiles diferentes**
```bash
# Test con base de datos H2
mvn test -Dspring.profiles.active=test

# Test con PostgreSQL local
mvn test -Dspring.profiles.active=dev
```

---

## 📋 Checklist de Testing

Antes de hacer push a Git:

- [ ] ✅ Ejecutar `mvn clean test` en cada servicio
- [ ] ✅ Verificar que NO haya failures
- [ ] ✅ Revisar coverage (debe ser >70%)
- [ ] ✅ Ejecutar `mvn clean package` para verificar build completo

---

## 🔥 Comando Rápido (TODO EN UNO)

```bash
# Limpia, compila, ejecuta tests y empaqueta - TODO EN UNO
cd /home/javasprinboot/Documentos/Assesment-SpringBoot-M7/credit-app-project && \
for service in auth-service core-service risk-service; do
    echo "🧪 Testing $service..."
    cd $service && mvn clean test && cd ..
done
```

---

## 📺 Ver Tests en Modo Verbose

```bash
# Ver cada test mientras se ejecuta
mvn test -Dsurefire.printSummary=true -Dsurefire.useFile=false
```

---

## 🎓 Ejemplo de Salida Exitosa

```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.example.authservice.domain.model.UsuarioTest
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.03 s
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS ✅
[INFO] ------------------------------------------------------------------------
```

---

## 🎉 ¡Listo para Usar!

Ahora puedes:

1. ✅ **Ejecutar tests**: `mvn test`
2. ✅ **Agregar más tests**: Copia la estructura de los tests existentes
3. ✅ **Ver reportes**: `target/surefire-reports/`
4. ✅ **Integrar en CI/CD**: Los tests se ejecutan automáticamente

---

## 📞 Comandos Útiles Adicionales

```bash
# Ver solo tests que fallaron
mvn test | grep -A 5 "FAILURE"

# Ejecutar tests en paralelo (más rápido)
mvn test -T 4  # usa 4 threads

# Ejecutar tests sin recompilar
mvn surefire:test

# Ver tiempo de ejecución de cada test
mvn test -DtrimStackTrace=false
```

---

**¡Tests configurados y funcionando! 🚀**
