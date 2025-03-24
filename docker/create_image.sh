#!/bin/bash

# Var
APP_NAME="webapp_10"

sed -i 's/\r//' "$0"

echo "Building image docker..."
docker build -t $APP_NAME .

echo "Image built success: $IMAGE_NAME"

