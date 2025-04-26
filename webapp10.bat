@echo off
echo Closing old containers...
cd /d C:\Users\irene\OneDrive\Escritorio\def\webapp10\docker
docker-compose down

echo.
echo Returning to the main folder...
cd ..

echo.
echo Creating new image...
call docker\create_image.sh

echo.
echo Publishing new image...
call docker\publish_image.sh

echo.
echo Returning to the docker folder...
cd docker

echo.
echo Starting containers...
docker-compose up
