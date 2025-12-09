import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Home from './pages/Home';
import AuthPage from './pages/AuthPage';
import SolicitudesPage from './pages/SolicitudesPage';
import RiskPage from './pages/RiskPage';
import './App.css';

function App() {
  return (
    <Router>
      <div className="App">
        <nav className="navbar">
          <div className="navbar-container">
            <div className="navbar-brand">
              💳 Sistema de Créditos
            </div>
            <ul className="navbar-nav">
              <li>
                <Link to="/" className="nav-link">Inicio</Link>
              </li>
              <li>
                <Link to="/auth" className="nav-link">Autenticación</Link>
              </li>
              <li>
                <Link to="/solicitudes" className="nav-link">Solicitudes</Link>
              </li>
              <li>
                <Link to="/risk" className="nav-link">Evaluación de Riesgo</Link>
              </li>
            </ul>
          </div>
        </nav>

        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/auth" element={<AuthPage />} />
          <Route path="/solicitudes" element={<SolicitudesPage />} />
          <Route path="/risk" element={<RiskPage />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
