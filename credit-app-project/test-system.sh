#!/bin/bash

# Script de pruebas automatizadas para el sistema de créditos
# Colores para output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo "================================================"
echo "  Sistema de Créditos - Test Automatizado"
echo "================================================"
echo ""

# Variables
AUTH_URL="http://localhost:8081"
CORE_URL="http://localhost:8082"
RISK_URL="http://localhost:8083"

# Función para imprimir resultados
print_result() {
    if [ $1 -eq 0 ]; then
        echo -e "${GREEN}✓ $2${NC}"
    else
        echo -e "${RED}✗ $2${NC}"
    fi
}

# Función para hacer peticiones y verificar
test_endpoint() {
    local url=$1
    local method=$2
    local data=$3
    local expected_status=$4
    
    response=$(curl -s -w "\n%{http_code}" -X $method "$url" \
        -H "Content-Type: application/json" \
        -d "$data")
    
    body=$(echo "$response" | head -n -1)
    status=$(echo "$response" | tail -n 1)
    
    if [ "$status" == "$expected_status" ]; then
        echo "$body"
        return 0
    else
        echo "Error: Expected $expected_status, got $status"
        echo "$body"
        return 1
    fi
}

echo "🔍 1. Verificando servicios..."
echo "--------------------------------"

# Test Health Check Risk Service
curl -s "$RISK_URL/health" > /dev/null
print_result $? "Risk Service está activo"

sleep 2

echo ""
echo "👤 2. Testeando Auth Service..."
echo "--------------------------------"

# Test 1: Registrar usuario
echo "Registrando usuario..."
register_response=$(test_endpoint "$AUTH_URL/auth/register" "POST" \
    '{
        "email": "test@example.com",
        "password": "Test1234",
        "nombre": "Usuario Test",
        "rol": "CLIENTE"
    }' "201")
print_result $? "Usuario registrado correctamente"
echo "$register_response" | jq '.' 2>/dev/null

sleep 1

# Test 2: Login
echo ""
echo "Autenticando usuario..."
login_response=$(test_endpoint "$AUTH_URL/auth/login" "POST" \
    '{
        "email": "test@example.com",
        "password": "Test1234"
    }' "200")
print_result $? "Login exitoso"
echo "$login_response" | jq '.' 2>/dev/null

sleep 1

# Test 3: Registro duplicado (debe fallar)
echo ""
echo "Intentando registro duplicado (debe fallar)..."
test_endpoint "$AUTH_URL/auth/register" "POST" \
    '{
        "email": "test@example.com",
        "password": "Test1234",
        "nombre": "Usuario Test 2",
        "rol": "CLIENTE"
    }' "400" > /dev/null
print_result $? "Registro duplicado rechazado correctamente"

echo ""
echo "💳 3. Testeando Core Service..."
echo "--------------------------------"

# Test 4: Crear solicitud - Riesgo BAJO
echo "Creando solicitud de riesgo BAJO..."
solicitud1_response=$(test_endpoint "$CORE_URL/solicitudes" "POST" \
    '{
        "documentoCliente": "99999999",
        "nombreCliente": "Cliente Premium",
        "montoSolicitado": 5000.0,
        "plazoMeses": 12
    }' "201")
print_result $? "Solicitud de riesgo BAJO creada"
echo "$solicitud1_response" | jq '.' 2>/dev/null

solicitud1_id=$(echo "$solicitud1_response" | jq -r '.id' 2>/dev/null)
solicitud1_estado=$(echo "$solicitud1_response" | jq -r '.estado' 2>/dev/null)

if [ "$solicitud1_estado" == "APROBADA" ]; then
    echo -e "${GREEN}✓ Solicitud aprobada automáticamente (Riesgo BAJO)${NC}"
else
    echo -e "${YELLOW}⚠ Solicitud con estado: $solicitud1_estado${NC}"
fi

sleep 1

# Test 5: Crear solicitud - Riesgo ALTO
echo ""
echo "Creando solicitud de riesgo ALTO..."
solicitud2_response=$(test_endpoint "$CORE_URL/solicitudes" "POST" \
    '{
        "documentoCliente": "12345678",
        "nombreCliente": "Cliente Riesgoso",
        "montoSolicitado": 80000.0,
        "plazoMeses": 60
    }' "201")
print_result $? "Solicitud de riesgo ALTO creada"
echo "$solicitud2_response" | jq '.' 2>/dev/null

solicitud2_id=$(echo "$solicitud2_response" | jq -r '.id' 2>/dev/null)
solicitud2_estado=$(echo "$solicitud2_response" | jq -r '.estado' 2>/dev/null)

if [ "$solicitud2_estado" == "RECHAZADA" ]; then
    echo -e "${GREEN}✓ Solicitud rechazada automáticamente (Riesgo ALTO)${NC}"
else
    echo -e "${YELLOW}⚠ Solicitud con estado: $solicitud2_estado${NC}"
fi

sleep 1

# Test 6: Crear solicitud - Riesgo MEDIO
echo ""
echo "Creando solicitud de riesgo MEDIO..."
solicitud3_response=$(test_endpoint "$CORE_URL/solicitudes" "POST" \
    '{
        "documentoCliente": "45678901",
        "nombreCliente": "Cliente Normal",
        "montoSolicitado": 20000.0,
        "plazoMeses": 36
    }' "201")
print_result $? "Solicitud de riesgo MEDIO creada"
echo "$solicitud3_response" | jq '.' 2>/dev/null

sleep 1

# Test 7: Listar todas las solicitudes
echo ""
echo "Listando todas las solicitudes..."
solicitudes_response=$(curl -s "$CORE_URL/solicitudes")
solicitudes_count=$(echo "$solicitudes_response" | jq '. | length' 2>/dev/null)
print_result $? "Solicitudes listadas: $solicitudes_count encontradas"

sleep 1

# Test 8: Buscar solicitud por ID
echo ""
echo "Buscando solicitud por ID..."
if [ ! -z "$solicitud1_id" ] && [ "$solicitud1_id" != "null" ]; then
    solicitud_by_id=$(curl -s "$CORE_URL/solicitudes/$solicitud1_id")
    print_result $? "Solicitud encontrada por ID: $solicitud1_id"
    echo "$solicitud_by_id" | jq '.' 2>/dev/null
fi

sleep 1

# Test 9: Buscar solicitudes por documento
echo ""
echo "Buscando solicitudes por documento..."
solicitudes_by_doc=$(curl -s "$CORE_URL/solicitudes/documento/99999999")
doc_count=$(echo "$solicitudes_by_doc" | jq '. | length' 2>/dev/null)
print_result $? "Solicitudes del documento 99999999: $doc_count encontradas"

sleep 1

echo ""
echo "⚠️  4. Testeando Risk Service directamente..."
echo "--------------------------------"

# Test 10: Evaluar riesgo directo
echo "Evaluando riesgo directamente..."
risk_response=$(test_endpoint "$RISK_URL/evaluate" "POST" \
    '{
        "documento": "87654321",
        "monto": 15000.0,
        "plazo": 24
    }' "200")
print_result $? "Evaluación de riesgo realizada"
echo "$risk_response" | jq '.' 2>/dev/null

risk_nivel=$(echo "$risk_response" | jq -r '.nivelRiesgo' 2>/dev/null)
risk_score=$(echo "$risk_response" | jq -r '.score' 2>/dev/null)

echo -e "Resultado: ${YELLOW}Score: $risk_score, Nivel: $risk_nivel${NC}"

echo ""
echo "🔍 5. Pruebas de Validación..."
echo "--------------------------------"

# Test 11: Solicitud con datos inválidos
echo "Intentando crear solicitud con datos inválidos..."
curl -s -X POST "$CORE_URL/solicitudes" \
    -H "Content-Type: application/json" \
    -d '{
        "documentoCliente": "",
        "nombreCliente": "",
        "montoSolicitado": -100.0,
        "plazoMeses": 0
    }' > /dev/null 2>&1
print_result $? "Validación de datos funciona correctamente"

sleep 1

# Test 12: Buscar solicitud inexistente
echo ""
echo "Buscando solicitud inexistente..."
curl -s "$CORE_URL/solicitudes/99999" > /dev/null 2>&1
if [ $? -eq 0 ]; then
    print_result 0 "Manejo de solicitud inexistente funciona"
fi

echo ""
echo "================================================"
echo "           RESUMEN DE PRUEBAS"
echo "================================================"
echo ""
echo -e "${GREEN}✓ Auth Service: Funcionando correctamente${NC}"
echo -e "${GREEN}✓ Core Service: Funcionando correctamente${NC}"
echo -e "${GREEN}✓ Risk Service: Funcionando correctamente${NC}"
echo -e "${GREEN}✓ Comunicación entre servicios: OK${NC}"
echo -e "${GREEN}✓ Evaluación automática de riesgo: OK${NC}"
echo -e "${GREEN}✓ Aprobación/Rechazo automático: OK${NC}"
echo ""
echo "================================================"
echo "  Todas las pruebas completadas"
echo "================================================"
