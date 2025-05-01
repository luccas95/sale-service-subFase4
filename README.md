
# Visão Geral do Projeto


*Sistema de revenda digital com gestão de veículos, vendas e integração com pagamentos*

Este projeto foi desenvolvido como parte do **Tech Challenge - Pós-Tech SOAT (Fase 2)**. Ele simula a transformação digital de uma empresa de revenda de veículos, oferecendo uma API robusta para gestão de produtos e vendas, além de integração com um serviço de pagamentos.

## 🎯 Visão de Negócio

A plataforma online permite:

- ✅ **Cadastrar veículos para venda** (marca, modelo, ano, cor, preço)
- ✏️ **Editar informações** dos veículos
- 💰 **Efetuar a venda** de veículos, vinculando comprador e data
- 📃 **Listar veículos disponíveis e vendidos**, ordenados por preço
- 🔄 **Receber notificações de pagamento via webhook** com status (efetuado/cancelado)

> **Objetivo**: tornar o processo de revenda mais transparente, rastreável e eficiente.

---

## 🧱 Visão Técnica

*Separação de responsabilidades usando Clean Architecture*

O projeto segue os princípios de:
- **Clean Architecture**
- **SOLID**
- **Desenvolvimento orientado a microsserviços**

A infraestrutura foi desenhada para execução em ambientes **Docker** e **Kubernetes**.

### Inclui:
- `Dockerfile` para cada serviço
- `docker-compose.yml` para ambiente local
- Manifestos Kubernetes: `Deployment`, `Service`, `ConfigMap`, `Secret`
- Documentação de API via **Swagger/OpenAPI**

---

## 📦 Estrutura dos Microsserviços

*Serviços independentes com integração via REST*

- `vehicle-service`: Cadastro e listagem de veículos
- `sale-service`: Venda e status da transação
- `payment-service`: Recebimento de confirmação de pagamento e atualização da venda





# Sale Service 🚗💰

Este microserviço faz parte do ecossistema do projeto Tech Challenge e é responsável pela **gestão de vendas de veículos**.  
Ele permite criar vendas, consultar e atualizar o status das vendas, integrando-se com os demais serviços (Vehicle Service e Payment Service).

## Features

✅ Criação de vendas  
✅ Consulta de todas as vendas  
✅ Atualização de status da venda (CONCLUIDO ou CANCELADO)  
✅ Integração com o Payment Service para criação de pagamentos  
✅ Integração com o Vehicle Service para atualização de status do veículo vendido  
✅ Clean Architecture  
✅ Docker e Kubernetes Ready  
✅ Documentação OpenAPI / Swagger (em breve!)

## Tecnologias

- Java 21
- Spring Boot 3.x
- Gradle
- PostgreSQL (banco de dados)
- Docker
- Kubernetes
- REST API
- Clean Architecture
- Feign Client para comunicação entre serviços

## Estrutura do Projeto

```
src
├── main
│   ├── java
│   │   └── com.fiap.saleservice
│   │       ├── application
│   │       │   └── usecase (casos de uso da aplicação)
│   │       ├── domain
│   │       │   └── entity (entidades de domínio)
│   │       ├── infrastructure
│   │       │   ├── controller (controllers REST)
│   │       │   ├── gateway (implementações dos gateways)
│   │       │   ├── repository (JPA repositories)
│   │       │   └── client (feign/rest clients)
│   └── resources
│       └── application.yml
└── test
    └── java
```

## 🚀 Como executar localmente (Docker Compose)

1. Gere o build da aplicação:

```bash
./gradlew clean build
```

2. Criar a network para que as aplicações se comuniquem entre os containers:


```bash
docker network create microservices-network
```

3. Suba os containers com Docker Compose:

```bash
docker-compose up --build
```

4. Acesse o Swagger:

```
http://localhost:8082/swagger-ui/index.html
```

## ☸️ Como executar no Kubernetes (Docker Desktop)

1. Certifique-se que o Kubernetes está habilitado no Docker Desktop.

2. Construa a imagem da aplicação:

```bash
docker build -t sale-service:latest .
```

3. Aplique os manifests do Kubernetes:

```bash
kubectl apply -f .\k8s\postgres\
kubectl apply -f .\k8s\sale\local\
```

4. Verifique os serviços e pegue a porta NodePort:

```bash
kubectl get svc
```

Acesse no navegador usando a porta exibida:

```
http://localhost:<NODE_PORT>/swagger-ui/index.html
```

A aplicação irá subir na porta **8082**.

### Endpoints disponíveis

| Método | Endpoint                       | Descrição                          |
|--------|--------------------------------|-----------------------------------|
| POST   | `/sales`                       | Cria uma nova venda               |
| GET    | `/sales`                       | Lista todas as vendas             |
| PUT    | `/sales/{id}/concluir`         | Conclui uma venda                 |
| PUT    | `/sales/{id}/cancelar`         | Cancela uma venda                 |


## 🧪 Testes

Para rodar os testes automatizados :

```bash
./gradlew test
```
![Cobertura de Testes](images/Cobertura%20de%20Testes%20-%20sale-service.png)

## Observações

- O serviço se comunica com o `vehicle-service` e `payment-service`, certifique-se que ambos estejam ativos.
- O `payment-service` automaticamente cria o pedido de pagamento quando a venda é criada.

---

© Tech Challenge - 2025
