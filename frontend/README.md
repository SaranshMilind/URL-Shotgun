# URL Shotgun Frontend

React + Vite frontend for the URL Shotgun Spring Boot URL shortener.

## Run

From this directory:

```bash
npm install
npm run dev
```

Frontend: http://localhost:5173

Backend: http://localhost:8080

Optional API base URL:

```env
VITE_API_BASE_URL=http://localhost:8080
```

The frontend uses the real backend endpoints:

- `POST /api/urls`
- `GET /api/urls/{shortCode}/analytics`
- `GET /{shortCode}` for redirects
