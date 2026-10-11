# 🕹️ Kanban Corporativo Full-stack

`guiga14@github:~/kanban $ ./start`

**Sistema de gerenciamento de tarefas | Java 21 • Spring Boot 4 • React • PostgreSQL • Docker**

Aplicação full-stack de gerenciamento de tarefas, equipes e responsáveis, com filtros e indicadores de produtividade. O back-end é uma API REST em Java e Spring Boot; a interface React utiliza uma estética Retro/Pixel Art.

Este projeto foi desenvolvido para praticar modelagem relacional, persistência de dados, integração entre front-end e back-end, containerização e organização de aplicações.

## 📸 Demonstração

| Painel principal | Filtros dinâmicos |
| :---: | :---: |
| ![Painel principal](./assets/painel.png) | ![Filtros](./assets/filtros.png) |

| Indicadores | Gerenciamento de tarefas |
| :---: | :---: |
| ![Indicadores](./assets/metricas.png) | ![Formulário de tarefa](./assets/modal.png) |

## ⚙️ Tecnologias

| Camada | Tecnologias |
| --- | --- |
| Back-end | Java 21, Spring Boot 4.1.1, Spring Data JPA, Hibernate, Maven |
| Front-end | React, JavaScript, Vite, Tailwind CSS |
| Banco de dados | PostgreSQL 16 |
| Infraestrutura | Docker, Docker Compose, Nginx |
| Versionamento | Git e GitHub |

## 🚀 Funcionalidades

- CRUD de tarefas, incluindo alteração de status.
- Organização por equipes e responsáveis.
- Filtros de tarefas por usuário ou equipe.
- Indicadores de tarefas totais, pendentes, em andamento, concluídas e atrasadas.
- Persistência de dados com JPA e PostgreSQL.
- Interface com identidade visual Retro/Pixel Art.

## 🏗️ Arquitetura

```text
Frontend (React + Nginx)
        │ HTTP
        ▼
API REST (Java 21 + Spring Boot)
        │ JPA / Hibernate
        ▼
PostgreSQL 16
```

O projeto contém as pastas `frontend/` e `backend/` e um `docker-compose.yml` na raiz. O Docker Compose define três serviços:

| Serviço | Papel | Acesso local |
| --- | --- | --- |
| `frontend` | Interface React servida pelo Nginx | http://localhost:5173 |
| `api` | API Spring Boot | http://localhost:8080 |
| `db` | PostgreSQL | localhost:5432 |

O volume `postgres_data` preserva os dados entre recriações dos contêineres, desde que não seja removido.

## 🔌 Endpoints da API

URL base local: `http://localhost:8080`

| Método | Rota | Operação |
| --- | --- | --- |
| `GET` | `/tasks` | Listar tarefas; filtros opcionais `userId` e `teamId` |
| `GET` | `/tasks/{id}` | Buscar tarefa |
| `POST` | `/tasks` | Criar tarefa |
| `PUT` | `/tasks/{id}` | Atualizar tarefa |
| `DELETE` | `/tasks/{id}` | Excluir tarefa |
| `GET` | `/teams` | Listar equipes |
| `POST` | `/teams` | Criar equipe |
| `DELETE` | `/teams/{id}` | Excluir equipe |
| `GET` | `/users` | Listar usuários |
| `POST` | `/users` | Criar usuário |
| `DELETE` | `/users/{id}` | Excluir usuário |
| `GET` | `/metrics` | Consultar indicadores |
| `GET` | `/hello` | Testar resposta simples da API |

Exemplo de corpo para criação de tarefa (IDs de usuário e equipe devem existir):

```json
{
  "title": "Revisar documentação",
  "description": "Atualizar o README do projeto",
  "status": "TODO",
  "dueDate": "2026-10-15",
  "userId": 1,
  "teamId": 1
}
```

## 🔐 Configuração e segurança

As credenciais são configuradas por variáveis de ambiente. O arquivo `.env` é ignorado pelo Git para evitar a publicação de informações sensíveis.

1. Copie `.env.example` para `.env` na raiz do projeto.
2. Configure `DB_NAME`, `DB_USER`, `DB_PASSWORD` e `POSTGRES_ADMIN_PASSWORD`.
3. Configure `FLYWAY_USER` e `FLYWAY_PASSWORD`.
4. Não publique arquivos `.env`, senhas ou backups do PostgreSQL.

### Separação de permissões

O PostgreSQL utiliza dois usuários com responsabilidades distintas:

- `kanban_app`: utilizado pela aplicação para consultar e modificar dados, sem privilégios administrativos.
- `kanban_migrator`: utilizado pelo Flyway para executar migrações e gerenciar a estrutura do banco.

Em instalações novas, o script `database/init/01-init-users.sh` cria os usuários e configura automaticamente suas permissões.

### Migrações com Flyway

O Flyway gerencia o versionamento do esquema do PostgreSQL.

A migração inicial está localizada em:

`backend/src/main/resources/db/migration/V1__create_initial_tables.sql`

Em um banco vazio, essa migração cria as tabelas `users`, `teams` e `tasks`, incluindo suas chaves primárias e relacionamentos.

O Hibernate utiliza `spring.jpa.hibernate.ddl-auto=validate`, verificando a estrutura do banco sem modificá-la automaticamente.

O histórico das migrações executadas é armazenado na tabela `flyway_schema_history`.


## 🐳 Executando com Docker

**Pré-requisitos:** Git, Docker e Docker Compose.

### 1. Clonar o repositório

```bash
git clone https://github.com/Guiga14/kanban-corporativo-fullstack.git
cd kanban-corporativo-fullstack
```

### 2. Configurar as variáveis de ambiente

Copie o arquivo `.env.example` para `.env`.

No Windows (CMD):

```bat
copy .env.example .env
```

Configure as senhas no arquivo `.env`, mantendo separadas as credenciais do PostgreSQL, da aplicação e do Flyway.

### 3. Iniciar a aplicação

```bash
docker compose up -d --build
```

Em uma instalação com banco vazio:

- O PostgreSQL cria o banco de dados.
- O script `01-init-users.sh` cria os usuários e configura suas permissões.
- O Flyway executa a migração V1 e cria as tabelas.
- O Spring Boot valida o esquema com Hibernate.

A inicialização automática foi validada em um ambiente PostgreSQL isolado.

### 4. Acessar o sistema

- Front-end: http://localhost:5173
- API REST: http://localhost:8080

Para verificar o estado dos serviços:

```bash
docker compose ps
docker compose logs --tail=50 api
```

### 5. Encerrar os serviços

```bash
docker compose down
```

O volume `postgres_data` mantém os dados entre recriações dos contêineres.

**Atenção:** não execute `docker compose down -v` caso queira preservar os dados do PostgreSQL.

### Testes de integração

O arquivo `docker-compose.test.yml` fornece um ambiente PostgreSQL separado para validar a inicialização automática.

```bash
docker compose -p kanban-init-test -f docker-compose.test.yml up -d db-test
```

Para executar a API de teste, primeiro construa a imagem da API principal, se ainda não existir, e depois utilize o perfil `integration`:

```bash
docker compose build api
docker compose -p kanban-init-test -f docker-compose.test.yml --profile integration up -d api-test
```

O ambiente de teste utiliza seu próprio volume, `postgres_init_test_data`, sem compartilhar os dados do banco principal.

**Observação:** o banco deve estar disponível antes da inicialização da API. Em algumas máquinas, pode ser necessário aguardar o PostgreSQL ficar pronto e reiniciar a API.


## 📚 Aprendizados e próximos passos

Este projeto reúne conhecimentos práticos de desenvolvimento e infraestrutura:

- Desenvolvimento de APIs REST com Java 21 e Spring Boot 4.
- Integração entre React e Spring Boot.
- Modelagem relacional e persistência com JPA/Hibernate.
- Containerização de aplicações com Docker Compose.
- Versionamento do banco de dados com Flyway.
- Inicialização automatizada do PostgreSQL.
- Separação de permissões entre aplicação e migrações.
- Gerenciamento de credenciais com variáveis de ambiente.
- Testes de integração em ambiente isolado.
- Versionamento com Git e Conventional Commits.

### Próximas melhorias

- Implementar testes automatizados adicionais para a API.
- Adicionar healthchecks ao Docker Compose.
- Melhorar o tratamento de erros e as validações.
- Preparar o projeto para implantação em ambiente de hospedagem.

## 👨‍💻 Autor

**Guilherme Nobre** — Desenvolvimento Back-end com foco em Java e Spring Boot.

[GitHub](https://github.com/Guiga14) • [LinkedIn](https://www.linkedin.com/in/guilherme-alexandrino-nobre-96a4701a9/)

---

`guiga14@github:~$ echo "Building, learning and improving."` 👾
