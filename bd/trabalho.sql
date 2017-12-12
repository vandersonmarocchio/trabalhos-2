create database if not exists trabalho;
 use trabalho;

 create table fornecedor (
     id_fornecedor int AUTO_INCREMENT,
     nome varchar(50) not null,
     telefone varchar(15) not null,
     endereco varchar(100) not null,
     primary key (id_fornecedor));

 create table funcionario (
     id_funcionario int AUTO_INCREMENT,
     nome varchar(50) not null,
     cpf varchar(15) not null,
     rg varchar(15) not null,
     nascimento date not null,
     endereco varchar(100) not null,
     telefone varchar(15) not null,
     primary key (id_funcionario));

 create table garagem (
     id_garagem int AUTO_INCREMENT,
     nome varchar(50) not null,
     capacidade_item int not null,
     capacidade_maquina int not null,
     primary key (id_garagem));

 create table maquina (
     id_maquina int AUTO_INCREMENT,
     tipo varchar(15) not null,
     modelo varchar(15) not null,
     horas_trabalhadas int not null,
     cor varchar(15) not null,
     ano int not null,
     id_garagem int not null,
     foreign key (id_garagem) references garagem(id_garagem),
     primary key (id_maquina));

 create table item (
     id_item int AUTO_INCREMENT,
     nome varchar(50) not null,
     tipo int not null,
     descricao varchar(100) not null,
     horas_duracao int,
     id_garagem int not null,
     quantidade int,
     foreign key (id_garagem) references garagem(id_garagem),
     primary key (id_item));

 create table maquina_item (
     id_item int not null,
     id_maquina int not null,
     quantidade int not null,
     horas_trabalhadas int,
     foreign key (id_maquina) references maquina(id_maquina),
     foreign key (id_item) references item(id_item));

 create table manutencao (
     id_manutencao int AUTO_INCREMENT,
     data_manutencao date not null,
     id_funcionario int not null,
     id_maquina int not null,
     foreign key (id_funcionario) references funcionario(id_funcionario),
     foreign key (id_maquina) references maquina(id_maquina),
     primary key (id_manutencao));

 create table usuario (
     nome varchar(50) not null,
     login varchar(50) not null,
     senha varchar(50) not null,
     primary key (login));

 create table manutencao_item(
     id_manutencao int not null,
     id_item int not null,
     data_manutencao date not null,
     foreign key (id_manutencao) references manutencao(id_manutencao),
     foreign key (id_item) references item(id_item));

 create table fornecedor_item(
     id_fornecedor int not null,
     id_item int not null,
     data_compra date not null,
     valor_compra float not null,
     foreign key (id_fornecedor) references fornecedor(id_fornecedor),
     foreign key (id_item) references item(id_item));

 create table trabalho(
     id_trabalho int AUTO_INCREMENT,
     id_funcionario int not null,
     id_maquina int not null,
     data_trabalho date not null,
     horas_trabalhadas float not null,
     foreign key (id_funcionario) references funcionario(id_funcionario),
     foreign key (id_maquina) references maquina(id_maquina),
     primary key (id_trabalho));

 DELIMITER //
 CREATE PROCEDURE `AdicionaItem`(nomee varchar(50) , tipoo int , descricaoo varchar(100) , horas_duracaoo int , id_garagemm int , quantidadee int )
 BEGIN
     declare qtd int;
     SET qtd = (SELECT capacidade_item FROM garagem WHERE id_garagem = id_garagemm);

     IF qtd >= quantidadee THEN
 	insert into item (nome, tipo, descricao, horas_duracao, id_garagem, quantidade)
 	values (nomee, tipoo, descricaoo, horas_duracaoo, id_garagemm, quantidadee);

         UPDATE garagem SET capacidade_item = capacidade_item  quantidadee
         WHERE id_garagem = id_garagemm;
     ELSE
 	select 'Sem capacidade nessa garagem!';
 	END IF;
 END;
 DELIMITER //

 DELIMITER //
 CREATE PROCEDURE `AdicionaMaquina`(tipoo varchar(50) , modeloo varchar(15) , horas_trabalhadass int , corr varchar(15) , anoo int, id_garagemm int )
 BEGIN
     declare qtd int;
     SET qtd = (SELECT capacidade_maquina FROM garagem WHERE id_garagem = id_garagemm);

     IF qtd >= 1 THEN
 	insert into  maquina (tipo, modelo, horas_trabalhadas, cor, ano, id_garagem)
 	values (tipoo, modeloo, horas_trabalhadass, corr, anoo, id_garagemm);

         UPDATE garagem SET capacidade_maquina = capacidade_maquina  1
         WHERE id_garagem = id_garagemm;
     ELSE
 	select 'Sem capacidade nessa garagem!';
 	END IF;
 END;
 DELIMITER //


 DELIMITER //
 CREATE PROCEDURE `RelacionaMaquinaItem`(id_itemm int , id_maquinaa int , quantidadee int, horas_trabalhadass int)
 BEGIN
 	DECLARE qtd_item INT;
     DECLARE id_garagemm int;

     SET id_garagemm = (select id_garagem from item where id_item = id_itemm);

     SET qtd_item = (SELECT quantidade FROM item WHERE id_item = id_itemm);

     IF qtd_item >= quantidadee THEN
 	insert into maquina_item (id_item, id_maquina, quantidade, horas_trabalhadas)
 	values (id_itemm, id_maquinaa, quantidadee, horas_trabalhadass);

 	UPDATE item SET quantidade = quantidade  quantidadee
 	WHERE id_item = id_itemm;

     UPDATE garagem SET capacidade_item = capacidade_item + quantidadee
 	WHERE id_garagem = id_garagemm;

     ELSE
 	select 'Não há itens suficiente!';
 	END IF;
 END;
 DELIMITER //

 DELIMITER //
 CREATE PROCEDURE `AdicionaTrabalho`(id_funcionarioo int, id_maquinaa int, data_trabalhoo date, horas_trabalhadass int)
 BEGIN
     INSERT INTO trabalho (id_funcionario, id_maquina, data_trabalho, horas_trabalhadas)
     values (id_funcionarioo, id_maquinaa, data_trabalhoo, horas_trabalhadass);
     UPDATE maquina SET horas_trabalhadas = horas_trabalhadas + horas_trabalhadass where id_maquina = id_maquinaa;
     UPDATE maquina_item SET horas_trabalhadas = horas_trabalhadas + horas_trabalhadass where id_maquina = id_maquinaa;
 END;
 DELIMITER //

 DELIMITER //
 CREATE PROCEDURE `AdicionaManutencaoItem`(id_manutencaoo int, id_itemm int)
 BEGIN
     declare id_maquinaa int;
     declare data_manutencaoo date;
     declare horas int;

     set data_manutencaoo = (select data_manutencao from manutencao where id_manutencao = id_manutencaoo);
     set id_maquinaa = (select id_maquina from manutencao where id_manutencao = id_manutencaoo);
     set horas = (select horas_trabalhadas from maquina_item where id_maquina = id_maquinaa and id_item = id_itemm);

     if (exists(select id_item from maquina_item where id_maquina = id_maquinaa and id_item = id_itemm)) then
 		insert into manutencao_item values(id_manutencaoo, id_itemm, data_manutencaoo);
 		if horas is not null then
 			update maquina_item set horas_trabalhadas = 0 where id_maquina = id_maquinaa and id_item = id_itemm;
 		end if;
     else
 		select "item não relacionado com a maquina";
     end if;
 END;
 DELIMITER //
 
 DELIMITER //
 CREATE FUNCTION `media_horas_trabalhadas`() RETURNS float
 BEGIN
 	return (select avg(horas_trabalhadas) from trabalho.maquina);
 end;
 DELIMITER //

insert into usuario
values ("Diogo Almeida", "diogo", "123456"),
       ("Luis Felipe", "lufe", "654321");

insert into funcionario (nome, cpf, rg, nascimento, endereco, telefone)
values ("Narcizo Gabriel", "06463841986", "10443223" , "19971008", "Rua São Carlos, nº 3", "44995632145"),
       ("Andre Alves", "02350800989", "93624106" , "19940501", "Avenida Barcelona, nº 1203 A", "4432658978"),
       ("Pedro Alcantara", "04745232145", "32687421" , "19871230", "Rua 9, nº 112", "44988651245");

insert into garagem (nome, capacidade_item, capacidade_maquina)
values ("Garagem A", 50, 3),
       ("Garagem B", 50, 4),
       ("Garagem C", 50, 6);

call AdicionaMaquina ("Trator" , "MF 250XE", 0, "vermelho", 2006, 1);
call AdicionaMaquina ("Trator" , "MF 255", 0, "vermelho", 2010, 1);
call AdicionaMaquina ("Colheitadeira", "S690", 0, "verde", 2008, 2);
call AdicionaMaquina ("Colheitaderia", "CR10.90", 0, "amarelo", 2017, 3);
call AdicionaMaquina ("Plantadeira", "2130", 0, "amareloverde", 2012, 3);
call AdicionaMaquina ("Caminhão", "FH440", 0 , "prata", 2014, 2);
call AdicionaMaquina ("Aviao", "Embraer Ipanema", 0, "verdeazul", 2013, 3);


call AdicionaItem ("Pneu trator", 1, "pneu de trator", null, 1, 12);
call AdicionaItem ("Pneu colheitadeira", 1, "pneu de colheitadeira", null, 1, 12);
call AdicionaItem ("Pneu plantadeira", 1, "pneu de plantadeira", null, 1, 8);
call AdicionaItem ("Pneu caminhao", 1, "pneu de caminhao", null, 2, 12);
call AdicionaItem ("Graxa", 2, "galao de graxa para engrenagem", 120, 2, 14);
call AdicionaItem ("Oleo de motor", 2, "galao de oleo para motor de qualquer maquina", 240, 3, 15);
call AdicionaItem ("Filtro ar condicionado", 2, "filtro para ar condicionado", 480, 3, 10);
call AdicionaItem ("Arla 32", 2, "galao de arla 32", 480, 3, 5);
call AdicionaItem ("Tacografo", 2, "disco de tacografo para caminhao", 240, 3, 5);


insert into fornecedor (nome, telefone, endereco)
values ("Pneumar", 4432325685, "Avenida Tuiuti, nº 500"),
       ("McLaren, oleos e filtros", 4432658545, "Avenida Barcos, nº 123 A");

insert into fornecedor_item
values (1, 1, "20170203", 1560.75),
       (1, 2, "20170203", 1350.75),
       (1, 3, "20170203", 1250.75),
       (1, 4, "20170203", 950.75),
       (2, 5, "20170210", 160.90),
       (2, 6, "20170210", 190.90),
       (2, 7, "20170210", 112.90),
       (2, 8, "20170210", 115.90),
       (2, 9, "20170210", 50.90);

call RelacionaMaquinaItem (1, 1, 4, null);
call RelacionaMaquinaItem (1, 2, 4, null);
call RelacionaMaquinaItem (2, 3, 4, null);
call RelacionaMaquinaItem (2, 4, 4, null);
call RelacionaMaquinaItem (3, 5, 4, null);
call RelacionaMaquinaItem (4, 6, 6, null);
call RelacionaMaquinaItem (5, 1, 1, 0);
call RelacionaMaquinaItem (5, 2, 1, 0);
call RelacionaMaquinaItem (5, 3, 1, 0);
call RelacionaMaquinaItem (5, 4, 1, 0);
call RelacionaMaquinaItem (5, 5, 1, 0);
call RelacionaMaquinaItem (5, 6, 1, 0);
call RelacionaMaquinaItem (5, 7, 1, 0);
call RelacionaMaquinaItem (6, 1, 1, 0);
call RelacionaMaquinaItem (6, 2, 1, 0);
call RelacionaMaquinaItem (6, 3, 1, 0);
call RelacionaMaquinaItem (6, 4, 1, 0);
call RelacionaMaquinaItem (6, 5, 1, 0);
call RelacionaMaquinaItem (6, 6, 1, 0);
call RelacionaMaquinaItem (6, 7, 1, 0);
call RelacionaMaquinaItem (7, 1, 1, 0);
call RelacionaMaquinaItem (7, 2, 1, 0);
call RelacionaMaquinaItem (7, 3, 1, 0);
call RelacionaMaquinaItem (7, 4, 1, 0);
call RelacionaMaquinaItem (7, 5, 1, 0);
call RelacionaMaquinaItem (7, 6, 1, 0);
call RelacionaMaquinaItem (7, 7, 1, 0);
call RelacionaMaquinaItem (8, 1, 1, 0);
call RelacionaMaquinaItem (8, 2, 1, 0);
call RelacionaMaquinaItem (8, 6, 1, 0);
call RelacionaMaquinaItem (9, 1, 1, 0);
call RelacionaMaquinaItem (9, 2, 1, 0);
call RelacionaMaquinaItem (9, 6, 1, 0);

call AdicionaTrabalho (1, 1, "20170505", 4);
call AdicionaTrabalho (2, 2, "20170505", 4);
call AdicionaTrabalho (3, 5, "20170505", 8);
call AdicionaTrabalho (1, 1, "20170508", 8);
call AdicionaTrabalho (2, 2, "20170508", 8);
call AdicionaTrabalho (3, 5, "20170508", 16);
call AdicionaTrabalho (3, 7, "20170822", 3);
call AdicionaTrabalho (2, 7, "20170822", 4);
call AdicionaTrabalho (1, 1, "20171205", 4);
call AdicionaTrabalho (2, 3, "20171205", 10);
call AdicionaTrabalho (3, 4, "20171205", 10);
call AdicionaTrabalho (1, 6, "20171229", 30);

insert into manutencao (data_manutencao, id_funcionario, id_maquina)
values ("20170612", 1, 1),
       ("20170607", 2, 2),
       ("20171115", 3, 3),
       ("20170823", 1, 4),
       ("20171030", 1, 5),
       ("20170817", 2, 6),
       ("20171227", 2, 7);

call AdicionaManutencaoItem(1,1);
call AdicionaManutencaoItem(1,5);
call AdicionaManutencaoItem(1,6);
call AdicionaManutencaoItem(2,5);
call AdicionaManutencaoItem(2,6);
call AdicionaManutencaoItem(3,5);
call AdicionaManutencaoItem(3,6);
call AdicionaManutencaoItem(4,5);
call AdicionaManutencaoItem(4,6);
call AdicionaManutencaoItem(5,5);
call AdicionaManutencaoItem(5,6);
call AdicionaManutencaoItem(6,5);
call AdicionaManutencaoItem(6,6);
call AdicionaManutencaoItem(7,5);
call AdicionaManutencaoItem(7,6);
call AdicionaManutencaoItem(7,7);

/* 1) Inner Join > une o id do trabalho, a data, o funcionario e com que maquina ele trabalhou*/
select trabalho.id_trabalho, trabalho.data_trabalho, funcionario.nome, trabalho.id_maquina, maquina.tipo
from trabalho inner join funcionario inner join maquina
where trabalho.id_funcionario = funcionario.id_funcionario and trabalho.id_maquina = maquina.id_maquina;

/* 2) Union > une o as maquinas e itens que sao da garagem 1*/
select modelo from maquina where id_garagem = 1
union
select descricao from item where id_garagem = 1;

/* 3) Except > retorna os itens e sua garagem que não estão na garagem 1*/
select item.nome, garagem.nome from item left join garagem
on garagem.id_garagem = item.id_garagem where item.id_garagem not in (select id_garagem from item where id_garagem = 1);

/* 4) Intersect > retorna a maquina que esta na garagem 1 e teve uma manutencao antes do dia 01/08/2017 */
select id_maquina from maquina where id_garagem in (select id_garagem from garagem where id_garagem = 1)
and id_maquina in (select id_maquina from manutencao where data_manutencao < "20170801");

/* 5) retorna o nome do fornecedor que o id dele esta relacionado com o fornecedor do item 1*/
select fornecedor.nome from fornecedor
where id_fornecedor in (select id_fornecedor from fornecedor_item where id_item = 1);

/* 6) a) retorna os itens cujo o id_item está relacionado com o fornecedor 1 */
select item.descricao from item
where id_item in (select id_item from fornecedor_item where id_fornecedor = 1);

/* 6) b) retorna a descrição de qualquer item no qual a horas_duracao seja maior que 100*/
SELECT item.descricao FROM item
WHERE horas_duracao = ANY (SELECT item.horas_duracao FROM item WHERE horas_duracao > 100);

/* 6) c) retorna a descrição de todos os itens da tabela item se existir algum item na tabela item que tenha a hora_duracao igual a 480*/
SELECT item.descricao
FROM item
WHERE EXISTS (SELECT item.id_item FROM item WHERE item.horas_duracao = 480);
/* 6) d) retorna a descrição de todos os itens na qual a hora de duração é igual a 120*/
SELECT item.descricao FROM item
WHERE horas_duracao = ALL (SELECT item.horas_duracao FROM item WHERE horas_duracao = 120);

/* 7) seleciona a hora de duracao e as horas trabalhadas no trabalho onde id_trabalho é 2*/
SELECT
	(SELECT DISTINCT COUNT(item.id_item) FROM item),
    (SELECT DISTINCT COUNT(maquina.id_maquina) FROM maquina);

/* 8) right join de funcionario.id_funcionario e trabalho.id_funcionario */
SELECT funcionario.id_funcionario, funcionario.cpf, manutencao.id_manutencao
FROM funcionario
RIGHT JOIN manutencao ON manutencao.id_funcionario = funcionario.id_funcionario;

/* 9) left join de item.id_item e maquina_item.id_maquina*/
SELECT item.id_item, maquina_item.id_maquina, maquina_item.quantidade
FROM item
LEFT JOIN maquina_item ON item.id_item = maquina_item.id_maquina
ORDER BY item.id_item;

/* 10) media dos valores dos pneus, cujo id_item sao de 1 até 4 */
SELECT AVG(valor_compra) FROM fornecedor_item WHERE id_item >= 1 AND id_item <= 4;

/* 11) soma as horas trabalhadas agrupando-as por funcionario */
SELECT SUM(trabalho.horas_trabalhadas) FROM  trabalho GROUP BY trabalho.id_funcionario;

/* 12) conta o numero de horas trabalhadas do funcionario onde seu id é 2 */
SELECT SUM(trabalho.horas_trabalhadas) FROM trabalho GROUP BY trabalho.id_funcionario HAVING trabalho.id_funcionario = 2;

/* 13) cria a view do funcionario.nome e funcionario.cpf */
CREATE VIEW view_exercicio13 AS
SELECT funcionario.nome, funcionario.cpf
FROM funcionario;

/* 14) cria a view que une nome do funcionario com a data de manutencao da tabela manutenção*/
CREATE VIEW view_exercicio14 AS
SELECT funcionario.nome FROM funcionario
UNION
SELECT manutencao.data_manutencao FROM manutencao
UNION
SELECT item.descricao FROM item
UNION
SELECT usuario.login FROM usuario;

/* 15) */
CREATE TEMPORARY TABLE tabela_temporaria (
	id_temporario int auto_increment,
	anotacoes varchar(255) not null,
	PRIMARY KEY (id_temporario)
);
INSERT INTO tabela_temporaria VALUES
(1, "sistema está rodando perfeitamente"),
(2, "sistema não apresenta falhas");

SELECT * FROM trabalho.usuario;
SELECT * FROM trabalho.funcionario;
SELECT * FROM trabalho.garagem;
SELECT * FROM trabalho.maquina;
SELECT * FROM trabalho.item;
SELECT * FROM trabalho.maquina_item;
SELECT * FROM trabalho.fornecedor;
SELECT * FROM trabalho.fornecedor_item;
SELECT * FROM trabalho.manutencao;
SELECT * FROM trabalho.trabalho;
SELECT * FROM trabalho.manutencao_item;
