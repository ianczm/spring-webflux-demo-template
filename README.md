# webflux-onboarding-demo

A starter template based on Spring Reactive Web, pre-configured with libraries typically used in Spring projects.

### Requirements

- Java JDK 23
- Docker and Docker Compose
- Gradle

---

## About

The project already has working REST endpoints that allow creation of Users in the linked database.

It can be used as a playground to test implementation details on a smaller scale.

## Project Features

This project aims to showcase some features of a typical Spring project:

| Feature                                        | Description                                            |
|------------------------------------------------|--------------------------------------------------------|
| Database Migration with Liquibase              | Changelog examples for creating a simple table.        |
| Database Access with R2DBC                     | Example repository and controller for Users.           |
| Dependency Injection with Spring               | Providing beans for configuration.                     |
| Serialization and Deserialization with Jackson | `@RequestBody` and `@Validation` for DTOs.             |
| Bean Validation with Jakarta                   | Adding simple Jakarta annotations to DTOs.             |
| WebFilters for before/after request actions    | Logging HTTP requests.                                 |
| Application Configuration                      | Environment variables and Spring profiles.             |
| Schedulers                                     | Simple cron jobs.                                      |
| Mapping with MapStruct                         | Easy mapping from DTO to Entity.                       |
| Lombok                                         | Simplify boilerplate code like getters and setters.    |
| Caching with Caffeine, and other AOP features  | Leveraging caching and additional AOP functionalities. |

## Resources

- [Spring Initializr link](https://start.spring.io/#!type=gradle-project&language=java&platformVersion=3.4.0&packaging=jar&jvmVersion=23&groupId=com.onboarding&artifactId=demo&name=demo&description=Demo%20project%20for%20Spring%20WebFlux&packageName=com.onboarding.demo&dependencies=webflux,devtools,lombok,docker-compose,configuration-processor,data-r2dbc,liquibase,postgresql,testcontainers,cloud-contract-stub-runner,data-jpa,validation)
