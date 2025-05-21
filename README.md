# Demo Spring Boot API

Este projeto é uma API RESTful construída com Spring Boot para gerenciamento de Projetos e Tarefas.

## 🧰 Tecnologias utilizadas

* Java 17
* Spring Boot
* Spring Data JPA
* H2 Database (para testes)
* Jakarta Validation
* Lombok

## 📁 Estrutura do projeto

```
src
└── main
    ├── java
    │   └── com.example.demo
    │       └── business
    │           ├── controllers
    │           ├── dtos
    │           ├── models
    │           ├── repositories
    │           └── services
    └── resources
        ├── application.properties
        └── data.sql
```

## ⚙️ Como executar

```bash
./mvnw spring-boot:run
```

Acesse: [http://localhost:8080/tarefas](http://localhost:8080/tarefas)

## 📝 Funcionalidades

* CRUD de Projetos
* CRUD de Tarefas
* Validações de entrada
* Associação de tarefas a projetos

## 📬 Endpoints principais

* `GET /tarefas`
* `POST /tarefas`
* `PUT /tarefas/{id}`
* `DELETE /tarefas/{id}`
* `GET /projetos`
* `POST /projetos`

## ✅ Próximos passos sugeridos

* Adicionar testes automatizados (unitários e de integração)
* Implementar autenticação (JWT)
* Documentar a API com Swagger
* Separar camada de mapeamento (ex: MapStruct ou manual)
* Criar tratamento global de exceções com `@ControllerAdvice`

---

## 👨‍💼 Autor

[Matheus Pimentel](https://github.com/MatheusPimentel)
