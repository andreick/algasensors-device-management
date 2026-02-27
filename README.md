# AlgaSensors Device Management

Device Management is a Spring Boot microservice in the AlgaSensors study project. It focuses on device registration and basic device metadata.

## Responsibilities

- Manage device lifecycle (register, update, deactivate).
- Store and expose device metadata used by other services.

## Tech Stack

- Java 21+
- Spring Boot
- Gradle (wrapper included)

## Run Locally

1. Start shared infrastructure from the workspace root:

	docker compose up -d

2. Start this service:

	./gradlew bootRun

## Configuration

- Defaults are in src/main/resources/application.yml.
- Override via environment variables or an external application.yml.

## Related Services

- temperature-monitoring: reads device data to ingest temperature events.
- temperature-processing: consumes temperature events for processing.
