# curl tests
1. Get exchange rate from USD to EUR (Success case):
```sh
curl -X GET 'http://localhost:8081/rate?from=USD&to=EUR' \
-u apiuser:your_password \
-H 'Content-Type: application/json'
```
2. Check service status (No authentication required):
```bash
curl -X GET 'http://localhost:8081/status'
```

```sh
{
    "status": "UP"
}
```

3. Test invalid currency code (Error case):
```bash
curl -X GET 'http://localhost:8081/rate?from=INVALID&to=EUR' \
-u apiuser:your_password \
-H 'Content-Type: application/json'
```
```bash
{
    "error": "Invalid 'from' currency code"
}
```
4. Test missing parameter (Error case):
```bash
curl -X GET 'http://localhost:8081/rate?from=USD' \
-u apiuser:your_password \
-H 'Content-Type: application/json'
```
Missing parameter:
```bash 
{
    "error": "Missing required parameter: to"
}
```

5. Test without authentication (Unauthorized case):
```bash
curl -X GET 'http://localhost:8081/rate?from=USD&to=EUR' \
-H 'Content-Type: application/json'
```
Unauthorized:
```bash
{
    "error": "Unauthorized"
}
```