package model.bo;

import model.dao.PessoaDAO;
import model.vo.PessoaVO;

public class PessoaBO {
	
		PessoaDAO dao = new PessoaDAO();
		public String cadastrarPessoaBO(PessoaVO pessoa) {
			String mensagem = "";

			//Os campos devem possuir no mínimo dígitos 3 e no máximo 100
			//Nome:
			if(pessoa.getNome().length() < 3) {
				mensagem += "O nome deve conter no mínimo 3 caracteres";
			}else if(pessoa.getNome().length() > 100) {
				mensagem += "O nome não pode passar de 100 caracteres";
			}
			//Email:
			if(pessoa.getEmail().length() < 3) {
				mensagem += "O email deve conter no mínimo 3 caracteres";
			}else if(pessoa.getEmail().length() > 100) {
				mensagem += "O email não pode passar de 100 caracteres";
			}
			//Endereco:
			if(pessoa.getEndereco().length() < 3) {
				mensagem += "O endereço deve conter no mínimo 3 caracteres";
			}else if(pessoa.getEndereco().length() > 100) {
				mensagem += "O endereço não pode passar de 100 caracteres";
			}
			
			//Os campos devem possuir no mínimo dígitos 3 e no máximo 50
			//Cidade:
			if(pessoa.getCidade().length() < 3) {
				mensagem += "A cidade deve conter no mínimo 3 caracteres";
			}else if(pessoa.getNome().length() > 50) {
				mensagem += "A cidade não pode passar de 50 caracteres";
			}
			
			if(mensagem.isEmpty()) {
				mensagem = "Pessoa cadastrada com sucesso!";
			}
			dao.cadastrar(pessoa);
			return mensagem;
		}
		
}
