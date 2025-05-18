#!/bin/bash

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Function to display usage
show_usage() {
    echo -e "Usage: $0 [COMMAND]"
    echo -e "\nCommands:"
    echo -e "  start     - Start all services"
    echo -e "  stop      - Stop all services"
    echo -e "  restart   - Restart all services"
    echo -e "  logs      - View logs from all services"
    echo -e "  status    - Check status of services"
    echo -e "  clean     - Stop services and remove volumes"
    echo -e "  help      - Show this help message"
}

# Function to check if Docker is running
check_docker() {
    if ! docker info >/dev/null 2>&1; then
        echo -e "${RED}Error: Docker is not running${NC}"
        exit 1
    fi
}

# Function to start services
start_services() {
    echo -e "${GREEN}Starting services...${NC}"
    docker compose up -d
    
    echo -e "\n${GREEN}Checking service status:${NC}"
    docker compose ps
}

# Function to stop services
stop_services() {
    echo -e "${GREEN}Stopping services...${NC}"
    docker compose down
}

# Function to view logs
view_logs() {
    echo -e "${GREEN}Viewing logs from all services (Ctrl+C to exit):${NC}"
    docker compose logs -f
}

# Function to check status
check_status() {
    echo -e "${GREEN}Current service status:${NC}"
    docker compose ps
}

# Function to clean up
clean_up() {
    echo -e "${GREEN}Stopping services and removing volumes...${NC}"
    docker compose down -v
}

# Check if Docker is running
check_docker

# Process commands
case "$1" in
    "start")
        start_services
        ;;
    "stop")
        stop_services
        ;;
    "restart")
        stop_services
        start_services
        ;;
    "logs")
        view_logs
        ;;
    "status")
        check_status
        ;;
    "clean")
        clean_up
        ;;
    "help"|"")
        show_usage
        ;;
    *)
        echo -e "${RED}Error: Unknown command '$1'${NC}"
        show_usage
        exit 1
        ;;
esac
