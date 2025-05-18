# Realtime-Exchange

A microservices-based currency conversion system consisting of:
- **Rate Service**: Provides real-time exchange rates ([see rate-service/README.md](rate-service/README.md))
- **Main Service**: Handles currency conversions and history ([see main-service/README.md](main-service/README.md))

## Prerequisites
- Docker and Docker Compose

## Quick Start with Docker

### Method A: Using Docker Compose Directly

1. Start all services:
```bash
docker compose up -d
```

2. Verify services are running:
```bash
docker compose ps
```

3. View logs:
```bash
# All services
docker compose logs -f

# Specific service
docker compose logs -f [service-name]  # e.g., postgres, rate-service, or main-service
```

4. Stop services:
```bash
# Stop services
docker compose down

# Stop and remove data volumes
docker compose down -v
```

### Method B: Using the Helper Script

A helper script `docker-run.sh` is provided for easier management of Docker services:

1. Make the script executable (first time only):
```bash
chmod +x docker-run.sh
```

2. Available commands:
```bash
# Start all services
./docker-run.sh start

# Check service status
./docker-run.sh status

# View logs
./docker-run.sh logs

# Stop services
./docker-run.sh stop

# Restart services
./docker-run.sh restart

# Stop and remove volumes
./docker-run.sh clean

# Show help
./docker-run.sh help
```

## Service Endpoints

- Rate Service: http://localhost:8081
- Main Service: http://localhost:8080
- PostgreSQL: localhost:5432

For detailed API documentation and local development setup, please refer to the individual service READMEs:
- [Rate Service Documentation](rate-service/README.md)
- [Main Service Documentation](main-service/README.md)
