# Rate Service

A Spring Boot service that provides currency exchange rates through a secure REST API.

## Features

- Currency exchange rate lookup
- RESTful API with Spring Security authentication
- Validation for currency codes
- Error handling for invalid requests
- Integration with external exchange rate service

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Spring Boot 3.x

## Quick Start

1. Clone the repository:
```bash
git clone https://github.com/VictorPaulArony/Realtime-Exchange
cd rate-service
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The service will start on `http://localhost:8081`

## API Documentation

### Get Exchange Rate

Retrieves the exchange rate between two currencies.

```http
GET /rate?from={sourceCurrency}&to={targetCurrency}
```

#### Parameters

- `from` (required): Source currency code (3 uppercase letters, e.g., USD)
- `to` (required): Target currency code (3 uppercase letters, e.g., EUR)

#### Authentication

Basic Authentication is required:
- Username: apiuser
- Password: (check application logs for generated password)

#### Example Request

```bash
curl -X GET 'http://localhost:8081/rate?from=USD&to=EUR' \
  -H 'Authorization: Basic YXBpdXNlcjpwYXNzd29yZA=='
```

#### Success Response

```json
{
    "from": "USD",
    "to": "EUR",
    "rate": 0.85
}
```

#### Error Responses

- **400 Bad Request** - Invalid currency code or missing parameters
```json
{
    "error": "Invalid 'from' currency code"
}
```

- **401 Unauthorized** - Missing or invalid authentication
```json
{
    "error": "Unauthorized"
}
```

- **500 Internal Server Error** - Server-side error
```json
{
    "error": "Internal server error: ..."
}
```

## Testing

The project includes comprehensive unit tests covering:

1. Controller Layer Tests (`RateControllerTest`):
   - Valid exchange rate requests
   - Authentication requirements
   - Invalid currency code handling
   - Missing parameter validation

2. Service Layer Tests (`ExchangeRateServiceTest`):
   - Successful rate retrieval
   - Invalid currency handling

### Unit Tests
Run the unit tests using:
```bash
mvn test
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

This script performs end-to-end testing of the API endpoints including:
- Service health check
- Authentication validation
- Currency conversion scenarios
- Error handling cases

### Test Coverage

- **RateController Tests**:
  - `getRate_ValidRequest_ReturnsRate()`: Tests successful rate retrieval
  - `getRate_Unauthorized_Returns401()`: Tests authentication requirement
  - `getRate_InvalidCurrency_ReturnsBadRequest()`: Tests invalid currency code handling
  - `getRate_MissingParameter_ReturnsBadRequest()`: Tests missing parameter validation

- **ExchangeRateService Tests**:
  - `getExchangeRate_ValidCurrencies_ReturnsRate()`: Tests successful rate lookup
  - `getExchangeRate_InvalidTargetCurrency_ThrowsException()`: Tests invalid currency handling

## Security

The service implements Spring Security with:
- Basic Authentication
- Role-based access control (ROLE_API_USER)
- CSRF protection disabled for API endpoints
- Secure password handling

## Error Handling

Global exception handling (`GlobalExceptionHandler`) provides consistent error responses for:
- Invalid currency codes
- Missing parameters
- Authentication failures
- External service errors
- Generic server errors

## Dependencies

- Spring Boot Starter Web
- Spring Boot Starter Security
- Spring Boot Starter Validation
- Spring WebFlux (WebClient)
- Lombok
- Spring Boot Test
- JUnit Jupiter

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
