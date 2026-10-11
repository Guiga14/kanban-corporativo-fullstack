
#!/bin/sh
set -eu

# ==========================================
# KANBAN CORPORATIVO
# Inicializacao de usuarios do PostgreSQL
# ==========================================

# Verifica se as variaveis obrigatorias existem
: "${DB_USER:?DB_USER nao configurado}"
: "${DB_PASSWORD:?DB_PASSWORD nao configurado}"
: "${FLYWAY_USER:?FLYWAY_USER nao configurado}"
: "${FLYWAY_PASSWORD:?FLYWAY_PASSWORD nao configurado}"
: "${POSTGRES_USER:?POSTGRES_USER nao configurado}"
: "${POSTGRES_DB:?POSTGRES_DB nao configurado}"

echo "Configurando usuarios e permissoes do Kanban..."

# Cria os usuarios sem privilegios administrativos
psql -v ON_ERROR_STOP=1 \
     --username "$POSTGRES_USER" \
     --dbname "$POSTGRES_DB" \
     -v app_user="$DB_USER" \
     -v app_password="$DB_PASSWORD" \
     -v migrator_user="$FLYWAY_USER" \
     -v migrator_password="$FLYWAY_PASSWORD" \
     -v database_name="$POSTGRES_DB" <<'SQL'

SELECT format(
    'CREATE ROLE %I LOGIN NOSUPERUSER NOCREATEDB NOCREATEROLE PASSWORD %L',
    :'app_user', :'app_password'
) \gexec

SELECT format(
    'CREATE ROLE %I LOGIN NOSUPERUSER NOCREATEDB NOCREATEROLE PASSWORD %L',
    :'migrator_user', :'migrator_password'
) \gexec

-- Permite que os usuarios se conectem ao banco
GRANT CONNECT ON DATABASE :"database_name" TO :"app_user";
GRANT CONNECT ON DATABASE :"database_name" TO :"migrator_user";


-- ==========================================
-- PERMISSOES DO ESQUEMA PUBLIC
-- ==========================================

-- Permite que a aplicacao utilize o esquema
GRANT USAGE ON SCHEMA public TO :"app_user";

-- Permite que o Flyway utilize e crie objetos
GRANT USAGE, CREATE ON SCHEMA public TO :"migrator_user";

-- ==========================================
-- PERMISSOES PARA NOVAS TABELAS
-- ==========================================

-- Tabelas criadas futuramente pelo Flyway
-- receberao permissoes para a aplicacao
ALTER DEFAULT PRIVILEGES
FOR ROLE :"migrator_user"
IN SCHEMA public
GRANT SELECT, INSERT, UPDATE, DELETE
ON TABLES TO :"app_user";

-- ==========================================
-- PERMISSOES PARA NOVAS SEQUENCIAS
-- ==========================================

ALTER DEFAULT PRIVILEGES
FOR ROLE :"migrator_user"
IN SCHEMA public
GRANT USAGE, SELECT
ON SEQUENCES TO :"app_user";

SQL