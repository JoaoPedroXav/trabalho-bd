# Sistema de Resolução de Casos Clínicos
Este repositório contém o trabalho prático da disciplina de Banco de Dados (2ª Nota). O objetivo é demonstrar a integração de uma aplicação Java com um banco de dados relacional PostgreSQL, realizando operações de CRUD e consultas complexas.

## 📌 Sobre o Projeto
O tema escolhido foi um Sistema de Resolução de Casos Clínicos, onde alunos das áreas da saúde (Fisioterapia, Medicina e Odontologia) se cadastram, fazem login e respondem casos clínicos cadastrados no sistema. O professor pode acompanhar as respostas, atribuir notas e feedbacks.

## 🛠️ Tecnologias Utilizadas

- Linguagem: Java 25
- Banco de Dados: PostgreSQL
- Driver de Conexão: JDBC (postgresql-42.7.3)
- Interface: Console (Terminal)
- Gerenciador de Dependências: Maven


## 📂 Estrutura do Repositório

- /diagrama — Modelo Entidade-Relacionamento (DER)
- /ddl — Script de criação das tabelas e restrições
- /dml — Scripts de inserção, atualização e deleção
- /dql — Scripts de consulta (SELECT, JOINs, Filtros, Ordenação)
- /src — Código-fonte Java (CasoClinico.java e Conexao.java)


## 🚀 Como Executar o Projeto
Configuração do Banco de Dados

- No pgAdmin, crie um banco chamado casos_clinicos
- Execute o script /ddl/ddl.sql para criar as tabelas
- Execute o script /dml/dml.sql para inserir os casos clínicos

## Executando em Java

- Importe o projeto no NetBeans como projeto Maven
- No arquivo Conexao.java, ajuste as credenciais se necessário:

- javaprivate static final String URL     = "jdbc:postgresql://localhost:5432/casos_clinicos";
- private static final String USUARIO = "postgres";
- private static final String SENHA   = "postgres";

- Execute o projeto — o Maven baixa o driver JDBC automaticamente via pom.xml


## 📄 Consultas Complexas

- INNER JOIN: Lista todos os alunos que responderam casos clínicos, exibindo nome, matrícula, curso, caso respondido e nota
- LEFT JOIN: Lista todos os casos clínicos cadastrados, incluindo os que ainda não receberam nenhuma resposta, com total de respostas e média das notas


## 👤 Autor

Aluno: João Pedro Melo Xavier — Centro Universitário Santo Agostinho (UNIFSA)
