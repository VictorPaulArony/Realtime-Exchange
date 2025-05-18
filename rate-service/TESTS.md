# curl tests rate-service

## 1. Basic Tests

1.1. Check service status (No authentication required):
```bash
curl -X GET 'http://localhost:8081/status' -u apiuser:password
```
Expected Response:
```json
{
    "status": "UP"
}
```

1.2. Get exchange rate from USD to EUR (Success case):
```bash
curl -X GET 'http://localhost:8081/rate?from=USD&to=EUR' \
-u apiuser:password \
-H 'Content-Type: application/json'
```

## 2. East African Currency Tests

2.1. USD to East African Currencies

a) USD to Kenyan Shilling (KES):
```bash
curl -X GET 'http://localhost:8081/rate?from=USD&to=KES' \
-u apiuser:password \
-H 'Content-Type: application/json'
```

b) USD to Tanzanian Shilling (TZS):
```bash
curl -X GET 'http://localhost:8081/rate?from=USD&to=TZS' \
-u apiuser:password \
-H 'Content-Type: application/json'
```

c) USD to Ugandan Shilling (UGX):
```bash
curl -X GET 'http://localhost:8081/rate?from=USD&to=UGX' \
-u apiuser:password \
-H 'Content-Type: application/json'
```

d) USD to Rwandan Franc (RWF):
```bash
curl -X GET 'http://localhost:8081/rate?from=USD&to=RWF' \
-u apiuser:password \
-H 'Content-Type: application/json'
```

e) USD to Burundian Franc (BIF):
```bash
curl -X GET 'http://localhost:8081/rate?from=USD&to=BIF' \
-u apiuser:password \
-H 'Content-Type: application/json'
```

2.2. Cross East African Currency Rates

a) KES to TZS (Kenyan to Tanzanian Shilling):
```bash
curl -X GET 'http://localhost:8081/rate?from=KES&to=TZS' \
-u apiuser:password \
-H 'Content-Type: application/json'
```

b) UGX to KES (Ugandan Shilling to Kenyan Shilling):
```bash
curl -X GET 'http://localhost:8081/rate?from=KES&to=UGX' \
-u apiuser:password \
-H 'Content-Type: application/json'
```

c) RWF to UGX (Rwandan Franc to Ugandan Shilling):
```bash
curl -X GET 'http://localhost:8081/rate?from=RWF&to=UGX' \
-u apiuser:password \
-H 'Content-Type: application/json'
```

## 3. Error Cases

3.1. Invalid currency code:
```bash
curl -X GET 'http://localhost:8081/rate?from=INVALID&to=EUR' \
-u apiuser:password \
-H 'Content-Type: application/json'
```
Expected Response:
```json
{
    "error": "Invalid 'from' currency code"
}
```

3.2. Missing 'from' parameter:
```bash
curl -X GET 'http://localhost:8081/rate?to=EUR' \
-u apiuser:password \
-H 'Content-Type: application/json'
```
Expected Response:
```json
{
    "error": "Missing required parameter: from"
}
```

3.3. Missing 'to' parameter:
```bash
curl -X GET 'http://localhost:8081/rate?from=USD' \
-u apiuser:password \
-H 'Content-Type: application/json'
```
Expected Response:
```json
{
    "error": "Missing required parameter: to"
}
```

3.4. Missing authentication:
```bash
curl -X GET 'http://localhost:8081/rate?from=USD&to=EUR' \
-H 'Content-Type: application/json'
```
Expected Response: HTTP 401 Unauthorized

3.5. Invalid authentication:
```bash
curl -X GET 'http://localhost:8081/rate?from=USD&to=EUR' \
-u wronguser:wrongpass \
-H 'Content-Type: application/json'
```
Expected Response: HTTP 401 Unauthorized

## 4. Special Cases

4.1. Same currency (should return rate 1.0):
```bash
curl -X GET 'http://localhost:8081/rate?from=USD&to=USD' \
-u apiuser:password \
-H 'Content-Type: application/json'
```
Expected Response:
```json
{
    "from": "USD",
    "to": "USD",
    "rate": 1.0
}
```

```bash
curl -X GET 'http://localhost:8081/rate?from=USD' \
-u apiuser:password \
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