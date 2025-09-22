# The software clients should not be affected by the changes (if any) in the API, except in extreme cases.

---

## Design Objective

O objetivo principal deste design é garantir que alterações na API não impactem de forma negativa os clientes, exceto em casos extremos, promovendo estabilidade e continuidade no consumo dos serviços.

---

## Quality Attribute Scenarios

| **Element**          | **Statement**                                                                           |
|----------------------|-----------------------------------------------------------------------------------------|
| **Stimulus**         | Existe a necessidade de realizar mudanças na API sem impactar os clientes.              |
| **Stimulus source**  | A equipa de desenvolvimento introduz alterações na API.                                 |
| **Environment**      | Ambiente de produção com vários clientes a consumir a API.                              |
| **Artifact**         | API do sistema.                                                                         |
| **Response**         | Alterações devem ser compatíveis com versões anteriores sempre que possível.            |
| **Response measure** | Nenhum cliente deve sofrer impactos em mais de X% das suas funcionalidades ou chamadas. |

---

## Technical Memo

### Problema
As alterações na API podem causar impactos negativos nos clientes que a consomem, interrompendo os seus serviços ou exigindo mudanças significativas no consumo da API, o que deve ser evitado para garantir a estabilidade do sistema.

### Resumo da Solução
Adotar **Strangler Fig** para permitir uma progressão gradual de versões da API, permitindo transações graduais para os clientes, minimizando o risco de interrupção dos serviços disponibilizados pela API.

### Fatores
Necessidade de manter a compatibilidade com as versões anteriores enquanto se faz a transição para a nova versão. A importância de acompanhar o comportamento da API durante a transição para identificar e resolver rapidamente quaisquer problemas que possam surgir.

### Solução
- **Strangler Fig**: Utilizar este padrão para substituir partes atuais da API por novas versões de forma gradual, minimizando o impacto nos clientes existentes.
- **Messaging**: Utilizar comunicação assíncrona vai permitir incluir atualizações na API sem uma necessidade de integração imediata.
- **Domain Events**: O uso de **Domain Events** vai facilitar a comunicação de mudanças importantes na API.

### Motivação
Evitar interrupções no consumo da API é fundamental para manter a confiança e satisfação dos clientes, além de garantir a evolução contínua do sistema.

### Alternativas
- Remover a API existente e criar uma nova versão do zero.
- Utilizar um API Gateway para encaminhar as chamadas para as versões antigas ou novas da API.

### Questões Pendentes
- Como deverá ser o processo de migração entre versões da API?
- Como garantir que mudanças na API serão compatíveis com o sistema?