# The system must adhere to the company’s SOA strategy of API-led connectivity

---

## Design Objective

O objetivo principal deste design é fornecer um sistema escalável e modular que facilite a integração entre serviços, respeitando os princípios SOA e que possua conectividade orientada por APIs.

---

## Quality Attribute Scenarios

| **Element**          | **Statement**                                                                                   |
|----------------------|-------------------------------------------------------------------------------------------------|
| **Stimulus**         | O sistema deve estar preparado para adotar uma arquitetura orientada a serviços (SOA) com APIs. |
| **Stimulus source**  | Equipa de desenvolvimento e requisitos estratégicos da empresa.                                 |
| **Environment**      | Produção, com microserviços e sistemas monolíticos a operar em simultâneo.                      |
| **Artifact**         | APIs e componentes do sistema.                                                                  |
| **Response**         | Serviços implementados como microserviços, utilizando RabbitMQ como broker de mensagens.        |
| **Response measure** | Tempo de resposta inferior a 3 segundo para 95% das transações.                                 |

---

## Technical Memo

### Problema
Garantia de uma comunicação eficiente e confiável entre serviços num sistema distribuído, respeitando os princípios de SOA e conectividade orientada a APIs.

### Resumo da Solução
Adotar **Strangler Fig** para migrar de uma aplicação monolítica para microserviços de forma gradual e suave. Adotar **Messaging** por RabbitMQ para permitir uma comunicação desacoplada entre microserviços.

### Fatores
O sistema, em caso de lidar com falhas, não deve perder mensagens (confiabilidade). Serviços devem ser independentes, facilitando manutenção e implementação de novos requisitos.

### Solução
**Strangler Fig**: Essa abordagem permitirá a substituição incremental de funcionalidades do sistema monolítico, redirecionando gradualmente os fluxos de trabalho para microserviços.
**Messaging**: RabbitMQ será utilizado para comunicação assíncrona entre os microserviços, aproveitando exchanges e filas para garantir o roteamento apropriado e a entrega confiável de mensagens.

### Motivação
A abordagem Strangler Fig reduz o risco de migração para microserviços, permitindo uma evolução controlada e mensurável. A utilização de Messaging desacopla os serviços, promovendo escalabilidade, confiabilidade e facilidade de manutenção.

### Alternativas
- **Arquitetura Monolítica**: Manter o sistema monolítico pode facilitar a gestão inicialmente mas pode dificultar a escalabilidade.

### Questões Pendentes
Como garantir que o sistema possa lidar com falhas de comunicação entre os microserviços sem perder dados ou prejudicar a continuidade dos processos de negócios?
Como implementar uma solução de monitorização eficaz para analisar o estado de todos os microserviços?