# As a librarian, I want to create a Book, Author and Genre in the same process.

---

## Design Objective

O objetivo principal deste design é fornecer a visão para a criação de um livro, autor e género, permitindo que todos os elementos sejam geridos no mesmo processo, garantindo consistência nos dados e eficiência operacional.

---

## Quality Attribute Scenarios

| **Element**          | **Statement**                                                                                                                         |
|----------------------|---------------------------------------------------------------------------------------------------------------------------------------|
| **Stimulus**         | O sistema deve permitir a criação de um Book, Author e Genre no mesmo processo.                                                       |
| **Stimulus source**  | Utilizador (librarian) pretende criar um Book, Author e Genre no mesmo processo.                                                      |
| **Environment**      | Sistema em produção, acessível por API.                                                                                               |
| **Artifact**         | Módulos de gestão de dados de Book, Author e Genre.                                                                                   |
| **Response**         | Criação com sucesso de Book, Author e Genre, com validação de integridade. Em caso de falha, as alterações deve ser todas revertidas. |
| **Response measure** | Conclusão em menos de 5 segundos e sem inconsistências entre entidades.                                                               |

---

## Technical Memo

### Problema
Atualmente, a criação de Book, Author e Genre exige operações separadas em diferentes pontos do sistema. É necessário criar um Book, Author e Genre no mesmo processo.

### Resumo da Solução
Adotar o padrão **Saga** para permitir uma transação distribuida na criação de Book, Author e Genre no mesmo processo e, em caso de falha, todas as alterações são revertidas. Adotar **CQRS** para separar responsabilidades de leitura e escrita de dados.

### Fatores
A arquitetura deve permitir que cada serviço (Book, Author, Genre) opere de forma independente, mas colaborativa. Garantir que os dados estejam em sincronismo após o término do processo. A solução deve ser robusta a falhas em qualquer etapa do fluxo, garantindo compensações apropriadas. A implementação de padrões como Saga e CQRS para modularidade e maior clareza de responsabilidades.

### Solução
- **Messaging**: Utilizar mecanismos de mensagens assíncronas para permitir a comunicação entre microserviços, com o objetivo de notificar eventos ou acionar compensações em caso de falhas.
- **Saga**: Implementar um fluxo distribuído onde o serviço inicial (Book) inicia o pedido. Cada serviço (Book, Author, Genre) executa a sua operação específica e envia eventos ao RabbitMQ para confirmar o progresso ou notificar falhas. Em caso de falha, têm de ser acionadas compensações para reverter as operações concluídas.
- **CQRS**: Separar as operações de leitura e escrita.


### Motivação
A motivação para esta solução é garantir que a criação de um Book, Author e Genre seja tratada de forma transacional, ou seja, ou todos são criados com sucesso ou, em caso de falha, nenhuma criação deve persistir. O uso do padrão Saga proporciona a robustez necessária para controlar falhas em sistemas distribuídos, garantindo que todas as operações sejam tratadas de maneira coordenada. O padrão CQRS facilita a escalabilidade e a organização dos serviços, separando as operações de leitura de dados das operações de escrita de dados.

### Alternativas
- **Sistema Monolítico**: Criação de um único serviço que encapsulasse todas as responsabilidades para a criação de um Book, Author e Genre no mesmo processo, o que potencialmente causaria a interrupção do serviço caso alguma coisa no fluxo falhasse.
- **Transações Distribuídas (ACID)**: Usar transações distribuídas para garantir que as operações de criação de Book, Author e Genre sejam tratadas como uma única transação atômica.

### Questões Pendentes
- Como garantir que as compensações sejam tratadas corretamente em todos os serviços?
- Como garantir a consistência de dados em cenários de falha?
- Como implementar um sistema de monitorização eficiente para detetar falhas e o progresso de cada operação durante a execução da Saga?