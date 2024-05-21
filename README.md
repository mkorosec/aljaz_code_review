# Design pattern

## Microservices

We created 3 main microservices as loose coupled components with separated business/persistence logic:
- actors (To handle actors business logic)
- movies (To handle movies business logic)
- actor-movie (To act as bridge between actors and movies)

Communication is done through REST APIs using client Spring Cloud Open Feign with Eureka service discovery and Spring Cloud LoadBalancer.


## Gateway

We introduced edge server as single point of entry, where each request is routed to proper microservice and
authenticated with OAuth 2.0 and further authorized with predefined roles.

Using Spring Cloud Eureka as client side service discovery gateway can route to proper microservice using Spring Cloud LoadBalancer.

## Global Filter

REST request counter is implemented with GlobalFilter interface which is executed for every route defined in the API
Gateway.

## Handling higher load

With docker-compose you could set replicas for deployment to create more service containers. With Eureka as service discovery and Ribbon as client side load balancer .

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
   
