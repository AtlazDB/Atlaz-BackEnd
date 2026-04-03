# Server -  Sistema de Controle e Análise de Abastecimento
API REST feita em Java usando o Spring Boot Framework para o [Sistema de Controle e Análise de Abastecimento](https://github.com/AtlazDB/Atlaz)
# 🛠️ Pré-requisitos para rodar o projeto
-  **JDK 17+** - [Download](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- **Docker Desktop** - [Download](https://www.docker.com/get-started/)
- **Git** - [Download](https://git-scm.com/downloads)
# 🚀 Passos para executar a aplicação
<ol>

<li>  <strong> Clone o repositório e navegue até o diretório do projeto </strong> </li>
  
```bash
git clone https://github.com/AtlazDB/Atlaz-BackEnd.git
cd Atlaz-BackEnd/AtlazDB/
```

<li> <strong> Executar o projeto conforme seu sistema </strong> </li>

> [!IMPORTANT]
> Certifique-se de que o **Docker Desktop** esteja aberto. O Spring Boot gerenciará os containers automaticamente através do _Docker Compose Support_.

| Windows     | `.\mvnw spring-boot:run`                  |
| ----------- | ----------------------------------------- |
| **Linux/MacOS** | **`chmod +x mvnw && ./mvnw spring-boot:run`** |

</ol>

# 🧪 Como rodar os testes 
Com o projeto já clonado, execute: 

| Windows:         | `.\mvnw test` |
| ---------------- | ------------- |
| **Linux/MacOS:** | `./mvnw test` |


