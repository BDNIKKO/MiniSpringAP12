Here's the merged and final version of your README:

---

# MiniSpringAP12 - Spring Boot Application with HTTPS, Caching, CORS, and Redis Integration

## Table of Contents
- [Project Overview](#project-overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup Instructions](#setup-instructions)
- [Running the Application](#running-the-application)
- [Endpoints](#endpoints)
- [Caching with Redis](#caching-with-redis)
- [CORS Configuration](#cors-configuration)
- [HTTPS Configuration](#https-configuration)
- [Input Validation and Sanitization](#input-validation-and-sanitization)
- [Project Structure](#project-structure)
- [Nginx Configuration (Optional)](#nginx-configuration-optional)

## Project Overview
MiniSpringAP12 is a Spring Boot-based application that demonstrates the use of secure HTTPS connections, Redis caching, input validation and sanitization, and CORS support. The application is designed with security, performance, and cross-origin support in mind.

## Features
- **HTTPS**: Configured to run on port 8443 with a self-signed SSL certificate for secure communication.
- **CORS Support**: Configured to allow requests from specific origins.
- **Input Validation**: Includes validation for user inputs to ensure correct data formatting.
- **Caching**: Redis caching is implemented to improve performance by caching user data.
- **Sanitization**: User inputs are sanitized to prevent XSS attacks.
- **Security**: Basic authentication is implemented to protect the API endpoints.

## Technologies Used
- Spring Boot 3.x
- Spring Security
- Spring Cache (with Redis)
- Spring Validation (for input validation)
- OWASP Java Encoder (for input sanitization)
- Redis (for caching)
- SSL for HTTPS
- Nginx (optional, for reverse proxy)

## Setup Instructions

1. **Clone the repository:**
   ```bash
   git clone <your-repository-url>
   cd MiniSpringAP12
   ```

2. **Pre-requisites:**
   - JDK 11+
   - Maven
   - Redis server installed locally (or available remotely)
   - A Redis server running locally on port 6379

3. **Build the project:**
   ```bash
   mvn clean install
   ```

4. **Generate SSL Certificate:**
   If you don't already have the self-signed SSL certificate, generate it using the following command:
   ```bash
   keytool -genkeypair -alias springboot -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore src/main/resources/keystore.p12 -validity 3650
   ```
   Follow the prompts to generate the certificate.

5. **Application Properties:**
   The application is already configured with the following settings in `application.properties`:

   ```properties
   spring.application.name=MiniSpringAP12
   server.port=8443

   # HTTPS Configuration
   server.ssl.key-store=classpath:keystore.p12
   server.ssl.key-store-password=password
   server.ssl.key-store-type=PKCS12
   server.ssl.key-alias=springboot

   # Redis Configuration
   spring.data.redis.host=localhost
   spring.data.redis.port=6379
   spring.cache.redis.time-to-live=600000

   # Spring Security user credentials
   spring.security.user.name=admin
   spring.security.user.password=admin123
   ```

6. **Running Redis Server:**
   Make sure your Redis server is running locally on the default port 6379. Use this command to start the Redis server:
   ```bash
   redis-server
   ```

## Running the Application
To start the application, run:
```bash
mvn spring-boot:run
```
The application will start on `https://localhost:8443`.

## Endpoints
| HTTP Method  | Endpoint                   | Description                          |
|--------------|----------------------------|--------------------------------------|
| GET          | `/home`                    | Displays a welcome message.          |
| POST         | `/register`                | Registers a user with sanitized input. |
| GET          | `/getUser/{username}`       | Retrieves user data from the cache.  |
| DELETE       | `/evictUser/{username}`     | Evicts a user from the cache.        |

### Example cURL Requests:

- **Register a User:**
  ```bash
  curl -k -X POST https://localhost:8443/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser", "password":"testpassword"}' --user admin:admin123
  ```

- **Get User Data:**
  ```bash
  curl -k -X GET https://localhost:8443/getUser/testuser --user admin:admin123
  ```

- **Evict User from Cache:**
  ```bash
  curl -k -X DELETE https://localhost:8443/evictUser/testuser --user admin:admin123
  ```

## Caching with Redis
The application uses Redis as a caching mechanism. The following cache-related methods are implemented:

- **@Cacheable**: The `getUser(String username)` method caches the user data when retrieved.
- **@CachePut**: The `updateUser(String username, String newUserData)` method updates the cache when a new user is registered.
- **@CacheEvict**: The `evictUserFromCache(String username)` method evicts the user data from the cache.

## CORS Configuration
CORS is enabled to allow cross-origin requests from `http://localhost:8080`. This configuration is located in `CorsConfig.java` and is handled through Spring Security.

## HTTPS Configuration
The application is configured to use HTTPS on port 8443. A self-signed SSL certificate is provided in the `keystore.p12` file. The application uses this certificate to encrypt communications. You can replace this certificate with one from a trusted certificate authority for production use.

## Input Validation and Sanitization
- **Validation**: User inputs are validated using the `@NotEmpty` and `@Size` annotations in `UserDTO` to ensure the username and password meet the required conditions.
- **Sanitization**: Inputs are sanitized using the OWASP Java Encoder library to prevent XSS attacks.

## Project Structure
```bash
src
├── main
│   ├── java
│   │   └── org.example.minispringap12
│   │       ├── config       # CORS and Security configuration classes
│   │       ├── controllers  # REST controllers (HomeController)
│   │       ├── dto          # Data Transfer Objects (UserDTO)
│   │       ├── services     # Services for user operations (UserService)
│   └── resources
│       ├── application.properties  # Configuration properties
│       └── keystore.p12            # SSL keystore
└── test
    └── java
        └── org.example.minispringap12  # Unit and integration tests
```

## Nginx Configuration (Optional)
This project can optionally use Nginx as a reverse proxy for managing HTTP/HTTPS traffic and SSL termination.

### Steps to Configure Nginx:
1. **Install Nginx**:
   ```bash
   sudo apt update
   sudo apt install nginx
   ```

2. **Generate SSL Certificate**:
   Follow the steps mentioned under the [HTTPS Configuration](#https-configuration) section to generate a self-signed SSL certificate.


3. **Configure Nginx**:
   Edit the Nginx configuration file to point to the Spring Boot application running on port 8443.
   ```bash
   sudo nano /etc/nginx/sites-available/default
   ```
   Add the following configuration:
   ```nginx
   server {
       listen 80;
       server_name localhost;

       location / {
           proxy_pass http://localhost:8443;
           proxy_set_header Host $host;
           proxy_set_header X-Real-IP $remote_addr;
           proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
           proxy_set_header X-Forwarded-Proto $scheme;
       }
   }
   ```

4. **Test and Restart Nginx**:
   ```bash
   sudo nginx -t
   sudo systemctl restart nginx
   ```

5. **Verify the Setup**:
   Access the application via the browser at `http://localhost` or the domain you've set.

---

