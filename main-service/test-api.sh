#!/bin/bash
set -e

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Base URL and auth
BASE_URL="http://localhost:8080"
AUTH_HEADER="Authorization: Basic $(echo -n username:password | base64)"

# Initialize test counters
total_tests=0
passed_tests=0

# Function to run test
run_test() {
    local test_name=$1
    local method=$2
    local endpoint=$3
    local data=$4
    local expected_code=$5
    local skip_auth=${6:-false}
    
    echo -e "\n🔍 Testing: ${test_name}"
    
    if [ "$method" = "GET" ]; then
        response=$(curl -s -w "\n%{http_code}" \
            "${BASE_URL}${endpoint}")
    else
        if [ "$skip_auth" = "true" ]; then
            response=$(curl -s -w "\n%{http_code}" \
                -X "${method}" \
                "${BASE_URL}${endpoint}" \
                -H "Content-Type: application/json" \
                -d "${data}")
        else
            response=$(curl -s -w "\n%{http_code}" \
                -X "${method}" \
                "${BASE_URL}${endpoint}" \
                -H "Content-Type: application/json" \
                -H "${AUTH_HEADER}" \
                -d "${data}")
        fi
    fi
    
    http_code=$(echo "$response" | tail -n1)
    body=$(echo "$response" | sed \$d)
    
    echo "Method: ${method}"
    echo "Endpoint: ${endpoint}"
    [ ! -z "$data" ] && echo "Data: ${data}"
    echo "Response:"
    
    if [ "$http_code" = "$expected_code" ]; then
        echo -e "${GREEN}✓ Success (HTTP $http_code)${NC}"
        echo "$body"
        return 0
    else
        echo -e "${RED}✗ Failed (Expected HTTP $expected_code, got HTTP $http_code)${NC}"
        echo "$body"
        return 1
    fi
}

# Test function with result tracking
run_test_case() {
    total_tests=$((total_tests + 1))
    if run_test "$@"; then
        passed_tests=$((passed_tests + 1))
    fi
}

# Test 1: Health Check
run_test_case "Health Check" "GET" "/status" "" 200

# Test 2: USD to EUR conversion
run_test_case "USD to EUR Conversion" "POST" "/convert" \
    '{"from": "USD", "to": "EUR", "amount": 100}' 200

# Test 3: EUR to GBP conversion
run_test_case "EUR to GBP Conversion" "POST" "/convert" \
    '{"from": "EUR", "to": "GBP", "amount": 50}' 200

# Test 4: JPY to USD Large Amount
run_test_case "JPY to USD Large Amount" "POST" "/convert" \
    '{"from": "JPY", "to": "USD", "amount": 10000}' 200

# Test 5: Invalid Currency Code
run_test_case "Invalid Currency Code" "POST" "/convert" \
    '{"from": "INVALID", "to": "EUR", "amount": 100}' 503

# Test 6: Missing Authentication
run_test_case "Missing Authentication" "POST" "/convert" \
    '{"from": "USD", "to": "EUR", "amount": 100}' 401 true

# Test 7: Negative Amount
run_test_case "Negative Amount" "POST" "/convert" \
    '{"from": "USD", "to": "EUR", "amount": -100}' 400

# Test 8: Same Currency
run_test_case "Same Currency" "POST" "/convert" \
    '{"from": "USD", "to": "USD", "amount": 100}' 200

# Test 9: Missing Fields
run_test_case "Missing Fields" "POST" "/convert" \
    '{"from": "USD"}' 400

# Test 10: Invalid JSON
run_test_case "Invalid JSON" "POST" "/convert" \
    '{invalid json}' 500

# Print test summary
echo -e "\n=== Test Summary ==="
echo -e "Total Tests: $total_tests"
echo -e "Passed Tests: $passed_tests"
echo -e "Failed Tests: $((total_tests - passed_tests))"

# Set exit code based on test results
if [ "$passed_tests" -eq "$total_tests" ]; then
    echo -e "${GREEN}All tests passed!${NC}"
    exit 0
else
    echo -e "${RED}Some tests failed!${NC}"
    exit 1
fi
