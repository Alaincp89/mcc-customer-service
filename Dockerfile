# Usa una imagen base de Amazon Corretto 21
FROM amazoncorretto:17-alpine

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia el archivo JAR de la aplicación al contenedor
COPY target/mcc-customer-service-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto en el que la aplicación se ejecutará
EXPOSE 8080

# Define el comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]