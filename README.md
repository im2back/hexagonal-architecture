# Hexagonal Architecture Demo

Projeto de estudo desenvolvido em **Java 17 + Spring Boot** para demonstrar, na prática, a implementação da **Arquitetura Hexagonal (Ports and Adapters)**.

O principal objetivo é manter o **núcleo da aplicação isolado de tecnologias externas**, como:

- REST
- Spring
- JPA
- Hibernate
- H2
- Banco de dados
- Frameworks em geral

A comunicação entre o núcleo e o mundo externo acontece através de **Ports** e **Adapters**.

---

## Tecnologias utilizadas

- Java 17
- Spring Boot 4.1.1
- Spring Web
- Spring Data JPA
- Hibernate
- H2 Database
- Maven
- Jakarta Validation

---

# Visão geral da arquitetura

```text
Mundo Externo
     |
     | HTTP / JSON
     v
Adapter IN
OrderController
     |
     v
Port IN
CreateOrderUseCase
     |
     v
Application Service
CreateOrderService
     |
     v
Domain
Order / OrderItem / Money / Quantity
     |
     v
Port OUT
OrderRepositoryPort
     |
     v
Adapter OUT
OrderRepositoryAdapter
     |
     v
Persistence Mapper
OrderPersistenceMapper
     |
     v
Spring Data JPA
SpringDataOrderRepository
     |
     v
Banco de Dados
H2
```

---

# Estrutura do projeto

```text
src/main/java/com/github/im2back/hexagonal_architecture_demo
│
├── adapter
│   │
│   ├── in
│   │   │
│   │   ├── dto
│   │   │   ├── CreateOrderRequest.java
│   │   │   ├── CreateOrderItemRequest.java
│   │   │   └── OrderResponse.java
│   │   │
│   │   ├── mapper
│   │   │   └── OrderRestMapper.java
│   │   │
│   │   └── rest
│   │       └── OrderController.java
│   │
│   └── out
│       │
│       └── persistence
│           │
│           └── jpa
│               ├── OrderRepositoryAdapter.java
│               │
│               ├── entity
│               │   ├── OrderJpaEntity.java
│               │   └── OrderItemJpaEntity.java
│               │
│               ├── mapper
│               │   └── OrderPersistenceMapper.java
│               │
│               └── repository
│                   └── SpringDataOrderRepository.java
│
├── configuration
│   └── BeanConfiguration.java
│
└── core
    │
    ├── application
    │   │
    │   ├── port
    │   │   │
    │   │   ├── in
    │   │   │   └── CreateOrderUseCase.java
    │   │   │
    │   │   └── out
    │   │       └── OrderRepositoryPort.java
    │   │
    │   └── service
    │       └── CreateOrderService.java
    │
    └── domain
        │
        └── order
            │
            ├── model
            │   ├── Order.java
            │   ├── OrderItem.java
            │   └── OrderStatus.java
            │
            ├── valueobject
            │   ├── Money.java
            │   └── Quantity.java
            │
            └── exception
```

---

# Como a Arquitetura Hexagonal foi implementada

## Core

O `core` representa o núcleo da aplicação.

Ele concentra:

- regras de negócio;
- casos de uso;
- contratos de entrada;
- contratos de saída;
- entidades de domínio;
- Value Objects.

O objetivo é fazer com que essa camada conheça o mínimo possível sobre tecnologias externas.

```text
core
├── application
└── domain
```

---

# Domain

O pacote:

```text
core/domain
```

representa o domínio da aplicação.

Nele estão os conceitos de negócio:

```text
Order
OrderItem
Money
Quantity
OrderStatus
```

O domínio não conhece:

```text
REST
HTTP
JSON
Controller
JPA
Hibernate
H2
Spring Data
```

Isso permite que as regras de negócio permaneçam independentes da infraestrutura.

---

## Order

`Order` representa o pedido e concentra comportamentos e regras de negócio relacionadas ao pedido.

Exemplos:

```text
Adicionar item
Remover item
Confirmar pedido
Cancelar pedido
Calcular total
```

---

## OrderItem

Representa um item pertencente ao pedido.

Possui informações como:

```text
productId
quantity
unitPrice
```

---

## Value Objects

O projeto também utiliza **Value Objects**.

### Money

```text
Money
├── amount
└── currency
```

Representa um valor monetário.

### Quantity

```text
Quantity
└── value
```

Representa uma quantidade válida de itens.

Esses objetos encapsulam regras relacionadas aos próprios valores.

---

# Port IN

Uma **Port IN** representa uma funcionalidade que a aplicação oferece ao mundo externo.

Neste projeto:

```text
CreateOrderUseCase
```

Exemplo:

```java
public interface CreateOrderUseCase {

    Order execute(
        Long customerId,
        List<OrderItem> items
    );

}
```

Essa interface representa o contrato do caso de uso:

```text
Criar Pedido
```

O Adapter IN não precisa conhecer diretamente a implementação.

Ele depende apenas da interface.

```text
OrderController
      |
      v
CreateOrderUseCase
```

---

# Application Service

A implementação concreta do caso de uso é:

```text
CreateOrderService
```

Ela implementa:

```text
CreateOrderUseCase
```

Exemplo:

```text
CreateOrderUseCase
        ^
        |
CreateOrderService
```

A responsabilidade dessa classe é orquestrar o fluxo da aplicação.

Por exemplo:

```text
Criar Order
     |
Adicionar OrderItems
     |
Aplicar regras do domínio
     |
Persistir Order
```

Ela não conhece diretamente o banco de dados.

---

# Port OUT

A aplicação precisa persistir pedidos.

Porém, o core não deve conhecer diretamente:

```text
JPA
Hibernate
H2
PostgreSQL
MongoDB
```

Por isso foi criada uma **Port OUT**:

```text
OrderRepositoryPort
```

Exemplo:

```java
public interface OrderRepositoryPort {

    Order save(Order order);

}
```

A Service depende somente desse contrato:

```text
CreateOrderService
        |
        v
OrderRepositoryPort
```

---

# Adapter IN

O Adapter IN representa uma forma concreta de entrada na aplicação.

Neste projeto, a entrada utilizada é REST.

```text
adapter/in/rest
```

A implementação é:

```text
OrderController
```

O fluxo começa assim:

```text
Cliente
  |
  | HTTP
  v
OrderController
```

O Controller recebe um DTO:

```text
CreateOrderRequest
```

---

# OrderRestMapper

O Adapter IN possui um mapper:

```text
OrderRestMapper
```

Ele é responsável por traduzir dados entre:

```text
Mundo Externo
↕
Core
```

Na entrada:

```text
CreateOrderRequest
        |
        v
OrderRestMapper
        |
        v
OrderItem / objetos compreendidos pelo Core
```

Na saída:

```text
Order
  |
  v
OrderRestMapper
  |
  v
OrderResponse
```

Isso evita que o Controller fique responsável pela conversão dos objetos.

---

# Adapter OUT

O Adapter OUT representa uma implementação concreta de uma dependência externa.

No projeto:

```text
OrderRepositoryAdapter
```

Ele implementa:

```text
OrderRepositoryPort
```

Fluxo:

```text
OrderRepositoryPort
        ^
        |
OrderRepositoryAdapter
```

A implementação atual utiliza:

```text
JPA
Hibernate
H2
```

Caso fosse necessário trocar a tecnologia de persistência, o contrato do core poderia permanecer o mesmo.

Exemplo:

```text
OrderRepositoryPort
        ^
        |
        +-------------------+
        |                   |
JpaOrderAdapter      MongoOrderAdapter
```

---

# Persistence Mapper

O `OrderPersistenceMapper` traduz o modelo de domínio para o modelo de persistência.

```text
Domain
  ↕
Persistence
```

Exemplo:

```text
Order
  ↕
OrderJpaEntity
```

E:

```text
OrderItem
  ↕
OrderItemJpaEntity
```

Dessa forma:

```text
Order
```

não precisa conhecer:

```java
@Entity
@Table
@Column
@OneToMany
@ManyToOne
```

Esses detalhes ficam restritos ao Adapter OUT.

---

# Spring Data Repository

A interface:

```text
SpringDataOrderRepository
```

utiliza Spring Data JPA.

Exemplo:

```java
public interface SpringDataOrderRepository
        extends JpaRepository<OrderJpaEntity, Long> {
}
```

Ela é utilizada pelo:

```text
OrderRepositoryAdapter
```

Fluxo:

```text
OrderRepositoryAdapter
        |
        v
SpringDataOrderRepository
        |
        v
JPA / Hibernate
        |
        v
Banco
```

---

# Injeção de dependência

Para evitar colocar anotações do Spring dentro da `CreateOrderService`, a instanciação é feita externamente.

Classe:

```text
BeanConfiguration
```

Exemplo:

```java
@Configuration
public class BeanConfiguration {

    @Bean
    public CreateOrderUseCase createOrderUseCase(
            @Qualifier("orderRepositoryAdapter")
            OrderRepositoryPort orderRepositoryPort
    ) {

        return new CreateOrderService(
                orderRepositoryPort
        );
    }
}
```

O Spring monta:

```text
CreateOrderUseCase
        |
        v
CreateOrderService
        |
        v
OrderRepositoryPort
        |
        v
OrderRepositoryAdapter
```

Dessa forma, a camada de aplicação depende apenas de abstrações.

---

# Fluxo completo de criação de pedido

```text
POST /orders
     |
     v
CreateOrderRequest
     |
     v
OrderController
     |
     v
OrderRestMapper
     |
     v
CreateOrderUseCase
     |
     v
CreateOrderService
     |
     v
Order
     |
     +--> OrderItem
     |
     +--> Money
     |
     +--> Quantity
     |
     v
OrderRepositoryPort
     |
     v
OrderRepositoryAdapter
     |
     v
OrderPersistenceMapper
     |
     v
OrderJpaEntity
     |
     +--> OrderItemJpaEntity
     |
     v
SpringDataOrderRepository
     |
     v
Hibernate / JPA
     |
     v
H2
```

---

# Fluxo resumido

```text
Cliente
  ↓
Adapter IN
  ↓
Port IN
  ↓
Application Service
  ↓
Domain
  ↓
Port OUT
  ↓
Adapter OUT
  ↓
Banco
```

---

# Como executar o projeto

## Pré-requisitos

É necessário possuir:

```text
Java 17
Maven
```

Verifique:

```bash
java -version
```

e:

```bash
mvn -version
```

---

## Executando a aplicação

Na raiz do projeto:

```bash
mvn spring-boot:run
```

A aplicação será iniciada em:

```text
http://localhost:8080
```

---

# Criando um pedido

Endpoint:

```http
POST /orders
```

Exemplo de requisição:

```json
{
  "customerId": 10,
  "items": [
    {
      "productId": 100,
      "quantity": 2,
      "unitPrice": 25.90,
      "currency": "BRL"
    }
  ]
}
```

---

## Testando pelo PowerShell

```powershell
Invoke-RestMethod `
  -Uri "http://localhost:8080/orders" `
  -Method POST `
  -ContentType "application/json" `
  -Body (@{
      customerId = 10
      items = @(
          @{
              productId = 100
              quantity = 2
              unitPrice = 25.90
              currency = "BRL"
          }
      )
  } | ConvertTo-Json -Depth 5)
```

Resposta esperada:

```json
{
  "id": 1,
  "customerId": 10,
  "status": "CREATED"
}
```

---

# Banco H2

O projeto utiliza **H2 em memória**.

Exemplo de configuração:

```properties
spring.datasource.url=jdbc:h2:mem:hexagonal_db
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=create-drop

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

---

## Console do H2

Acesse:

```text
http://localhost:8080/h2-console
```

Utilize:

```text
JDBC URL: jdbc:h2:mem:hexagonal_db
User: sa
Password:
```

Como o banco está configurado em memória, os dados são removidos quando a aplicação é encerrada.

---

# Persistência

O Hibernate cria as estruturas equivalentes a:

```text
ORDERS
ORDER_ITEMS
```

O relacionamento é aproximadamente:

```text
ORDERS
  |
  | 1:N
  |
ORDER_ITEMS
```

---

# Por que utilizar Arquitetura Hexagonal?

A principal ideia é proteger o núcleo da aplicação das tecnologias externas.

O core não deve precisar saber se os dados chegam através de:

```text
REST
Kafka
RabbitMQ
CLI
gRPC
Scheduler
```

Da mesma forma, ele não deve precisar saber se os dados são armazenados em:

```text
H2
PostgreSQL
MySQL
MongoDB
Cassandra
```

Esses detalhes são tratados pelos Adapters.

---

# Ports e Adapters

Uma forma simples de visualizar a arquitetura é:

```text
                    MUNDO EXTERNO

                         |
                         v

                  +--------------+
                  |  ADAPTER IN  |
                  +--------------+
                         |
                         v
                  +--------------+
                  |   PORT IN    |
                  +--------------+
                         |
                         v

                +------------------+
                |                  |
                |       CORE       |
                |                  |
                | Application      |
                | Domain           |
                |                  |
                +------------------+

                         |
                         v
                  +--------------+
                  |   PORT OUT   |
                  +--------------+
                         |
                         v
                  +--------------+
                  | ADAPTER OUT  |
                  +--------------+
                         |
                         v

                    MUNDO EXTERNO
```

O Adapter IN permite que tecnologias externas entrem na aplicação.

Exemplos:

```text
REST Controller
Kafka Consumer
RabbitMQ Consumer
Scheduler
CLI
WebSocket
gRPC
```

O Adapter OUT permite que a aplicação utilize recursos externos.

Exemplos:

```text
Banco de dados
Kafka Producer
API externa
Sistema de arquivos
Cache
Serviço de e-mail
```

---

# Diferença entre Port IN e Port OUT

## Port IN

Representa algo que o sistema **oferece**.

Exemplo:

```text
CreateOrderUseCase
```

Significa:

> A aplicação sabe criar pedidos.

Fluxo:

```text
Adapter IN
    |
    v
Port IN
    |
    v
Application Service
```

---

## Port OUT

Representa algo que o sistema **precisa do mundo externo**.

Exemplo:

```text
OrderRepositoryPort
```

Significa:

> A aplicação precisa de alguém capaz de persistir pedidos.

Fluxo:

```text
Application Service
        |
        v
Port OUT
        |
        v
Adapter OUT
```

---

# Função dos Mappers

Neste projeto existem dois tipos principais de mapper.

## REST Mapper

```text
OrderRestMapper
```

Responsável pela fronteira entre:

```text
REST
↕
Core
```

Exemplo:

```text
CreateOrderRequest
        ↓
OrderRestMapper
        ↓
OrderItem
```

e:

```text
Order
  ↓
OrderRestMapper
  ↓
OrderResponse
```

---

## Persistence Mapper

```text
OrderPersistenceMapper
```

Responsável pela fronteira entre:

```text
Core
↕
Persistência
```

Exemplo:

```text
Order
  ↕
OrderJpaEntity
```

e:

```text
OrderItem
  ↕
OrderItemJpaEntity
```

Assim, cada Adapter fica responsável por traduzir o formato externo para o formato compreendido pelo núcleo.

---

# Separação entre Domain e JPA

O projeto mantém dois modelos diferentes.

## Modelo de domínio

```text
Order
OrderItem
Money
Quantity
```

Representa o negócio.

## Modelo de persistência

```text
OrderJpaEntity
OrderItemJpaEntity
```

Representa como os dados são persistidos utilizando JPA.

Essa separação permite que o domínio continue independente da tecnologia de banco utilizada.

---

# Benefícios dessa organização

A implementação permite:

- trocar tecnologias externas com menor impacto no core;
- manter regras de negócio independentes de frameworks;
- facilitar testes unitários;
- reduzir acoplamento;
- aplicar Dependency Inversion;
- deixar contratos explícitos;
- separar regras de negócio de infraestrutura;
- permitir múltiplos Adapters para a mesma Port.

Por exemplo, o mesmo caso de uso poderia ser acionado por:

```text
REST Controller
Kafka Consumer
CLI
Scheduler
```

sem alterar a lógica interna da aplicação.

Da mesma forma, o mesmo `OrderRepositoryPort` poderia possuir diferentes implementações:

```text
JPA
MongoDB
InMemory
API externa
```

---

# Conceitos demonstrados

Este projeto demonstra:

- Arquitetura Hexagonal
- Ports and Adapters
- Dependency Inversion
- Dependency Injection
- Domain Model
- Application Service
- Use Cases
- Port IN
- Port OUT
- Adapter IN
- Adapter OUT
- DTO
- Mapper
- Value Objects
- JPA
- Hibernate
- H2
- Spring Data
- Separação entre domínio e infraestrutura

---

# Objetivo do projeto

Este repositório foi criado com finalidade de estudo.

O objetivo é compreender, de forma prática, como uma aplicação pode ser organizada utilizando Arquitetura Hexagonal, mantendo:

```text
Regras de negócio
```

separadas de:

```text
Frameworks
Banco de dados
Protocolos
Tecnologias externas
```

A ideia central pode ser resumida em:

```text
O mundo externo se adapta ao Core.

O Core não se adapta ao mundo externo.
```
