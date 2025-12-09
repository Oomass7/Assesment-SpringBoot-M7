import { useState, useEffect } from 'react';
import { solicitudAPI } from '../services/api';

function SolicitudesPage() {
  const [solicitudes, setSolicitudes] = useState([]);
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState({ type: '', text: '' });
  const [showForm, setShowForm] = useState(false);
  const [searchDoc, setSearchDoc] = useState('');

  const [formData, setFormData] = useState({
    documentoCliente: '',
    nombreCliente: '',
    montoSolicitado: '',
    plazoMeses: ''
  });

  useEffect(() => {
    loadSolicitudes();
  }, []);

  const loadSolicitudes = async () => {
    setLoading(true);
    try {
      const data = await solicitudAPI.listarTodas();
      setSolicitudes(data);
    } catch (error) {
      setMessage({ type: 'error', text: 'Error al cargar solicitudes' });
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage({ type: '', text: '' });

    try {
      const data = {
        ...formData,
        montoSolicitado: parseFloat(formData.montoSolicitado),
        plazoMeses: parseInt(formData.plazoMeses)
      };

      const response = await solicitudAPI.crear(data);
      setMessage({ 
        type: 'success', 
        text: `✓ Solicitud creada exitosamente! Estado: ${response.estado} - Nivel de Riesgo: ${response.nivelRiesgo}` 
      });
      setFormData({ documentoCliente: '', nombreCliente: '', montoSolicitado: '', plazoMeses: '' });
      setShowForm(false);
      loadSolicitudes();
    } catch (error) {
      setMessage({ 
        type: 'error', 
        text: error.response?.data?.error || 'Error al crear solicitud' 
      });
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async () => {
    if (!searchDoc) {
      loadSolicitudes();
      return;
    }

    setLoading(true);
    try {
      const data = await solicitudAPI.buscarPorDocumento(searchDoc);
      setSolicitudes(data);
      if (data.length === 0) {
        setMessage({ type: 'warning', text: 'No se encontraron solicitudes para ese documento' });
      }
    } catch (error) {
      setMessage({ type: 'error', text: 'Error al buscar solicitudes' });
    } finally {
      setLoading(false);
    }
  };

  const getEstadoBadge = (estado) => {
    const badges = {
      'APROBADA': 'badge-success',
      'RECHAZADA': 'badge-danger',
      'PENDIENTE': 'badge-warning'
    };
    return badges[estado] || 'badge-info';
  };

  const getRiesgoBadge = (nivel) => {
    const badges = {
      'BAJO': 'badge-success',
      'MEDIO': 'badge-warning',
      'ALTO': 'badge-danger'
    };
    return badges[nivel] || 'badge-info';
  };

  return (
    <div className="container">
      <div className="card">
        <div className="card-header">💳 Gestión de Solicitudes de Crédito</div>

        {message.text && (
          <div className={`alert alert-${message.type}`}>
            {message.text}
          </div>
        )}

        <div style={{ display: 'flex', gap: '1rem', marginBottom: '2rem', flexWrap: 'wrap' }}>
          <button
            className="btn btn-primary"
            onClick={() => setShowForm(!showForm)}
          >
            {showForm ? '❌ Cancelar' : '➕ Nueva Solicitud'}
          </button>

          <div style={{ display: 'flex', gap: '0.5rem', flex: '1', minWidth: '300px' }}>
            <input
              type="text"
              className="form-input"
              placeholder="Buscar por documento..."
              value={searchDoc}
              onChange={(e) => setSearchDoc(e.target.value)}
              style={{ flex: 1 }}
            />
            <button className="btn btn-secondary" onClick={handleSearch}>
              🔍 Buscar
            </button>
            <button className="btn btn-secondary" onClick={() => { setSearchDoc(''); loadSolicitudes(); }}>
              🔄 Todas
            </button>
          </div>
        </div>

        {showForm && (
          <div className="card" style={{ backgroundColor: '#f8fafc', marginBottom: '2rem' }}>
            <form onSubmit={handleSubmit}>
              <div className="grid grid-2">
                <div className="form-group">
                  <label className="form-label">Documento del Cliente</label>
                  <input
                    type="text"
                    className="form-input"
                    placeholder="12345678"
                    value={formData.documentoCliente}
                    onChange={(e) => setFormData({ ...formData, documentoCliente: e.target.value })}
                    required
                  />
                </div>

                <div className="form-group">
                  <label className="form-label">Nombre del Cliente</label>
                  <input
                    type="text"
                    className="form-input"
                    placeholder="Juan Pérez"
                    value={formData.nombreCliente}
                    onChange={(e) => setFormData({ ...formData, nombreCliente: e.target.value })}
                    required
                  />
                </div>

                <div className="form-group">
                  <label className="form-label">Monto Solicitado ($)</label>
                  <input
                    type="number"
                    className="form-input"
                    placeholder="10000"
                    value={formData.montoSolicitado}
                    onChange={(e) => setFormData({ ...formData, montoSolicitado: e.target.value })}
                    required
                    min="1000"
                    step="100"
                  />
                </div>

                <div className="form-group">
                  <label className="form-label">Plazo (meses)</label>
                  <input
                    type="number"
                    className="form-input"
                    placeholder="24"
                    value={formData.plazoMeses}
                    onChange={(e) => setFormData({ ...formData, plazoMeses: e.target.value })}
                    required
                    min="6"
                    max="60"
                  />
                </div>
              </div>

              <button type="submit" className="btn btn-primary" disabled={loading}>
                {loading ? 'Procesando...' : '💾 Crear Solicitud'}
              </button>
            </form>
          </div>
        )}

        {loading && !showForm ? (
          <div className="loading">
            <div className="spinner"></div>
          </div>
        ) : (
          <>
            <p style={{ marginBottom: '1rem', color: '#6b7280' }}>
              Total de solicitudes: <strong>{solicitudes.length}</strong>
            </p>

            <div className="table-container">
              <table className="table">
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Cliente</th>
                    <th>Documento</th>
                    <th>Monto</th>
                    <th>Plazo</th>
                    <th>Score</th>
                    <th>Nivel Riesgo</th>
                    <th>Estado</th>
                    <th>Fecha</th>
                  </tr>
                </thead>
                <tbody>
                  {solicitudes.length === 0 ? (
                    <tr>
                      <td colSpan="9" style={{ textAlign: 'center', padding: '2rem', color: '#6b7280' }}>
                        No hay solicitudes registradas
                      </td>
                    </tr>
                  ) : (
                    solicitudes.map((solicitud) => (
                      <tr key={solicitud.id}>
                        <td><strong>#{solicitud.id}</strong></td>
                        <td>{solicitud.nombreCliente}</td>
                        <td>{solicitud.documentoCliente}</td>
                        <td>${solicitud.montoSolicitado?.toLocaleString()}</td>
                        <td>{solicitud.plazoMeses} meses</td>
                        <td><strong>{solicitud.scoreRiesgo || '-'}</strong></td>
                        <td>
                          {solicitud.nivelRiesgo ? (
                            <span className={`badge ${getRiesgoBadge(solicitud.nivelRiesgo)}`}>
                              {solicitud.nivelRiesgo}
                            </span>
                          ) : '-'}
                        </td>
                        <td>
                          <span className={`badge ${getEstadoBadge(solicitud.estado)}`}>
                            {solicitud.estado}
                          </span>
                        </td>
                        <td>{new Date(solicitud.fechaSolicitud).toLocaleDateString()}</td>
                      </tr>
                    ))
                  )}
                </tbody>
              </table>
            </div>
          </>
        )}
      </div>

      <div className="alert alert-info">
        <strong>ℹ️ Información:</strong> Las solicitudes son evaluadas automáticamente por el Risk Service. 
        El sistema aprueba automáticamente solicitudes de riesgo BAJO y rechaza las de riesgo ALTO.
      </div>
    </div>
  );
}

export default SolicitudesPage;
