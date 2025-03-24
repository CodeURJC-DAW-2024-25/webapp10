#!/bin/bash

if [ $# -lt 2 ]; then
  echo "Uso: ./publish_image.sh <usuario_docker> <nombre_imagen> [tag]"
  exit 1
fi

DOCKER_USER=$1
IMAGE_NAME=$2
TAG=${3:-latest} 

FULL_IMAGE_NAME="$DOCKER_USER/$IMAGE_NAME:$TAG"

echo "Login in Docker Hub..."
docker login

echo "Building image..."
docker build -t "$FULL_IMAGE_NAME" .

echo "Pushing image to Docker Hub..."
docker push "$FULL_IMAGE_NAME"

echo "Image in Docker Hub: $FULL_IMAGE_NAME"
