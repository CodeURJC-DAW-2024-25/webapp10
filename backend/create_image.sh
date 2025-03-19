#Image and Dockerhub user data
DOCKER_USERNAME="garrobo0"
DOCKER_PASSWORD="DockerDAW@2025"
DOCKER_REPO="$DOCKER_USERNAME/pruebadocker"
DOCKER_TAG="latest"

#Build and push project image
docker build -t $DOCKER_REPO:$DOCKER_TAG .
echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
docker push $DOCKER_REPO:$DOCKER_TAG
docker logout
