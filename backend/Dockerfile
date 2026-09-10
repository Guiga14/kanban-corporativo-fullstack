# ESTÁGIO 1: Build da aplicação (compilação do JAR)
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Copia as definições de dependências primeiro para aproveitar o cache do Docker
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código-fonte e gera o pacote .jar sem rodar os testes unitários
COPY src ./src
RUN mvn clean package -DskipTests

# ESTÁGIO 2: Imagem final de execução (apenas o necessário para rodar)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia apenas o .jar gerado no estágio anterior
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando que inicializa a aplicação Java
ENTRYPOINT ["java", "-jar", "app.jar"]