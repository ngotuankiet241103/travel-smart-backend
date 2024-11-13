# Travel smart app
## Technologies and frameworks
- Java 17
- Spring boot 3.3
- Kafka

## Run the application
- Run file docker compose:
``docker-compose up -d``
- Create database like file initDB.sql in project
- Need to set up connect mysql with ``username: root`` and ``password: 1234``
- Start all service
### Swagger service
- identity-service: ``http:localhost:8080/swagger-ui/index.html``
- profile-service: ``http:localhost:8081/swagger-ui/index.html``
- blog-service: ``http:localhost:8082/swagger-ui/index.html``
- location-service: ``http:localhost:8084/swagger-ui/index.html``
- trip-service: ``http:localhost:8085/swagger-ui/index.html``
- review-service ``http:localhost:8086/swagger-ui/index.html``