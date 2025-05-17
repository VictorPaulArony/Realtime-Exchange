# Realtime-Exchange
A simple Spring Boot application showcasing service-to-service communication and third-party API integration. The system consists of two microservices:
- Currency Conversion Service: Converts currencies using real-time exchange rates from a free external API.
- Logging Service: Records conversion history in a PostgreSQL database.

## Prerequisites
- Docker and Docker Compose installed on your system
- Java 17 or higher (for local development)

## Running the Services

### 1. Database Setup
First, we'll start PostgreSQL and initialize the database:

```bash
# Start PostgreSQL container
docker compose up postgres -d

# Wait for PostgreSQL to be ready (about 10-15 seconds)
docker compose logs -f postgres
```

The database will be automatically initialized with:
- Database name: conversion_db
- User: postgres
- Password: postgres
- Table: conversions (created automatically by the main service)

### 2. Start Rate Service
Once PostgreSQL is running, start the Rate Service:

```bash
docker compose up rate-service -d
```

Verify it's running:
```bash
docker compose logs -f rate-service
```

### 3. Start Main Service
Finally, start the Main Service which will handle the currency conversions:

```bash
docker compose up main-service -d
```

Verify all services are running:
```bash
docker compose ps
```

### View Service Logs
To view logs from all services:
```bash
docker compose logs -f
```

To view logs from a specific service:
```bash
docker compose logs -f [service-name]  # e.g., postgres, rate-service, or main-service
```

### Stopping the Services
To stop all services:
```bash
docker compose down
```

To stop and remove all data (including database volume):
```bash
docker compose down -v
```

## Service URLs and Credentials
1. PostgreSQL Database:
   - Host: localhost:5432
   - Database: conversion_db
   - Username: postgres
   - Password: postgres

2. Rate Service:
   - Base URL: http://localhost:8081
   - Endpoints:
     - Get Exchange Rate: GET `/rate?from={FROM_CURRENCY}&to={TO_CURRENCY}`
     - Check Status: GET `/status`
   - Example:
     ```bash
     # Get exchange rate from USD to EUR
     curl "http://localhost:8081/rate?from=USD&to=EUR"
     ```

3. Main Service:
   - Base URL: http://localhost:8080
   - Endpoints:
     - Convert Currency: POST `/convert`
     - Check Status: GET `/status`
   - Example:
     ```bash
     # Convert 100 USD to EUR
     curl -X POST http://localhost:8080/convert \
       -H "Content-Type: application/json" \
       -d '{
         "fromCurrency": "USD",
         "toCurrency": "EUR",
         "amount": 100
       }'
     ```

## Testing the Services

1. Check if services are running:
   ```bash
   # Check Rate Service status
   curl http://localhost:8081/status

   # Check Main Service status
   curl http://localhost:8080/status
   ```

2. Get an exchange rate:
   ```bash
   curl "http://localhost:8081/rate?from=USD&to=EUR"
   ```

3. Convert currency:
   ```bash
   curl -X POST http://localhost:8080/convert \
     -H "Content-Type: application/json" \
     -d '{
       "fromCurrency": "USD",
       "toCurrency": "EUR",
       "amount": 100
     }'
   ```

Note: Currency codes should be in ISO 4217 format (e.g., USD, EUR, GBP, JPY).
