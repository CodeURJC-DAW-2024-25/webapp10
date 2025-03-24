#!/bin/bash
# Detener y eliminar todos los contenedores, imágenes y volúmenes de Docker

echo "Deteniendo todos los contenedores..."
docker stop $(docker ps -q)

echo "Eliminando todos los contenedores..."
docker rm -f $(docker ps -a -q)

echo "Eliminando todas las imágenes..."
docker rmi -f $(docker images -q)

echo "Eliminando todos los volúmenes..."
docker volume prune -f

echo "Docker ha sido limpiado exitosamente."
