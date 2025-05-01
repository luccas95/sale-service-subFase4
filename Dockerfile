#FROM eclipse-temurin:21-jdk-jammy
#VOLUME /tmp
#
#RUN gradle clean build -x test --no-daemon
#
#COPY build/libs/saleservice-0.0.1-SNAPSHOT.jar app.jar
#
#
#ENTRYPOINT ["java", "-jar", "/app.jar"]



# Etapa 1: Builder - build da aplicação usando Gradle
FROM gradle:8.3-jdk17 AS build

# Copia os arquivos necessários para o build
COPY --chown=gradle:gradle . /home/gradle/project

WORKDIR /home/gradle/project

# Build da aplicação (gera o .jar na pasta build/libs)
RUN gradle clean build -x test --no-daemon

# Etapa 2: Runner - imagem enxuta para rodar a aplicação
FROM eclipse-temurin:21-jdk-jammy

# Cria diretório da aplicação dentro do container
WORKDIR /app

# Copia o .jar gerado da etapa de build para a imagem final
COPY --from=build /home/gradle/project/build/libs/saleservice-*.jar app.jar

# Expõe a porta que a aplicação utiliza
EXPOSE 8081

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]

