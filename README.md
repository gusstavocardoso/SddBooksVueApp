# SDD Books App

A full-stack web application for managing a catalog of books. Built with a Vue 3 frontend and a Java Spring Boot backend, storing data in SQL Server.

## Architecture

- **Backend**: Java 17 + Spring Boot (Web, Data JPA, Validation) + SQL Server JDBC
- **Frontend**: Vue 3 + Vite + Vue Router + Axios
- **Database**: MS SQL Server (via Docker)
- **E2E Tests**: Playwright with Java JUnit 5
- **Performance Tests**: Gatling with Java API
- **CI/CD**: GitHub Actions

## Requirements

- Java 17
- Node.js 20+
- Maven 3.8+
- Docker & Docker Compose (for SQL Server)

## Setup and Running Locally

### 1. Database

Start the local SQL Server instance using Docker Compose:

```bash
docker-compose up -d
```
Wait about 15-30 seconds for the database to fully initialize and accept connections.

### 2. Backend

Navigate to the `backend` directory and run the Spring Boot app:

```bash
cd backend
./mvnw spring-boot:run
```
The backend API will be available at `http://localhost:8080/api/books`.
Swagger UI documentation is available at `http://localhost:8080/swagger-ui.html`.

*Note: The database is automatically seeded with sample books upon the first startup.*

### 3. Frontend

Navigate to the `frontend` directory, install dependencies, and start the Vite dev server:

```bash
cd frontend
npm install --legacy-peer-deps
npm run dev
```
The frontend application will be available at `http://localhost:5173`.

## Testing

### Backend Unit/Integration Tests
```bash
cd backend
./mvnw clean test
```

### E2E Tests (Playwright)
Ensure both frontend and backend are running, then execute:
```bash
cd e2e-tests
mvn clean test
```

To run the E2E tests in **headed mode** (seeing the browser UI) for debugging, use the `headless` system property:
```bash
mvn clean test -Dheadless=false
```

### Performance Tests (Gatling)
Ensure the backend is running, then execute:
```bash
cd perf-tests
mvn clean gatling:test
```

## API Documentation

The REST API is documented using OpenAPI (Swagger). When the backend is running, visit `http://localhost:8080/swagger-ui.html` to view the interactive documentation.
