# ----- ESTÁGIO 1: O "CONSTRUTOR" (Builder) -----
# Usamos uma imagem que já tem Maven e o JDK 21
FROM maven:3.9-eclipse-temurin-21 AS builder

# Define o diretório de trabalho
WORKDIR /build

# Copia só o pom.xml primeiro para aproveitar o cache do Docker.
# As dependências só serão baixadas novamente se o pom.xml mudar.
COPY pom.xml .
RUN mvn dependency:go-offline

# Agora copia o resto do código-fonte
COPY src ./src

# Roda o build dentro do contêiner. -DskipTests pula os testes, o que é comum em pipelines de CI/CD.
RUN mvn clean package -DskipTests


# ----- ESTÁGIO 2: A IMAGEM FINAL (Runner) -----
# Começamos com uma imagem limpa e muito menor, que tem apenas o Java Runtime (JRE)
FROM eclipse-temurin:21-jre-jammy

# Define o diretório de trabalho
WORKDIR /app

# A mágica do multi-stage: copia APENAS o .jar gerado no estágio "builder"
COPY --from=builder /build/target/*.jar app.jar

# Expõe a porta e define o comando de execução, como antes
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]