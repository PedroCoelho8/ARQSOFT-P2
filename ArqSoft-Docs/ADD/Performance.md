# The system must increase the performance by 25% when in high demand (i.e. >Y requests/period).

---

## Design Objective

O objetivo principal deste design é assegurar o aumento do desempenho do sistema em pelo menos 25% quando este se encontra sob alta demanda.

---

## Quality Attribute Scenarios

| **Element**          | **Statement**                                                                                                                                                                                                                                         |
|----------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Stimulus**         | Um aumento dos pedidos recebidos que excede o limiar de Y pedidos/período.                                                                                                                                                                            |
| **Stimulus source**  | Utilizadores geram muito tráfego e aumentam a demanda do sistema.                                                                                                                                                                                     |
| **Environment**      | Sistema atua sob condições de alta carga devido a múltiplos utilizadores em simultâneo.                                                                                                                                                               |
| **Artifact**         | Serviços operacionais do sistema.                                                                                                                                                                                                                     |
| **Response**         | Todos os pedidos efetuados pelos clientes devem ser satisfeitos de acordo com o Service Level Aggrement (SLA).                                                                                                                                        |
| **Response measure** | O sistema deve melhorar o seu desempenho em 25% em situações de alta demanda, mantendo a latência e a taxa de erro abaixo de X%. Todos os pedidos efectuados pelos clientes devem ser satisfeitos no prazo de 10 segundos e numa média de 2 segundos. |

---

## Technical Memo

### Problema
O sistema precisa de aumentar 25% o seu desempenho quando a demanda é elevada (p.ex. volume de solicitações ultrapassa Y requisições/período).

### Resumo da Solução
Criar múltiplas instâncias de cada microserviço, separando responsabilidades e aumentando o desempenho do sistema (**Cloning**). Implementar uma solução de escalabilidade automática (**autoscaling**), uso de **load balancers**, adoção de **Polyglot Persistence** para permitir a escolha do tipo de SGBD ideal, otimizando o desempenho. Adoção de **CQRS**  para separar as operações de leitura e escrita do sistema e reduzir a carga sobre os dados. Adotar mecanismos de **Messaging** por RabbitMQ para tornar a comunicação mais resiliente e eficiente (melhorando o desempenho).

### Fatores
O sistema deve ser capaz de aumentar o seu desempenho em ocasiões de alta demanada e, para isso, deve ser capaz de ser escalável, deve estar aberto a diferentes SGBDs, deve possuir mecanismos de mensagens assíncronas (RabbitMQ).

### Solução
- **Load Balancer**: Uso de um load balancer para distribuir as requisições de forma eficiente entre as várias instâncias dos microserviços. Isso melhora a utilização dos recursos disponíveis, aumentando o desempenho.
- **Polyglot persistence e Database per service**: Adotar diferentes tipos de SGBDs para diferentes microserviços, garantindo que cada um utilize a base de dados mais adequada ao seu caso de uso (e.g., NoSQL para alto desempenho de leitura e SQL para consistência transacional).
- **Messaging**: Implementar RabbitMQ para gerir a comunicação assíncrona entre os serviços, reduzindo o tempo de espera em operações críticas e permitindo maior resiliência e escalabilidade.
- **CRQS**: Separar as operações de leitura e escrita do sistema, permitindo otimizações específicas para cada tipo de operação e aumentando a escalabilidade, além de reduzir a latência das operações.
- **Scale by cloning**: Criar múltiplas instâncias de cada microserviço existente para distribuir a carga, garantindo o desempenho do sistema em picos de demanda.

### Motivação
Garantir que o sistema possa responder adequadamente a altos volumes de requisitos sem degradação perceptível no desempenho, oferecendo uma experiência fluida ao utilizador.

### Alternativas
- **Incluir cache no sistema:** - Implementar cache distribuído para aliviar a carga em serviços de leitura durante períodos de alta demanda, que pode melhorar o desempenho de leitura mas não a nível de escrita e escalabilidade.
- **Manual Scaling**: Ajustar a quantidade de recursos manualmente para cada pico de demanda. A implementação é mais simples mas exige intervenção constante, tornando o processo menos ágil.

### Questões Pendentes
- Como determinar o ponto de “alta carga” e automatizar a resposta de escalabilidade sem comprometer a performance?
- Como incluir o load balancer de forma eficiente?
 