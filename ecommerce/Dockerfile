# Use a imagem base para Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build


# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos do projeto para o container
COPY pom.xml .
COPY src ./src

# Executa o Maven para compilar o projeto
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copia o JAR gerado na etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Exponha a porta usada pelo Spring Boot (exemplo: 8080)
EXPOSE 8081

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
