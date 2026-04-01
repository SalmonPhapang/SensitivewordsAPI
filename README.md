# Sensitive Words API

A Spring Boot-based content moderation service that provides APIs to filter sensitive words from text and manage the sensitive words database.

## Getting Started

### Prerequisites

- **Java 17** or higher
- **Maven 3.6+**
- **MySQL 8.0+**

### Database Setup

1. Create a MySQL database named `content_moderation`:
   CREATE DATABASE content_moderation;

2. Update the credentials in application.properties if they differ from:
   - **URL**: `jdbc:mysql://localhost:3306/content_moderation`
   - **Username**: `****`
   - **Password**: `****`

Flyway will automatically handle table creation and migrations upon startup.

### Running the Application

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd SensitivewordsAPI
   ```
2. **Build the project**:
   ```bash
   mvn clean install
   ```
3. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

The API will be available at `http://localhost:8080`.

---

## API Documentation

The project uses OpenAPI (Swagger) for documentation. Once the application is running, you can access the interactive UI at:

http://localhost:8080/swagger-ui/index.html

---

## Features

- **Sensitive Word Filtering**: Check and replace sensitive words in chat messages with stars (`***`).
- **CRUD Management**: Full API for adding, updating, and deleting sensitive words.
- **JWT Authentication**: Secure endpoints using JSON Web Tokens.

---

## Security

To access most endpoints, you must:
1. Register a user via `/api/v1/auth/register`.
2. Authenticate via `/api/v1/auth/authenticate` to receive a JWT token.
3. Include the token in the `Authorization` header as: `Bearer <your_token>`.

---

## Testing

Run the full test suite using:
```bash
mvn test
```
The project uses an in-memory **H2 database** for testing to ensure isolation.

---

## Tech Stack

- **Framework**: Spring Boot 3.2.4
- **Database**: MySQL (Production), H2 (Test)
- **Security**: Spring Security & JWT
- **Persistence**: Spring Data JPA & Flyway
- **Documentation**: SpringDoc OpenAPI
- **Utilities**: Lombok
