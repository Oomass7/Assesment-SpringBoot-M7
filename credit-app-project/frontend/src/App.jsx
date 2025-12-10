import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import AuthPage from './pages/AuthPage';
import SolicitudesPage from './pages/SolicitudesPage';
import RiskPage from './pages/RiskPage';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/auth" element={<AuthPage />} />
        <Route path="/solicitudes" element={<SolicitudesPage />} />
        <Route path="/risk" element={<RiskPage />} />
      </Routes>
    </Router>
  );
}

export default App;
