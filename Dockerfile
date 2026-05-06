# Fase de construcción
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app

# 1. Copiamos los archivos del proyecto
COPY . .

# 2. DAMOS PERMISOS DE EJECUCIÓN (Esto arregla el error 126)
RUN chmod +x mvnw

# 3. Ejecutamos la compilación
RUN ./mvnw clean package -DskipTests

# Fase de ejecución (Runtime)
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]