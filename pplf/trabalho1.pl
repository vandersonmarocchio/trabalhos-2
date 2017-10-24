/*  Nome: Trabalho de PPLF
*   Autor: Diogo Alves de Almeida, Ra: 95108
*   Professor: Wagner Igarashi
*   Descrição: Para esse trabalho foi utilizado o predicado dinâmico cuja a definição pode ser alterada em tempo de
*   execução, por meio da inclusão ou exclusão de cláusulas (fatos ou ou regras).
*   Referências:  https://www.ime.usp.br/~slago/pl-9.pdf
*                 http://www.cse.unsw.edu.au/~billw/dictionaries/prolog/dynamic.html
*	          http://www.swi-prolog.org/pldoc/man?predicate=dynamic/1
*/

inicio:-
  write('# # # # # # # DIAGNÓSTICO DE DOENÇAS # # # # # # # \n\n'),
  primeiro_sintoma,
  write('\n\n# # # # # Obrigado por usar o programa!! # # # # #'),
  reset.

% Respostas
% Febre e menor de 1 ano
febre_menor('Febre em bebes com menos de 6 meses é muito perigoso. Procure um médico!!'):- febre_em_bebe, !.
febre_menor('Dermatite com febre.'):- dermatite_com_febre, !.
febre_menor('Otite Interna.'):- otite_interna, !.
febre_menor('Pneumonia.') :- pneumonia, !.
febre_menor('Resfriado ou, possivelmente gripe!') :- resfriado, !.
febre_menor('Meningite ou infecção no sistema urinário. \nURGENTE: Procure orientação médica imediatamente') :- meningite, !.
febre_menor('Infecção de garganta.'):- garganta, !.
febre_menor('Gastroenterite.') :- febre_gastroenterite, !.
febre_menor('Super Aquecido: geralmente bebês não precisam usar mais roupas que os adultos no mesmo ambiente, cuidado!!') :- super_aquecido, !.
febre_menor('Não consegui diagnostica-lo, procure orientação médica!!'). /*sem diagnóstico*/

% Febre e maior de 1 ano
febre_maior('Dermatite com febre.') :- dermatite_com_febre, !.
febre_maior('Meningite. \nURGENTE: Chame uma ambulância!!') :- meningite, !.
febre_maior('Infecção de garganta.'):- garganta, !.
febre_maior('Crupe Viral ou Bronquite.'):- crupe_viral, !.
febre_maior('Caxumba.'):- caxumba, !.
febre_maior('Pneumonia.') :- pneumonia, !.
febre_maior('Infecção no sistema urinário.') :- infeccao_urinario, !.
febre_maior('Gastroenterite.') :- febre_gastroenterite, !.
febre_maior('Otite Interna.') :- otite_interna, !.
febre_maior('Super Aquecido: geralmente bebês não precisam usar mais roupa que os adultos no mesmo ambiente, cuidado!!') :- super_aquecido, !.
febre_maior('Não consegui diagnostica-lo, procure orientação médica!!'). /*sem diagnóstico*/

% Diarreia e menor de 1 ano
diarreia_menor('Gastroenterite.') :- diarreia_menor_gastroenterite, !.
diarreia_menor('Intolerância alimentar é a causa mais provável para a idade. Outras possíveis causas menos comuns: giardíase, doença celíaca ou fibrose cística.') :- intolerancia_alimentar, !.
diarreia_menor('Efeito colateral de medicamento \nProcure orientação médica e pergunte se o medicamento pode estar causando esses sintomas e se pode parar de usá-lo.') :- efeito_colateral, !.
diarreia_menor('Em grandes quantidades, o açucar dos sucos e polpas de fruta pode causar diarreia.') :- muito_acucar, !.
diarreia_menor('Novos alimentos podem causar diarreia, mas por um período curto.') :- novo_alimento, !.
diarreia_menor('Gastroenterite branda, intolerância alimentar ou alergia alimentar.').

% Diarreia e maior de 1 ano
diarreia_maior('Gastroenterite') :- diarreia_maior_gastroenterite, !.
diarreia_maior('Eliminação de feses em resultado de constipação crônica') :- constipacao_cronica, !.
diarreia_maior('A diarreia pode ser um efeito colateral do medicamento que está tomando.') :- efeito_colateral, !.
diarreia_maior('Diarreia de criança pequena') :- diarreia_crianca_pequena, !.
diarreia_maior('Intolerância alimentar, alergia alimentar ou giardíase.\n Outras doenças muito menos comuns: doença celíaca e fibrose cística').

% Doenças e seus sintomas
dermatite_com_febre :-
  pergunta('tem erupção na pele?').
febre_em_bebe :-
  pergunta('tem menos de 6 meses?').
otite_interna :-
  pergunta('chora e puxa orelha ou acorda gritando?').
pneumonia :-
  pergunta('está com o ritmo respiratório mais rápido que o normal?').
resfriado :-
  pergunta('tem tosse ou coriza?').
meningite :-
  pergunta('tem vômito sem diarreia, sonolência anormal e irritabilidade incomum?').
garganta :-
  pergunta('rejeita comida sólida ou está com a garganta inflamada?').
febre_gastroenterite :-
  pergunta('sofre de vômito com diarreia?').
diarreia_menor_gastroenterite :-
  pergunta('tem febre?');
  pergunta('teve nos ultimos dias: vômito, pouco apetite e letargia?').
diarreia_maior_gastroenterite :-
  pergunta('está com diarreia nos ultimos 3 dias?'),
  pergunta('tem dores abdominais, vômito e febre?').
super_aquecido :-
  pergunta('está com muita roupa ou em um local muito aquecido?').
crupe_viral :-
  pergunta('tem tosse ou coriza?'),
  pergunta('está com a respiração incomumente ruidosa?').
caxumba :-
  pergunta("tem inchaço em um lado da face?").
infeccao_urinario :-
  pergunta('precisa urinar mais do que o normal ou se queixa de dor ao urinar?').
intolerancia_alimentar :-
  pergunta('está com diarreia por duas semanas ou mais?').
efeito_colateral :-
  pergunta('tem tomado algum medicamento nos ultimos dias para outro problema?').
muito_acucar :-
  pergunta('tem tomado mais sucos ou polpas de fruta que o normal?').
novo_alimento :-
  pergunta('comeu algum alimento novo nas ultimas 24 hrs?').
constipacao_cronica :-
  pergunta('tem constipação e diarreia ao mesmo tempo?').
diarreia_crianca_pequena :-
  pergunta('tem fezes com pedacinhos de comida?'),
  pergunta('tem menos de 3 anos?').


% Primeira página
febre_menor :-
  febre_menor(Doenca),
  nl,
  write('Diagnóstico: '),
  write(Doenca).

% Segunda página
febre_maior :-
  febre_maior(Doenca),
  nl,
  write('Diagnóstico: '),
  write(Doenca).

% Terceira página
diarreia_menor :-
  diarreia_menor(Doenca),
  nl,
  write('Diagnóstico: '),
  write(Doenca).

% Quarta página
diarreia_maior :-
  diarreia_maior(Doenca),
  nl,
  write('Diagnóstico: '),
  write(Doenca).

diarreia :-
  write('O Paciente tem menos de 1 ano?'),
  read(Resposta),
  (Resposta == sim; Resposta == s) -> diarreia_menor;
  diarreia_maior.

febre :-
  write('O Paciente tem menos de 1 ano?'),
  read(Resposta),
  (Resposta == sim; Resposta == s) -> febre_menor;
  febre_maior.

% Verifica o primeito sintoma
primeiro_sintoma :-
  write('O Paciente tem febre?|: '),
  read(Resposta),
  (Resposta == sim; Resposta == s) -> febre;
  write('O Paciente tem diarreia?'),
  read(Resposta),
  (Resposta == sim; Resposta == s) -> diarreia;
  write('Infelizmente não vou conseguir iagnostica-lo, procure orientação médica!!').

% Faz as perguntas
:- dynamic sim/1,nao/1.
pergunta(Sintomas) :-
  write('O Paciente '),
  write(Sintomas),
  read(Resposta),
  ((Resposta == sim ; Resposta == s)->assert(sim(Sintomas));
  assert(nao(Sintomas)), fail).

% Verifica os sintomas
pergunta(S):-(sim(S)->true; (nao(S)->fail; pergunta(S))).

% Desfaz todas as afirmações de sim e não
reset :- retract(sim(_)),fail.
reset :- retract(nao(_)),fail.
reset.
