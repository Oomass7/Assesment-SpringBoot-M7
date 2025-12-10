import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Layout from '../components/Layout';
import api from '../services/api';

function RiskPage() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    requestedAmount: '',
    termMonths: '',
    proposedRate: ''
  });
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    if (!localStorage.getItem('token')) {
      navigate('/auth');
    }
  }, [navigate]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    setResult(null);

    try {
      const response = await api.post('/risk/evaluate', {
        requestedAmount: parseFloat(formData.requestedAmount),
        termMonths: parseInt(formData.termMonths),
        proposedRate: parseFloat(formData.proposedRate)
      });
      
      setResult(response.data);
    } catch (error) {
      setError(error.response?.data?.message || 'Error al evaluar el riesgo');
    } finally {
      setLoading(false);
    }
  };

  const getRiskColor = (level) => {
    const colors = {
      BAJO: 'from-green-500 to-green-600',
      MEDIO: 'from-yellow-500 to-yellow-600',
      ALTO: 'from-red-500 to-red-600'
    };
    return colors[level] || 'from-gray-500 to-gray-600';
  };

  const getDecisionBadge = (decision) => {
    const config = {
      APROBADO: { class: 'badge-success', icon: '✅', text: 'Aprobado' },
      RECHAZADO: { class: 'badge-danger', icon: '❌', text: 'Rechazado' },
      REQUIERE_REVISION: { class: 'badge-warning', icon: '🔍', text: 'Requiere Revisión' }
    };
    const data = config[decision] || { class: 'badge-info', icon: '❓', text: decision };
    return (
      <span className={`badge ${data.class} text-lg px-4 py-2`}>
        <span className="mr-2">{data.icon}</span>
        {data.text}
      </span>
    );
  };

  return (
    <Layout>
      <div className="max-w-5xl mx-auto">
        {/* Header */}
        <div className="text-center mb-8">
          <h1 className="text-4xl font-bold text-gray-900 mb-3">
            📊 Evaluación de Riesgo Crediticio
          </h1>
          <p className="text-lg text-gray-600">
            Evalúa el nivel de riesgo basado en las condiciones del crédito
          </p>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
          {/* Formulario */}
          <div className="card">
            <h2 className="text-2xl font-bold text-gray-900 mb-6 flex items-center">
              <span className="mr-3">📝</span>
              Datos del Crédito
            </h2>

            <form onSubmit={handleSubmit} className="space-y-6">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  💰 Monto Solicitado ($)
                </label>
                <input
                  type="number"
                  required
                  min="1000"
                  max="200000"
                  step="100"
                  value={formData.requestedAmount}
                  onChange={(e) => setFormData({...formData, requestedAmount: e.target.value})}
                  className="input-field text-lg"
                  placeholder="50000"
                />
                <p className="text-xs text-gray-500 mt-1">Rango: $1,000 - $200,000</p>
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  📅 Plazo en Meses
                </label>
                <input
                  type="number"
                  required
                  min="6"
                  max="84"
                  value={formData.termMonths}
                  onChange={(e) => setFormData({...formData, termMonths: e.target.value})}
                  className="input-field text-lg"
                  placeholder="24"
                />
                <p className="text-xs text-gray-500 mt-1">Rango: 6 - 84 meses</p>
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  📈 Tasa de Interés Anual (%)
                </label>
                <input
                  type="number"
                  required
                  min="5"
                  max="30"
                  step="0.1"
                  value={formData.proposedRate}
                  onChange={(e) => setFormData({...formData, proposedRate: e.target.value})}
                  className="input-field text-lg"
                  placeholder="15.5"
                />
                <p className="text-xs text-gray-500 mt-1">Rango: 5% - 30%</p>
              </div>

              {error && (
                <div className="bg-red-50 border-l-4 border-red-500 p-4 rounded-lg">
                  <p className="text-red-700">{error}</p>
                </div>
              )}

              <button
                type="submit"
                disabled={loading}
                className="btn-primary w-full text-lg py-3"
              >
                {loading ? (
                  <span className="flex items-center justify-center">
                    <span className="animate-spin mr-2">⏳</span>
                    Evaluando...
                  </span>
                ) : (
                  '🔍 Evaluar Riesgo'
                )}
              </button>
            </form>

            {/* Info Panel */}
            <div className="mt-6 p-4 bg-blue-50 rounded-lg border border-blue-200">
              <h3 className="font-semibold text-blue-900 mb-2 flex items-center">
                <span className="mr-2">ℹ️</span>
                Información
              </h3>
              <ul className="text-sm text-blue-800 space-y-1">
                <li>• El score de riesgo va de 0 a 100</li>
                <li>• Riesgo BAJO: Score 0-40</li>
                <li>• Riesgo MEDIO: Score 41-70</li>
                <li>• Riesgo ALTO: Score 71-100</li>
              </ul>
            </div>
          </div>

          {/* Resultado */}
          <div>
            {result ? (
              <div className="space-y-6">
                {/* Card Principal de Resultado */}
                <div className={`bg-gradient-to-br ${getRiskColor(result.level)} rounded-2xl shadow-2xl p-8 text-white`}>
                  <div className="text-center mb-6">
                    <div className="text-6xl mb-4">
                      {result.level === 'BAJO' ? '🟢' : result.level === 'MEDIO' ? '🟡' : '🔴'}
                    </div>
                    <h3 className="text-3xl font-bold mb-2">
                      Riesgo {result.level}
                    </h3>
                    <div className="text-6xl font-bold mb-2">
                      {result.score}
                    </div>
                    <p className="text-xl opacity-90">de 100 puntos</p>
                  </div>

                  {/* Barra de progreso */}
                  <div className="w-full bg-white/30 rounded-full h-4 mb-6">
                    <div
                      className="bg-white rounded-full h-4 transition-all duration-1000"
                      style={{ width: `${result.score}%` }}
                    ></div>
                  </div>

                  <div className="text-center">
                    {getDecisionBadge(result.decision)}
                  </div>
                </div>

                {/* Detalles de la Evaluación */}
                <div className="card">
                  <h3 className="text-xl font-bold text-gray-900 mb-4 flex items-center">
                    <span className="mr-2">📋</span>
                    Detalles de la Evaluación
                  </h3>
                  <div className="space-y-3">
                    <div className="flex justify-between items-center py-3 border-b border-gray-200">
                      <span className="text-gray-600">💰 Monto:</span>
                      <span className="font-semibold text-lg">
                        ${parseFloat(formData.requestedAmount).toLocaleString()}
                      </span>
                    </div>
                    <div className="flex justify-between items-center py-3 border-b border-gray-200">
                      <span className="text-gray-600">📅 Plazo:</span>
                      <span className="font-semibold">{formData.termMonths} meses</span>
                    </div>
                    <div className="flex justify-between items-center py-3 border-b border-gray-200">
                      <span className="text-gray-600">📈 Tasa:</span>
                      <span className="font-semibold">{formData.proposedRate}%</span>
                    </div>
                    <div className="flex justify-between items-center py-3">
                      <span className="text-gray-600">🎯 Decisión:</span>
                      <span className="font-semibold">{result.decision.replace('_', ' ')}</span>
                    </div>
                  </div>
                </div>

                {/* Recomendaciones */}
                <div className="card bg-gradient-to-br from-purple-50 to-purple-100 border border-purple-200">
                  <h3 className="text-lg font-bold text-purple-900 mb-3 flex items-center">
                    <span className="mr-2">💡</span>
                    Recomendaciones
                  </h3>
                  <ul className="text-sm text-purple-800 space-y-2">
                    {result.level === 'BAJO' && (
                      <>
                        <li>✓ Excelente perfil crediticio</li>
                        <li>✓ Condiciones favorables para aprobación</li>
                        <li>✓ Bajo riesgo de incumplimiento</li>
                      </>
                    )}
                    {result.level === 'MEDIO' && (
                      <>
                        <li>⚠️ Requiere análisis adicional</li>
                        <li>⚠️ Considerar garantías adicionales</li>
                        <li>⚠️ Revisar historial crediticio</li>
                      </>
                    )}
                    {result.level === 'ALTO' && (
                      <>
                        <li>❌ Alto riesgo de incumplimiento</li>
                        <li>❌ Se recomienda rechazar o ajustar condiciones</li>
                        <li>❌ Requerir garantías sustanciales</li>
                      </>
                    )}
                  </ul>
                </div>

                <button
                  onClick={() => setResult(null)}
                  className="btn-secondary w-full"
                >
                  🔄 Nueva Evaluación
                </button>
              </div>
            ) : (
              <div className="card text-center py-16">
                <div className="text-8xl mb-6">🎯</div>
                <h3 className="text-2xl font-bold text-gray-900 mb-3">
                  Listo para Evaluar
                </h3>
                <p className="text-gray-600">
                  Completa el formulario y presiona "Evaluar Riesgo" para obtener el análisis
                </p>
              </div>
            )}
          </div>
        </div>
      </div>
    </Layout>
  );
}

export default RiskPage;
