# 👾 Kanban Corporativo (Full-Stack)

Um sistema completo de gerenciamento de tarefas (estilo Kanban) com um design exclusivo inspirado em interfaces retrô e pixel art. Este projeto foi desenvolvido para demonstrar a integração de uma API RESTful robusta em Java com uma interface moderna e interativa em React.

## 🚀 Tecnologias Utilizadas

### Back-end (API REST)
* **Java 17+**
* **Spring Boot** (Web, Data JPA, Validation)
* **PostgreSQL** (Banco de dados relacional)
* **Padrão DTO** (Data Transfer Object) utilizando `records`
* **Arquitetura em Camadas** (Controller, Service, Repository)

### Front-end
* **React.js** (com Vite)
* **Tailwind CSS** (Estilização avançada e customizada)
* **Drag and Drop API** (Para movimentação dos cards)
* **Integração assíncrona** com `fetch`

## ✨ Funcionalidades Principais

* **Gestão de Tarefas (CRUD):** Criação, edição, visualização e exclusão de tarefas.
* **Relacionamentos de Banco de Dados:** Tarefas estão vinculadas a **Usuários** e **Equipes** (`@ManyToOne`).
* **Dashboard de Métricas (High Scores):** Cálculo dinâmico de tarefas totais, pendentes, em progresso, concluídas e **atrasadas**.
* **Filtros Dinâmicos:** Filtros rápidos por Equipe, Responsável e Prazo diretamente na interface.
* **Drag-and-Drop:** Arraste e solte os cards entre as colunas (A FAZER, EM PROGRESSO, CONCLUÍDAS) para atualizar o status automaticamente no banco de dados.
* **Validação de Dados:** Proteção contra campos nulos ou inválidos tanto no front-end quanto no back-end.

## 🛠️ Como Executar o Projeto Localmente

### Pré-requisitos
* Java 17 ou superior
* Node.js e npm
* PostgreSQL instalado e rodando

### 1. Configurando o Back-end
1. Navegue até a pasta `backend`.
2. Abra o arquivo `src/main/resources/application.properties` e configure as credenciais do seu banco de dados PostgreSQL:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/seu_banco
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   spring.jpa.hibernate.ddl-auto=update
---

### 👨‍💻 Desenvolvido por

**Guilherme Nobre**  
*Estudante de Análise e Desenvolvimento de Sistemas | Desenvolvedor Back-end & Full-Stack*  

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/guilherme-alexandrino-nobre-96a4701a9/)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/Guiga14)