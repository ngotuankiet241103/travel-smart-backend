# Travel smart app
## Technologies and frameworks
- Java 17
- Spring boot 3.3
- Kafka

## Run the application
- If you don't have database mysql local you need to uncomment mysql service in file docker compose
- Run file docker compose 
``docker-compose up -d``
#### Just for database local
- Create database like file initDB.sql in project
- Need to set up connect mysql with ``username: root`` and ``password: 1234``
- Start all service
- The notification service won't active if don't have api key from brevo 
- So you need to have account brevo and paste it to file application.yaml in notification-service where ```
-  ${BREVO-API-KEY:YOUR_API_KEY}```
### Swagger service
- identity-service: ``http:localhost:8080/identity/swagger-ui/index.html``
- profile-service: ``http:localhost:8081/profile/swagger-ui/index.html``
- blog-service: ``http:localhost:8082/blog/swagger-ui/index.html``
- location-service: ``http:localhost:8084/location/swagger-ui/index.html``
- trip-service: ``http:localhost:8085/trip/swagger-ui/index.html``
- review-service ``http:localhost:8086/review/swagger-ui/index.html``
--------------------------------------------------------------------
##  Run all app With docker compose 
- You need to build all services 
- Uncomment all services in file docker-compose.yaml
- Run file docker compose
  ``docker-compose up -d``
--------------------------------------------------------------------
## K8S local
- First you need to download k8s and minikube for execute command
- After install k8s and minikube, start it: ```minikube start```
- Run command to init database: ```kubectl apply -f db.yaml```
- After service database is initiated and run nex command: ```kubectl apply -f app.yaml```