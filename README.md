# 🚀 Nivora Finance Backend

> A production-ready fintech backend built with **Spring Boot**, **PostgreSQL**, **Redis**, **JWT Authentication**, **Docker**, and **GitHub Actions** following modern backend engineering practices.

---

## ✨ Overview

Nivora Finance Backend is a scalable fintech backend that provides secure authentication, wallet management, money transfers, QR payments, and Redis-powered rate limiting.

The project emphasizes clean architecture, security, scalability, automated testing, containerization, and CI/CD, making it suitable for production-oriented backend development.

---

# ✨ Features

## 🔐 Authentication

- User Registration
- Login
- Logout
- OTP Verification
- JWT Authentication
- BCrypt Password Encryption
- Redis Session Management
- Current User Endpoint

---

## 💰 Wallet

- Create Wallet
- Deposit Money
- Withdraw Money
- Wallet Balance
- Balance Validation

---

## 💸 Transactions

- Money Transfer
- Transaction History
- Transaction Search
- Transaction Summary
- Recent Contacts
- Idempotency-Key Support

---

## 📱 QR Payments

- Generate Personal QR
- Resolve QR
- Pay using QR

---

## 🛡️ Security

- Spring Security
- JWT Authorization Filter
- Protected APIs
- Public Route Configuration
- BCrypt Password Encoding
- Redis Rate Limiting

---

## 🚦 Rate Limiting

Implemented using Redis.

### Public APIs

- Signup
- Login
- Verify OTP

### Protected APIs

- Money Transfer
- QR Payments

### Features

- IP-based rate limiting
- User-based rate limiting
- Redis TTL
- Automatic Counter Expiry

---

## 🐳 Infrastructure

- Docker
- Docker Compose
- PostgreSQL
- Redis
- Multi-stage Docker Build
- Health Checks

---

## ⚙️ CI/CD

Implemented using GitHub Actions.

Pipeline automatically:

- Runs Unit Tests
- Builds BootJar
- Builds Docker Image
- Publishes Docker Image to GitHub Container Registry (GHCR)

---

## 🧪 Testing

- JUnit 5
- Mockito
- Unit Tested Services

Current Coverage includes

- AuthService
- JwtService
- WalletService
- TransactionService
- QrService
- RateLimitService

---

# 🛠️ Tech Stack

| Category | Technology |
|-----------|------------|
| Language | Java 21 |
| Framework | Spring Boot 4 |
| Security | Spring Security, JWT |
| Database | PostgreSQL |
| Cache | Redis |
| ORM | Spring Data JPA / Hibernate |
| Build Tool | Gradle |
| Documentation | Swagger / OpenAPI |
| Containerization | Docker |
| Orchestration | Docker Compose |
| CI/CD | GitHub Actions |
| Container Registry | GitHub Container Registry (GHCR) |
| Testing | JUnit 5, Mockito |

---

# 🏗️ Architecture

```
                   Client
                      │
                      ▼
              Spring Boot Backend
          ┌───────────┼────────────┐
          │           │            │
          ▼           ▼            ▼
     PostgreSQL     Redis      Mail Service

                      │
                      ▼
             GitHub Actions CI

                      │
                      ▼
        GitHub Container Registry
```

---

# 📁 Project Structure

```
src
└── main
    ├── java
    │   └── com.nivora.finance
    │       ├── config
    │       ├── controller
    │       ├── dto
    │       ├── entity
    │       ├── exception
    │       ├── filter
    │       ├── repository
    │       ├── security
    │       ├── service
    │       └── util
    │
    └── resources
        ├── application.yml
        └── static
```

---

# 🚀 Getting Started

## Clone Repository

```bash
git clone https://github.com/Harshjha002/nivora-finance-backend.git

cd nivora-finance-backend
```

---

## Configure Environment Variables

Create a `.env` file.

Example:

```env
POSTGRES_DB=nivora

POSTGRES_USER=postgres

POSTGRES_PASSWORD=password

JWT_SECRET=your_secret_key

MAIL_HOST=smtp.gmail.com

MAIL_PORT=587

MAIL_USERNAME=example@gmail.com

MAIL_PASSWORD=password

CORS_ALLOWED_ORIGIN=http://localhost:3000
```

---

# 🐳 Running with Docker

```bash
docker compose up --build
```

Backend

```
http://localhost:8080
```

Swagger

```
http://localhost:8080/swagger-ui/index.html
```

---

# 💻 Running Locally

Start PostgreSQL and Redis

Then run

```bash
./gradlew bootRun
```

---

# 📖 API Documentation

Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```

---

# ⚙️ CI/CD Pipeline

```
Developer

      │

Git Push

      │

      ▼

GitHub Actions

      │

      ▼

Run Unit Tests

      │

      ▼

Build BootJar

      │

      ▼

Build Docker Image

      │

      ▼

Publish Image to GitHub Container Registry
```

Every push to the `main` branch automatically:

- Runs Unit Tests
- Builds the application
- Creates a Docker image
- Publishes the Docker image to GHCR

---

# 🔮 Future Enhancements

- AWS Deployment
- Integration Testing
- Kafka Notification Service
- Email Notification Service
- Monitoring & Observability
- Payment Gateway Integration
- Microservices Architecture

---

# 📌 Current Project Status

✅ Authentication

✅ Wallet

✅ Transactions

✅ QR Payments

✅ JWT Security

✅ Redis Sessions

✅ Redis Rate Limiting

✅ Docker

✅ Docker Compose

✅ GitHub Actions

✅ GitHub Container Registry

🚧 AWS Deployment (Planned)

🚧 Integration Testing (Planned)

---

# 🤝 Contributing

Contributions, suggestions, and improvements are welcome.

Feel free to fork the repository and submit a Pull Request.

---

# 📄 License

This project is licensed under the MIT License.