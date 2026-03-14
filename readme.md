# Xclone

A Spring Boot backend for the Xclone project.

---

## Getting Started (Docker)

### Prerequisites
- Docker Desktop

### Run
```bash
docker compose up --build
```

The app will start at `http://localhost:8080`

---

## Getting Started (Local)

### Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8+

### Configure MySQL
Create a database named `Xclone` and ensure a user is available:
- Username: `root`
- Password: `root`

### Run
```bash
mvn spring-boot:run
```

The app will start at `http://localhost:8080`

---

## API Documentation

Swagger UI is available at:
```
http://localhost:8080/swagger-ui/index.html
```

---

## Tech Stack

- Backend: Spring Boot
- Database: MySQL
- API Docs: Springdoc OpenAPI (Swagger UI)

---
