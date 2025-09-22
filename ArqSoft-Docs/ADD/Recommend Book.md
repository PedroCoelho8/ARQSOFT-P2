# As a reader, upon returning a Book, I want to recommend it (positively or negatively).

---

## Design Objective

O objetivo principal deste design é permitir que os leitores façam recomendações sobre os livros que devolveram, seja de forma positiva ou negativa, promovendo uma melhor interação com o sistema. A solução será baseada numa arquitetura de microserviços para garantir flexibilidade, escalabilidade e desacoplamento dos diferentes componentes do sistema.

---

## Quality Attribute Scenarios

| **Element**          | **Statement**                                                                                         |
|----------------------|-------------------------------------------------------------------------------------------------------|
| **Stimulus**         | O leitor devolve um livro e faz uma recomendação (positiva ou negativa).                              |
| **Stimulus source**  | Leitor interage com o sistema após devolver o livro, realizando uma recomendação.                     |
| **Environment**      | O sistema opera num ambiente distribuído, com microserviços independentes para cada módulo funcional. |
| **Artifact**         | Módulo de recomendações de livros.                                                                    |
| **Response**         | O sistema deve registar a recomendação do leitor de forma eficiente e disponibilizá-la para consulta. |
| **Response measure** | A recomendação deve ser registada em menos de 3 segundos e estar disponível para consulta.            |

---

## Technical Memo

### Problema
O sistema precisa ser capaz de registar recomendações feitas pelos leitores de forma eficiente, garantindo que a recomendação (positiva ou negativa) seja associada corretamente ao livro devolvido, de modo a fornecer feedback relevante aos outros leitores.

### Resumo da Solução
Adotar mecanismos de mensagens assíncronas (**Messaging**) entre os diferentes serviços do sistema, adotar **Domain Events** para notificar serviços sobre novas recomendações feitas e adotar **CQRS** para separar responsabilidades de leitura e escrita de dados.

### Fatores
A solução deve ser escalável, permitindo com que um grande número de recomendações seja processado sem impactar o desempenho do sistema. O sistema deve garantir que cada recomendação seja associada corretamente ao livro devolvido, sem falhas.

### Solução
- **Messaging**: Para garantir que as recomendações sejam processadas de forma desacoplada e eficiente, será implementada uma solução como RabbitMQ. Quando um leitor faz uma recomendação, um evento será lançado e processado por outros microserviços conforme necessário.
- **Domain Events**: Um evento será emitido quando um livro for devolvido e recomendado, garantindo que todos os serviços relevantes sejam notificados para realizarem as suas ações.
- **CQRS**: A implementação deste padrão  ajudará a separar as operações de escrita (registo da recomendação) das operações de leitura (consulta de recomendações), permitindo uma otimização das duas operações independetes.

### Motivação
A recomendação de livros pelos leitores oferece feedbacks valiosos sobre a qualidade de um livro. Além disso, permite aos leitores interagir de maneira mais eficiente com o sistema. A arquitetura baseada em microserviços proporciona escalabilidade, flexibilidade e desacoplamento, atendendo à necessidade de crescimento e de alterações no sistema sem causar impacto noutras funcionalidades.

### Alternativas
- **Arquitetura Monolítica**: Centralizar o processamento de recomendações no próprio sistema monolítico. Embora mais simples inicialmente, isso dificultaria a escalabilidade e a evolução do sistema a longo prazo.
- **Recomendações Diretas**:  Permitir que as recomendações sejam feitas diretamente numa base de dados centralizada, sem a necessidade de uma comunicação assíncrona.
- 
### Questões Pendentes
- Como o sistema lidará com falhas durante o processamento das recomendações? 
-  Haverá um sistema de validação para garantir que as recomendações sejam legítimas (por exemplo, sem spam ou dados falsos)?
