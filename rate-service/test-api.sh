#!/bin/bash

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color
YELLOW='\033[1;33m'

# Base URL
BASE_URL="http://localhost:8081"

# Authentication
USERNAME="apiuser"
PASSWORD="password"
AUTH_HEADER="Authorization: Basic $(echo -n ${USERNAME}:${PASSWORD} | base64)"

# Function to check if service is running
check_service() {
    echo -e "\n🔍 Checking Rate Service..."
    response=$(curl -s -w "\n%{http_code}" "${BASE_URL}/status" -u "${USERNAME}:${PASSWORD}" || echo "000")
    http_code=$(echo "$response" | tail -n1)
    
    if [[ $http_code == 2* ]]; then
        echo -e "${GREEN}✓ Rate Service is running${NC}"
        return 0
    else
        echo -e "${RED}✗ Rate Service is not running${NC}"
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
    echo "Method: ${method}"
    echo "Endpoint: ${endpoint}"
    
    if [[ -n "$data" ]]; then
        echo "Data: ${data}"
        response=$(curl -s -w "\n%{http_code}" -X ${method} \
            -H "Content-Type: application/json" \
            -H "${AUTH_HEADER}" \
            -d "${data}" \
            "${BASE_URL}${endpoint}")
    else
        response=$(curl -s -w "\n%{http_code}" -X ${method} \
            -H "${AUTH_HEADER}" \
            "${BASE_URL}${endpoint}")
    fi

    http_code=$(echo "$response" | tail -n1)
    body=$(echo "$response" | sed '$d')

    echo -e "Response (HTTP ${http_code}):"
    if [[ -n "$body" ]]; then
        echo "${body}"
    fi

    if [[ "$http_code" == "$expected_code" ]]; then
        echo -e "${GREEN}✓ Success (Expected HTTP ${expected_code})${NC}"
        return 0
    else
        echo -e "${RED}✗ Failed (Expected HTTP ${expected_code}, got ${http_code})${NC}"
        return 1
    fi
}

# Check if service is running
check_service || exit 1

echo -e "\nRunning API Tests..."

# Test 1: Health Check
run_test "Health Check" "GET" "/status" "" "200"

# Test 2: Get Rate USD to EUR
run_test "Get Rate USD to EUR" "GET" "/rate?from=USD&to=EUR" "" "200"

# Test 3: Get Rate EUR to GBP
run_test "Get Rate EUR to GBP" "GET" "/rate?from=EUR&to=GBP" "" "200"

# Test 4: Get Rate JPY to USD
run_test "Get Rate JPY to USD" "GET" "/rate?from=JPY&to=USD" "" "200"

# Test 5: Invalid Source Currency
run_test "Invalid Source Currency" "GET" "/rate?from=INVALID&to=USD" "" "400"

# Test 6: Invalid Target Currency
run_test "Invalid Target Currency" "GET" "/rate?from=USD&to=INVALID" "" "400"

# Test 7: Same Currency
run_test "Same Currency" "GET" "/rate?from=USD&to=USD" "" "200"

# Final Summary
echo -e "\n${YELLOW}Test Summary${NC}"
echo "✓ All tests completed"
