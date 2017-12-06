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

        UPDATE garagem SET capacidade_item = capacidade_item - quantidadee
        WHERE id_garagem = id_garagemm;
    ELSE
	select 'Sem capacidade nessa garagem!';
	END IF;
END
DELIMITER //

DELIMITER //
CREATE PROCEDURE `AdicionaMaquina`(tipoo varchar(50) , modeloo varchar(15) , horas_trabalhadass int , corr varchar(15) , anoo int, id_garagemm int )
BEGIN
    declare qtd int;
    SET qtd = (SELECT capacidade_maquina FROM garagem WHERE id_garagem = id_garagemm);

    IF qtd >= 1 THEN
	insert into  maquina (tipo, modelo, horas_trabalhadas, cor, ano, id_garagem)
	values (tipoo, modeloo, horas_trabalhadass, corr, anoo, id_garagemm);

        UPDATE garagem SET capacidade_maquina = capacidade_maquina - 1
        WHERE id_garagem = id_garagemm;
    ELSE
	select 'Sem capacidade nessa garagem!';
	END IF;
END
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

	UPDATE item SET quantidade = quantidade - quantidadee
	WHERE id_item = id_itemm;

    UPDATE garagem SET capacidade_item = capacidade_item + quantidadee
	WHERE id_garagem = id_garagemm;

    ELSE
	select 'Não há itens suficiente!';
	END IF;
END
DELIMITER //

DELIMITER //
CREATE PROCEDURE `AdicionaTrabalho`(id_funcionarioo int, id_maquinaa int, data_trabalhoo date, horas_trabalhadass int)
BEGIN
    INSERT INTO trabalho (id_funcionario, id_maquina, data_trabalho, horas_trabalhadas)
    values (id_funcionarioo, id_maquinaa, data_trabalhoo, horas_trabalhadass);
    UPDATE maquina SET horas_trabalhadas = horas_trabalhadas + horas_trabalhadass where id_maquina = id_maquinaa;
    UPDATE maquina_item SET horas_trabalhadas = horas_trabalhadas + horas_trabalhadass where id_maquina = id_maquinaa;
END
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
END
DELIMITER //

insert into usuario
values ("Diogo Almeida", "diogo", "123456"),
       ("Luis Felipe", "lufe", "654321");

insert into funcionario (nome, cpf, rg, nascimento, endereco, telefone)
values ("Narcizo Gabriel", "06463841986", "10443223" , "1997-10-08", "Rua São Carlos, nº 3", "44995632145"),
       ("Andre Alves", "02350800989", "93624106" , "1994-05-01", "Avenida Barcelona, nº 1203 A", "4432658978"),
       ("Pedro Alcantara", "04745232145", "32687421" , "1987-12-30", "Rua 9, nº 112", "44988651245");

insert into garagem (nome, capacidade_item, capacidade_maquina)
values ("Garagem A", 50, 3),
       ("Garagem B", 50, 4),
       ("Garagem C", 50, 6);

call AdicionaMaquina ("Trator" , "MF 250XE", 0, "vermelho", 2006, 1);
call AdicionaMaquina ("Trator" , "MF 255", 0, "vermelho", 2010, 1);
call AdicionaMaquina ("Colheitadeira", "S690", 0, "verde", 2008, 2);
call AdicionaMaquina ("Colheitaderia", "CR10.90", 0, "amarelo", 2017, 3);
call AdicionaMaquina ("Plantadeira", "2130", 0, "amarelo-verde", 2012, 3);
call AdicionaMaquina ("Caminhão", "FH-440", 0 , "prata", 2014, 2);
call AdicionaMaquina ("Aviao", "Embraer Ipanema", 0, "verde-azul", 2013, 3);


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
values (1, 1, "2017-02-03", 1560.75),
       (1, 2, "2017-02-03", 1350.75),
       (1, 3, "2017-02-03", 1250.75),
       (1, 4, "2017-02-03", 950.75),
       (2, 5, "2017-02-10", 160.90),
       (2, 6, "2017-02-10", 190.90),
       (2, 7, "2017-02-10", 112.90),
       (2, 8, "2017-02-10", 115.90),
       (2, 9, "2017-02-10", 50.90);

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

call AdicionaTrabalho (1, 1, "2017-05-05", 4);
call AdicionaTrabalho (2, 2, "2017-05-05", 4);
call AdicionaTrabalho (3, 5, "2017-05-05", 8);
call AdicionaTrabalho (1, 1, "2017-05-08", 8);
call AdicionaTrabalho (2, 2, "2017-05-08", 8);
call AdicionaTrabalho (3, 5, "2017-05-08", 16);
call AdicionaTrabalho (3, 7, "2017-08-22", 3);
call AdicionaTrabalho (2, 7, "2017-08-22", 4);
call AdicionaTrabalho (1, 1, "2017-12-05", 4);
call AdicionaTrabalho (2, 3, "2017-12-05", 10);
call AdicionaTrabalho (3, 4, "2017-12-05", 10);
call AdicionaTrabalho (1, 6, "2017-12-29", 30);

insert into manutencao (data_manutencao, id_funcionario, id_maquina)
values ("2017-06-12", 1, 1),
       ("2017-06-07", 2, 2),
       ("2017-11-15", 3, 3),
       ("2017-08-23", 1, 4),
       ("2017-10-30", 1, 5),
       ("2017-08-17", 2, 6),
       ("2017-12-27", 2, 7);

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

/* 1) Inner Join --> une o id do trabalho, a data, o funcionario e com que maquina ele trabalhou*/
select trabalho.id_trabalho, trabalho.data_trabalho, funcionario.nome, trabalho.id_maquina, maquina.tipo
from trabalho inner join funcionario inner join maquina
where trabalho.id_funcionario = funcionario.id_funcionario and trabalho.id_maquina = maquina.id_maquina;

/* 2) Union --> une o as maquinas e itens que sao da garagem 1*/
select modelo from maquina where id_garagem = 1
union
select descricao from item where id_garagem = 1;

/* 3) Except --> retorna os itens e sua garagem que não estão na garagem 1*/
select item.nome, garagem.nome from item left join garagem
on garagem.id_garagem = item.id_garagem where item.id_garagem not in (select id_garagem from item where id_garagem = 1);

/* 4) Intersect --> retorna a maquina que esta na garagem 1 e teve uma manutencao antes do dia 01/08/2017 */
select id_maquina from maquina where id_garagem in (select id_garagem from garagem where id_garagem = 1)
and id_maquina in (select id_maquina from manutencao where data_manutencao < "2017-08-01");

/* 5) retorna o nome do fornecedor que o id dele esta relacionado com o fornecedor do item 1*/
select fornecedor.nome from fornecedor
where id_fornecedor in (select id_fornecedor from fornecedor_item where id_item = 1);

/* 6) a) retorna os itens cujo o id_item está relacionado com o fornecedor 1 */
select item.descricao from item
where id_item in (select id_item from fornecedor_item where id_fornecedor = 1);

/* 6) b) */
/* 6) c) */
/* 6) d) */
/* 7) */
/* 8) */
/* 9) */
/* 10) */
/* 11) */
/* 12) */
/* 13) */
/* 14) */
/* 15) */
/* 16) */
/* 17) */

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
