INSERT INTO caso_clinico (titulo, descricao, area, nivel_dificuldade) VALUES
('Paciente com dor lombar crônica',
 'Homem, 45 anos, queixa de dor lombar há 6 meses, piora ao sentar por longo período. Sem irradiação para MMII. Exame: limitação de flexão anterior. Peça a conduta fisioterapêutica.',
 'Fisioterapia', 'Medio'),
 
('Paciente com febre e dispneia',
 'Mulher, 32 anos, febre 38.8°C há 3 dias, tosse produtiva, dor torácica pleurítica. Radiografia com opacidade em base direita. Discuta hipótese diagnóstica e conduta.',
 'Medicina', 'Dificil'),
 
('Paciente com cárie extensa no elemento 46',
 'Criança, 9 anos, cárie profunda no primeiro molar inferior direito. Radiografia mostra proximidade com polpa. Descreva o plano de tratamento odontológico.',
 'Odontologia', 'Facil'),
 
('Reabilitação pós-AVC',
 'Homem, 62 anos, hemiplegia à esquerda após AVC isquêmico há 3 semanas. Apresenta espasticidade em flexores de punho. Elabore programa de reabilitação.',
 'Fisioterapia', 'Dificil'),
 
('Criança com manchas brancas na mucosa oral',
 'Menino, 5 anos, manchas brancas removíveis em mucosa jugal bilateral. Sem febre. Descreva a hipótese diagnóstica e tratamento.',
 'Odontologia', 'Facil');
 
-- EXEMPLO DE ATUALIZAÇÃO: corrigir nivel de dificuldade de um caso
UPDATE caso_clinico
SET nivel_dificuldade = 'Dificil'
WHERE titulo = 'Paciente com dor lombar crônica';
 
-- EXEMPLO DE DELEÇÃO: remover um caso pelo id
DELETE FROM caso_clinico WHERE id_caso = 5;