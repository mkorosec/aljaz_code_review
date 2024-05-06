# Design pattern
## Microservices 
We separated main business logic to 3 microservices.

- Actors microservice is responsible for operations of Actors
- Movies microservice is responsible for operations of Movies
- ActorMovie microservice acts as a bridge to connect Actors to Movies 

Communication is handled using client service discovery (Eureka) with Spring Cloud Load Balancer and Spring Cloud Open Feign.

## Gateway
We introduced edge server as single point of entry, where each request is routed to proper microservice and authenticated with OAuth 2.0 and further authorized with predefined roles.

## Global Filter
REST request counter is implemented with GlobalFilter interface which is executed for every route defined in the API Gateway.

# Initialization of DB

## Liquibase 
Each microservice has defined changeSet for initial database creation and test data with structure:

- classpath:/db.changelog/changes/*
- classpath:/db.changelog/db.changelog-master.yaml

# How to run
## Build Docker Images
For building Docker images we used Google Jib maven plugin.

Example: mvn compile jib:dockerBuild

## Docker compose
After building docker images start application under /deployment/default with command: docker compose up.

# Keycloak setup
## Create client with roles
Access Keycloak admin with: http://localhost:7080/admin/ and credentials admin/admin

1. Create new client with enabled Service accounts roles.
2. Create Realm roles:
- ACTORS for GET operations on actors microservice
- MOVIES for GET operations on movies microservice
- ACTOR-MOVIE for GET operations on actor-movie microservice
- ADMIN for PUT/POST/DELETE operations on all microservices
3. Assign all roles to clients

# REST API settings using Oauth 2.0
## Client authentication settings
1. Grant type: Client Credentials
2. Access token url should be retrieved from: http://localhost:7080/realms/master/.well-known/openid-configuration
- "token_endpoint": http://localhost:7080/realms/master/protocol/openid-connect/token
3. After fetching token you should retrieve valid JWT token
   
