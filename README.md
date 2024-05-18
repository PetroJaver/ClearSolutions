# ClearSolutions Practical Test Solution

## Description:

This project completes the task assignment from ClearSolutions, meeting all requirements. You can view the task details here.

The project has two profiles:

* default - Quick start without JPA and Docker.
* jpa - Includes JPA and requires Docker Engine installation and running. Be careful when choosing the application profile.

The project also includes Swagger documentation, which can be accessed here after successfully running the application.

Additionally, the project uses Flyway for database migration. Although I'm not entirely sure why I decided to include Flyway, it is available in this project.

## How to Build and Run

1. Run the following command to build the project:
   `./gradlew build`
2. Depending on the profile you want, choose one of these commands to run the application:
   * **default** - `./gradlew bootRun`
   * **jpa** - `./gradlew bootRun --args='--spring.profiles.active=jpa'`