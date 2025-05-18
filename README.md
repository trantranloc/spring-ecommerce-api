# Spring E-Commerce API

## Overview

A robust Spring Boot-based RESTful API for an e-commerce application, providing comprehensive backend services for managing products, users, orders, and payment transactions.

## 🚀 Features

- User authentication and authorization
- Product management (CRUD operations)
- Category management
- Shopping cart functionality
- Order processing and tracking
- Payment integration
- Role-based access control (RBAC)

## 🛠 Technology Stack

- **Language**: Java 21
- **Framework**: Spring Boot 3.x
- **ORM**: Spring Data JPA
- **Security**: Spring Security
- **Authentication**: JWT (JSON Web Tokens)
- **Database**: PostgreSQL/MySQL
- **Build Tool**: Maven
- **Testing**: JUnit 5, Mockito

## 📋 Prerequisites

Before you begin, ensure you have met the following requirements:

- Java Development Kit (JDK) 17+
- Maven 3.8+
- PostgreSQL or MySQL
- Postman (optional, for API testing)

## 🔧 Installation

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/spring-ecommerce-api.git
cd spring-ecommerce-api
```

### 2. Database Configuration

1. Create a database in PostgreSQL or MySQL
2. Update `src/main/resources/application.properties`:

```properties
# PostgreSQL Example
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce_db
spring.datasource.username=your_username
spring.datasource.password=your_password

# MySQL Example
# spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
# spring.datasource.username=your_username
# spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
```

### 3. Build and Run

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

## 🔐 Authentication

The API uses JWT (JSON Web Tokens) for authentication:

- Register a new user
- Login to receive an access token
- Include the token in the Authorization header for protected endpoints

## 📍 API Endpoints

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `POST /api/auth/refresh` - Refresh JWT token

### Product Management
- `GET /api/products` - List all products
- `GET /api/products/{id}` - Get product details
- `POST /api/products` - Create a new product (Admin)
- `PUT /api/products/{id}` - Update product (Admin)
- `DELETE /api/products/{id}` - Delete product (Admin)

### Order Management
- `POST /api/orders` - Create a new order
- `GET /api/orders` - List user's orders
- `GET /api/orders/{id}` - Get order details

## 🧪 Testing

### Run Unit Tests

```bash
mvn test
```

### Run Integration Tests

```bash
mvn verify
```

## 📄 Documentation

Swagger/OpenAPI documentation is available at:
- `http://localhost:8080/swagger-ui.html`
- `http://localhost:8080/v3/api-docs`

## 🐳 Docker Deployment (Optional)

```bash
# Build Docker image
docker build -t spring-ecommerce-api .

# Run Docker container
docker run -p 8080:8080 spring-ecommerce-api
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Open a Pull Request

## 📝 License

Distributed under the MIT License. See `LICENSE` for more information.

## 📞 Contact

- Loc Tran Tran
- Email: Tran26122003@gmail.com
- Project Link:https://github.com/trantranloc/spring-ecommerce-api.git.

## 🙏 Acknowledgements

- Spring Boot
- Spring Security
- Hibernate
- Maven
- JWT

---

**Happy Coding!** 🚀
