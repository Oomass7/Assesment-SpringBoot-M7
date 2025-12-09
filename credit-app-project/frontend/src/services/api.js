import axios from 'axios';

const API_BASE_URL = 'http://localhost:8082';
const AUTH_BASE_URL = 'http://localhost:8081';
const RISK_BASE_URL = 'http://localhost:8083';

// Auth Service API
export const authAPI = {
  register: async (userData) => {
    const response = await axios.post(`${AUTH_BASE_URL}/auth/register`, userData);
    return response.data;
  },
  
  login: async (credentials) => {
    const response = await axios.post(`${AUTH_BASE_URL}/auth/login`, credentials);
    return response.data;
  }
};

// Core Service API - Solicitudes
export const solicitudAPI = {
  crear: async (solicitudData) => {
    const response = await axios.post(`${API_BASE_URL}/solicitudes`, solicitudData);
    return response.data;
  },
  
  listarTodas: async () => {
    const response = await axios.get(`${API_BASE_URL}/solicitudes`);
    return response.data;
  },
  
  buscarPorId: async (id) => {
    const response = await axios.get(`${API_BASE_URL}/solicitudes/${id}`);
    return response.data;
  },
  
  buscarPorDocumento: async (documento) => {
    const response = await axios.get(`${API_BASE_URL}/solicitudes/documento/${documento}`);
    return response.data;
  }
};

// Risk Service API
export const riskAPI = {
  evaluar: async (evaluacionData) => {
    const response = await axios.post(`${RISK_BASE_URL}/evaluate`, evaluacionData);
    return response.data;
  },
  
  health: async () => {
    const response = await axios.get(`${RISK_BASE_URL}/health`);
    return response.data;
  }
};
