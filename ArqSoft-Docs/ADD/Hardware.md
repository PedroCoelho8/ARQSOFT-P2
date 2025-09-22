# The system must use hardware parsimoniously, according to the runtime demanding of the system. Demanding peeks of >Y requests/period occur seldom.

---

## Design Objective

O objetivo principal deste design é otimizar a utilização do hardware para garantir uma boa relação custo-benefício, mantendo o desempenho durante picos pouco frequentes de elevada demanda.

---

## Quality Attribute Scenarios

| **Element**          | **Statement**                                                                                                                                                      |
|----------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Stimulus**         | O sistema deve utilizar o hardware de forma eficiente, conforme o tempo de execução exigido pelo sistema. Picos exigentes de >Y pedidos/período ocorrem raramente. |
| **Stimulus source**  | Existem hardwares melhores para rodar o ACME, mas são muito caros para operar.<br/>                                                                                |
| **Environment**      | Ambiente de produção em condições normais e sob demanda esporádica elevada.                                                                                        |
| **Artifact**         | Hardware e o sistema.                                                                                                                                              |
| **Response**         | Quando há alta demanda, o sistema deve ajustar o hardware de acordo com a necessidade.                                                                             |
| **Response measure** | O hardware é escalado automaticamente, mantendo o custo abaixo de X% adicional ao mês.                                                                             |

---

## Technical Memo

### Problema
O sistema precisa otimizar o uso de hardware para balancear desempenho e custo, garantindo eficiência em períodos de demanda normal e picos ocasionais (>Y requisições por período).

### Resumo da Solução
Adotar escalabilidade automática baseada em demanda (**autoscaling**) para aumentar ou reduzir os recursos de hardware. Implementar soluções como containers (**Docker**) para melhorar o uso dos recursos e virtualização para alocar hardware sob demanda. Adotar **Stangler Fig** permitirá a migração incremental de componentes, permitindo com que os recursos existentes sejam bem geridos perante o hardware existente. **Database per service** permitirá com que cada microserviço possua a sua base de dados, fazendo com que haja uma melhor gestão de dados e evitando sobrecarga no sistema.

### Fatores
A necessidade de balancear o uso de hardware entre a eficiência e o custo, evitando gastos excessivos durante períodos de baixa demanda. A capacidade de aumentar ou diminuir rapidamente os recursos de hardware conforme as mudanças de demanda.

 
### Solução
- **Autoscaling**: Configurar escalabilidade automática para adicionar ou remover instâncias de hardware conforme a necessidade.
- **Containers e Virtualização**: Uso de Docker para criar ambientes leves e facilmente escaláveis.
- **Strangler Fig**: Substituição gradual de partes da arquitetura atual por componentes novos e otimizados, permitindo uma transição suave e aproveitando os recursos existentes.
- **Database per service**: Adoção de uma abordagem onde cada microserviço possui a sua própria base de dados, permitindo otimizações específicas e um melhor desempenho, além de evitar sobrecarga dos sistemas de armazenamento.

### Motivação
Reduzir custos operacionais mantendo o sistema responsivo e preparado para uso de hardware de forma correta, promovendo eficiência e escalabilidade.

### Alternativas
- **Manual Scaling**: Ajustar a quantidade de recursos manualmente para cada pico de demanda. A implementação é mais simples mas exige intervenção constante, tornando o processo menos ágil.
### Questões Pendentes
- Quais são as dificuldades de implementar o autoscaling e quais são as suas consequências no desempenho da aplicação em situações de alta carga.