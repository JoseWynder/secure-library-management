<h1 align="center">Secure Library Management API</h1>

<p align="center">

<img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21"/>
<img src="https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot 3.x"/>
<img src="https://img.shields.io/badge/Spring%20Security-OAuth2%20%7C%20JWT-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white" alt="Spring Security OAuth2 | JWT"/>
<img src="https://img.shields.io/badge/PostgreSQL-Database-316192?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL Database"/>
<img src="https://img.shields.io/badge/Docker-Containerized-2496ED?style=for-the-badge&logo=docker&logoColor=white" alt="Docker Containerized"/>
<img src="https://img.shields.io/badge/AWS-EC2%20%7C%20RDS-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white" alt="AWS EC2 | RDS"/>
<img src="https://img.shields.io/badge/OpenAPI-Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black" alt="OpenAPI Swagger"/>

</p>

API REST desenvolvida com **Java e Spring Boot** para gerenciamento de autores e livros, com foco em **segurança de aplicações backend e organização arquitetural**.

O projeto simula um cenário próximo de produção, implementando **autenticação e autorização com OAuth2 e JWT**, separação clara de responsabilidades entre camadas e proteção de endpoints baseada em perfis de usuário.

Além da estrutura da aplicação, o projeto também explora aspectos de **infraestrutura e deploy**, utilizando **Docker para containerização** e **AWS (EC2 + RDS)** para execução da aplicação e gerenciamento do banco de dados.

O objetivo principal foi consolidar conhecimentos em **arquitetura backend, segurança de APIs e deploy em ambiente cloud**, estruturando uma aplicação organizada, segura e próxima de um ambiente real.

---

## Features

* Gerenciamento de **Autores, Livros, Clientes e Usuários**
* Autenticação e autorização utilizando **OAuth2 e JWT**
* Controle de acesso baseado em **roles de usuário**
* Proteção de endpoints sensíveis da API
* Documentação automática da API com **Swagger**
* Tratamento global de exceções
* Containerização da aplicação com **Docker**
* Deploy em ambiente **AWS**

---

## Autenticação e Segurança

A aplicação implementa autenticação baseada em **OAuth2**, atuando simultaneamente como:

* **Authorization Server** (responsável pela emissão de tokens)
* **Resource Server** (responsável pela proteção dos endpoints)

Entre os fluxos OAuth2 suportados estão:

* Authorization Code
* Client Credentials
* Refresh Token

### Interface de autenticação

<img width="1919" height="964" alt="1" src="https://github.com/user-attachments/assets/7d2e0a5e-aebd-4b95-a40b-dab7f951f6ba" />

A autenticação do usuário é realizada através de uma interface de login da própria aplicação. Após a validação das credenciais, um **token de acesso JWT** é gerado e utilizado nas requisições subsequentes.

### Fluxo de obtenção do token (Authorization Code)

<img width="1791" height="1025" alt="3" src="https://github.com/user-attachments/assets/568ee48f-a7c9-4859-8e17-e3d62e873536" />

Neste fluxo, o cliente solicita um token de acesso e o usuário realiza a autenticação através da interface da aplicação. Após a autenticação bem-sucedida, o **access token** é gerado e disponibilizado para utilização nas requisições à API.

---

Após a obtenção do token, ele é utilizado como **Bearer Token** para acessar endpoints protegidos da API.

<img width="1027" height="1026" alt="4" src="https://github.com/user-attachments/assets/d85b4b85-c248-488d-b484-36ba41a4eef7" />

A imagem mostra a realização de uma requisição via **Postman**, utilizando o **Bearer Token** no header `Authorization`, retornando **HTTP 200 OK** ao acessar um endpoint protegido da API.

---

## Documentação da API

A API possui documentação interativa gerada automaticamente via **OpenAPI / Swagger**, permitindo explorar e testar os endpoints diretamente pelo navegador.

<img width="1920" height="1080" alt="2" src="https://github.com/user-attachments/assets/c5887684-b818-4228-890b-209960600719" />

Após autenticação, os endpoints protegidos podem ser testados diretamente na interface do Swagger.

---

## Architecture

A aplicação segue uma **arquitetura em camadas**, separando responsabilidades entre diferentes partes do sistema:

* **Controller** → exposição dos endpoints REST
* **Service** → regras de negócio da aplicação
* **Repository** → acesso e persistência de dados
* **Security** → configuração de autenticação e autorização
* **Config** → configurações da aplicação
* **Validator** → validações de domínio
* **Exceptions** → tratamento centralizado de erros

Algumas decisões estruturais adotadas:

* Separação entre **Authorization Server** e **Resource Server**
* Autenticação **stateless com JWT**
* Implementação de **Custom Authentication Provider**
* Uso de **DTOs** para evitar exposição direta das entidades
* Uso de **Specifications** para consultas mais flexíveis no JPA
* Tratamento global de exceções com `@RestControllerAdvice`


### Diagrama de arquitetura

<img width="2150" height="780" alt="5" src="https://github.com/user-attachments/assets/790af49a-8cea-4936-88a0-b2e7af1ca501" />

O diagrama apresenta uma visão simplificada da arquitetura utilizando o **C4 Model**, destacando a comunicação entre cliente, aplicação e banco de dados.

---

## Deployment

O deploy da aplicação foi realizado manualmente na **AWS**, utilizando a seguinte infraestrutura:

* **EC2** para execução da aplicação
* **RDS** para hospedagem do banco PostgreSQL
* **Docker** para containerização da aplicação

### Infraestrutura na AWS

![7](https://github.com/user-attachments/assets/3a704037-6114-47cb-8360-0dc3eb083d1c)

A imagem da aplicação foi publicada no **Docker Hub** e utilizada durante o processo de execução da aplicação dentro da instância EC2.

---

## Running the Project

### Pré-requisitos

* Java 21
* PostgreSQL
* Docker (opcional)

### Passos

Clone o repositório:

```
git clone https://github.com/seu-usuario/secure-library-management.git
```

Configure as variáveis de conexão com o banco de dados no arquivo:

```
src/main/resources/application.yaml
```

Execute a aplicação:

```
./mvnw spring-boot:run
```

Após iniciar, a documentação da API estará disponível em:

```
http://localhost:8080/swagger-ui.html
```

---

## Tests

Alguns testes de integração foram implementados para validação da camada de persistência.

Estratégias mais avançadas de testes com **JUnit, Mockito e MockMvc** foram exploradas em um repositório separado dedicado exclusivamente a experimentação de estratégias de teste no ecossistema Spring.

[Spring Boot Testing](https://github.com/JoseWynder/spring-boot-testing-playground)

---

## Possible Improvements

* Versionamento de banco de dados com **Flyway ou Liquibase**
* Orquestração local com **Docker Compose**
* Ampliação da cobertura de testes automatizados
* Ampliação da cobertura de testes

---

## Status

Projeto concluído como estudo avançado de **segurança em APIs REST, autenticação OAuth2 e deploy em cloud utilizando o ecossistema Spring**.
