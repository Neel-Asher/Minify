# Minify - URL Shortener API

A Spring Boot RESTful API that converts long URLs into short, shareable links with analytics and redirection support.

---

## Features

- Create short URLs from long URLs
- Retrieve original URL using short code
- Update existing URLs
- Delete URLs
- Redirect to original URL using short link
- Track access count (analytics)
- Global exception handling
- Input validation
- Swagger API documentation
- MySQL persistence

---

## Tech Stack

- Java 21
- Spring Boot 3.3.2
- Spring Web
- Spring Data JPA
- MySQL
- Maven
- Swagger (OpenAPI)

---

## API Endpoints

### Create Short URL
POST /shorten

Request:
{
  "url": "https://example.com"
}

Response:
{
  "id": 1,
  "url": "https://example.com",
  "shortCode": "abc123",
  "createdAt": "2026-05-25T10:00:00",
  "updatedAt": "2026-05-25T10:00:00",
  "accessCount": 0
}

---

### Get URL
GET /shorten/{code}

Response:
{
  "id": 1,
  "url": "https://example.com",
  "shortCode": "abc123",
  "createdAt": "2026-05-25T10:00:00",
  "updatedAt": "2026-05-25T10:00:00",
  "accessCount": 1
}

---

### Update URL
PUT /shorten/{code}

Request:
{
  "url": "https://updated.com"
}

Response:
{
  "id": 1,
  "url": "https://updated.com",
  "shortCode": "abc123",
  "createdAt": "2026-05-25T10:00:00",
  "updatedAt": "2026-05-25T10:30:00",
  "accessCount": 1
}

---

### Delete URL
DELETE /shorten/{code}

Response:
204 No Content

---

### Redirect URL
GET /shorten/r/{code}

Response:
HTTP 302 Redirect
Location: https://example.com

---

### Get Stats
GET /shorten/{code}/stats

Response:
{
  "id": 1,
  "url": "https://example.com",
  "shortCode": "abc123",
  "createdAt": "2026-05-25T10:00:00",
  "updatedAt": "2026-05-25T10:00:00",
  "accessCount": 5
}

---

## Architecture

Client → Controller → Service → Repository → MySQL

- Controller handles HTTP requests
- Service contains business logic
- Repository interacts with database

---

## Swagger UI

http://localhost:8080/swagger-ui/index.html

---

## How to Run

1. Clone repository
2. Configure MySQL in application.properties
3. Run Spring Boot application
4. Open Swagger UI in browser

---

## Future Improvements

- URL expiration system
- Rate limiting
- Redis caching
- Frontend integration (React)