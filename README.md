# webflux-demo-template

A starter template based on Spring Reactive Web, pre-configured with libraries typically used in Spring projects.

### Requirements

- Java JDK 23
- Docker and Docker Compose
- Gradle

### Installation and Running

Note: If you have an IDE like IDEA IntelliJ, you may simply use the Gradle Sync and Application Run configuration.

- First, build your project.

    ```shell
    ./gradlew build
    ```
  
    Ensure that your project is configured as follows:
    - Uses JDK 23
    - Has annotation processing enabled
    - Uses the Spring Profile `local` when running the application locally


- Run the tests:

    ```shell
    ./gradlew test
    ```


- Run the following command to start the database:

    ```shell
    docker compose up
    ```


- Run the application:

    ```shell
    ./gradlew bootRun --args='--spring.profiles.active=local'
    ```


## About

The project already has working REST endpoints that allow creation of Users in the linked database.

It can be used as a playground to test implementation details on a smaller scale.

## Project Features

This project aims to showcase some features of a typical Spring project:

| Feature                                         | Description                                            |
|-------------------------------------------------|--------------------------------------------------------|
| + Database Migration with Liquibase             | Changelog examples for creating a simple table.        |
| + Database Access with R2DBC                    | Example repository and controller for Users.           |
| + Dependency Injection with Spring              | Providing beans for configuration.                     |
| Serialization and Deserialization with Jackson  | `@RequestBody` and `@Validation` for DTOs.             |
| + Bean Validation with Jakarta                  | Adding simple Jakarta annotations to DTOs.             |
| * WebFilters for before/after request actions   | Logging HTTP requests.                                 |
| Application Configuration                       | Environment variables and Spring profiles.             |
| * Schedulers                                    | Simple cron jobs.                                      |
| * Mapping with MapStruct                        | Easy mapping from DTO to Entity.                       |
| + Lombok                                        | Simplify boilerplate code like getters and setters.    |
| * Caching with Caffeine, and other AOP features | Leveraging caching and additional AOP functionalities. |
| Global Error Handling                           | Map exceptions to JSON with `@ExceptionHandler`        |
| * Unit Tests                                    | Testing with Reactor, Mockito and AssertJ              |
| * Integration Tests                             | Stubbing HTTP with Wiremock                            |

## Resources

- [Spring Initializr link](https://start.spring.io/#!type=gradle-project&language=java&platformVersion=3.4.0&packaging=jar&jvmVersion=23&groupId=demo.template&artifactId=webflux&name=webflux-demo-template&description=Demo%20project%20for%20Spring%20WebFlux&packageName=demo.template.webflux&dependencies=webflux,devtools,lombok,docker-compose,configuration-processor,data-r2dbc,liquibase,postgresql,testcontainers,cloud-contract-stub-runner,data-jpa,validation)
