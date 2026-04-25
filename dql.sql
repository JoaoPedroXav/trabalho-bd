-- Listar todos os casos clínicos ordenados por dificuldade
SELECT id_caso, titulo, area, nivel_dificuldade, data_cadastro
FROM caso_clinico
ORDER BY nivel_dificuldade, area;
 
-- Filtrar casos por área
SELECT id_caso, titulo, descricao, nivel_dificuldade
FROM caso_clinico
WHERE area = 'Fisioterapia'
ORDER BY nivel_dificuldade;
 
-- Listar respostas de um aluno específico ordenadas por data
SELECT r.id_resposta, cc.titulo, r.texto_resposta, r.nota, r.data_envio
FROM resposta r
INNER JOIN caso_clinico cc ON r.id_caso = cc.id_caso
WHERE r.id_aluno = 1
ORDER BY r.data_envio DESC;
 
-- INNER JOIN: alunos com suas respostas e os casos respondidos
-- (retorna apenas alunos que responderam ao menos um caso)
SELECT
a.nome AS aluno, a.matricula, a.curso, cc.titulo AS caso, cc.area, r.nota, r.data_envio
FROM resposta r
INNER JOIN aluno a ON r.id_aluno = a.id_aluno
INNER JOIN caso_clinico cc ON r.id_caso  = cc.id_caso
ORDER BY a.nome, r.data_envio DESC;
 
-- LEFT JOIN: todos os casos, incluindo os que não possuem nenhuma resposta
-- (útil para o professor ver quais casos estão sem interesse dos alunos)
SELECT cc.titulo AS caso, cc.area, cc.nivel_dificuldade,
COUNT(r.id_resposta) AS total_respostas,
AVG(r.nota) AS media_nota
FROM caso_clinico cc
LEFT JOIN resposta r ON cc.id_caso = r.id_caso
GROUP BY cc.id_caso, cc.titulo, cc.area, cc.nivel_dificuldade
ORDER BY total_respostas DESC;
 
-- LEFT JOIN: todos os alunos, mesmo os que nunca responderam nenhum caso
SELECT a.nome, a.matricula, a.curso,
COUNT(r.id_resposta) AS total_respostas
FROM aluno a
LEFT JOIN resposta r ON a.id_aluno = r.id_aluno
GROUP BY a.id_aluno, a.nome, a.matricula, a.curso
ORDER BY total_respostas DESC;
 
-- Filtrar respostas com nota acima de 8, ordenado por nota decrescente
SELECT a.nome AS aluno, cc.titulo AS caso, r.nota, r.feedback
FROM resposta r
INNER JOIN aluno a ON r.id_aluno = a.id_aluno
INNER JOIN caso_clinico cc ON r.id_caso  = cc.id_caso
WHERE r.nota >= 8.0
ORDER BY r.nota DESC;
 
-- Média de notas por aluno
SELECT a.nome, a.curso,
COUNT(r.id_resposta) AS casos_respondidos,
ROUND(AVG(r.nota), 2) AS media_geral
FROM aluno a
INNER JOIN resposta r ON a.id_aluno = r.id_aluno
GROUP BY a.id_aluno, a.nome, a.curso
ORDER BY media_geral DESC;