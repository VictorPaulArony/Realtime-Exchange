# Currency Conversion Service

A robust Spring Boot service that handles currency conversions with PostgreSQL persistence and comprehensive test coverage.

## 🚀 Features

- Currency conversion with real-time exchange rates
- Transaction history persistence in PostgreSQL
- RESTful API endpoints
- Input validation
- Comprehensive error handling
- Secure endpoints with Spring Security
- Detailed API documentation

## 🛠️ Tech Stack

- Java 17+
- Spring Boot 3.4.5
- Spring Data JDBC
- Spring Security
- PostgreSQL
- Maven
- JUnit 5
- Mockito
- Lombok

## 📋 Prerequisites

- JDK 17 or higher
- Maven 3.6+
- PostgreSQL 12+
- Git

## 🔧 Configuration

### Database Setup

1. Create a PostgreSQL database:
```sql
CREATE DATABASE currency_conversion;
```

2. Configure database connection in `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/currency_conversion
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Schema

The service uses the following table structure:

```sql
CREATE TABLE conversions (
    id SERIAL PRIMARY KEY,
    from_currency VARCHAR(3) NOT NULL,
    to_currency VARCHAR(3) NOT NULL,
    amount DECIMAL(19,4) NOT NULL,
    converted_amount DECIMAL(19,4) NOT NULL,
    rate DECIMAL(19,4) NOT NULL,
    timestamp TIMESTAMP NOT NULL
);
```

## 🚀 Running the Application

1. Clone the repository:
```bash
git clone <repository-url>
cd main-service
```

2. Build the project:
```bash
./mvnw clean install
```

3. Run the application:
```bash
./mvnw spring-boot:run
```

## 🔒 Security

The service implements Basic Authentication:
- `/status` endpoint is publicly accessible
- All other endpoints require authentication
- Default credentials can be configured in `application.properties`

## 🔍 API Documentation

### Status Check
```http
GET /status
Response: {"status": "UP"}
```

### Currency Conversion
```http
POST /convert
Authorization: Basic <credentials>
Content-Type: application/json

Request:
{
    "from": "USD",
    "to": "EUR",
    "amount": 100.00
}

Response:
{
    "from": "USD",
    "to": "EUR",
    "amount": 100.00,
    "rate": 0.85,
    "result": 85.00
}
```

### Database Queries

Example queries for transaction history:

```sql
-- Get all conversions
SELECT * FROM conversions;

-- Get conversions by currency pair
SELECT * FROM conversions 
WHERE from_currency = 'USD' AND to_currency = 'EUR';

-- Get daily conversion volume
SELECT 
    DATE(timestamp) as date,
    COUNT(*) as conversion_count,
    SUM(amount) as total_amount
FROM conversions 
GROUP BY DATE(timestamp)
ORDER BY date DESC;
```

## 🧪 Testing

The service includes comprehensive test coverage:

1. Unit Tests:
```bash
./mvnw test
```

2. Integration Tests:
```bash
./mvnw verify -P integration-test
```

### Test Categories

1. Controller Tests
- Status endpoint accessibility
- Currency conversion validation
- Authentication requirements
- Response format validation

2. Service Tests
- Currency conversion logic
- Rate calculation accuracy
- Error handling

3. Repository Tests
- Data persistence
- Query functionality
- Transaction management

## 📊 Monitoring

The service provides basic monitoring through the `/status` endpoint. For production environments, consider implementing:

- Prometheus metrics
- Grafana dashboards
- ELK stack for logging

## 🔄 Error Handling

The service implements global exception handling for:

- Invalid input validation
- Currency conversion failures
- Database errors
- Authentication/Authorization failures

## 🏗️ Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/main_service/
│   │       ├── config/
│   │       ├── controller/
│   │       ├── dto/
│   │       ├── exception/
│   │       ├── model/
│   │       ├── repository/
│   │       └── service/
│   └── resources/
│       ├── application.properties
│       └── schema.sql
└── test/
    └── java/
        └── com/main_service/
            ├── controller/
            ├── service/
            └── repository/
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.
