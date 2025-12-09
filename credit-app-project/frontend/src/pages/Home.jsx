import { useState, useEffect } from 'react';
import { solicitudAPI } from '../services/api';

function Home() {
  const [stats, setStats] = useState({
    total: 0,
    aprobadas: 0,
    rechazadas: 0,
    pendientes: 0
  });
  const [loading, setLoading] = useState(true);

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

  return (
    <div className="container">
      <div className="hero">
        <h1>🏦 Sistema de Gestión de Créditos</h1>
        <p>Plataforma profesional con arquitectura de microservicios y arquitectura hexagonal</p>
      </div>

      <h2 style={{ marginBottom: '1.5rem', color: 'var(--primary-dark)' }}>📊 Estadísticas del Sistema</h2>

      {loading ? (
        <div className="loading">
          <div className="spinner"></div>
        </div>
      ) : (
        <div className="stats-grid">
          <div className="stat-card">
            <div className="stat-value">{stats.total}</div>
            <div className="stat-label">Total Solicitudes</div>
          </div>
          <div className="stat-card" style={{ borderLeftColor: 'var(--success-color)' }}>
            <div className="stat-value" style={{ color: 'var(--success-color)' }}>{stats.aprobadas}</div>
            <div className="stat-label">Aprobadas</div>
          </div>
          <div className="stat-card" style={{ borderLeftColor: 'var(--danger-color)' }}>
            <div className="stat-value" style={{ color: 'var(--danger-color)' }}>{stats.rechazadas}</div>
            <div className="stat-label">Rechazadas</div>
          </div>
          <div className="stat-card" style={{ borderLeftColor: 'var(--warning-color)' }}>
            <div className="stat-value" style={{ color: 'var(--warning-color)' }}>{stats.pendientes}</div>
            <div className="stat-label">Pendientes</div>
          </div>
        </div>
      )}

      <div className="grid grid-3">
        <div className="card">
          <h3 style={{ color: 'var(--primary-dark)', marginBottom: '1rem' }}>🔐 Auth Service</h3>
          <p style={{ marginBottom: '1rem', color: '#6b7280' }}>
            Gestión de usuarios y autenticación con tokens seguros.
          </p>
          <p style={{ fontSize: '0.875rem', color: '#9ca3af' }}>Puerto: 8081</p>
        </div>

        <div className="card">
          <h3 style={{ color: 'var(--primary-dark)', marginBottom: '1rem' }}>💳 Core Service</h3>
          <p style={{ marginBottom: '1rem', color: '#6b7280' }}>
            Gestión completa de solicitudes de crédito con evaluación automática.
          </p>
          <p style={{ fontSize: '0.875rem', color: '#9ca3af' }}>Puerto: 8082</p>
        </div>

        <div className="card">
          <h3 style={{ color: 'var(--primary-dark)', marginBottom: '1rem' }}>⚖️ Risk Service</h3>
          <p style={{ marginBottom: '1rem', color: '#6b7280' }}>
            Evaluación de riesgo crediticio con algoritmo de scoring avanzado.
          </p>
          <p style={{ fontSize: '0.875rem', color: '#9ca3af' }}>Puerto: 8083</p>
        </div>
      </div>

      <div className="card">
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

      <div className="alert alert-info">
        <strong>💡 Nota:</strong> Asegúrate de que todos los servicios backend estén ejecutándose con <code>docker compose up</code>
      </div>
    </div>
  );
}

export default Home;
