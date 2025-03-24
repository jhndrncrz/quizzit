markdown
# Quizzit - Terminal-Based Quiz Reviewer

## Overview

Quizzit is a simple, terminal-based application designed to help users review and test their knowledge through interactive quizzes. Built with Java 23, Maven, and MariaDB, Quizzit offers a user-friendly way to study and reinforce learning.

## Features

-   **Interactive Quizzes:** Engage with quizzes directly in your terminal.
-   **Question Management:**  Create and delete questions organized in separate quizzes, or randomize to create a new one.
-   **MariaDB Integration:** Store quiz data using MariaDB.
-   **User-friendly Interface:** Intuitive controls and clear presentation using a terminal-based UI.

## Tech Stack

-   **Java 23:** The core programming language.
-   **Maven:** Dependency management and project building.
-   **MariaDB:** Database management system.

## Getting Started

### Prerequisites

-   Java 23 JDK installed.
-   Maven installed.
-   MariaDB server running.

### Installation

1.  Clone the repository:
    ```bash
    git clone <repository-url>
    ```
2.  Navigate to the project directory:
    ```bash
    cd quizzit
    ```
3.  Build the project with Maven:
    ```bash
    mvn clean install
    ```
4. Configure MariaDB:
   - Create a .env file containing the following values:
   ```ini
    DB_DATABASE_NAME="<your_database_name>"
    DB_USERNAME="<your_database_username>"
    DB_PASSWORD="<your_database_password>"
   ```
   - Create the necessary database tables in `src/main/sql`, and create seed data if needed.

### Running the Application

1.  Execute the application:
    ```bash
    mvn exec:java
    ```

## Usage

*(To be added: Detailed instructions on how to use the application)*

## Contributing

*(To be added: Guidelines for contributing to the project)*

## License

This project is licensed under the *(To be added: License name)* License - see the [LICENSE.md](LICENSE.md) file for details.
