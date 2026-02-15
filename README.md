<h1 align="center">Secure Library Management</h1>

API REST desenvolvida com Java 21 e Spring Boot 3, com foco em autenticação OAuth2, controle de acesso e organização arquitetural voltada para ambientes reais de produção.

---

## 📌 Visão Geral

Aplicação backend para gerenciamento de:

- Autores  
- Livros  
- Clientes  
- Usuários  

O projeto foi construído para simular um cenário completo de API segura, incluindo autenticação, autorização por perfil e deploy em ambiente cloud.

---

## 🚀 Funcionalidades

- Gestão completa de Autores, Livros, Clientes e Usuários  
- Sistema de autenticação com emissão de tokens  
- Controle de acesso baseado em perfis de usuário  
- Proteção de endpoints sensíveis  
- Documentação automática da API via OpenAPI/Swagger  
- Tratamento global de exceções  
- Containerização da aplicação com Docker  
- Deploy em ambiente AWS

---

## 🛠 Stack Tecnológica

- Java 21  
- Spring Boot 3.5.9  
- Spring Security  
- Spring Authorization Server  
- Spring Data JPA (Hibernate)  
- PostgreSQL  
- Docker  
- AWS (EC2 + RDS)  
- OpenAPI / Swagger  

---

## 🧱 Arquitetura

A aplicação segue o padrão em camadas (Layered Architecture), com separação explícita entre:

- `controller` → exposição dos endpoints  
- `service` → regras de negócio  
- `repository` → acesso a dados  
- `security` → configuração de autenticação e autorização  
- `config` → configurações gerais da aplicação  
- `validator` → validações customizadas  
- `exceptions` → tratamento centralizado de erros  

### Decisões Arquiteturais

- Uso de DTOs para evitar exposição direta das entidades.
- Separação entre configuração de Authorization Server e Resource Server.
- Autenticação stateless utilizando JWT.
- Hashing de senhas com BCrypt.
- Tratamento global de exceções com `@RestControllerAdvice`.

A organização foi pensada para manter baixo acoplamento e facilitar evolução futura.

---

## 🔐 Segurança

A aplicação atua simultaneamente como:

- Authorization Server (emissão de tokens)
- Resource Server (proteção dos recursos)

### Estratégia adotada

- Tokens JWT assinados
- Controle de acesso baseado em roles
- Configuração explícita de SecurityFilterChain
- Fluxos OAuth2 implementados:
  - Authorization Code
  - Client Credentials
  - Refresh Token

O modelo adotado permite simular um ambiente real de autenticação centralizada.

---

## 🐳 Containerização

A aplicação possui Dockerfile próprio.

A imagem é publicada no Docker Hub e pode ser executada isoladamente ou conectada a um banco PostgreSQL externo.

Comandos utilizados para build e execução estão documentados em `docker-commands.txt`.

---

## ☁️ Deploy

Deploy manual realizado na AWS:

- Instância EC2 para execução da aplicação
- PostgreSQL hospedado no RDS
- Configuração manual de variáveis de ambiente

A escolha pelo deploy manual teve como objetivo aprofundar o entendimento da infraestrutura e configuração do ambiente.

---

## 🧪 Testes

- Testes de integração implementados para validação da camada de persistência.
- Estratégias mais aprofundadas de testes (unitários, integração e configuração de contexto Spring) foram exploradas em um repositório dedicado:
  [Spring Boot Testing](https://github.com/JoseWynder/spring-boot-testing-playground)

---

## 🔄 Possíveis Evoluções

- Versionamento de banco com Flyway ou Liquibase  
- Implementação de pipeline CI/CD  
- Docker Compose para orquestração local  
- Ampliação da cobertura de testes  
- Implementação de métricas e observabilidade  
- Refinamento da modularização da segurança  

---

## 📊 Status

Projeto concluído como estudo avançado de autenticação OAuth2, controle de acesso e deploy em cloud utilizando o ecossistema Spring.
