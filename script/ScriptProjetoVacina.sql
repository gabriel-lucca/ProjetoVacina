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
    paisOrigem varchar(50) not null,
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
insert into pessoa(nomePessoa, cpf, email, telefone, dtNascimento, cidade, estado, endereco) values("Roberto Augusto", "111.111.111-11", "robertoool123@gmail.com", "(21)91111-1111", '1970-06-04', "Florianópolis", "SC", "Servidão Araquari"); 
insert into pessoa(nomePessoa, cpf, email, telefone, dtNascimento, cidade, estado, endereco) values("André Araujo Lopes", "245.046.289-11", "andrefilipe@gmail.com", "(48)91164-8911", '2000-08-08', "Florianópolis", "SC", "Rodovia Barreiros");
insert into pessoa(nomePessoa, cpf, email, telefone, dtNascimento, cidade, estado, endereco) values("Frederico Martins de Almeida", "245.046.760-11", "gabrielmarti@gmail.com", "(48)91164-8333", '2001-08-01', "Florianópolis", "SC", "Rodovia Gonzaga");
insert into pessoa(nomePessoa, cpf, email, telefone, dtNascimento, cidade, estado, endereco) values("Rodolfo bawbis", "215.489.289-11", "rodolfobawbis@gmail.com", "(48)91167-7911", '1980-05-08', "Florianópolis", "SC", "Avenida Ropi");
insert into pessoa(nomePessoa, cpf, email, telefone, dtNascimento, cidade, estado, endereco) values("Arlindo Rodrigues", "479.046.222-40", "arlindorod455@gmail.com", "(48)91199-8911", '1999-06-02', "Florianópolis", "SC", "Rua São Paulo");
insert into pessoa(nomePessoa, cpf, email, telefone, dtNascimento, cidade, estado, endereco) values("Luiz Vicente Viana", "052.989.637-06", "lluizvicenteviana@imagemeaudio.com.br", "(84)98578-0813", '1970-09-02', "Natal", "RN", "Travessa São Pedro");
insert into pessoa(nomePessoa, cpf, email, telefone, dtNascimento, cidade, estado, endereco) values("Guilherme Ryan Nicolas Brito", "769.509.372-61", "gguilhermeryannicolasbrito@kascher.com.br", "(84)99078-0813", '1990-09-11', "Manaus", "AM", "Beco São Geraldo");
insert into pessoa(nomePessoa, cpf, email, telefone, dtNascimento, cidade, estado, endereco) values("Lúcia Carla Fogaça", "170.331.931-14", "luciacarlafogaca__luciacarlafogaca@terrabrasil.com.br", "(41)99791-6258", '1980-10-11', "Fazenda Rio Grande", "PR", "Rua Baraúna");
insert into pessoa(nomePessoa, cpf, email, telefone, dtNascimento, cidade, estado, endereco) values("Lucas Manoel Pereira", "325.722.095-27", "lucasmanoelpereira-77@diebold.com", "(44)99393-3799", '1990-10-12', "Umuarama", "PR", "Rua Andorinha");
insert into pessoa(nomePessoa, cpf, email, telefone, dtNascimento, cidade, estado, endereco) values("Allana Flávia Sabrina Dias", "491.084.346-97", "allanaflaviasabrinadias-75@jcffactoring.com.br", "(93)99566-4532", '1992-10-13', "Itaituba", "PA", "Rua Vigésima Oitava");
insert into pessoa(nomePessoa, cpf, email, telefone, dtNascimento, cidade, estado, endereco) values("Giovanna Aline Nair Moreira", "173.300.600-11", "giovannaaline@maliziaarranjosflorais.com.br", "(33)98148-8068", '1982-10-14', "Governador Valadares", "MG", "Praça do Teodoro");
insert into pessoa(nomePessoa, cpf, email, telefone, dtNascimento, cidade, estado, endereco) values("Renato Leonardo Ferreira", "992.987.638-32", "renatoleonardogabrielferreira@yoma.com.br", "(51)98655-4597", '1972-10-14', "Santa Cruz do Sul", "RS", "Rua Willi Eick");
insert into pessoa(nomePessoa, cpf, email, telefone, dtNascimento, cidade, estado, endereco) values("Carolina Renata Francisca Ramos", "546.394.538-17", "carolinarenatafranciscaramos@dmadvogados.com", "(91)98310-9654", '1972-10-14', "Belém", "PA", "Rua Rosas");
insert into pessoa(nomePessoa, cpf, email, telefone, dtNascimento, cidade, estado, endereco) values("Anthony José Corte Real", "702.896.142-79", "anthonyjosecortereal@franciscofilho.adv.br", "(92)98587-0900", '1962-10-15', "Manaus", "AM", "Beco Santa Bárbara");
insert into pessoa(nomePessoa, cpf, email, telefone, dtNascimento, cidade, estado, endereco) values("Guilherme Henry da Cruz", "683.954.742-61", "gguilhermehenrydacruz@soespodonto.com.br", "(83)99884-6142", '1952-10-15', "Bayeux", "PB", "Rua Tabelião Severino Araújo");
insert into pessoa(nomePessoa, cpf, email, telefone, dtNascimento, cidade, estado, endereco) values("Anderson Ryan Nascimento", "447.954.578-67", "andersonryannascimento@ruizonline.com.br", "(81)99192-4178", '1992-09-15', "Recife", "PE", "1ª Travessa Coripós");
insert into pessoa(nomePessoa, cpf, email, telefone, dtNascimento, cidade, estado, endereco) values("Cláudio Pedro Henrique Silva", "846.058.659-69", "claudiopedrohenriquesilva_@bol.br", "(68)99229-3388", '1995-08-18', "Rio Branco", "AC", "Rua Thaumaturgo de Azevedo");
insert into pessoa(nomePessoa, cpf, email, telefone, dtNascimento, cidade, estado, endereco) values("Rebeca Sabrina Freitas", "712.696.315-78", "rebecasabrinafreitas_@jcffactoring.com.br", "(89)98976-5121", '1975-02-18', "Fronteiras", "PI", "Praça Getúlio Vargas");



insert into vacina(nomePesquisadorResponsavel, paisOrigem, nomeVacina, dtInicioPesquisa, quantidadeDoses, intervaloDoses) values("Laboratório Sinovac", "China", "Coronavac", '2020-05-05', 2, 21);
insert into vacina(nomePesquisadorResponsavel, paisOrigem, nomeVacina, dtInicioPesquisa, quantidadeDoses, intervaloDoses) values("Özlem Türeci", "Alemanha", "Pfizer", '2020-10-10', 2, 90);
insert into vacina(nomePesquisadorResponsavel, paisOrigem, nomeVacina, dtInicioPesquisa, quantidadeDoses, intervaloDoses) values("Laboratório Aztrazeneca", "Reino Unido", "Oxford", '2020-06-05', 2, 90);
insert into vacina(nomePesquisadorResponsavel, paisOrigem, nomeVacina, dtInicioPesquisa, quantidadeDoses, intervaloDoses) values("Janssen Pharmaceutical Companies", "Estados Unidos", "Jahssen", '2020-08-05', 1, 0);
insert into vacina(nomePesquisadorResponsavel, paisOrigem, nomeVacina, dtInicioPesquisa, quantidadeDoses, intervaloDoses) values("Instituto de Pesquisa Gamaleya", "Rússia", "Sputnik V", '2020-09-09', 2, 21);
insert into vacina(nomePesquisadorResponsavel, paisOrigem, nomeVacina, dtInicioPesquisa, quantidadeDoses, intervaloDoses) values("Bharat Biotech", "Índia", "Covaxin", '2020-11-11', 2, 28);

insert into aplicacaoVacina(idPessoa, idVacina, dtAplicacao) values(1, 1, '2021-03-09');
insert into aplicacaoVacina(idPessoa, idVacina, dtAplicacao) values(1, 1, '2021-04-02');

insert into aplicacaoVacina(idPessoa, idVacina, dtAplicacao) values(3, 1, '2021-03-09');
insert into aplicacaoVacina(idPessoa, idVacina, dtAplicacao) values(3, 1, '2021-04-02');

-- Select:
select * from pessoa;
select * from vacina;
select * from aplicacaoVacina;
