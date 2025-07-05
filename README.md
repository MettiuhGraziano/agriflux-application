# agriflux-application

## Introduction

Agriflux is a comprehensive application designed for agricultural data management and visualization. It features a web-based dashboard for monitoring various agricultural metrics and a batch processing system for data generation and manipulation. The application is modular, allowing for independent development and deployment of its core components.

## Modules

This project is composed of the following modules:

-   **`web`**: This module contains the main web application, "agriflux-web". It provides a dashboard for users to visualize and interact with agricultural data. It uses Spring Boot with Thymeleaf for server-side rendering and ChartJS for data visualization.
-   **`batch`**: The "agriflux-batch" module is responsible for batch processing tasks. This includes generating random data to simulate agricultural activities and feeding this data to the dashboard. It exposes REST services and utilizes Spring Batch and Spring Data JPA.
-   **`shared`**: This module, "agriflux-shared", contains common classes, utilities, and data models used by both the `web` and `batch` modules, promoting code reusability and consistency across the application.

## Prerequisites

Before you begin, ensure you have met the following requirements:

-   **Java Development Kit (JDK):** Version 21 or higher.
-   **Apache Maven:** Version 3.6.x or higher (for building the project).
-   An internet connection to download dependencies.

## Getting Started

To get a local copy up and running, follow these simple steps:

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/agriflux-application.git
    # Replace with the actual repository URL
    ```
2.  **Navigate to the project directory:**
    ```bash
    cd agriflux-application
    ```
3.  **Build the project:**
    This command will compile all modules and download necessary dependencies.
    ```bash
    mvn clean install
    ```

## Running the Application

Each module (web and batch) can be run independently.

### Web Module (agriflux-web)

The web module runs the main dashboard application.

1.  **Navigate to the web module directory:**
    ```bash
    cd web
    ```
2.  **Run the application using Maven:**
    ```bash
    mvn spring-boot:run
    ```
    Alternatively, after building the project with `mvn clean install` from the root, you can run the packaged JAR:
    ```bash
    java -jar target/agriflux-web-0.0.1-SNAPSHOT.jar
    ```
The web application will typically be available at `http://localhost:8080`.
The H2 database console for the web module can be accessed at `http://localhost:8080/h2-console` (DataSource URL: `jdbc:h2:mem:webDb`, User: `web`, Password: `password`).

### Batch Module (agriflux-batch)

The batch module runs background processes and exposes REST services for data generation.

1.  **Navigate to the batch module directory:**
    ```bash
    cd batch
    ```
2.  **Run the application using Maven:**
    ```bash
    mvn spring-boot:run
    ```
    Alternatively, after building the project with `mvn clean install` from the root, you can run the packaged JAR:
    ```bash
    java -jar target/agriflux-batch-0.0.1-SNAPSHOT.jar
    ```
The batch application will run on port `8081` (configured in `batch/src/main/resources/application.properties`) to avoid conflicts with the web module.
The H2 database console for the batch module can be accessed at `http://localhost:8081/h2-console` (DataSource URL: `jdbc:h2:mem:batchDb`, User: `batch`, Password: `password`).
Its REST endpoints (e.g., for triggering data generation) will be available under `http://localhost:8081/`.

## Running Tests

To run the automated tests for all modules, navigate to the root project directory (`agriflux-application`) and use the following Maven command:

```bash
mvn test
```

This will execute unit and integration tests in the `web`, `batch`, and `shared` modules.

## Built With

-   [Spring Boot](https://spring.io/projects/spring-boot) (Versions: `web` - 3.5.3, `batch` - 3.4.7) - Core application framework.
-   [Spring Data JPA](https://spring.io/projects/spring-data-jpa) - For database interaction.
-   [Spring Batch](https://spring.io/projects/spring-batch) - For batch processing in the `batch` module.
-   [Thymeleaf](https://www.thymeleaf.org/) - Server-side Java template engine for the `web` module.
-   [ChartJS](https://www.chartjs.org/) - For rendering charts in the web dashboard.
-   [SpringDoc OpenAPI](https://springdoc.org/) - For API documentation.
-   [H2 Database](https://www.h2database.com/) - In-memory database for development and testing.
-   [Maven](https://maven.apache.org/) - Dependency Management and build tool.
-   [Jackson](https://github.com/FasterXML/jackson) - For JSON processing (e.g., JSR310 for date/time).
-   [ModelMapper](http://modelmapper.org/) - For object mapping in the `batch` module.

## Contributing

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1.  Fork the Project
2.  Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3.  Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4.  Push to the Branch (`git push origin feature/AmazingFeature`)
5.  Open a Pull Request

## License

This project is licensed under the MIT License. See the `LICENSE.txt` file for more details.

*(Note: A `LICENSE.txt` file with the MIT License text should be added to the repository. If a different license is used, please update this section and the license file accordingly.)*

<!-- Optional: Contact section -->
<!-- ## Contact -->

<!-- Your Name - @your_twitter - email@example.com -->

<!-- Project Link: [https://github.com/your_username/repo_name](https://github.com/your_username/repo_name) -->
