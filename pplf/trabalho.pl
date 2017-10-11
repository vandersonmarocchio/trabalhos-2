/* Trabalho de PPLF
*  Diogo Alves de Almeida, Ra: 95108
*
*
*/

inicio:- hipoteses(Doenca),
  nl,
  write('Diagnostico -> '),
  write(Doenca),
  undo.

% respostas
hipoteses('Dermatite com febre'):- dermatite_com_febre, !.
hipoteses('Febre em bebes com menos de 6 meses é muito perigoso!! Procure um médico'):- febre_em_bebe, !.
hipoteses('Otite Interna'):- otite_interna, !.
hipoteses('Não consegui diagnostica-lo procura um médico para mais exames!!'). /*sem diagnóstico*/

% Doenças e seus sintomas
dermatite_com_febre :-
  pergunta('febre?'),
  pergunta('erupção na pele?').
febre_em_bebe :-
  pergunta('febre?'),
  pergunta('menos de 6 meses?').
otite_interna :-
  pergunta('febre?'),
  pergunta('chora e puxa orelha ou acorda gritando?').
pneumonia :-
  pergunta('febre?'),
  pergunta('ritmo respiratório está mais rápido que o normal?').


% Faz as perguntas
:- dynamic sim/1,nao/1.

pergunta(Sintomas) :-
    write('Você está com '),
    write(Sintomas),
    read(Resposta),
    ((Resposta == sim ; Resposta == s)->assert(sim(Sintomas));
    assert(nao(Sintomas)), fail).


% Verifica os sintomas
pergunta(S):-(sim(S)->true; (nao(S)->fail; pergunta(S))).

% desfaz todas as afirmações de sim e não
undo :- retract(sim(_)),fail.
undo :- retract(nao(_)),fail.
undo.
