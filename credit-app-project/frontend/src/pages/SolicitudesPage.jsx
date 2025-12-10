import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Layout from '../components/Layout';
import api from '../services/api';

function SolicitudesPage() {
  const navigate = useNavigate();
  const [solicitudes, setSolicitudes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [formData, setFormData] = useState({
    clientDocument: '',
    clientName: '',
    requestedAmount: '',
    termMonths: '',
    proposedRate: ''
  });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [searchDocument, setSearchDocument] = useState('');

  const user = JSON.parse(localStorage.getItem('user') || '{}');
  const isAdmin = user.role === 'ADMIN';

  useEffect(() => {
    if (!localStorage.getItem('token')) {
      navigate('/auth');
      return;
    }
    fetchSolicitudes();
  }, [navigate]);

  const fetchSolicitudes = async () => {
    try {
      setLoading(true);
      let response;
      if (isAdmin) {
        response = await api.get('/requests');
      } else {
        // Si no es admin, buscar por documento del usuario
        const userDoc = user.document || '123456';
        response = await api.get(`/requests/document/${userDoc}`);
      }
      setSolicitudes(Array.isArray(response.data) ? response.data : [response.data]);
    } catch (error) {
      console.error('Error al cargar solicitudes:', error);
      if (error.response?.status === 403 || error.response?.status === 401) {
        setError('No tienes permisos para ver todas las solicitudes');
      }
      setSolicitudes([]);
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');

    try {
      const response = await api.post('/requests', {
        ...formData,
        requestedAmount: parseFloat(formData.requestedAmount),
        termMonths: parseInt(formData.termMonths),
        proposedRate: parseFloat(formData.proposedRate)
      });
      
      setSuccess('Solicitud creada exitosamente');
      setShowModal(false);
      setFormData({
        clientDocument: '',
        clientName: '',
        requestedAmount: '',
        termMonths: '',
        proposedRate: ''
      });
      fetchSolicitudes();
      
      setTimeout(() => setSuccess(''), 3000);
    } catch (error) {
      setError(error.response?.data?.message || 'Error al crear solicitud');
    }
  };

  const handleSearch = async () => {
    if (!searchDocument) {
      fetchSolicitudes();
      return;
    }

    try {
      setLoading(true);
      const response = await api.get(`/requests/document/${searchDocument}`);
      setSolicitudes(Array.isArray(response.data) ? response.data : [response.data]);
    } catch (error) {
      setError('No se encontraron solicitudes con ese documento');
      setSolicitudes([]);
    } finally {
      setLoading(false);
    }
  };

  const getStatusBadge = (status) => {
    const statusConfig = {
      PENDIENTE: { class: 'badge-warning', icon: '⏳', text: 'Pendiente' },
      APROBADO: { class: 'badge-success', icon: '✅', text: 'Aprobado' },
      RECHAZADO: { class: 'badge-danger', icon: '❌', text: 'Rechazado' },
      REQUIERE_REVISION: { class: 'badge-info', icon: '🔍', text: 'En Revisión' }
    };
    const config = statusConfig[status] || { class: 'badge-info', icon: '❓', text: status };
    return (
      <span className={`badge ${config.class}`}>
        <span className="mr-1">{config.icon}</span>
        {config.text}
      </span>
    );
  };

  const getRiskBadge = (level) => {
    const riskConfig = {
      BAJO: { class: 'badge-success', icon: '🟢', text: 'Riesgo Bajo' },
      MEDIO: { class: 'badge-warning', icon: '🟡', text: 'Riesgo Medio' },
      ALTO: { class: 'badge-danger', icon: '🔴', text: 'Riesgo Alto' }
    };
    const config = riskConfig[level] || { class: 'badge-info', icon: '⚪', text: level };
    return (
      <span className={`badge ${config.class}`}>
        <span className="mr-1">{config.icon}</span>
        {config.text}
      </span>
    );
  };

  return (
    <Layout>
      {/* Header */}
      <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center mb-8 gap-4">
        <div>
          <h1 className="text-3xl font-bold text-gray-900 mb-2">
            📋 Gestión de Solicitudes
          </h1>
          <p className="text-gray-600">
            {isAdmin ? 'Administra todas las solicitudes del sistema' : 'Consulta y gestiona tus solicitudes de crédito'}
          </p>
        </div>
        <button
          onClick={() => setShowModal(true)}
          className="btn-primary whitespace-nowrap"
        >
          ➕ Nueva Solicitud
        </button>
      </div>

      {/* Alertas */}
      {error && (
        <div className="bg-red-50 border-l-4 border-red-500 p-4 mb-6 rounded-lg">
          <p className="text-red-700">{error}</p>
        </div>
      )}
      {success && (
        <div className="bg-green-50 border-l-4 border-green-500 p-4 mb-6 rounded-lg">
          <p className="text-green-700">{success}</p>
        </div>
      )}

      {/* Búsqueda */}
      {isAdmin && (
        <div className="card mb-6">
          <h3 className="text-lg font-semibold mb-4">🔍 Buscar por Documento</h3>
          <div className="flex gap-3">
            <input
              type="text"
              value={searchDocument}
              onChange={(e) => setSearchDocument(e.target.value)}
              placeholder="Ingrese número de documento..."
              className="input-field flex-1"
              onKeyPress={(e) => e.key === 'Enter' && handleSearch()}
            />
            <button onClick={handleSearch} className="btn-primary whitespace-nowrap">
              Buscar
            </button>
            <button onClick={() => { setSearchDocument(''); fetchSolicitudes(); }} className="btn-secondary">
              Limpiar
            </button>
          </div>
        </div>
      )}

      {/* Lista de Solicitudes */}
      {loading ? (
        <div className="flex justify-center items-center py-12">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-600"></div>
        </div>
      ) : solicitudes.length === 0 ? (
        <div className="card text-center py-12">
          <div className="text-6xl mb-4">📭</div>
          <h3 className="text-xl font-semibold text-gray-900 mb-2">No hay solicitudes</h3>
          <p className="text-gray-600 mb-6">
            {isAdmin 
              ? 'No hay solicitudes en el sistema' 
              : 'Aún no has creado ninguna solicitud'}
          </p>
          <button onClick={() => setShowModal(true)} className="btn-primary">
            Crear primera solicitud
          </button>
        </div>
      ) : (
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
          {solicitudes.map((solicitud) => (
            <div key={solicitud.id} className="card hover:shadow-xl transition-all">
              <div className="flex justify-between items-start mb-4">
                <div>
                  <h3 className="text-xl font-bold text-gray-900 mb-1">
                    {solicitud.clientName}
                  </h3>
                  <p className="text-sm text-gray-600">Doc: {solicitud.clientDocument}</p>
                </div>
                {getStatusBadge(solicitud.status)}
              </div>

              <div className="space-y-3 mb-4">
                <div className="flex justify-between items-center py-2 border-b border-gray-100">
                  <span className="text-gray-600">💰 Monto Solicitado:</span>
                  <span className="font-semibold text-lg text-primary-600">
                    ${solicitud.requestedAmount?.toLocaleString()}
                  </span>
                </div>
                <div className="flex justify-between items-center py-2 border-b border-gray-100">
                  <span className="text-gray-600">📅 Plazo:</span>
                  <span className="font-semibold">{solicitud.termMonths} meses</span>
                </div>
                <div className="flex justify-between items-center py-2 border-b border-gray-100">
                  <span className="text-gray-600">📈 Tasa Propuesta:</span>
                  <span className="font-semibold">{solicitud.proposedRate}%</span>
                </div>
                {solicitud.riskScore !== null && (
                  <div className="flex justify-between items-center py-2 border-b border-gray-100">
                    <span className="text-gray-600">📊 Score de Riesgo:</span>
                    <span className="font-semibold">{solicitud.riskScore}/100</span>
                  </div>
                )}
              </div>

              {solicitud.riskLevel && (
                <div className="pt-3 border-t border-gray-200">
                  {getRiskBadge(solicitud.riskLevel)}
                </div>
              )}

              {solicitud.createdAt && (
                <p className="text-xs text-gray-400 mt-3">
                  Creado: {new Date(solicitud.createdAt).toLocaleDateString()}
                </p>
              )}
            </div>
          ))}
        </div>
      )}

      {/* Modal Nueva Solicitud */}
      {showModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
          <div className="bg-white rounded-2xl max-w-2xl w-full max-h-[90vh] overflow-y-auto shadow-2xl">
            <div className="sticky top-0 bg-white border-b border-gray-200 p-6 flex justify-between items-center">
              <h2 className="text-2xl font-bold text-gray-900">📝 Nueva Solicitud de Crédito</h2>
              <button
                onClick={() => {
                  setShowModal(false);
                  setError('');
                }}
                className="text-gray-400 hover:text-gray-600 text-2xl"
              >
                ×
              </button>
            </div>

            <form onSubmit={handleSubmit} className="p-6 space-y-6">
              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Documento del Cliente *
                  </label>
                  <input
                    type="text"
                    required
                    value={formData.clientDocument}
                    onChange={(e) => setFormData({...formData, clientDocument: e.target.value})}
                    className="input-field"
                    placeholder="123456789"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Nombre del Cliente *
                  </label>
                  <input
                    type="text"
                    required
                    value={formData.clientName}
                    onChange={(e) => setFormData({...formData, clientName: e.target.value})}
                    className="input-field"
                    placeholder="Juan Pérez"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Monto Solicitado ($) *
                  </label>
                  <input
                    type="number"
                    required
                    min="1000"
                    step="100"
                    value={formData.requestedAmount}
                    onChange={(e) => setFormData({...formData, requestedAmount: e.target.value})}
                    className="input-field"
                    placeholder="50000"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Plazo (meses) *
                  </label>
                  <input
                    type="number"
                    required
                    min="6"
                    max="84"
                    value={formData.termMonths}
                    onChange={(e) => setFormData({...formData, termMonths: e.target.value})}
                    className="input-field"
                    placeholder="24"
                  />
                </div>

                <div className="md:col-span-2">
                  <label className="block text-sm font-medium text-gray-700 mb-2">
                    Tasa de Interés Propuesta (%) *
                  </label>
                  <input
                    type="number"
                    required
                    min="5"
                    max="30"
                    step="0.1"
                    value={formData.proposedRate}
                    onChange={(e) => setFormData({...formData, proposedRate: e.target.value})}
                    className="input-field"
                    placeholder="15.5"
                  />
                </div>
              </div>

              {error && (
                <div className="bg-red-50 border-l-4 border-red-500 p-4 rounded-lg">
                  <p className="text-red-700 text-sm">{error}</p>
                </div>
              )}

              <div className="flex gap-3 pt-4">
                <button type="submit" className="btn-primary flex-1">
                  Crear Solicitud
                </button>
                <button
                  type="button"
                  onClick={() => {
                    setShowModal(false);
                    setError('');
                  }}
                  className="btn-secondary flex-1"
                >
                  Cancelar
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </Layout>
  );
}

export default SolicitudesPage;
