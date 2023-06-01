# Stock Control API in Java with Spring Boot

<div align="center">
  <img style="width: 250px;" src="https://media.giphy.com/media/l2aQDfA2Ky15oxtYLn/giphy-downsized-large.gif" />
</div>

This is a simple API for stock control implemented in Java using the Spring Boot framework. It provides basic CRUD (Create, Read, Update, Delete) operations for managing products.

## Features

- List all products with pagination
- Add a new product
- Update an existing product
- Delete a product
- Authentication using JWT (JSON Web Tokens)

## Technologies Used

- Spring Boot: A Java framework for building web applications and APIs
- JPA (Java Persistence API): A specification for managing relational data in Java applications
- MySQL: A popular open-source relational database management system
- Spring Security: A powerful and highly customizable authentication and access-control framework
- JWT (JSON Web Tokens): A compact and URL-safe means of representing claims to be transferred between two parties

## Getting Started

To run the Stock Control API, follow these steps:

1. Run a MySQL database container or have a MySQL server running.
2. Add the required database connection information to the `secrets.properties` file (see `secrets.properties.example` for an example).
3. Build and run the application using your preferred method (e.g., IDE, command line).

Please note that you'll need to configure the JWT secret key and other authentication-related properties in the `application.properties` file.

---

-- ***A project for study purposes*** --
