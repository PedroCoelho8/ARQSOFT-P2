# The system must improve its availability

---

## Design Objective
O objetivo principal deste design é aumentar a disponibilidade do sistema, garantindo que ele esteja operacional de forma contínua e eficiente. Para alcançar essa melhoria, foram implementadas estratégias que visam minimizar o tempo de inatividade, melhorar a resiliência do sistema em casos de falha e assegurar a capacidade de suportar picos de demanda.

---

## Quality Attribute Scenarios

| **Element**          | **Statement**                                                                                                                                                                                                               |
|----------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Stimulus**         | O sistema está sob uma grande escala de pedidos ou enfrenta uma falha.                                                                                                                                                      |
| **Stimulus source**  | O aumento de tráfego de utlizadores em simultâneo ou falhas em componentes do sistema.                                                                                                                                      |
| **Environment**      | O sistema opera num ambiente de produção sob carga normal (50) ou sob uma carga elevada de pedidos/utilizadores (200).                                                                                                      |
| **Artifact**         | O sistema e os seus componentes (bases de dados, serviços, etc.).                                                                                                                                                           |
| **Response**         | O sistema deve permanecer operacional sem falhas críticas perceptíveis aos utilizadores, processando os pedidos com um desempenho adequado.                                                                                 |
| **Response measure** | O tempo de resposta do sistema deve ser inferior a 3 segundos durante uma carga normal e a 5 segundos durante uma carga elevada de pedidos e a taxa de disponibilidade do sistema deve ser mantida ou ser superior a 99.9%. |

---

## Technical Memo

### Problema
Assegurar que o sistema permaneça altamente disponível em caso de alta carga de pedidos ou de possíveis falhas.

### Resumo da Solução
Adotar técnicas como uma migração incremental de versões do sistema, permitindo uma transição suave entre estas (**Strangler Fig**), dividir o sistema monolítico em partes menores, facilitando a manutenção e escalabilidade (**Functional Decomposition**), adotar diferentes bases de dados para microserviços diferentes (**Database per service**), adotar mecanismos de ***Messaging*** através de RabbitMQ permitirá garantir uma comunicação entre microserviços mais resiliente e eficiente, adotar ***Command-Query Responsibility Segregation*** (**CQRS**) para separar as operações de leitura e escrita do sistema, permitindo otimizações específicas para cada tipo de operação e aumentando a escalabilidade e por fim a adoção do padrão **Saga** para garantir a consistência dos dados e recuperação em caso de falhas.

### Fatores
Para o sistema manter uma disponibilidade alta, deverá suportar escalabilidade, resiliência na recuperação de falhas, uma transição suave, uma consistência de dados através de transações distribuídas e mecanismos de comunicação eficientes.

### Solução
- **Scale by cloning**: Criar múltiplas instâncias de cada microserviço existente para distribuir a carga, garantindo que o sistema não fique sobrecarregado em picos de demanda.
- **Strangler Fig**: Realizar uma transição suave entre diferentes versões do sistema (migração incremental).
- **Functional Decomposition**: Divisão do sistema como um todo, em partes mais pequenas (microserviços), permitindo uma maior flexibilidade e escalabilidade, além de facilitar a manutenção e a implementação de novas funcionalidades sem afetar a operação do sistema como um todo.
- **Database per service**: Cada microserviço terá a sua própria base de dados, o que garante uma maior independência entre os serviços e aumenta a disponibilidade do sistema, permitindo com que a falha de uma base de dados não impacte outros microserviços.
- **CRQS**: Separar as operações de leitura e escrita do sistema, permitindo otimizações específicas para cada tipo de operação e aumentando a escalabilidade, além de reduzir a latência das operações.
- **Saga**: Implementação de um padrão de transações distribuídas para garantir a consistência dos dados num sistema altamente distribuído, permitindo a recuperação de falhas e garantindo que as transações sejam realizadas de maneira segura e eficiente.

### Motivação 
Assegurar a alta disponibilidade do sistema, permitindo com que os utilizadores utilizem o sistema de forma continua e sem interrupções em caso de falhas ou alta carga de pedidos.

### Alternativas
- **Shared Database**: Utilizar uma base de dados de forma a reduzir possíveis orçamentais e de complexididade. Não foi considerada devido a criar dependências entre microserviços, reduzindo a autonomia de cada um, possuindo também um problema na escalabilidade e confiabilidade do projeto.
- **Scale by Partioning**: Dividir cada instância a lidar apenas com uma parte do microserviço, reduzindo carga e aumentando desempenho do projeto LMS. Não foi considerado pelo possivel aumento de resposta visto que as consultas que requerem acesso a várias partições simultaneamente podem ser mais demoradas e consumir mais recursos.
- **Single Database for Reads and Writes:**: Usar uma base de dados para leitura e outra para escrita, de forma a melhorar índices para as consultas mais frequentes. Não foi considerado devido às limitações de escalabilidade e desempenho.


### Questões Pendentes
- Como validar se as metricas de desempenho e disponibilidade estão a ser atingidas?
- Qual vai ser o impacto na performance e disponibilidade durante a transição para a nova arquitetura?
- Quais serão as dificuldades na implementação do SAGA e CQRS ?