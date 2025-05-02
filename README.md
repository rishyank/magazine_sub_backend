# Magazine Subscription API

This project is a Spring Boot-based backend service that provides APIs for managing magazines, subscription plans, and user subscriptions. It is designed to work with a MySQL database and is containerized using Docker.

---

## Tech Stack

- Java 17+
- Spring Boot
- Spring Security (JWT)
- MySQL
- Docker, Docker Compose
- Postman (for API testing)

---

## Features

- **User Registration and Login** (JWT-based authentication)
- **Magazine Management** (CRUD)
- **Subscription Plans**
- **Magazine Subscriptions** (Subscribe, Change Plan, Cancel)
- **Search Magazines** by name and ID
- **View User Subscriptions**

---

## Getting Started

### Prerequisites

- Docker & Docker Compose installed
- Java 17+
- MySQL (optional, if not using Dockerized DB)

---

## Running with Docker Compose

The project includes Docker Compose configuration to run:

- Spring Boot Application (port `8950`)
- MySQL Database (port `3306`)

### Steps

1. Clone the repo:

   ```bash
   git clone https://your-repo-url.git
   cd your-repo
   ```

2. Start containers:

   ```bash
   docker-compose up --build
   ```

---

## Application Configuration
Will be overidered if ran from Docker compose
### `application.properties`

```properties
spring.application.name=magazine
server.port=8950

spring.datasource.url=jdbc:mysql://localhost:3306/magazine_db?createDatabaseIfNotExist=true&useSSL=false
spring.datasource.username=root
spring.datasource.password=root

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

security.jwt.secret-key=K8ruQnpr+et9JAXadx0qmxQV45xqCw+R7sYzcvCReyc=
security.jwt.expiration-time=7200000
```

---

## CORS Configuration

The Spring Boot app must allow requests from the frontend (`localhost:8085`). Add the following bean in a configuration class and change url to point frontend (e.g., `WebConfig.java`):

```java
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(List.of("http://localhost:8085")); // Frontend origin
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    config.setAllowedHeaders(List.of("*"));
    config.setAllowCredentials(true); // Allow cookies and authorization headers

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
}
```

---

## API Collection

all endpoints:

- `auth/login`
- `auth/register`
- `users/me`
- `users/find-user`
- `api/plans`, `api/plans/{id}`
- `api/magazines`, `api/magazines/{id}`, `api/magazines?name=`
- `api/subscriptions`, `api/subscriptions/user/{id}`, `api/subscriptions/change-plan`, `api/subscriptions/{id}`

Tokens are passed as Bearer tokens in headers.

---

## Example Request

```http
POST /auth/login HTTP/1.1
Content-Type: application/json

{
  "username": "demo_user",
  "password": "$Test1234"
}
```

```http
GET /api/magazines HTTP/1.1
Authorization: Bearer <your-jwt-token>
```

---

## License

This project is licensed under MIT License. Feel free to modify and reuse.

---
