import { useState } from 'react';
import { riskAPI } from '../services/api';

function RiskPage() {
  const [loading, setLoading] = useState(false);
  const [result, setResult] = useState(null);
  const [message, setMessage] = useState({ type: '', text: '' });

  const [formData, setFormData] = useState({
    documento: '',
    monto: '',
    plazo: ''
  });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage({ type: '', text: '' });
    setResult(null);

    try {
      const data = {
        documento: formData.documento,
        monto: formData.monto ? parseFloat(formData.monto) : null,
        plazo: formData.plazo ? parseInt(formData.plazo) : null
      };

      const response = await riskAPI.evaluar(data);
      setResult(response);
      setMessage({ type: 'success', text: '✓ Evaluación completada exitosamente' });
    } catch (error) {
      setMessage({ 
        type: 'error', 
        text: error.response?.data?.error || 'Error al evaluar riesgo' 
      });
    } finally {
      setLoading(false);
    }
  };

  const getRiesgoColor = (nivel) => {
    const colors = {
      'BAJO': 'var(--success-color)',
      'MEDIO': 'var(--warning-color)',
      'ALTO': 'var(--danger-color)'
    };
    return colors[nivel] || 'var(--primary-color)';
  };

  const getRiesgoIcon = (nivel) => {
    const icons = {
      'BAJO': '✅',
      'MEDIO': '⚠️',
      'ALTO': '❌'
    };
    return icons[nivel] || '📊';
  };

  const getScoreDescription = (score) => {
    if (score <= 500) return 'Riesgo muy alto - Rechazo recomendado';
    if (score <= 650) return 'Riesgo alto - Requiere garantías adicionales';
    if (score <= 700) return 'Riesgo moderado - Requiere evaluación manual';
    if (score <= 850) return 'Riesgo bajo - Aprobación recomendada';
    return 'Riesgo muy bajo - Aprobación automática';
  };

  return (
    <div className="container">
      <div className="grid" style={{ gridTemplateColumns: '1fr 1fr', gap: '2rem' }}>
        <div className="card">
          <div className="card-header">⚖️ Evaluador de Riesgo Crediticio</div>

          {message.text && (
            <div className={`alert alert-${message.type}`}>
              {message.text}
            </div>
          )}

          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label className="form-label">Documento del Cliente *</label>
              <input
                type="text"
                className="form-input"
                placeholder="12345678"
                value={formData.documento}
                onChange={(e) => setFormData({ ...formData, documento: e.target.value })}
                required
              />
              <small style={{ color: '#6b7280', fontSize: '0.875rem' }}>
                El documento es usado para calcular el score base
              </small>
            </div>

            <div className="form-group">
              <label className="form-label">Monto Solicitado ($) - Opcional</label>
              <input
                type="number"
                className="form-input"
                placeholder="10000"
                value={formData.monto}
                onChange={(e) => setFormData({ ...formData, monto: e.target.value })}
                min="0"
                step="100"
              />
              <small style={{ color: '#6b7280', fontSize: '0.875rem' }}>
                Montos superiores a $50,000 aumentan el nivel de riesgo
              </small>
            </div>

            <div className="form-group">
              <label className="form-label">Plazo (meses) - Opcional</label>
              <input
                type="number"
                className="form-input"
                placeholder="24"
                value={formData.plazo}
                onChange={(e) => setFormData({ ...formData, plazo: e.target.value })}
                min="0"
                max="120"
              />
              <small style={{ color: '#6b7280', fontSize: '0.875rem' }}>
                Plazos superiores a 36 meses aumentan el nivel de riesgo
              </small>
            </div>

            <button type="submit" className="btn btn-primary" disabled={loading} style={{ width: '100%' }}>
              {loading ? 'Evaluando...' : '📊 Evaluar Riesgo'}
            </button>
          </form>
        </div>

        <div className="card">
          <div className="card-header">📋 Resultado de la Evaluación</div>

          {!result ? (
            <div style={{ textAlign: 'center', padding: '3rem', color: '#6b7280' }}>
              <div style={{ fontSize: '4rem', marginBottom: '1rem' }}>📊</div>
              <p>Completa el formulario para evaluar el riesgo crediticio</p>
            </div>
          ) : (
            <div>
              <div style={{ 
                textAlign: 'center', 
                padding: '2rem',
                background: 'linear-gradient(135deg, #f8fafc, #e2e8f0)',
                borderRadius: '0.75rem',
                marginBottom: '1.5rem'
              }}>
                <div style={{ fontSize: '4rem', marginBottom: '1rem' }}>
                  {getRiesgoIcon(result.nivelRiesgo)}
                </div>
                <div style={{ 
                  fontSize: '3rem', 
                  fontWeight: 'bold',
                  color: getRiesgoColor(result.nivelRiesgo),
                  marginBottom: '0.5rem'
                }}>
                  {result.score}
                </div>
                <div style={{ 
                  fontSize: '1.5rem',
                  fontWeight: 600,
                  color: getRiesgoColor(result.nivelRiesgo),
                  marginBottom: '0.5rem'
                }}>
                  Riesgo {result.nivelRiesgo}
                </div>
                <div style={{ color: '#6b7280', fontSize: '0.875rem' }}>
                  {getScoreDescription(result.score)}
                </div>
              </div>

              <div className="card" style={{ backgroundColor: '#f8fafc' }}>
                <h4 style={{ marginBottom: '1rem', color: 'var(--primary-dark)' }}>Detalles</h4>
                <div style={{ display: 'grid', gap: '0.75rem' }}>
                  <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                    <span style={{ color: '#6b7280' }}>Documento:</span>
                    <strong>{result.documento}</strong>
                  </div>
                  <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                    <span style={{ color: '#6b7280' }}>Score Calculado:</span>
                    <strong>{result.score}</strong>
                  </div>
                  <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                    <span style={{ color: '#6b7280' }}>Nivel de Riesgo:</span>
                    <span className={`badge ${
                      result.nivelRiesgo === 'BAJO' ? 'badge-success' :
                      result.nivelRiesgo === 'MEDIO' ? 'badge-warning' : 'badge-danger'
                    }`}>
                      {result.nivelRiesgo}
                    </span>
                  </div>
                </div>
              </div>

              <div className={`alert ${
                result.nivelRiesgo === 'BAJO' ? 'alert-success' :
                result.nivelRiesgo === 'MEDIO' ? 'alert-warning' : 'alert-error'
              }`} style={{ marginTop: '1rem' }}>
                <strong>Recomendación:</strong><br />
                {result.nivelRiesgo === 'BAJO' && 'Cliente de bajo riesgo. Se recomienda aprobación automática del crédito.'}
                {result.nivelRiesgo === 'MEDIO' && 'Cliente de riesgo medio. Requiere evaluación manual por un analista.'}
                {result.nivelRiesgo === 'ALTO' && 'Cliente de alto riesgo. Se recomienda rechazo automático o solicitar garantías adicionales.'}
              </div>
            </div>
          )}
        </div>
      </div>

      <div className="card">
        <h3 style={{ color: 'var(--primary-dark)', marginBottom: '1rem' }}>🧮 Algoritmo de Scoring</h3>
        <p style={{ marginBottom: '1rem' }}>
          El sistema utiliza un algoritmo de evaluación de riesgo que considera múltiples factores:
        </p>
        
        <div className="grid grid-3">
          <div className="card" style={{ backgroundColor: '#f8fafc' }}>
            <h4 style={{ color: 'var(--primary-dark)', marginBottom: '0.5rem' }}>📝 Base</h4>
            <p style={{ fontSize: '0.875rem', color: '#6b7280' }}>
              Score base entre 300-950 calculado a partir del hash del documento del cliente
            </p>
          </div>

          <div className="card" style={{ backgroundColor: '#f8fafc' }}>
            <h4 style={{ color: 'var(--warning-color)', marginBottom: '0.5rem' }}>💰 Monto</h4>
            <p style={{ fontSize: '0.875rem', color: '#6b7280' }}>
              +50 puntos de riesgo si el monto solicitado es mayor a $50,000
            </p>
          </div>

          <div className="card" style={{ backgroundColor: '#f8fafc' }}>
            <h4 style={{ color: 'var(--danger-color)', marginBottom: '0.5rem' }}>📅 Plazo</h4>
            <p style={{ fontSize: '0.875rem', color: '#6b7280' }}>
              +30 puntos de riesgo si el plazo es mayor a 36 meses
            </p>
          </div>
        </div>

        <div style={{ marginTop: '1.5rem', padding: '1rem', backgroundColor: '#f8fafc', borderRadius: '0.5rem' }}>
          <h4 style={{ marginBottom: '0.75rem' }}>Clasificación de Riesgo:</h4>
          <ul style={{ paddingLeft: '1.5rem', color: '#6b7280' }}>
            <li><strong style={{ color: 'var(--success-color)' }}>BAJO (701-950):</strong> Aprobación automática recomendada</li>
            <li><strong style={{ color: 'var(--warning-color)' }}>MEDIO (501-700):</strong> Requiere evaluación manual</li>
            <li><strong style={{ color: 'var(--danger-color)' }}>ALTO (300-500):</strong> Rechazo automático recomendado</li>
          </ul>
        </div>
      </div>
    </div>
  );
}

export default RiskPage;
