import { useState } from 'react';
import { authAPI } from '../services/api';

function AuthPage() {
  const [activeTab, setActiveTab] = useState('login');
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState({ type: '', text: '' });

  // Login form
  const [loginForm, setLoginForm] = useState({
    email: '',
    password: ''
  });

  // Register form
  const [registerForm, setRegisterForm] = useState({
    email: '',
    password: '',
    nombre: '',
    rol: 'CLIENTE'
  });

  const handleLogin = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage({ type: '', text: '' });

    try {
      const response = await authAPI.login(loginForm);
      setMessage({ 
        type: 'success', 
        text: `✓ ${response.mensaje || 'Login exitoso!'} Token: ${response.token?.substring(0, 20)}...` 
      });
      setLoginForm({ email: '', password: '' });
    } catch (error) {
      setMessage({ 
        type: 'error', 
        text: error.response?.data?.error || 'Error al iniciar sesión' 
      });
    } finally {
      setLoading(false);
    }
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage({ type: '', text: '' });

    try {
      const response = await authAPI.register(registerForm);
      setMessage({ 
        type: 'success', 
        text: `✓ ${response.mensaje || 'Usuario registrado exitosamente!'}` 
      });
      setRegisterForm({ email: '', password: '', nombre: '', rol: 'CLIENTE' });
    } catch (error) {
      setMessage({ 
        type: 'error', 
        text: error.response?.data?.error || 'Error al registrar usuario' 
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container">
      <div className="card" style={{ maxWidth: '600px', margin: '0 auto' }}>
        <div className="card-header">🔐 Autenticación de Usuarios</div>

        {message.text && (
          <div className={`alert alert-${message.type === 'success' ? 'success' : 'error'}`}>
            {message.text}
          </div>
        )}

        <div style={{ display: 'flex', gap: '1rem', marginBottom: '2rem' }}>
          <button
            className={`btn ${activeTab === 'login' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setActiveTab('login')}
            style={{ flex: 1 }}
          >
            Iniciar Sesión
          </button>
          <button
            className={`btn ${activeTab === 'register' ? 'btn-primary' : 'btn-secondary'}`}
            onClick={() => setActiveTab('register')}
            style={{ flex: 1 }}
          >
            Registrarse
          </button>
        </div>

        {activeTab === 'login' ? (
          <form onSubmit={handleLogin}>
            <div className="form-group">
              <label className="form-label">Email</label>
              <input
                type="email"
                className="form-input"
                placeholder="usuario@example.com"
                value={loginForm.email}
                onChange={(e) => setLoginForm({ ...loginForm, email: e.target.value })}
                required
              />
            </div>

            <div className="form-group">
              <label className="form-label">Contraseña</label>
              <input
                type="password"
                className="form-input"
                placeholder="••••••••"
                value={loginForm.password}
                onChange={(e) => setLoginForm({ ...loginForm, password: e.target.value })}
                required
              />
            </div>

            <button type="submit" className="btn btn-primary" disabled={loading} style={{ width: '100%' }}>
              {loading ? 'Iniciando sesión...' : '🔓 Iniciar Sesión'}
            </button>
          </form>
        ) : (
          <form onSubmit={handleRegister}>
            <div className="form-group">
              <label className="form-label">Nombre Completo</label>
              <input
                type="text"
                className="form-input"
                placeholder="Juan Pérez"
                value={registerForm.nombre}
                onChange={(e) => setRegisterForm({ ...registerForm, nombre: e.target.value })}
                required
              />
            </div>

            <div className="form-group">
              <label className="form-label">Email</label>
              <input
                type="email"
                className="form-input"
                placeholder="usuario@example.com"
                value={registerForm.email}
                onChange={(e) => setRegisterForm({ ...registerForm, email: e.target.value })}
                required
              />
            </div>

            <div className="form-group">
              <label className="form-label">Contraseña</label>
              <input
                type="password"
                className="form-input"
                placeholder="••••••••"
                value={registerForm.password}
                onChange={(e) => setRegisterForm({ ...registerForm, password: e.target.value })}
                required
                minLength="6"
              />
            </div>

            <div className="form-group">
              <label className="form-label">Rol</label>
              <select
                className="form-select"
                value={registerForm.rol}
                onChange={(e) => setRegisterForm({ ...registerForm, rol: e.target.value })}
              >
                <option value="CLIENTE">Cliente</option>
                <option value="ADMIN">Administrador</option>
                <option value="EVALUADOR">Evaluador</option>
              </select>
            </div>

            <button type="submit" className="btn btn-primary" disabled={loading} style={{ width: '100%' }}>
              {loading ? 'Registrando...' : '📝 Registrarse'}
            </button>
          </form>
        )}
      </div>

      <div className="alert alert-info" style={{ maxWidth: '600px', margin: '2rem auto 0' }}>
        <strong>💡 Prueba rápida:</strong><br />
        Email: test@example.com<br />
        Password: Test1234
      </div>
    </div>
  );
}

export default AuthPage;
