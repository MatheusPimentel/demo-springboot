# Demo Spring Boot API

Este projeto é uma API RESTful construída com Spring Boot para gerenciamento de Projetos e Tarefas.

## 🧰 Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- H2 Database (para testes)
- Jakarta Validation
- Lombok
- Springdoc OpenAPI (Swagger UI)
- JUnit 5 + Mockito + AssertJ (testes unitários)

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
    │   ├── application.properties
    │   └── data.sql
    └── test
        └── java
            └── com.example.demo
                └── business
                    ├── controllers
                    └── services
```

## ⚙️ Como executar

```bash
./mvnw spring-boot:run
```

Acesse: [http://localhost:8088/tarefas](http://localhost:8088/tarefas)

Acesse a documentação Swagger em:
/swagger-ui.html ou /swagger-ui/index.html

## 📝 Funcionalidades

* CRUD de Projetos
* CRUD de Tarefas
* Validações de entrada
* Associação de tarefas a projetos

## 📬 Endpoints principais

### Tarefas
* `GET /tarefas`
* `GET /tarefas/{id}`
* `POST /tarefas`
* `PUT /tarefas/{id}`
* `DELETE /tarefas/{id}`

### Projetos
* `GET /projetos`
* `GET /projetos/{id}`
* `POST /projetos`
* `PUT /projetos/{id}`
* `DELETE /projetos/{id}`

## ✅ Próximos passos sugeridos

* Implementar autenticação (JWT)
* Separar camada de mapeamento (ex: MapStruct ou manual)
* Criar tratamento global de exceções com `@ControllerAdvice`

---

## 👨‍💼 Autor

[Matheus Pimentel](https://github.com/MatheusPimentel)
