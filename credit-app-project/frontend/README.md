# 💳 Frontend - Sistema de Gestión de Créditos

Interfaz web profesional desarrollada con **React + Vite** para consumir los microservicios del sistema de créditos.

## 🚀 Características

- ✨ **Interfaz Moderna**: Diseño limpio y profesional con componentes reutilizables
- 🎯 **SPA con React Router**: Navegación fluida sin recargas de página
- 📡 **Integración con Microservicios**: Consume APIs REST de los 3 microservicios
- 🎨 **Diseño Responsive**: Funciona perfectamente en desktop y móvil
- ⚡ **Vite**: Build rápido y desarrollo con HMR (Hot Module Replacement)
- 🐳 **Docker Ready**: Incluye Dockerfile y configuración de nginx

## 🏗️ Estructura del Proyecto

```
frontend/
├── src/
│   ├── pages/
│   │   ├── Home.jsx              # Dashboard con estadísticas
│   │   ├── AuthPage.jsx          # Login y registro de usuarios
│   │   ├── SolicitudesPage.jsx   # Gestión de solicitudes de crédito
│   │   └── RiskPage.jsx          # Evaluación de riesgo crediticio
│   ├── services/
│   │   └── api.js                # Cliente Axios para APIs
│   ├── App.jsx                   # Componente principal con rutas
│   ├── App.css                   # Estilos globales
│   └── main.jsx                  # Entry point
├── Dockerfile                    # Build multi-stage para producción
├── nginx.conf                    # Configuración de nginx
└── package.json                  # Dependencias del proyecto
```

## 📋 Páginas y Funcionalidades

### 1. 🏠 Home (Dashboard)
- Estadísticas en tiempo real del sistema
- Tarjetas informativas de los microservicios
- Información sobre arquitectura hexagonal

### 2. 🔐 Autenticación
- **Login**: Autenticación con email y contraseña
- **Registro**: Creación de nuevos usuarios (Cliente, Admin, Evaluador)
- Integración con **Auth Service** (puerto 8081)

### 3. 💳 Solicitudes de Crédito
- **Crear Solicitud**: Formulario para nueva solicitud de crédito
- **Listar Todas**: Tabla con todas las solicitudes
- **Buscar por Documento**: Filtro por documento del cliente
- **Evaluación Automática**: Score y nivel de riesgo calculado por Risk Service
- Integración con **Core Service** (puerto 8082)

### 4. ⚖️ Evaluación de Riesgo
- **Evaluador Manual**: Calcula score de riesgo para un documento
- **Visualización Detallada**: Muestra score, nivel de riesgo y recomendación
- **Información del Algoritmo**: Explica cómo se calcula el scoring
- Integración con **Risk Service** (puerto 8083)

## 📦 Instalación y Ejecución

### Desarrollo Local

```bash
# Instalar dependencias
npm install

# Ejecutar en modo desarrollo (puerto 5173)
npm run dev

# Build para producción
npm run build
```

### Con Docker Compose (recomendado)

```bash
# Desde la raíz del proyecto
docker compose up -d

# El frontend estará disponible en:
# http://localhost:3000
```

## 🌐 URLs de Acceso

- **Frontend**: http://localhost:3000
- **Auth Service**: http://localhost:8081
- **Core Service**: http://localhost:8082
- **Risk Service**: http://localhost:8083

## 🎨 Sistema de Diseño

### Paleta de Colores

```css
--primary-color: #2563eb;    /* Azul principal */
--success-color: #10b981;    /* Verde éxito */
--warning-color: #f59e0b;    /* Amarillo advertencia */
--danger-color: #ef4444;     /* Rojo peligro */
```

## 📊 Niveles de Riesgo

| Nivel | Rango Score | Acción |
|-------|-------------|--------|
| BAJO | 701-950 | Aprobación automática |
| MEDIO | 501-700 | Evaluación manual |
| ALTO | 300-500 | Rechazo automático |

---

Desarrollado con ❤️ usando React + Vite
