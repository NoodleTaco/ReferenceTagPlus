# Image Tag Sorting Application

This project is a Spring Boot application that functions as a tag sorting system for images. Users can add images, assign them tags, and then search based on these tags. The application consists of a frontend and a backend module.

## Features

- Add and manage images
- Create and assign tags to images
- Search images based on tags
- GUI Built with Java-FX
- RESTful API for communication between frontend and backend
- Containerized backend and database using Docker

## Architecture

### Backend

- Spring Boot application with REST API
- Uses Spring Data JPA and Hibernate for database interactions
- PostgreSQL database for data storage
- Containerized with Docker alongisde the containerized Postgres DB and volume for persistence, all launched via the docker-compose

### Frontend

- JavaFX integrated with Spring Boot for GUI
- Communicates with the backend through REST API calls

## Prerequisites

- Java 17
- Maven
- Docker and docker-compose

## Setup and Running

### Backend

1. Navigate to the backend directory
2. Build the project: ./mvnw (might be just mvn depending on the wrapper used) clean package
3. Start the backend services: docker-compose up --build
### Frontend

1. Navigate to the frontend directory
2. Run the application: ./mvnw spring-boot:run

## Testing

The project includes Unit Testing for the service layer and Integration tests for entity repositories and controller
Run tests using: ./mvnw tests

## API Endpoints

- `/api/images`: CRUD operations for images
- `/api/tags`: CRUD operations for tags
- `/api/image-tags`: Manage associations between images and tags

For detailed API documentation, please refer to the controller classes in the backend module.
