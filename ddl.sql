CREATE TABLE aluno (
id_aluno SERIAL PRIMARY KEY,
nome VARCHAR(100) NOT NULL,
matricula VARCHAR(20) UNIQUE NOT NULL,
email VARCHAR(100) NOT NULL,
curso VARCHAR(50) NOT NULL,
senha VARCHAR(100) NOT NULL
);
 
CREATE TABLE caso_clinico (
id_caso SERIAL PRIMARY KEY,
titulo VARCHAR(150) NOT NULL,
descricao TEXT NOT NULL,
area VARCHAR(50) NOT NULL,
nivel_dificuldade VARCHAR(20) NOT NULL CHECK (nivel_dificuldade IN ('Facil', 'Medio', 'Dificil')),
data_cadastro DATE DEFAULT CURRENT_DATE
);
 
CREATE TABLE resposta (
id_resposta SERIAL PRIMARY KEY,
texto_resposta TEXT NOT NULL,
nota DECIMAL(4,2) CHECK (nota >= 0 AND nota <= 10),
feedback TEXT,
data_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
id_aluno INT NOT NULL REFERENCES aluno(id_aluno) ON DELETE CASCADE,
id_caso INT NOT NULL REFERENCES caso_clinico(id_caso) ON DELETE CASCADE
);