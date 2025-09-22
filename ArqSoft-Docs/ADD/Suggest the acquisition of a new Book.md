# As a reader, I want to suggest the acquisition of a new Book

---

## Design Objective

O objetivo principal deste design é permitir que os leitores sugiram a aquisição de novos livros de forma fácil e eficiente.

---

## Quality Attribute Scenarios

| **Element**          | **Statement**                                                                       |
|----------------------|-------------------------------------------------------------------------------------|
| **Stimulus**         | Um leitor sugere a aquisição de um novo Book.                                       |
| **Stimulus source**  | Leitor utiliza o sistema e realiza a sugestão de um novo Book.                      |
| **Environment**      | Sistema opera num ambiente distribuído, com diferentes microserviços.               |
| **Artifact**         | Módulos de sugestões e de Books.                                                    |
| **Response**         | O sistema regista a sugestão realizada pelo leitor e disponibiliza-a para consulta. |
| **Response measure** | Sugestão registaada em menos de 3 segundos.                                         |

---

## Technical Memo

### Problema
O sistema precisa ser capaz de registar sugestões de aquisição de livros feitas pelos leitores de forma rápida e eficiente.

### Resumo da Solução
Implementar mecanismos de comunicação assíncrona entre microserviços (**Messaging**), adotar **Domain Events** para notificar os serviços sobre a criação de uma sugestão de livro e seguir o padrão **CQRS** para separar responsabilidades de leitura e escrita de dados.

### Fatores
A comunicação deverá ser desacoplada para garantir a escalabilidade e resiliência do sistema.

### Solução
- **Messaging**: Utilizar RabbitMQ ou outra tecnologia de mensageria para permitir a comunicação entre microserviços de maneira assíncrona. Quando uma sugestão de livro for criada, um evento será enviado para uma fila de mensagens para processamentos posteriores, como notificações ou a validação da sugestão.
- **CQRS**: Separar as operações de leitura e escrita, garantindo que o sistema seja otimizado para processar grandes volumes de dados em operações de leitura.
- **Domain Events**:  Criar eventos de domínio, que serão emitidos quando um novo livro for sugerido. Outros microserviços, vão escutar esses eventos e agir de acordo com as regras de negócio definidas.

### Motivação
Essa solução visa melhorar a performance, escalabilidade e manutenção do sistema, permitindo que sugestões de livros sejam registadas rapidamente enquanto os serviços do sistema processam e respondem às ações de maneira eficiente e desacoplada. O uso de CQRS também facilita a implementação de futuras melhorias ou mudanças no sistema sem comprometer a integridade do fluxo de dados.

### Alternativas
- **Comunicação síncrona**: Utilizar chamadas de API direta entre os microserviços para criar e processar as sugestões.

### Questões Pendentes
- Como o sistema lidará com falhas na comunicação entre microserviços?
- Como será o fluxo de aprovação das sugestões de livros? 
- Haverá algum mecanismo para garantir que sugestões inválidas sejam rejeitadas antes de serem criadas no sistema?