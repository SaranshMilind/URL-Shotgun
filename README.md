# 🔗 URL Shotgun

-> Fire shorter. Reach farther.

URL Shotgun is a full-stack URL shortening and click analytics platform built with Java, Spring Boot, MySQL, and React.

The application converts long URLs into short, shareable links and records click activity whenever a shortened URL is accessed.

---

## 🚀 Features

- 🔗 Shorten long URLs
- ⚡ Generate unique 6-character short codes
- ↪️ Redirect short URLs to original URLs
- 📊 Track click counts
- 🌐 Record IP address, User-Agent, and referrer
- 🕒 Record click timestamps
- ✅ URL validation
- ❌ Custom exception handling
- 🛡️ Global REST API exception handling
- 🌐 React frontend
- 📱 Responsive UI
- 🗄️ MySQL persistence
- 🔄 REST API architecture
- 🔐 CORS configuration for frontend-backend communication

---

## 🛠️ Tech Stack

### Backend

- Java 26
- Spring Boot 4.1.1
- Spring Web
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Jakarta Validation

### FRONTEND

- React
- Vite
- JavaScript
- Axios
- CSS

---

## 🏗️ Architecture

```text
                    URL Shotgun
                        │
             ┌──────────┴──────────┐
             │                     │
          Frontend              Backend
        React + Vite         Spring Boot
             │                     │
             │ HTTP/REST           │
             └──────────┬──────────┘
                        │
                   Service Layer
                        │
                 Repository Layer
                        │
                        ▼
                      MySQL
