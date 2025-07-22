# Demo Spring Boot API

Este projeto é uma API RESTful construída com Spring Boot para gerenciamento de Projetos e Tarefas.

## 🧰 Tecnologias utilizadas

- Java 21
- Spring Boot 3.3+
- Spring Data JPA
- MapStruct: Para mapeamento de DTOs e Entidades
- Jakarta Validation: Para validações de entrada
- Lombok
- Springdoc OpenAPI: Para documentação da API (Swagger UI)
- Docker & Docker Compose: Para containerização e ambiente de desenvolvimento
- Spring Boot DevTools: Para hot reload no ambiente de desenvolvimento
- Banco de Dados: H2 (para ambiente de desenvolvimento e testes)
- Testes: JUnit 5, Mockito e AssertJ

## 📁 Estrutura do projeto

```
src
└── main
    ├── java
    │   └── com.example.demo
    │       ├── business              // 1. Lógica de negócio específica
    │       │   ├── controllers
    │       │   ├── mappers
    │       │   ├── models
    │       │   ├── repositories
    │       │   └── services
    │       │
    │       ├── core                  // 2. Componentes genéricos e reutilizáveis
    │       │   ├── controller
    │       │   ├── mapper
    │       │   └── service
    │       │
    │       ├── config                // Configurações do Spring, Swagger, etc.
    │       └── exceptions            // Tratamento global de exceções
    │
    └── resources
    │   └── application.properties
    └── test
        └── java
            └── com.example.demo
                └── business
                    ├── controllers
                    └── services
```

## ⚙️ Como Executar o Projeto

Existem duas maneiras recomendadas para executar a aplicação, dependendo do seu objetivo.

### 1. Ambiente de Desenvolvimento (Recomendado)

Esta abordagem usa **Docker Compose** e **Spring Boot DevTools** para criar um ambiente com **hot reload**: qualquer alteração no seu código Java é refletida instantaneamente na aplicação em execução, sem a necessidade de reiniciar o contêiner.

**Pré-requisitos:**
- Docker e Docker Compose instalados.
- Dependência `spring-boot-devtools` no `pom.xml`.

**Comando:**
Na raiz do projeto, execute:
```bash
docker-compose up --build
```
A aplicação estará disponível em http://localhost:8088. A documentação da API estará em http://localhost:8088/swagger-ui.html.

### 2. Gerando uma Imagem de Produção

Este método utiliza um **Dockerfile de múltiplos estágios** para criar uma imagem Docker otimizada, pequena e segura, pronta para ser implantada em qualquer ambiente de produção.

**Comandos:**
````bash:
# 1. Construir a imagem Docker
docker build -t gerenciador-projetos-api .

# 2. Rodar o contêiner a partir da imagem criada
docker run -p 8088:8080 gerenciador-projetos-api
````

Acesse: [http://localhost:8088/tarefas](http://localhost:8088/tarefas)

Acesse a documentação Swagger em:
/swagger-ui.html ou /swagger-ui/index.html

## 📝 Funcionalidades Implementadas

* CRUD completo para Projetos e Tarefas.
* Arquitetura Genérica Reutilizável: Classes AbstractCrudController e AbstractCrudService que eliminam código repetitivo.
* Tratamento Global de Exceções: Um @ControllerAdvice centraliza o tratamento de erros, retornando respostas padronizadas.
* Validação de Dados de Entrada: Uso do Jakarta Validation para garantir a integridade dos dados.
* Documentação da API: Geração automática de documentação interativa com OpenAPI (via Swagger UI).
* Containerização: Suporte completo a Docker para desenvolvimento e produção.
* Testes Abrangentes: Estratégia de testes em múltiplas camadas (unidade, slice e integração).

## ✅ Estratégia de Testes

O projeto valoriza a qualidade do código através de uma suíte de testes robusta:

* Testes de Unidade: Para a camada de serviço (@ExtendWith(MockitoExtension.class)), testando a lógica de negócio em isolamento.
* Testes de Fatia (Slice Tests):
  * Para a camada de Controller (@WebMvcTest), validando a camada web (endpoints, serialização, status HTTP).
  * Para a camada de Repositório (@DataJpaTest), validando os mapeamentos JPA e queries customizadas contra um banco em memória.
* Testes de Integração: Para validar o fluxo completo da aplicação (@SpringBootTest), garantindo que todas as camadas funcionam corretamente em conjunto.

## 🚀 Próximos Passos Sugeridos

* Implementar um Banco de Dados Real: Integrar com um banco de dados como PostgreSQL ou MySQL, gerenciado via Docker Compose.
* Autenticação e Autorização: Adicionar Spring Security e JWT para proteger os endpoints.
* Cache: Implementar uma camada de cache com Caffeine ou Redis para otimizar o desempenho de leituras frequentes.
* CI/CD: Configurar um pipeline de Integração e Entrega Contínua (ex: com GitHub Actions) para automatizar os testes e o build do projeto.
* Monitoramento: Adicionar Spring Boot Actuator para expor métricas e informações da aplicação.

---

## 👨‍💼 Autor

[Matheus Pimentel](https://github.com/MatheusPimentel)
