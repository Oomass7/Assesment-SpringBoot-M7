import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { authAPI } from '../services/api';

function AuthPage() {
  const navigate = useNavigate();
  const [isLogin, setIsLogin] = useState(true);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const [loginData, setLoginData] = useState({
    email: '',
    password: ''
  });

  const [registerData, setRegisterData] = useState({
    email: '',
    password: '',
    name: '',
    role: 'AFILIADO'
  });

  const handleLogin = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      const response = await authAPI.login(loginData);

      localStorage.setItem('token', response.token);
      localStorage.setItem('user', JSON.stringify({
        email: loginData.email,
        name: registerData.name,
        role: response.role || 'AFILIADO'
      }));

      navigate('/');
    } catch (error) {
      setError(error.response?.data?.message || 'Error al iniciar sesión');
    } finally {
      setLoading(false);
    }
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');
    setLoading(true);

    try {
      await authAPI.register(registerData);
      setSuccess('Registro exitoso. Ahora puedes iniciar sesión.');
      setIsLogin(true);
      setLoginData({ email: registerData.email, password: '' });

      setTimeout(() => setSuccess(''), 3000);
    } catch (error) {
      setError(error.response?.data?.message || 'Error al registrarse');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex">
      {/* Panel Izquierdo - Info */}
      <div className="hidden lg:flex lg:w-1/2 bg-gradient-to-br from-primary-600 to-primary-900 p-12 text-white flex-col justify-between">
        <div>
          <div className="flex items-center space-x-3 mb-12">
            <span className="text-5xl">💳</span>
            <h1 className="text-3xl font-bold">CreditApp</h1>
          </div>

          <div className="space-y-8">
            <div>
              <h2 className="text-4xl font-bold mb-4">
                Sistema de Gestión de Créditos
              </h2>
              <p className="text-xl opacity-90">
                Plataforma integral para la gestión y evaluación de solicitudes crediticias
              </p>
            </div>

            <div className="space-y-4">
              <div className="flex items-start space-x-3">
                <span className="text-2xl">✨</span>
                <div>
                  <h3 className="font-semibold text-lg">Evaluación Automatizada</h3>
                  <p className="opacity-80">Sistema inteligente de análisis de riesgo</p>
                </div>
              </div>
              <div className="flex items-start space-x-3">
                <span className="text-2xl">⚡</span>
                <div>
                  <h3 className="font-semibold text-lg">Respuesta Rápida</h3>
                  <p className="opacity-80">Decisiones en tiempo real</p>
                </div>
              </div>
              <div className="flex items-start space-x-3">
                <span className="text-2xl">🔒</span>
                <div>
                  <h3 className="font-semibold text-lg">Seguro y Confiable</h3>
                  <p className="opacity-80">Protección de datos garantizada</p>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div className="border-t border-white/20 pt-6">
          <p className="opacity-70 text-sm">
            © 2025 CreditApp. Todos los derechos reservados.
          </p>
        </div>
      </div>

      {/* Panel Derecho - Formularios */}
      <div className="w-full lg:w-1/2 flex items-center justify-center p-8 bg-gray-50">
        <div className="w-full max-w-md">
          {/* Logo Mobile */}
          <div className="lg:hidden text-center mb-8">
            <div className="inline-flex items-center space-x-2 mb-4">
              <span className="text-4xl">💳</span>
              <h1 className="text-2xl font-bold text-primary-600">CreditApp</h1>
            </div>
          </div>

          {/* Tabs */}
          <div className="flex bg-white rounded-xl shadow-sm p-1 mb-8">
            <button
              onClick={() => {
                setIsLogin(true);
                setError('');
                setSuccess('');
              }}
              className={`flex-1 py-3 rounded-lg font-semibold transition-all ${isLogin
                ? 'bg-primary-600 text-white shadow-md'
                : 'text-gray-600 hover:text-gray-900'
                }`}
            >
              Iniciar Sesión
            </button>
            <button
              onClick={() => {
                setIsLogin(false);
                setError('');
                setSuccess('');
              }}
              className={`flex-1 py-3 rounded-lg font-semibold transition-all ${!isLogin
                ? 'bg-primary-600 text-white shadow-md'
                : 'text-gray-600 hover:text-gray-900'
                }`}
            >
              Registrarse
            </button>
          </div>

          {/* Alertas */}
          {error && (
            <div className="bg-red-50 border-l-4 border-red-500 p-4 mb-6 rounded-lg">
              <p className="text-red-700 text-sm">{error}</p>
            </div>
          )}
          {success && (
            <div className="bg-green-50 border-l-4 border-green-500 p-4 mb-6 rounded-lg">
              <p className="text-green-700 text-sm">{success}</p>
            </div>
          )}

          {/* Formulario Login */}
          {isLogin ? (
            <form onSubmit={handleLogin} className="bg-white rounded-2xl shadow-xl p-8 space-y-6">
              <div>
                <h2 className="text-2xl font-bold text-gray-900 mb-2">
                  Bienvenido de nuevo 👋
                </h2>
                <p className="text-gray-600">Ingresa tus credenciales para continuar</p>
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  📧 Correo Electrónico
                </label>
                <input
                  type="email"
                  required
                  value={loginData.email}
                  onChange={(e) => setLoginData({ ...loginData, email: e.target.value })}
                  className="input-field"
                  placeholder="usuario@ejemplo.com"
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  🔒 Contraseña
                </label>
                <input
                  type="password"
                  required
                  value={loginData.password}
                  onChange={(e) => setLoginData({ ...loginData, password: e.target.value })}
                  className="input-field"
                  placeholder="••••••••"
                />
              </div>

              <button
                type="submit"
                disabled={loading}
                className="btn-primary w-full text-lg py-3"
              >
                {loading ? 'Iniciando sesión...' : 'Iniciar Sesión'}
              </button>
            </form>
          ) : (
            /* Formulario Registro */
            <form onSubmit={handleRegister} className="bg-white rounded-2xl shadow-xl p-8 space-y-6">
              <div>
                <h2 className="text-2xl font-bold text-gray-900 mb-2">
                  Crear cuenta 🚀
                </h2>
                <p className="text-gray-600">Completa el formulario para empezar</p>
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  👤 Nombre Completo
                </label>
                <input
                  type="text"
                  required
                  value={registerData.name}
                  onChange={(e) => setRegisterData({ ...registerData, name: e.target.value })}
                  className="input-field"
                  placeholder="Juan Pérez"
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  📧 Correo Electrónico
                </label>
                <input
                  type="email"
                  required
                  value={registerData.email}
                  onChange={(e) => setRegisterData({ ...registerData, email: e.target.value })}
                  className="input-field"
                  placeholder="usuario@ejemplo.com"
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  🔒 Contraseña
                </label>
                <input
                  type="password"
                  required
                  minLength="6"
                  value={registerData.password}
                  onChange={(e) => setRegisterData({ ...registerData, password: e.target.value })}
                  className="input-field"
                  placeholder="Mínimo 6 caracteres"
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  🎭 Rol
                </label>
                <select
                  value={registerData.role}
                  onChange={(e) => setRegisterData({ ...registerData, role: e.target.value })}
                  className="input-field"
                >
                  <option value="AFILIADO">Afiliado</option>
                  <option value="ADMIN">Administrador</option>
                  <option value="ANALISTA">Analista</option>
                </select>
              </div>

              <button
                type="submit"
                disabled={loading}
                className="btn-primary w-full text-lg py-3"
              >
                {loading ? 'Registrando...' : 'Crear Cuenta'}
              </button>
            </form>
          )}

          {/* Footer */}
          <p className="text-center text-sm text-gray-600 mt-6">
            Al continuar, aceptas nuestros{' '}
            <a href="#" className="text-primary-600 hover:underline">
              Términos y Condiciones
            </a>
          </p>
        </div>
      </div>
    </div>
  );
}

export default AuthPage;
