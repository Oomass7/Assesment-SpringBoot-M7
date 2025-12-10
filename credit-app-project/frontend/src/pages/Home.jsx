import { useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import Layout from '../components/Layout';
import { solicitudAPI } from '../services/api';

function Home() {
  const navigate = useNavigate();
  const [user, setUser] = useState({});
  const [stats, setStats] = useState({
    total: 0,
    aprobadas: 0,
    rechazadas: 0,
    pendientes: 0
  });
  const [loading, setLoading] = useState(true);

  const token = localStorage.getItem('token');

  useEffect(() => {
    if (!token) {
      navigate('/auth');
      return;
    }

    const userData = JSON.parse(localStorage.getItem('user') || '{}');
    setUser(userData);
  }, [token, navigate]);

  useEffect(() => {
    loadStats();
  }, []);

  const loadStats = async () => {
    try {
      const solicitudes = await solicitudAPI.listarTodas();
      setStats({
        total: solicitudes.length,
        aprobadas: solicitudes.filter(s => s.estado === 'APROBADA').length,
        rechazadas: solicitudes.filter(s => s.estado === 'RECHAZADA').length,
        pendientes: solicitudes.filter(s => s.estado === 'PENDIENTE').length
      });
    } catch (error) {
      console.error('Error al cargar estadísticas:', error);
    } finally {
      setLoading(false);
    }
  };

  const features = [
    {
      icon: '📝',
      title: 'Solicitar Crédito',
      description: 'Crea una nueva solicitud de crédito de manera rápida y sencilla',
      action: () => navigate('/solicitudes'),
      color: 'bg-blue-500',
    },
    {
      icon: '📊',
      title: 'Evaluación de Riesgo',
      description: 'Evalúa el nivel de riesgo de las solicitudes de crédito',
      action: () => navigate('/risk'),
      color: 'bg-purple-500',
    },
    {
      icon: '📋',
      title: 'Mis Solicitudes',
      description: 'Consulta y gestiona todas tus solicitudes de crédito',
      action: () => navigate('/solicitudes'),
      color: 'bg-green-500',
    },
  ];

  return (
    <Layout>
      {/* Hero Section */}
      <div className="bg-gradient-to-r from-primary-600 to-primary-800 rounded-2xl shadow-xl p-8 md:p-12 mb-8 text-white">
        <div className="max-w-3xl">
          <h1 className="text-3xl md:text-4xl font-bold mb-4">
            Bienvenido, {user.name || 'Usuario'} 👋
          </h1>
          <p className="text-lg md:text-xl opacity-90 mb-6">
            Sistema de Gestión de Créditos - Panel de Control
          </p>
          <div className="flex flex-wrap gap-3">
            <span className="badge bg-white/20 text-white border border-white/30">
              Rol: {user.role || 'AFILIADO'}
            </span>
            <span className="badge bg-white/20 text-white border border-white/30">
              Email: {user.email || 'usuario@ejemplo.com'}
            </span>
          </div>
        </div>
      </div>

      {/* Stats Section */}
      <h2 style={{ marginBottom: '1.5rem', color: 'var(--primary-dark)', fontSize: '1.5rem', fontWeight: 'bold' }}>
        📊 Estadísticas del Sistema
      </h2>

      {loading ? (
        <div className="loading" style={{ textAlign: 'center', padding: '2rem' }}>
          <div className="spinner"></div>
          <p>Cargando estadísticas...</p>
        </div>
      ) : (
        <div className="stats-grid" style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))', gap: '1rem', marginBottom: '2rem' }}>
          <div className="stat-card" style={{ background: 'white', padding: '1.5rem', borderRadius: '12px', boxShadow: '0 2px 8px rgba(0,0,0,0.1)', borderLeft: '4px solid var(--primary-color)' }}>
            <div className="stat-value" style={{ fontSize: '2rem', fontWeight: 'bold', color: 'var(--primary-color)' }}>{stats.total}</div>
            <div className="stat-label" style={{ color: '#6b7280' }}>Total Solicitudes</div>
          </div>

          <div className="stat-card" style={{ background: 'white', padding: '1.5rem', borderRadius: '12px', boxShadow: '0 2px 8px rgba(0,0,0,0.1)', borderLeft: '4px solid var(--success-color)' }}>
            <div className="stat-value" style={{ fontSize: '2rem', fontWeight: 'bold', color: 'var(--success-color)' }}>{stats.aprobadas}</div>
            <div className="stat-label" style={{ color: '#6b7280' }}>Aprobadas</div>
          </div>

          <div className="stat-card" style={{ background: 'white', padding: '1.5rem', borderRadius: '12px', boxShadow: '0 2px 8px rgba(0,0,0,0.1)', borderLeft: '4px solid var(--danger-color)' }}>
            <div className="stat-value" style={{ fontSize: '2rem', fontWeight: 'bold', color: 'var(--danger-color)' }}>{stats.rechazadas}</div>
            <div className="stat-label" style={{ color: '#6b7280' }}>Rechazadas</div>
          </div>

          <div className="stat-card" style={{ background: 'white', padding: '1.5rem', borderRadius: '12px', boxShadow: '0 2px 8px rgba(0,0,0,0.1)', borderLeft: '4px solid var(--warning-color)' }}>
            <div className="stat-value" style={{ fontSize: '2rem', fontWeight: 'bold', color: 'var(--warning-color)' }}>{stats.pendientes}</div>
            <div className="stat-label" style={{ color: '#6b7280' }}>Pendientes</div>
          </div>
        </div>
      )}

      {/* Features Grid */}
      <div className="mb-8">
        <h2 style={{ fontSize: '1.5rem', fontWeight: 'bold', color: '#1f2937', marginBottom: '1.5rem' }}>
          Accesos Rápidos
        </h2>
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(280px, 1fr))', gap: '1.5rem' }}>
          {features.map((feature, index) => (
            <div
              key={index}
              onClick={feature.action}
              className="card"
              style={{ cursor: 'pointer', transition: 'transform 0.3s', padding: '1.5rem' }}
              onMouseEnter={(e) => e.currentTarget.style.transform = 'scale(1.02)'}
              onMouseLeave={(e) => e.currentTarget.style.transform = 'scale(1)'}
            >
              <div style={{
                width: '64px',
                height: '64px',
                borderRadius: '12px',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                fontSize: '2rem',
                marginBottom: '1rem',
                background: index === 0 ? '#3b82f6' : index === 1 ? '#8b5cf6' : '#22c55e'
              }}>
                {feature.icon}
              </div>
              <h3 style={{ fontSize: '1.25rem', fontWeight: '600', color: '#1f2937', marginBottom: '0.5rem' }}>
                {feature.title}
              </h3>
              <p style={{ color: '#6b7280', marginBottom: '1rem' }}>
                {feature.description}
              </p>
              <button className="btn-primary" style={{ width: '100%' }}>
                Acceder →
              </button>
            </div>
          ))}
        </div>
      </div>

      {/* Services Info */}
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(280px, 1fr))', gap: '1.5rem', marginBottom: '2rem' }}>
        <div className="card" style={{ padding: '1.5rem' }}>
          <h3 style={{ color: 'var(--primary-dark)', marginBottom: '1rem' }}>🔐 Auth Service</h3>
          <p style={{ marginBottom: '1rem', color: '#6b7280' }}>
            Gestión de usuarios y autenticación con tokens seguros.
          </p>
          <p style={{ fontSize: '0.875rem', color: '#9ca3af' }}>Puerto: 8081</p>
        </div>

        <div className="card" style={{ padding: '1.5rem' }}>
          <h3 style={{ color: 'var(--primary-dark)', marginBottom: '1rem' }}>💳 Core Service</h3>
          <p style={{ marginBottom: '1rem', color: '#6b7280' }}>
            Gestión completa de solicitudes de crédito con evaluación automática.
          </p>
          <p style={{ fontSize: '0.875rem', color: '#9ca3af' }}>Puerto: 8082</p>
        </div>

        <div className="card" style={{ padding: '1.5rem' }}>
          <h3 style={{ color: 'var(--primary-dark)', marginBottom: '1rem' }}>⚖️ Risk Service</h3>
          <p style={{ marginBottom: '1rem', color: '#6b7280' }}>
            Evaluación de riesgo crediticio con algoritmo de scoring avanzado.
          </p>
          <p style={{ fontSize: '0.875rem', color: '#9ca3af' }}>Puerto: 8083</p>
        </div>
      </div>

      {/* Architecture Info */}
      <div className="card" style={{ padding: '1.5rem', marginBottom: '2rem' }}>
        <h3 style={{ color: 'var(--primary-dark)', marginBottom: '1rem' }}>🏗️ Arquitectura Hexagonal</h3>
        <p style={{ marginBottom: '1rem' }}>
          Sistema implementado con <strong>Arquitectura Hexagonal (Ports & Adapters)</strong> en cada microservicio:
        </p>
        <ul style={{ paddingLeft: '2rem', color: '#6b7280' }}>
          <li>🔵 <strong>Dominio:</strong> Lógica de negocio pura sin dependencias de frameworks</li>
          <li>🟢 <strong>Aplicación:</strong> Casos de uso con puertos de entrada y salida</li>
          <li>🟡 <strong>Infraestructura:</strong> Adaptadores para REST, JPA, HTTP, etc.</li>
        </ul>
      </div>

      {/* Info Cards */}
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(280px, 1fr))', gap: '1.5rem' }}>
        <div className="card" style={{ padding: '1.5rem', background: 'linear-gradient(135deg, #eff6ff, #dbeafe)', border: '1px solid #bfdbfe' }}>
          <h3 style={{ fontSize: '1.25rem', fontWeight: '600', color: '#1e40af', marginBottom: '0.75rem', display: 'flex', alignItems: 'center' }}>
            <span style={{ marginRight: '0.5rem' }}>ℹ️</span>
            Información Importante
          </h3>
          <ul style={{ color: '#1e3a8a', listStyle: 'none', padding: 0 }}>
            <li style={{ marginBottom: '0.5rem' }}>• Las solicitudes son evaluadas automáticamente por nuestro sistema de riesgo</li>
            <li style={{ marginBottom: '0.5rem' }}>• El tiempo de respuesta es de 24-48 horas hábiles</li>
            <li>• Puedes consultar el estado de tus solicitudes en tiempo real</li>
          </ul>
        </div>

        <div className="card" style={{ padding: '1.5rem', background: 'linear-gradient(135deg, #f0fdf4, #dcfce7)', border: '1px solid #bbf7d0' }}>
          <h3 style={{ fontSize: '1.25rem', fontWeight: '600', color: '#166534', marginBottom: '0.75rem', display: 'flex', alignItems: 'center' }}>
            <span style={{ marginRight: '0.5rem' }}>💡</span>
            Consejos
          </h3>
          <ul style={{ color: '#14532d', listStyle: 'none', padding: 0 }}>
            <li style={{ marginBottom: '0.5rem' }}>• Completa todos los campos requeridos para agilizar el proceso</li>
            <li style={{ marginBottom: '0.5rem' }}>• Mantén actualizada tu información de contacto</li>
            <li>• Revisa los términos y condiciones antes de solicitar</li>
          </ul>
        </div>
      </div>

      {/* Footer Note */}
      <div className="alert alert-info" style={{ marginTop: '2rem' }}>
        <strong>💡 Nota:</strong> Asegúrate de que todos los servicios backend estén ejecutándose con <code>docker compose up</code>
      </div>
    </Layout>
  );
}

export default Home;
