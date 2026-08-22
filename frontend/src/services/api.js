import axios from "axios";

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || "http://localhost:8080",
  headers: {
    "Content-Type": "application/json"
  },
  timeout: 10000
});

export async function createShortUrl(originalUrl) {
  const response = await api.post("/api/urls", { originalUrl });
  return response.data;
}

export async function getAnalytics(shortCode) {
  const response = await api.get(`/api/urls/${encodeURIComponent(shortCode)}/analytics`);
  return response.data;
}

export default api;
