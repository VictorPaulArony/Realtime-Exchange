# curl test for main-service

1. Check Status (GET /status)
```bash
curl -X GET http://localhost:8080/status \
-H "Authorization: Basic $(echo -n apiuser:apipass | base64)"
```

2. Basic Currency Conversion (USD to EUR)
```bash
curl -X POST http://localhost:8080/convert \
-H "Content-Type: application/json" \
-H "Authorization: Basic $(echo -n username:password | base64)" \
-d '{
    "from": "USD",
    "to": "EUR",
    "amount": 100.00
}'
```

3. East African Currency Conversions

   a. USD to Kenyan Shilling (KES)
   ```bash
   curl -X POST http://localhost:8080/convert \
   -H "Content-Type: application/json" \
   -H "Authorization: Basic $(echo -n username:password | base64)" \
   -d '{
       "from": "USD",
       "to": "KES",
       "amount": 100.00
   }'
   ```

   b. USD to Tanzanian Shilling (TZS)
   ```bash
   curl -X POST http://localhost:8080/convert \
   -H "Content-Type: application/json" \
   -H "Authorization: Basic $(echo -n username:password | base64)" \
   -d '{
       "from": "USD",
       "to": "TZS",
       "amount": 100.00
   }'
   ```

   c. USD to Ugandan Shilling (UGX)
   ```bash
   curl -X POST http://localhost:8080/convert \
   -H "Content-Type: application/json" \
   -H "Authorization: Basic $(echo -n username:password | base64)" \
   -d '{
       "from": "USD",
       "to": "UGX",
       "amount": 100.00
   }'
   ```

   d. USD to Rwandan Franc (RWF)
   ```bash
   curl -X POST http://localhost:8080/convert \
   -H "Content-Type: application/json" \
   -H "Authorization: Basic $(echo -n username:password | base64)" \
   -d '{
       "from": "USD",
       "to": "RWF",
       "amount": 100.00
   }'
   ```

   e. USD to Burundian Franc (BIF)
   ```bash
   curl -X POST http://localhost:8080/convert \
   -H "Content-Type: application/json" \
   -H "Authorization: Basic $(echo -n username:password | base64)" \
   -d '{
       "from": "USD",
       "to": "BIF",
       "amount": 100.00
   }'
   ```

4. Cross East African Currency Conversions

   a. KES to TZS (Kenyan Shilling to Tanzanian Shilling)
   ```bash
   curl -X POST http://localhost:8080/convert \
   -H "Content-Type: application/json" \
   -H "Authorization: Basic $(echo -n username:password | base64)" \
   -d '{
       "from": "KES",
       "to": "TZS",
       "amount": 1000.00
   }'
   ```

   b. UGX to KES (Ugandan Shilling to Kenyan Shilling)
   ```bash
   curl -X POST http://localhost:8080/convert \
   -H "Content-Type: application/json" \
   -H "Authorization: Basic $(echo -n username:password | base64)" \
   -d '{
       "from": "UGX",
       "to": "KES",
       "amount": 10000.00
   }'
   ```

   c. RWF to UGX (Rwandan Franc to Ugandan Shilling)
   ```bash
   curl -X POST http://localhost:8080/convert \
   -H "Content-Type: application/json" \
   -H "Authorization: Basic $(echo -n username:password | base64)" \
   -d '{
       "from": "RWF",
       "to": "UGX",
       "amount": 5000.00
   }'
   ```


