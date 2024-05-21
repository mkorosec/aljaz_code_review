build: build-actors build-actors-movies build-eurekaserver build-gateway build-movies ## Build Docker images we used Google Jib maven plugin.

# Define a target for each module
build-actors:
	cd actors && mvn compile jib:dockerBuild

build-actors-movies:
	cd actors-movies && mvn compile jib:dockerBuild

build-eurekaserver:
	cd eurekaserver && mvn compile jib:dockerBuild

build-gateway:
	cd gateway && mvn compile jib:dockerBuild

build-movies:
	cd movies && mvn compile jib:dockerBuild

run: ## Start the application using Docker Compose.
	docker compose -f deployment/default/docker-compose.yml up

start: run

clean:
	cd actors && mvn clean
	cd actors-movies && mvn clean
	cd eurekaserver && mvn clean
	cd gateway && mvn clean
	cd movies && mvn clean

help: ## Show this help
	@echo "Usage: make [target]"
	@echo
	@echo "Targets:"
	@awk 'BEGIN {FS = ": .*## "}; /^[a-zA-Z_-]+: .*## / {printf "  %-15s %s\n", $$1, $$2}' $(MAKEFILE_LIST)
