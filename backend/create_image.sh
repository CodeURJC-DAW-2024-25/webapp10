#!/bin/bash

if ! command -v docker &> /dev/null; then
    echo "Error: Docker not downloaded."
    exit 1
fi

DOCKER_USER=${1:-$DOCKER_USER}
if [ -z "$DOCKER_USER" ]; then
    echo "Error: Not docker user."
    echo "Uso: ./create_image.sh <usuario_docker> [nombre_imagen] [tag]"
    exit 1
fi

IMAGE_NAME=${2:-"mi_aplicacion"}

TAG=${3:-"latest"}

FULL_IMAGE_NAME="$DOCKER_USER/$IMAGE_NAME:$TAG"

echo "Building image Docker..."
docker build -t "$FULL_IMAGE_NAME" .

echo "Image built: $FULL_IMAGE_NAME"
