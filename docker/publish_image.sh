#!/bin/bash

DOCKER_USER="albertoml199904"
IMAGE_NAME="webapp_10"
TAG="latest" 

FULL_IMAGE_NAME="$DOCKER_USER/$IMAGE_NAME"

echo "Login in Docker Hub..."
docker login -u ${DOCKER_USER}

echo "Creating tag..."
docker tag ${IMAGE_NAME}:${TAG} ${FULL_IMAGE_NAME}:${TAG}

echo "Pushing image to Docker Hub..."
docker push ${FULL_IMAGE_NAME}:${TAG}

echo "Image in Docker Hub: $FULL_IMAGE_NAME"
