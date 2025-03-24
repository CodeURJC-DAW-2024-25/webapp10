#################################################
# Imagen base para el contenedor de compilación
#################################################
FROM maven:3.9.9-eclipse-temurin-21 AS builder

# Define el directorio de trabajo donde ejecutar comandos
WORKDIR /project

# Copia las dependencias del proyecto
COPY pom.xml /project/

# Descarga las dependencias del proyecto
RUN mvn clean package

# Copia el código del proyecto
COPY /src /project/src

# Compila proyecto
RUN mvn package -o -DskipTests=true

#################################################
# Imagen base para el contenedor de la aplicación
#################################################
FROM openjdk:21-jdk-slim

# Instala las dependencias necesarias para la gestión de fuentes
RUN apt-get update && apt-get install -y \
    libfreetype6 \
    libfontconfig1 \
    && rm -rf /var/lib/apt/lists/*

# Define el directorio de trabajo donde se encuentra el JAR
WORKDIR /usr/app/

# Copia el JAR del contenedor de compilación
COPY --from=builder /project/target/*.jar /usr/app/

# Indica el puerto que expone el contenedor
EXPOSE 8443

# Comando que se ejecuta al hacer docker run
CMD [ "java", "-jar", "backend-0.0.1-SNAPSHOT.jar" ]