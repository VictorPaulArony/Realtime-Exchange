# curl test 

1. Check Status (GET /status)
```bash
curl -X GET http://localhost:8080/status \
-H "Authorization: Basic $(echo -n apiuser:apipass | base64)"
```

2. Convert Currency (POST /convert):
```bash
curl -X POST http://localhost:8080/convert \
-H "Content-Type: application/json" \
-H "Authorization: Basic $(echo -n apiuser:apipass | base64)" \
-d '{
    "from": "USD",
    "to": "EUR",
    "amount": 100.00
}'
```


