# 📋 Projeto: API de Gerenciamento de Tarefas

Este projeto é uma API RESTful desenvolvida com Spring Boot, destinada ao gerenciamento de tarefas.

## 🚀 Tecnologias Utilizadas

- Java 21
- Spring Boot 3.4.5
- Spring Data JPA
- H2 Database
- Lombok
- Maven

## 📂 Estrutura do Projeto

src/  
├── main/  
│ ├── java/  
│ │ └── com/  
│ │ └── example/  
│ │ └── demo/  
│ │ └── business/  
│ │ ├── controllers/  
│ │ ├── models/  
│ │ ├── repositories/  
│ │ └── services/  
│ └── resources/  
│ ├── application.properties  
│ └── data.sql

## ⚙️ Configuração

No arquivo `application.properties`, configure as propriedades do banco de dados H2:

spring.datasource.url=jdbc:h2:mem:demo
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true


## 📬 Endpoints da API

- **Criar Tarefa**: `POST /tarefas`
- **Atualizar Tarefa**: `PUT /tarefas`
- **Buscar Tarefa por ID**: `GET /tarefas/{id}`
- **Listar Todas as Tarefas**: `GET /tarefas`
- **Deletar Tarefa**: `DELETE /tarefas/{id}`

## 🧪 Testando com Postman

Você pode utilizar o Postman para testar os endpoints acima.  
Certifique-se de que a aplicação esteja em execução e utilize a URL base `http://localhost:8080`.

## 📝 Autor

- **Nome**: Matheus Pimentel
- **GitHub**: [MatheusPimentel](https://github.com/MatheusPimentel)
