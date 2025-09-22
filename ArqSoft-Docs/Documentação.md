# Documentação ARQSOFT P2
O segundo projeto foca na realização da reengenharia do projeto base Library Management System (LMS) da sua arquitetura monolítica para uma arquitetura descentralizada baseada em microserviços.
Os principais requisitos não funcionais desta transição da arquitetura do projeto são:
- Disponibilidade
  - O sistema deve  melhorar a sua disponibilidade (estar disponível continuamente, mesmo em caso de falhas ou manutenção).
- Desempenho
  - Deve haver um aumento de pelo menos 25% no desempenho em períodos de alta demanda (>Y solicitações/tempo).
- Uso de Recursos
  - O sistema deve usar hardware de forma eficiente e ajustar de forma correta a carga.
- Releasability
  - Atualizações devem ocorrer sem impacto significativo nos serviços.
- Compatibilidade
  - Alterações na API não devem afetar os clientes, exceto em casos extremos.
- SOA
  - O sistema deve seguir a estratégia de API-led connectivity da empresa,  garantindo que as APIs sejam reutilizáveis, fáceis de integrar e organizadas em camadas

Neste projeto, há um foco significativo na melhoria do desempenho da aplicação, pelo que métricas relacionadas ao desempenho da API, como tempo de resposta, número de requisições por segundo e utilização de recursos, serão analisadas para validar as melhorias esperadas.


## System as Is
Esta secção apresenta uma análise do sistema atual (System as Is) utilizando o modelo arquitetural 4+1. Vão ser explorados os aspectos principais da arquitetura monolítica existente, as suas funcionalidades e limitações, com foco em identificar os desafios que motivam a transição para uma arquitetura baseada em microserviços. 

## Modelo de Arquitetura 4+1 
O modelo 4+1 é uma abordagem para a documentação e análise de arquiteturas de software, organizada em cinco perspetivas complementares:
- **Vista Lógica:**
    - Foca na funcionalidade do sistema, mostrando como os elementos se relacionam para atender aos requisitos.
- **Vista de Processo:**
    - Descreve os aspetos dinâmicos, como a comunicação e o comportamento em tempo de execução.
- **Vista de Implementação:**
    - Representa a estrutura estática do software, incluindo a organização dos módulos e componentes.
- **Vista Física:**
    - Detalha como o sistema será implementado na infraestrutura física, como servidores e redes.
- **Vista de Casos de Uso:**
    - Conecta as outras vistas, descrevendo os requisitos funcionais e como os utilizadores interagem com o sistema.


Neste caso vamos apenas abordar as 4 primeiras vistas, tendo em conta que o principal objetivo deste tópico é uma descrição técnica e da arquitetura do sistema.

### Vista Lógica 
#### Vista Lógica Nível 1
![VLN1 System as Is.png](Imagens%2FSystem-As-Is%2FVLN1%20System%20as%20Is.png)
#### Vista Lógica Nível 2
![VLN2 System as is.png](Imagens%2FSystem-As-Is%2FVLN2%20System%20as%20is.png)
#### Vista Lógica Nível 3
![VLN3 System as is.png](Imagens%2FSystem-As-Is%2FVLN3%20System%20as%20is.png)


### Vista de Implementação
#### Vista de Implementação Nível 1
![VIN1 System as is.png](Imagens%2FSystem-As-Is%2FVIN1%20System%20as%20is.png)
#### Vista de Implementação Nível 2
![VIN2 System as is.png](Imagens%2FSystem-As-Is%2FVIN2%20System%20as%20is.png)
#### Vista de Implementação Nível 3
![VIN3 System as is.png](Imagens%2FSystem-As-Is%2FVIN3%20System%20as%20is.png)
#### Vista de Implementação Nível 4
![VIN4 System as is.png](Imagens%2FSystem-As-Is%2FVIN4%20System%20as%20is.png)


### Vistas de Processos





### Vista Física
![VFN1 System as is.png](Imagens%2FSystem-As-Is%2FVFN1%20System%20as%20is.png)

### Vista Lógica x Vista de Implementação
![VLxVI.png](Imagens%2FSystem-As-Is%2FVLxVI.png)

## System to Be

Esta secção apresenta uma análise do sistema futuro (System to Be) utilizando o modelo arquitetural 4+1. Vão ser explorados os aspectos principais de uma arquitetura orientada a microserviços, as suas funcionalidades e limitações.

### Vista Lógica

![VL N2.svg](Vistas%20L%C3%B3gicas%2FVL%20N2.svg)


### Vista Física

![N2 VF.svg](\ArqSoft-Docs\Vistas Físicas\N2 VF.svg)


### Vista de Processo
#### As a librarian, I want to create a Book, Author and Genre in the same process
![Vista de Processo.svg](Vistas%20de%20Processo%2FAs%20a%20librarian%2C%20I%20want%20to%20create%20a%20Book%2C%20Author%20and%20Genre%20in%20the%20same%20process%2FProject%2FVista%20de%20Processo.svg)
#### As a reader, I want to suggest the acquisition of a new Book
![Vista de Processo.svg](Vistas%20de%20Processo%2FAs%20a%20reader%2C%20I%20want%20to%20suggest%20the%20acquisition%20of%20a%20new%20Book%2FVista%20de%20Processo.svg)
#### As a reader, upon returning a Book, I want to recommend it (positively or negatively)
![Vista de Processo.svg](Vistas%20de%20Processo%2FAs%20a%20reader%2C%20upon%20returning%20a%20Book%2C%20I%20want%20to%20recommend%20it%20%28positively%20or%20negatively%29%2FVista%20de%20Processo.svg)
---
## ADD
### Availability
[Availability.md](ADD%2FAvailability.md)
### Compatibility
[Compatibility.md](ADD%2FCompatibility.md)
### Hardware
[Hardware.md](ADD%2FHardware.md)
### Performance
[Performance.md](ADD%2FPerformance.md)
### Releasability
[Releasability.md](ADD%2FReleasability.md)
### SOA
[SOA_strategy.md](ADD%2FSOA_strategy.md)
### Primeiro Requisito
[Book, Author and Genre in the same process.md](ADD%2FBook%2C%20Author%20and%20Genre%20in%20the%20same%20process.md)
### Segundo Requisito
[Suggest the acquisition of a new Book.md](ADD%2FSuggest%20the%20acquisition%20of%20a%20new%20Book.md)
### Terceiro Requisito
[Recommend Book.md](ADD%2FRecommend%20Book.md)

---

## Microserviços
### Justificação para adoção de microserviços

A adoção de uma arquitetura de microserviços oferece diversas vantagens em comparação com uma abordagem monolítica, especialmente à medida que a aplicação cresce em complexidade e escala.
- **Escalabilidade**: Permite a escalabilidade independente de serviços específicos, otimizando recursos. Por exemplo, se o módulo de "Consulta de Autores" tiver mais uso, apenas ele vai/pode ser escalado.
- **Flexibilidade**: Cada serviço é isolado, o que facilita a realização de alterações, correções ou atualizações em partes específicas sem impactar o resto.
- **Resiliência**: Cada serviço é isolado e independente, ou seja, mesmo que um serviço falhe, outros vão continuar a funcionar. Por exemplo, caso o serviço de Authors falhem, os Books vão continuar a funcionar.
- **Reutilização**: Um serviço pode ser reutilizado em vários projetos. Por exemplo, o serviço de Autenticação pode servir diferentes aplicações.

### **1. Microserviço de Utilizadores e Autenticação (LMSUsers)**
- **Justificação**: Gerir utilizadores e a sua autenticação é um aspecto fundamental do sistema que opera de forma independente dos demais componentes. Este microserviço encapsula funcionalidades como o registo de utilizadores, autenticação e autorização. Ao isolar essas operações, garantimos que preocupações de segurança, como gestão de senhas e geração de tokens, sejam tratadas em um serviço dedicado, reduzindo a complexidade e vulnerabilidades noutras partes do sistema.
- **Impacto**:
  - Escalabilidade independente para lidar com grandes volumes de requisições de login e autenticação.
  - Simplificação da integração com provedores de autenticação de terceiros.
  - Maior segurança ao isolar operações sensíveis dos demais serviços existentes.

### **2. Microserviço de Livros (LMSBooks)**
- **Justificação**: Gerir livros é um domínio central e distinto. Este microserviço é responsável por armazenar e fornecer informações sobre livros, gerir procuras no catálogo de livros e suportar tarefas administrativas.
- **Impacto**:
  - Facilita a escalabilidade independente para atender às demandas de procuras de livros, que podem diferir de outras atividades do sistema.
  - Permite o uso de SGBDs especializados.
  - Simplifica a implementação de recursos como recomendações de livros ou filtros avançados de pesquisa.

### **3. Microserviço de Autores (LMSAuthors)**
- **Justificação**: Autores representam um domínio distinto que requer lógica e gestão próprias. Este microserviço faz a gestão de dados específicos sobre autores, como informações sobre nome  biografia. Ao isolar essa funcionalidade, permitimos que as operações relacionadas a autores evoluam de forma independente dos livros e géneros.
- **Impacto**:
  - Suporte à escalabilidade para recursos centrados em autores, como procuras, filtros e atualizações de autores.
  - Redução da complexidade noutros serviços ao encapsular a lógica específica de autores.

### **4. Microserviço de Géneros (LMSGenres)**
- **Justificação**: Géneros funcionam como um sistema de classificação para livros, sendo um elemento essencial para organização e descoberta. Este microserviço gere o ciclo de vida dos géneros.
- **Impacto**:
  - Garantia de que alterações na estrutura géneros não interfiram nos serviços de livros.
  - Escalabilidade independente para lidar com criação e/ou consultas relacionadas a géneros.
  - Simplificação da integração de futuros recursos, como recomendações baseadas em géneros ou filtros avançados de pesquisa.

### **5. Microserviço de Género Query (LMSGenresQuery)**
- **Justificação**: A pesquisa de géneros é essencial em vários aspetos do LMS pelo que um microserviço de consulta de géneros especializado na pesquisa e recuperação de informações relacionadas a géneros literários  facilita a execução de consultas complexas e otimiza a busca por géneros sem sobrecarregar outros serviços.
- **Impacto**:
  - Melhora a performance das buscas relacionadas a géneros, permitindo a implementação de filtros e índices específicos.
  - Permite a escalabilidade independente para suportar picos de consulta sem afetar os serviços de gestão de géneros.
  - Facilita a implementação de novas funcionalidades, como a busca por géneros mais populares ou mais recentes, sem impactar o desempenho de outros serviços.


  ![5000PedidosGenre.png](Imagens%2FPerformance%2FBase%2F5000PedidosGenre.png)
Na imagem acima vemos os resultados da aplicação monolítica quando lhe é realizado 5000 pedidos a um endpoint que retorna todos os géneros
  ![5000Pedidos Results GET Genres MicroServicos.png](Imagens%2FPerformance%2F2%20Instancias%2F5000Pedidos%20Results%20GET%20Genres%20MicroServicos.png)
Na imagem acima vemos os resultados do microserviço Genres Query quando lhe é requisitado o mesmo, verificando uma melhoria nas métricas mais relevantes, no caso **Throughtput** que indica o número de pedidos que foram processados por segundo.

Relativamente a performance da aplicação monólitica vemos que o teste foi executado em 26.22 segundos, quando no microserviço com duas instâncias foi de 19.46 segundos, demonstrando uma melhoria de 25.79% entre as duas abordagens.

### **6. Microserviço de Empréstimos de Livros (LMSLendings)**
- **Justificação**: O microserviço de empréstimos de livros é responsável pela gestão do processo de empréstimo de livros aos leitores. Ele trata das regras de negócio associadas ao empréstimo, como o registo de datas de empréstimo e devolução, etc. Ao isolar essa funcionalidade, o sistema torna-se mais flexível para lidar com casos complexos de empréstimos e devoluções sem impactar outras partes do sistema.
- **Impacto**:
  - Permite a gestão centralizada dos empréstimos de livros, garantindo que o processo seja fluido.
  - Pode ser escalado independentemente para lidar com picos de demanda, como durante períodos de alta procura por livros ou quando muitos leitores estão a requisitar livros simultaneamente.
  - Pode ser integrado com os microserviços de livros.


### **7. Microserviço de Sugestões de Livros (LMSSuggests)**
- **Justificação**: O microserviço de sugestões de livros permite com que os leitores sugiram a aquisição de novos livros para a plataforma de libraria. Ao permitir que os utilizadores façam essas sugestões, o sistema ganha uma dinâmica colaborativa, onde os leitores podem influenciar diretamente no processo de gestão de livros. Este microserviço realiza a gestão do processo de recebimento, validação e registoo dessas sugestões de novos livros.
- **Impacto**:
  - Permite que o processo de sugestões seja tratado separadamente de outras operações do sistema.
  

### **Porquê esses microserviços e não outros??**
Essa decomposição específica está alinhada com os **contextos delimitados** do sistema, garantindo que cada microserviço foque num domínio bem definido e independente:
- **Coesão Funcional**: Cada serviço encapsula uma responsabilidade distinta, reduzindo interdependências.
- **Escalabilidade**: Os serviços podem escalar de forma independente, melhorando o desempenho e a eficiência de custos.
- **Isolamento de Falhas**: Problemas num serviço não afetam outros.
- **Manutenibilidade**: A lógica específica de cada domínio é encapsulada, facilitando atualizações e melhorias.
- **Extensibilidade**: A arquitetura suporta adições futuras, como a integração de APIs externas ou novos recursos.

---

### **Conclusão**
Ao adotar uma arquitetura baseada em microserviços, lembrando que essa decomposição não é arbitrária, garantimos que o sistema seja modular, escalável e de fácil manutenção. Essa decomposição permite uma clara separação de responsabilidades, alinhamento com os domínios de negócio e suporte a requisitos não funcionais críticos, como segurança, desempenho e disponibilidade. Essa estrutura cria uma base sólida para o crescimento futuro e a adaptação às necessidades dos utilizadores.

# Pipeline CI/CD Jenkins

A pipeline está dividida em 9 stages, incluindo uma secção enviroment e inclui também um serviço SMTP capaz de enviar um email após a execução da mesma com informações relevantes para todos os stakeholders da aplicação a ser desenvolvida.
<br>
![Enviroment.png](Imagens%2FPipeline%2FEnviroment.png)


Na imagem acima vemos o enviroment utilizado na pipeline com informações utéis, desde o repositório e credenciais até ao nome da imagem a ser criada no docker. Estas informações definidas no enviroment serão depois introduzidas em uma ou mais stages da pipeline
<br>
![Stage1-4.png](Imagens%2FPipeline%2FStage1-4.png)


Na imagem acima vemos as 4 primeiras stages da pipeline, onde são exibidas as variavéis do sistema (que nos foi util para a verificação de alguns erros no deploy com o docker), uma stage a verificar a versão do docker instalado, uma a fazer checkout do repositório anteriormente definido no enviroment e um a executar um mvn clean dentro do package de cada pipeline(lms-authnusers).
<br>
![Stage5-7.png](Imagens%2FPipeline%2FStage5-7.png)


Na imagem acima vemos novamente uma série de comando a serem executados dentro da package indicada na pipeline (lms-authunsers), onde se começa por executar um mvn package, seguido do mvn test e mais tarde cria-se a imagem no docker do lms-authnusers
<br>
![Stage8-9.png](Imagens%2FPipeline%2FStage8-9.png)


Na stage 8 temos o deploy no docker do microserviço através do ficheiro run.sh e após o sucesso o shutdown desse microserviço através do ficheiro shutdown.sh

<br>

![PostSuccess.png](Imagens%2FPipeline%2FPostSuccess.png)
<br>

![PostFailure.png](Imagens%2FPipeline%2FPostFailure.png)


Nas imagens acima vemos a configuração para a comunicação via email do resultado da execução da pipeline, de forma a garantir que as partes interessadas sejam notificadas de forma clara e rápida sobre o sucesso ou falha da execução da mesma. Para isso é enviado um email com o resultado da execução da pipeline para o email definido no enviroment

---
## Performance
De forma a entender melhoria da performance entre a aplicação base e a aplicação em microserviços foram realizados testes através da ferramenta JMeter. Nestes testes simulamos 4 diferentes cenários para entender como as aplicações se comportavam.

Esses 4 cenários foram os seguintes: um Load Test, um Soak Test, um Stress Test e por fim um teste para verificar o tempo que demoram a serem processados 5000 pedidos.

Para o Load test consideramos o seguinte cenário, onde simulamos uma carga esperada de utilizadores sobre o sistema e analisamos o seu comportamento, considerando erros todos os pedidos que excediam os 3 segundos.
![Load Test.png](Imagens%2FPerformance%2FLoad%20Test.png)

Para o Soak test consideramos o seguinte cenário, onde simulamos uma carga constante e contínua sobre o sistema por um longo período e analisamos seu comportamento para identificar possíveis falhas ou degradações ao longo do tempo,  considerando erros todos os pedidos que excediam os 3 segundos.
![Soak Test.png](Imagens%2FPerformance%2FSoak%20Test.png)

Para o Stress test consideramos o seguinte cenário, onde simulamos uma carga extrema, além do esperado para o sistema, e analisamos seu comportamento ao atingir ou ultrapassar seus limites de capacidade, considerando erros todos os pedidos que excediam os 5 segundos.
![Stress Test.png](Imagens%2FPerformance%2FStress%20Test.png)


### Performance Aplicação Monolítica
Os resultados da aplicação monolítica foram os seguintes

Load Test:
![Load Result.png](Imagens%2FPerformance%2FBase%2FLoad%20Result.png)
Soak Test:
![Soak Result.png](Imagens%2FPerformance%2FBase%2FSoak%20Result.png)
Stress Test:
![Stress Result.png](Imagens%2FPerformance%2FBase%2FStress%20Result.png)
5000 Pedidos:
![5000Pedidos Results.png](Imagens%2FPerformance%2FBase%2F5000Pedidos%20Results.png)
Para calcularmos o tempo total do pedido, basta utilizarmos o **Throughput** (métrica que identifica o número de pedidos por segundo) e o **número total de pedidos**.

---

**Cálculo**:

**Tempo Total** = Número de Pedidos / Throughput

**Substituindo os valores**:  
### **Tempo Total** = 5000 / 152.8 ≈ 32.71 segundos

---


### Performance da Aplicação baseada em Microserviços

### 2 Instancias do microserviço Books
Load Test:
![Load Result MicroServicos.png](Imagens%2FPerformance%2F2%20Instancias%2FLoad%20Result%20MicroServicos.png)
Soak Test:
![Soak Result MicroServicos.png](Imagens%2FPerformance%2F2%20Instancias%2FSoak%20Result%20MicroServicos.png)
Stress Test:
![Stress Result MicroServicos.png](Imagens%2FPerformance%2F2%20Instancias%2FStress%20Result%20MicroServicos.png)
5000 Pedidos:
![5000Pedidos Results MicroServicos.png](Imagens%2FPerformance%2F2%20Instancias%2F5000Pedidos%20Results%20MicroServicos.png)
Para calcularmos o tempo total do pedido, basta utilizarmos o **Throughput** (métrica que identifica o número de pedidos por segundo) e o **número total de pedidos**.
---

**Cálculo**:

**Tempo Total** = Número de Pedidos / Throughput

**Substituindo os valores**:  
### **Tempo Total** = 5000 / 213 ≈ 23.44 segundos

---

### 3 Instancias do microserviço Books
Load Test:
![Load Result MicroServicos 3 Instancias.png](Imagens%2FPerformance%2F3%20Instancias%2FLoad%20Result%20MicroServicos%203%20Instancias.png)
Soak Test:
![Soak Result MicroServicos 3 Instancias.png](Imagens%2FPerformance%2F3%20Instancias%2FSoak%20Result%20MicroServicos%203%20Instancias.png)
Stress Test:
![Stress Result MicroServicos 3 Instancias.png](Imagens%2FPerformance%2F3%20Instancias%2FStress%20Result%20MicroServicos%203%20Instancias.png)
5000 Pedidos:
![5000Pedidos Results MicroServicos 3 Instancias.png](Imagens%2FPerformance%2F3%20Instancias%2F5000Pedidos%20Results%20MicroServicos%203%20Instancias.png)
Para calcularmos o tempo total do pedido, basta utilizarmos o **Throughput** (métrica que identifica o número de pedidos por segundo) e o **número total de pedidos**.
---

**Cálculo**:

**Tempo Total** = Número de Pedidos / Throughput

**Substituindo os valores**:  
### **Tempo Total** = 5000 / 172 ≈ 28.94 segundos




---
### Conclusão
Com base nos resultados, observa-se uma performance consistente e positiva do endpoint em todas as situações testadas, tanto na aplicação monolítica quanto na arquitetura baseada em microserviços.

Embora os resultados sejam próximos, destaca-se uma melhoria de aproximadamente 28% no tempo de resposta para 5000 requisições, conforme demonstrado no cálculo abaixo. Além disso, os tempos médios registados são amplamente similares aos da aplicação base. A baixa porcentagem de erros reforça que a aplicação suporta bem os cenários que simulamos.

---

**Fórmula:**

Melhoria (%) = (Tempo inicial - Tempo final) / Tempo inicial × 100

---

**Cálculo:**

Tempo Inicial = 32.71 segundos  
Tempo Final = 23.44 segundos

Melhoria (%) = (32.71 - 23.44) / 32.71 × 100

Substituindo os valores:  
### **Melhoria (%) = 9.27 / 32.71 × 100 ≈ 28,34%**

---

