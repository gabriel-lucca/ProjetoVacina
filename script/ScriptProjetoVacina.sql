drop database if exists dbprojetoVacina;
create database dbprojetoVacina;
use dbprojetoVacina;

-- Tabelas:
create table pessoa(
	idPessoa int not null auto_increment primary key,
    nomePessoa varchar(100) not null,
    cpf varchar(20) not null,
    email varchar(100),
    telefone varchar(15) not null,
    dtNascimento date not null,
    cidade varchar(50) not null,
    estado varchar(50) not null,
    endereco varchar(100) not null
);
create table vacina(
	idVacina int not null auto_increment primary key,
    nomePesquisadorResponsavel varchar(100) not null,
    paisorigem varchar(50) not null,
    nomeVacina varchar(50) not null,
    dtInicioPesquisa date not null,
    quantidadeDoses varchar(2) not null,
    intervaloDoses varchar(3) not null
);
create table aplicacaoVacina(
	idAplicacao int not null auto_increment primary key,
    idPessoa int not null,
    idVacina int not null,
    dtAplicacao date not null,
	foreign key(IdPessoa) references pessoa(idPessoa) 
);
-- Inserts:
insert into pessoa(nomePessoa, cpf, email, telefone, dtNascimento, cidade, estado, endereco) values("Gabriel", "111.111.111-11", "gabriel123@gmail.com", "21911111111", '1970-06-04', "Florianopolis", "SC", "Servidão Araquari"); 
insert into vacina(nomePesquisadorResponsavel, paisOrigem, nomeVacina, dtInicioPesquisa, quantidadeDoses, intervaloDoses) values("Felipe Oliveira", "Brasil", "Coronavac", '2020-05-05', 2, 90);
insert into aplicacaoVacina(idPessoa, idVacina, dtAplicacao) values(1, 1, '2021-03-02');
insert into pessoa(nomePessoa, cpf, email, telefone, dtNascimento, cidade, estado, endereco) values("Usuario para teste de exclusão", "222.111.111-11", "BigodinGrossin@gmail.com", "21911111211", '1970-07-04', "Rio de Fevereiro", "Rio de JFevereiro", "Bem alí"); 
-- Select:
select * from pessoa;
select * from vacina;
select * from aplicacaoVacina;
