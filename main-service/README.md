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

## 🔧 Setup and Configuration

### 1. Database Setup

Choose one of the following methods to set up the database:

#### Method A: Using System PostgreSQL (requires sudo)

1. Start PostgreSQL service:
```bash
sudo service postgresql start
# or
sudo systemctl start postgresql
```

2. Create the database and user:
```sql
-- Connect to PostgreSQL as postgres user
sudo -u postgres psql
```

#### Method B: Local PostgreSQL Instance (no sudo required)

1. Initialize a new database cluster:
```bash
# Create a directory for the database
mkdir -p ~/pgdata

# Initialize the database cluster
initdb -D ~/pgdata

# Start PostgreSQL server
pg_ctl -D ~/pgdata -l ~/pgdata/pg_logfile start

# Create the database
creatdb mydb

# Connect to the database
psql -d mydb
```

3. Set up the database schema (run these commands in psql):
```sql
-- Create user if using Method A (skip for Method B)
CREATE USER username WITH PASSWORD 'password';
GRANT ALL PRIVILEGES ON DATABASE mydb TO username;

-- Connect to the database (if not already connected)
\c mydb

-- Create conversions table
CREATE TABLE conversions (
    id SERIAL PRIMARY KEY,
    from_currency VARCHAR(3) NOT NULL,
    to_currency VARCHAR(3) NOT NULL,
    amount DECIMAL(19,4) NOT NULL,
    rate DECIMAL(19,6) NOT NULL,
    result DECIMAL(19,4) NOT NULL,
    timestamp TIMESTAMP NOT NULL
);

-- Grant table permissions
GRANT ALL PRIVILEGES ON TABLE conversions TO username;
GRANT USAGE, SELECT ON SEQUENCE conversions_id_seq TO username;
```

4. Configure database connection in `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
spring.datasource.username=username
spring.datasource.password=password
```

5. Verify database connection:
```bash
# For Method A
psql -h localhost -U username -d mydb

# For Method B
psql -d mydb

# List tables
\dt
```

# Describe conversions table
```sql
\d conversions
```


### 2. Application Setup


## 🚀 Running the Application

1. Clone the repository:
```bash
git clone https://github.com/VictorPaulArony/Realtime-Exchange
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

### Integration Tests
Alternatively, you can run integration tests using the provided test script. First, make the script executable:
```bash
chmod +x test-api.sh
```

Then run the tests:
```bash
./test-api.sh
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
