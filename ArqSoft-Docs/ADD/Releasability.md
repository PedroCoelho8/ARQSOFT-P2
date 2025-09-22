# The system must maintain (or improve) releasability. 

---

## Design Objective

O objetivo principal deste design é garantir que o sistema possa manter ou melhorar a sua **releasability** rapidamente e com alta qualidade, minimizando os riscos e impactos negativos de novas versões, mesmo em cenários de alta frequência de **releases**.

---

## Quality Attribute Scenarios

| **Element**          | **Statement**                                                                                                     |
|----------------------|-------------------------------------------------------------------------------------------------------------------|
| **Stimulus**         | O sistema deve manter (ou melhorar) a sua **releasability**.                                                      |
| **Stimulus source**  | Possíveis alterações no sistema interno (componentes, serviços, *updates*, etc.).                                 |
| **Environment**      | Ambiente de desenvolvimento contínuo com alterações suaves e incrementais.                                        |
| **Artifact**         | Código-fonte e configurações do sistema.                                                                          |
| **Response**         | O sistema precisa de ser atualizado de forma rápida, segura e sem interrupções perceptíveis para os utilizadores. |
| **Response measure** | O tempo médio para liberar uma nova versão é menor que X horas, com taxa de falhas em produção abaixo de Y%.      |

---

## Technical Memo

### Problema
O sistema deve manter (ou melhorar) a sua **releasability**, garantindo alta frequências de **releases** sem comprometer a estabilidade e a qualidade do sistema e do uso dos utilizadores.

### Resumo da Solução
Deve ser adotado **Strangle Fig** que permite uma evolução gradual das novas versões do sistema, garantindo que não haja mudanças que afetem de forma critíca e percetível os utilizadores, garantindo o funcionamento contínuo. Práticas de **CI/CD** podem ser adotadas para garantir a continuidade dos processos do sistema, sem interrupções. **CQRS** será uma vantagem pois permite a separação de responsabilidades de leitura e de escrita de dados, fazendo com que não exista interrupções num tipo de responsabilidades caso outro falhe. Por fim, o padrão **Saga** vai permitir garantir uma série de transações distribuídas com garantia de recuperação em caso de falhas e a continuidade dos processos.

### Fatores
Existe uma necessidade de lançar novas versões rapidamente, com um impacto mínimo nos utilizadores. O sistema deve garantir que permanece estável e funcional durante e após os **releases**.

### Solução
- **Strangler Fig**: Evolução gradual, onde novos componentes/serviços substituem partes do sistema legadas de forma incremental, garantindo uma transição suave e sem interrupções no sistema.
- **CI/CD**: Configuração de uma pipeline de CI/CD, automatizando testes e deployment para reduzir o risco e o tempo de release.
- **CQRS**: Separar responsabilidades de leitura e escrita de dados, otimizando o desempenho e minimizando o impacto de alterações numa responsabilidade sobre a outra.
- **Saga**: Padrão Saga para gerir transações distribuídas, garantindo consistência e recuperação automática em caso de falhas.

### Motivação
Manter uma alta capacidade de releasability contínua é essencial para atender às demandas dos clientes, corrigir erros rapidamente e introduzir novas funcionalidades sem comprometer o uso do utilizador ou a integridade do sistema.

### Alternativas
- **Feature Flags:** Utilizar "feature flags" para ativar ou desativar novas funcionalidades sem a necessidade de implantar uma nova versão, porém podem aumentar a complexidade, dificultar a gestão de testes e introduzir riscos de inconsistências e sobrecarga de performance.
### Questões Pendentes
Quais são os critérios exatos para determinar a funcionalidade de uma nova versão em uma pipeline CI/CD?