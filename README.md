# Claro Customer MS

Microsserviço desenvolvido em **Spring Boot** para gerenciamento de clientes, implementado como parte de um desafio técnico.

A aplicação expõe uma API REST protegida por **OAuth2 Password Flow**, persiste dados em **H2 em memória**, publica eventos em **RabbitMQ**, possui documentação via **Swagger/OpenAPI**, testes automatizados e esteira de **CI/CD com GitHub Actions**.

---

# Arquitetura

O projeto segue uma arquitetura típica de microsserviços baseada em camadas:

```
Controller
   ↓
Service
   ↓
Repository
   ↓
Database (H2)
```

- Eventos são publicados em **RabbitMQ**
- Autenticação é feita via **OAuth2**
- Documentação automática via **OpenAPI / Swagger**

---

# Tecnologias Utilizadas

- Java 11
- Spring Boot 2.7
- Spring Security
- OAuth2 Password Flow
- Spring Data JPA
- H2 Database
- RabbitMQ
- OpenAPI / Swagger (springdoc)
- JUnit 5
- Mockito
- Docker
- Docker Compose
- GitHub Actions (CI/CD)
- Maven

---

# Estrutura do Projeto

```
src
 ├── config
 │     └── Security 
 │            └── AuthorizationServerConfig
 │            └── ResourceServerConfig
 │            └── SecurityConfig
 │     └── RabbitMQConfig
 │     └── DatabaseInit
 │     └── OpenApiConfig
 │
 ├── controller
 │     └── ClienteController
 │
 ├── service
 │     └── ClienteService
 │
 ├── repository
 │     └── ClienteRepository
 │
 ├── entity
 │     └── Cliente
 │
 ├── dto
 │     └── ClienteDTO
 │     └── ClienteResumo
 │
 ├── messaging
 │     └── event
 │            └── ClienteCriadoEvent
 │     └── producer
 │            └── ClienteProducer  
 │     └── consumer
 │            └── ClienteProducer
 │
 ├── mapper
 │     └── ClienteMapper
 │
 └── exception
       └── config 
              └── GlobalExceptionHandler
       └── ClienteNaoEncontradoException
       └── ErrorResponse
```

---

# Funcionalidades

API de gerenciamento de clientes:

- Listar todos os clientes resumidos
- Buscar cliente por id
- Buscar cliente por CPF
- Criar cliente
- Atualizar cliente
- Remover cliente

Funcionalidades adicionais:

- Autenticação OAuth2
- Publicação de eventos RabbitMQ
- Documentação Swagger
- Validação com Bean Validation
- Tratamento global de exceções
- Testes unitários e de integração
- CI/CD com GitHub Actions

---

# Executando a Aplicação

## Pré-requisitos

- Java 11
- Maven
- Docker
- Docker Compose

---

# Execução com Docker Compose

Subir a aplicação e o RabbitMQ:

```bash
docker compose up
```

Serviços disponíveis:

API

```
http://localhost:8080
```

Swagger

```
http://localhost:8080/swagger-ui.html
```

RabbitMQ Management

```
http://localhost:15672
```

H2 console

```
http://localhost:8080/h2-console/
```

Credenciais padrão do RabbitMQ:

```
user: guest
password: guest
```

---

# Autenticação

A API utiliza **OAuth2 Password Flow**.

## Gerar Access Token

Endpoint:

```
POST /oauth/token
```

Autenticação Basic:

```
client_id: claro-client
client_secret: claro-secret
```

Body (`x-www-form-urlencoded`):

```
grant_type=password
username=admin
password=admin
```

Exemplo de resposta:

```json
{
  "access_token": "eyJhbGc...",
  "token_type": "bearer",
  "expires_in": 3600
}
```

---

# Utilizando o Token

Envie o token no header:

```
Authorization: Bearer <access_token>
```

Exemplo:

```
GET /clientes
Authorization: Bearer eyJhbGc...
```

---

# Documentação da API

A documentação da API está disponível via **Swagger UI**.

Acesse:

```
http://localhost:8080/swagger-ui.html
```

No Swagger:

1. clique em **Authorize**
2. informe o **access token**
3. execute os endpoints protegidos

---

# Mensageria RabbitMQ

Ao criar um cliente, a aplicação publica um evento na fila:

```
cliente.criado
```

Esse evento pode ser consumido por outros serviços interessados.

---

# Testes

O projeto possui testes para:

- Controllers
- Services
- Repository
- Segurança (integração)

Ferramentas utilizadas:

- JUnit 5
- Mockito
- Spring Boot Test
- MockMvc

Executar testes:

```bash
mvn test
```

---

# CI/CD

O projeto possui uma esteira de **Integração Contínua** utilizando **GitHub Actions**.

Pipeline executada em:

- push
- pull request

A pipeline executa:

1. checkout do código
2. setup do Java 11
3. build Maven
4. execução de testes
5. build da imagem Docker
6. publicação da imagem no Docker Hub (apenas em push na branch principal ou na develop)

---

# Variáveis de Ambiente

A aplicação suporta configuração via variáveis de ambiente:

```
RABBITMQ_HOST
RABBITMQ_PORT
RABBITMQ_USERNAME
RABBITMQ_PASSWORD
```

---

# Tratamento de Erros

A API possui tratamento global de exceções via `@RestControllerAdvice`.

Exemplo de erro:

```json
{
  "timestamp": "2026-03-13T12:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Cliente não encontrado"
}
```

---

# Autor

Eduardo Alves

Linkedin  
[https://github.com/edudu4](https://linkedin.com/in/edudu4)

---

# Observação

O fluxo **OAuth2 Password Flow** foi implementado para atender aos requisitos do desafio técnico, embora atualmente seja considerado um fluxo legado nas recomendações mais recentes do OAuth2.
