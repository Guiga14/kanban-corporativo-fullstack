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

As credenciais não ficam fixas nos arquivos de configuração atuais. O Compose recebe os valores de variáveis de ambiente; o arquivo `.env` é ignorado pelo Git.

1. Copie `.env.example` para `.env` na raiz do projeto.
2. Defina valores locais para `DB_NAME`, `DB_USER`, `DB_PASSWORD` e `POSTGRES_ADMIN_PASSWORD`.
3. Não publique o `.env`, credenciais ou backups do banco.

O usuário `kanban_app` é destinado ao acesso da aplicação, com permissões de leitura e escrita sobre os dados necessários, sem privilégios administrativos. A API utiliza `spring.jpa.hibernate.ddl-auto=validate`, que verifica o esquema em vez de criá-lo ou alterá-lo.

> **Estado atual da instalação:** a configuração de um banco novo **ainda não é automática**. É necessário criar as tabelas e o usuário `kanban_app`, definir sua senha e conceder as permissões apropriadas antes de iniciar a API. O Docker Compose inicializa o serviço PostgreSQL, mas não provisiona esse usuário e esse esquema sozinho. Migrações versionadas com **Flyway** estão planejadas como próxima evolução.

## 🐳 Executando com Docker (banco já preparado)

**Pré-requisitos:** Git, Docker e Docker Compose.

```bash
git clone https://github.com/Guiga14/kanban-corporativo-fullstack.git
cd kanban-corporativo-fullstack
```

Prepare o `.env` como descrito acima. Para reproduzir o ambiente já configurado, o PostgreSQL deve conter o esquema do Kanban e o usuário da aplicação, com suas permissões. Em uma instalação do zero, essas etapas ainda são manuais.

Com o banco preparado, execute:

```bash
docker compose up -d --build
```

Verifique os serviços e os logs:

```bash
docker compose ps
docker compose logs --tail=50 api
```

Abra o front-end em **http://localhost:5173**. A API utiliza a porta **8080**.

Para interromper os serviços sem solicitar a exclusão do volume:

```bash
docker compose down
```

> Evite `docker compose down -v` quando quiser preservar os dados do PostgreSQL: a opção `-v` remove os volumes gerenciados pelo Compose.

## 📚 Aprendizados e próximos passos

Este projeto reúne práticas de APIs REST, modelagem de entidades, integração React–Spring Boot, persistência com PostgreSQL, Docker Compose, gerenciamento de credenciais, permissões de banco e commits semânticos.

**Próxima melhoria planejada:** adicionar Flyway para versionar o esquema e facilitar a inicialização de novos ambientes. A documentação de instalação será atualizada quando esse fluxo estiver implementado e testado.

## 👨‍💻 Autor

**Guilherme Nobre** — Desenvolvimento Back-end com foco em Java e Spring Boot.

[GitHub](https://github.com/Guiga14) • [LinkedIn](https://www.linkedin.com/in/guilherme-alexandrino-nobre-96a4701a9/)

---

`guiga14@github:~$ echo "Building, learning and improving."` 👾
