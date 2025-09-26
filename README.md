# ARQSOFT-P2 — Reengenharia do Library Management System (Projeto 2)

## Contexto

Este repositório corresponde ao Projeto 2 da unidade curricular de **Arquitetura de Software** (2024/2025). Após resolver problemas de configurabilidade no projeto anterior, o objetivo deste sprint foi superar restrições de desempenho, disponibilidade e escalabilidade provocadas pela arquitetura centralizada (monolítica/modular monolítica) da aplicação LMS.

## Problema

Apesar de funcional e configurável, o sistema monolítico revelou-se limitado em:
- **Desempenho**: Incapacidade de escalar eficientemente sob carga elevada.
- **Disponibilidade**: Pontos únicos de falha comprometem o serviço.
- **Elasticidade**: Utilização ineficiente de recursos de hardware.
- **Evolução**: Dificuldade em adaptar rapidamente a novas necessidades de negócio.

## Objetivo

Reengenharia da aplicação LMS, migrando para uma arquitetura descentralizada/distribuída em microserviços com foco em:
- **Aumento da disponibilidade**
- **Melhoria do desempenho (>25% em situações de elevada procura)**
- **Elasticidade e utilização eficiente de recursos**
- **Preservação da compatibilidade da API para clientes**
- **Aderência à estratégia SOA/API-led connectivity da organização**

## Requisitos

### Não-funcionais
- Alta disponibilidade
- Escalabilidade dinâmica baseada em carga
- Eficiência no uso de hardware (auto-scaling/down)
- Manutenção da releasability
- Compatibilidade da API para clientes externos

### Funcionais
- Criação simultânea de Livro, Autor e Género (librarian)
- Sugestão de aquisição de novo Livro (reader)
- Recomendação de Livro aquando da devolução (reader)

## Abordagem Arquitetural

- **Microserviços**: Cada domínio do LMS (autores, livros, géneros, empréstimos, sugestões, autenticação) foi extraído para um serviço independente, favorecendo isolamento de falhas e escalabilidade granular.
- **API Gateway**: Centralização do acesso externo, roteamento inteligente e abstração das mudanças internas.
- **Mensageria/Assíncrono**: Comunicação entre serviços via eventos, reduzindo acoplamento e aumentando a resiliência.
- **Auto scaling**: Serviços configurados para escalar horizontalmente conforme a carga, otimizando custos de infraestrutura.
- **Testes de performance e integração**: Uso de JMeter e Postman para garantir SLAs e integração entre serviços.
- **Documentação e coleções**: Postman para exemplos de uso e validação da API distribuída.

## Padrões, Táticas e Referências

- **Service Discovery** para escalabilidade e failover automático
- **Circuit Breaker** e **Retry** para tolerância a falhas
- **API Versioning** para evolução sem breaking changes
- **Event Sourcing** e **CQRS** (quando aplicável)
- **Docker** para ambientes reprodutíveis e deployment consistente

## Tecnologias Utilizadas

- **Java 17** (backend dos microserviços)
- **Spring Boot** (desenvolvimento de microserviços, discovery, config server)
- **RabbitMQ** (mensageria e comunicação assíncrona)
- **MySQL** (persistência polimórfica por serviço)
- **Docker & Docker Compose** (contenorização e orquestração local)
- **JMeter** (testes de performance)
- **Postman** (testes e documentação da API)
- **Swagger/OpenAPI** (documentação de APIs)

## Organização do Repositório

- `lms-authnusers/` — Serviço de autenticação e utilizadores
- `lms-authors/` — Serviço de gestão de autores
- `lms-books/` — Serviço de gestão de livros
- `lms-genres/` — Serviço de géneros
- `lms-genres-query/` — Serviço de queries sobre géneros
- `lms-lendings/` — Serviço de empréstimos
- `lms-suggests/` — Serviço de sugestões de novos livros
- `ArqSoft-Docs/` — Relatórios técnicos, diagramas e documentação arquitetural
- `jmeter/` — Scripts e resultados de testes de carga
- `postman collection/` — Coleções para teste e demonstração da API distribuída

## Racional Arquitetural

A adoção da arquitetura distribuída foi guiada por requisitos de negócio e qualidade, garantindo que cada serviço possa evoluir e escalar de acordo com as suas necessidades, reduzindo riscos sistémicos e otimizando a experiência dos utilizadores finais. O uso de SOA e padrões modernos permite também alinhar o sistema com estratégias empresariais de integração e inovação.


---

> **Arquitetura de Software, 2024/2025**  
