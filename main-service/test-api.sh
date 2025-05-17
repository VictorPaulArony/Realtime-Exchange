

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color
YELLOW='\033[1;33m'

# Base URLs
MAIN_URL="http://localhost:8080"
RATE_URL="http://localhost:8081"


# Credentials for both main service and rate service
USERNAME="apiuser"
PASSWORD="apipass"
AUTH_HEADER="Authorization: Basic $(echo -n ${USERNAME}:${PASSWORD} | base64)"

# Function to check if services are running
check_service() {
    local service_url=$1
    local service_name=$2
    
    echo -e "\n🔍 Checking ${service_name}..."
    response=$(curl -s -w "\n%{http_code}" -H "${AUTH_HEADER}" "${service_url}/status" || echo "000")
    http_code=$(echo "$response" | tail -n1)
    
    if [[ $http_code == 2* ]]; then
        echo -e "${GREEN}✓ ${service_name} is running${NC}"
        return 0
    else
        echo -e "${RED}✗ ${service_name} is not running${NC}"
        return 1
    fi
}

# Function to run test
run_test() {
    local test_name=$1
    local method=$2
    local endpoint=$3
    local data=$4
    local expected_code=$5
    
    echo -e "\n🔍 Testing: ${test_name}"
    
    # Add small delay between requests
    sleep 1
    
    if [ "$method" = "GET" ]; then
        response=$(curl -s -w "\n%{http_code}" \
            -H "${AUTH_HEADER}" \
            "${MAIN_URL}${endpoint}")
    else
        response=$(curl -s -w "\n%{http_code}" \
            -X "${method}" \
            "${MAIN_URL}${endpoint}" \
            -H "Content-Type: application/json" \
            -H "${AUTH_HEADER}" \
            -d "${data}")
    fi
    
    http_code=$(echo "$response" | tail -n1)
    body=$(echo "$response" | sed \$d)
    
    echo "Method: ${method}"
    echo "Endpoint: ${endpoint}"
    [ ! -z "$data" ] && echo "Data: ${data}"
    echo "Response (HTTP ${http_code}):"
    echo "$body"
    
    if [[ $http_code == $expected_code ]]; then
        echo -e "${GREEN}✓ Success (Expected HTTP $expected_code)${NC}"
        return 0
    else
        echo -e "${RED}✗ Failed (Expected HTTP $expected_code, got $http_code)${NC}"
        return 1
    fi
}

# Check if services are running
check_service "$MAIN_URL" "Main Service" || exit 1
check_service "$RATE_URL" "Rate Service" || exit 1

echo -e "\n${YELLOW}Running API Tests...${NC}"

# Test 1: Health Check
run_test "Health Check" "GET" "/status" "" "200"

# Test 2: USD to EUR conversion
run_test "USD to EUR Conversion" "POST" "/convert" \
    '{"from": "USD", "to": "EUR", "amount": 100.00}' "200"

# Test 3: EUR to GBP conversion
run_test "EUR to GBP Conversion" "POST" "/convert" \
    '{"from": "EUR", "to": "GBP", "amount": 50.00}' "200"

# Test 4: JPY to USD conversion
run_test "JPY to USD Conversion" "POST" "/convert" \
    '{"from": "JPY", "to": "USD", "amount": 10000.00}' "200"

# Test 5: Invalid currency code (Expected 400)
run_test "Invalid Currency Code" "POST" "/convert" \
    '{"from": "INVALID", "to": "EUR", "amount": 100.00}' "400"

# Test 6: Missing Authentication (Expected 401)
echo -e "\n🔍 Testing: Missing Authentication"
AUTH_HEADER_SAVE=$AUTH_HEADER
AUTH_HEADER=""
response=$(curl -s -w "\n%{http_code}" "${MAIN_URL}/status")
http_code=$(echo "$response" | tail -n1)
if [[ $http_code == 401 ]]; then
    echo -e "${GREEN}✓ Success (Expected HTTP 401)${NC}"
else
    echo -e "${RED}✗ Failed (Expected HTTP 401, got ${http_code})${NC}"
fi
AUTH_HEADER=$AUTH_HEADER_SAVE

# Test 7: Zero amount (Expected 400)
run_test "Zero Amount" "POST" "/convert" \
    '{"from": "USD", "to": "EUR", "amount": 0.00}' "400"

# Test 8: Same currency (Expected 200)
run_test "Same Currency" "POST" "/convert" \
    '{"from": "USD", "to": "USD", "amount": 100.00}' "200"

# Test 9: Missing required fields (Expected 400)
run_test "Missing Fields" "POST" "/convert" \
    '{"from": "USD"}' "400"

# Final Summary
echo -e "\n${YELLOW}Test Summary${NC}"
echo -e "${GREEN}✓ All tests completed${NC}"

