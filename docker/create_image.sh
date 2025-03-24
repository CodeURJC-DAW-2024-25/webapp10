#!/bin/bash

# Variables
APP_NAME="webapp_10"
TAG="latest"

# Remove carriage return characters
sed -i 's/\r//' "$0"

cd "$(dirname "$0")/.."

echo "Building Docker image..."
docker build -t ${APP_NAME}:${TAG} -f  docker/Dockerfile . || {
    echo "ERROR: Docker failed"
    exit 1
}

echo "Image built successfully: $APP_NAME"