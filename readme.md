# Xclone API

A Spring Boot 3.x backend that powers a Twitter/X-style clone, with JWT auth, MySQL persistence, and Redis-backed caching.

**Features**
- JWT authentication and stateless security
- Core social graph: users, follows, tweets, retweets, likes
- Rich metadata: hashtags and mentions
- Redis caching (feed TTL configurable)
- OpenAPI/Swagger UI for interactive docs

**Tech Stack**
- Java 17, Spring Boot 3.x, Maven
- Spring Security + JWT
- MySQL 8 (JPA/Hibernate)
- Redis (Spring Cache)
- Springdoc OpenAPI (Swagger UI)

**Project Structure**
- `src/main/java/com/raghav/xclone`
- Feature modules: `tweet/`, `user/`, `follow/`, `mention/`, `hashtag/`, `Like/`, `Retweet/`
- Cross-cutting: `security/`, `config/`, `common/`
- Config: `src/main/resources/application.yaml`
- Tests: `src/test/java/com/raghav/xclone`

**Quickstart (Docker)**
```bash
docker compose up --build
```
The API starts at `http://localhost:8080`.

**Local Development**
Prerequisites:
- Java 17+
- Maven 3.8+
- MySQL 8+
- Redis 7+

Create the database:
```sql
CREATE DATABASE Xclone;
```

Run:
```bash
mvn spring-boot:run
```
The API starts at `http://localhost:8080`.

**Configuration**
Environment variables are optional; defaults come from `src/main/resources/application.yaml`.
- `SPRING_DATASOURCE_URL` default: `jdbc:mysql://localhost:3306/Xclone?allowPublicKeyRetrieval=true&useSSL=false`
- `SPRING_DATASOURCE_USERNAME` default: `root`
- `SPRING_DATASOURCE_PASSWORD` default: `root`
- `SPRING_REDIS_HOST` default: `localhost`
- `SPRING_REDIS_PORT` default: `6379`

**API Docs**
Swagger UI:
`http://localhost:8080/swagger-ui/index.html`

OpenAPI spec:
`http://localhost:8080/v3/api-docs`

**Health Check**
`http://localhost:8080/api/health`

**Testing**
```bash
mvn test
```

**Notes**
- Schema generation uses `spring.jpa.hibernate.ddl-auto=update` by default. Adjust for production.
- `logging.level.org.springframework.security=DEBUG` is enabled in `application.yaml` for local troubleshooting.
