# Usar a imagem base do OpenJDK para executar aplicações Java
FROM openjdk:11-jdk as build

# Definir diretório de trabalho
WORKDIR /app

# Copiar todos os arquivos do projeto para dentro do contêiner
COPY . /app

# Construir o projeto usando Gradle (assumindo que você está usando Gradle)
RUN ./gradlew build

# Etapa final: usar uma imagem mais leve do OpenJDK apenas para executar a aplicação
FROM openjdk:11-jre-slim as run

# Definir diretório de trabalho
WORKDIR /app

# Copiar o JAR gerado pela fase de build
COPY --from=build /app/build/libs/app-all.jar /app/app-all.jar

# Definir o comando para iniciar a aplicação
CMD ["java", "-jar", "/app/app-all.jar"]
