import axios from 'axios';

const api = axios.create({
  headers: {
    'Content-Type': 'application/json',
  },
  baseURL: import.meta.env.VITE_APP_BASE_URL,
});

api.defaults.withCredentials = true;

export default api;
