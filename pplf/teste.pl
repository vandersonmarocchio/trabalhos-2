/* Trabalho de PPLF
*  Diogo Alves de Almeida, Ra: 95108
*
*
*/

%chama a função pergunta que faz as perguntas dos sintomas
sintomas(P, Val):-
	pergunta('O paciente',Val).

%vaidação das respostas
pergunta(Obj, Val):-
	resposta(Obj, Val, true),!.
pergunta(Obj, Val):-
	resposta(Obj, Val, false),!, fail.

%função que faz a pergunta a registra (assert) sua resposta
pergunta(Obj, Val):-
	write(Obj),
	write(' '),
	write( Val),
	read(Resposta), !,
	((Resposta=s, assert(resposta(Obj, Val, true)));
	(assert(resposta(Obj, Val, false)),fail)).

%faz o diagnostico
diagnostico:-
	write("Por favor, digite (s/sim) ou (n/nao) para as perguntas:"),
	nl,
	doenca(sintomas,Doenca),!,
	nl,
	write('A sua doença pode ser '),
	write(Doenca).

%entre nesse caso quando não acha nenhuma doença
diagnostico:-
	nl,
	write('Desculpe, mas não fui capaz de diagnosticar a sua doença!!').

% main do programa
inicio:-
	abolish(resposta/3),
	dynamic(resposta/3),
	retractall(resposta/3),
	diagnostico.

%base de dados das doenças e seus sintomas
doenca(Paciente,'Dermatite com Febre'):-
						sintomas(Paciente, 'tem febre?|: '),
						sintomas(Paciente, 'tem erupção na pele?').

doenca(Paciente,'febre em bebe'):-
						sintomas(Paciente,'tem febre?|: '),
						sintomas(Paciente,'tem menos de 6 meses?').

doenca(Paciente,'Otite Interna'):-
						sintomas(Paciente,'tem febre?|: '),
						sintomas(Paciente,'chora e puxa orelha ou acorda gritando?').

doenca(Paciente,'Pneumonia'):-
						sintomas(Paciente,'tem febre?|: '),
						sintomas(Paciente,'está com o ritmo respiratório mais rápido que o normal?').

doenca(Paciente,'Resfriado, ou provavelmente Gripe'):-
						sintomas(Paciente, 'tem febre?|: '),
						sintomas(Paciente, 'tem tosse e coriza?').

doenca(Paciente,'Meningite, ou Infecção no Sistema Urinário \nURGENTE: vá ao médico'):-
						sintomas(Paciente, 'tem febre?|: '),
						sintomas(Paciente, 'tem vomito sem diarreia, sonolencia anormal e irritabilidade incomum?').

doenca(Paciente,'Resfriado, ou provavelmente Gripe'):-
						sintomas(Paciente, 'tem febre?|: '),
						sintomas(Paciente, 'tem tosse e coriza?').
