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
    dtAplicacao date not null
);
-- Inserts:
insert into pessoa(nomePessoa, cpf, email, telefone, dtNascimento, cidade, estado, endereco) values("Gabriel", "111.111.111-11", "gabriel123@gmail.com", "21911111111", '1970-06-04', "Florianopolis", "SC", "Servidão Araquari"); 
insert into pessoa(nomePessoa, cpf, email, telefone, dtNascimento, cidade, estado, endereco) values("Usuario para teste de exclusão", "222.111.111-11", "BigodinGrossin@gmail.com", "21911111211", '1970-07-04', "Rio de Fevereiro", "Rio de JFevereiro", "Bem alí"); 
insert into pessoa(nomePessoa, cpf, email, telefone, dtNascimento, cidade, estado, endereco) values("André Jacinto", "245.046.289-11", "andrefilipe@gmail.com", "48911648911", '2000-08-08', "Florianopolis", "SC", "Rodovia Barreiros");
insert into pessoa(nomePessoa, cpf, email, telefone, dtNascimento, cidade, estado, endereco) values("Gabriel Martins", "245.046.760-11", "gabrielmarti@gmail.com", "48911648333", '2001-08-01', "Florianopolis", "SC", "Rodovia Gonzaga");
insert into pessoa(nomePessoa, cpf, email, telefone, dtNascimento, cidade, estado, endereco) values("Rodolfo bawbis", "215.489.289-11", "rodolfobawbis@gmail.com", "48911677911", '1980-05-08', "Florianopolis", "SC", "Avenida Ropi");
insert into pessoa(nomePessoa, cpf, email, telefone, dtNascimento, cidade, estado, endereco) values("Arlindo Rodrigues", "479.046.222-40", "andrefilipe@gmail.com", "48911998911", '1999-06-02', "Florianopolis", "SC", "Rua São Paulo");
insert into pessoa(nomePessoa, cpf, email, telefone, dtNascimento, cidade, estado, endereco) values("Luiz Vicente Viana", "052.989.637-06", "lluizvicenteviana@imagemeaudio.com.br", "8498578-0813", '1970-09-02', "Natal", "RN", "Travessa São Pedro");
insert into pessoa(nomePessoa, cpf, email, telefone, dtNascimento, cidade, estado, endereco) values("Guilherme Ryan Nicolas Brito", "769.509.372-61", "gguilhermeryannicolasbrito@kascher.com.br", "8499078-0813", '1990-09-11', "Manaus", "AM", "Beco São Geraldo");
insert into pessoa(nomePessoa, cpf, email, telefone, dtNascimento, cidade, estado, endereco) values("Lúcia Carla Fogaça", "170.331.931-14", "luciacarlafogaca__luciacarlafogaca@terrabrasil.com.br", "4199791-6258", '1980-10-11', "Fazenda Rio Grande", "PR", "Rua Baraúna");
insert into pessoa(nomePessoa, cpf, email, telefone, dtNascimento, cidade, estado, endereco) values("Lucas Manoel Pereira", "325.722.095-27", "lucasmanoelpereira-77@diebold.com", "4499393-3799", '1990-10-12', "Umuarama", "PR", "Rua Andorinha");

insert into vacina(nomePesquisadorResponsavel, paisOrigem, nomeVacina, dtInicioPesquisa, quantidadeDoses, intervaloDoses) values("Felipe Oliveira", "Brasil", "Coronavac", '2020-05-05', 2, 90);
insert into vacina(nomePesquisadorResponsavel, paisOrigem, nomeVacina, dtInicioPesquisa, quantidadeDoses, intervaloDoses) values("VAC", "China", "Coronavac", '2020-06-05', 2, 90);
insert into vacina(nomePesquisadorResponsavel, paisOrigem, nomeVacina, dtInicioPesquisa, quantidadeDoses, intervaloDoses) values("BioNTech", "Alemanha", "Pfizer", '2020-10-10', 2, 90);
insert into vacina(nomePesquisadorResponsavel, paisOrigem, nomeVacina, dtInicioPesquisa, quantidadeDoses, intervaloDoses) values("Oxford", "Reino Unido", "Aztrazeneca", '2020-06-05', 2, 90);
insert into vacina(nomePesquisadorResponsavel, paisOrigem, nomeVacina, dtInicioPesquisa, quantidadeDoses, intervaloDoses) values("Oxford", "índia", "Covaxin", '2020-11-11', 2, 90);

insert into aplicacaoVacina(idPessoa, idVacina, dtAplicacao) values(1, 1, '2021-03-02');

-- Select:
select * from pessoa;
select * from vacina;
select * from aplicacaoVacina;
