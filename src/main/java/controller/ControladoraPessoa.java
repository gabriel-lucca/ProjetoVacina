package controller;

import exception.exception_pessoa.AnalisarCamposPessoaException;
import model.bo.PessoaBO;
import model.dao.PessoaDAO;
import model.vo.PessoaVO;

public class ControladoraPessoa {

private PessoaBO bo = new PessoaBO();
private PessoaDAO dao = new PessoaDAO();
	
	public String cadastrar(PessoaVO novaPessoa) throws AnalisarCamposPessoaException {
		String respostaValidacao = validarCampos(novaPessoa);
		if(respostaValidacao != null && !respostaValidacao.isEmpty()) {
			throw new AnalisarCamposPessoaException(respostaValidacao);
		}
		return bo.cadastrarPessoaBO(novaPessoa);
	}
	
	public String validarCampos(PessoaVO pessoa) {
		String mensagem=null;
		//Os campos devem possuir no m�nimo d�gitos 3 e no m�ximo 100
		//Nome:
		if(pessoa.getNome().length() < 3) {
			mensagem += "O nome deve conter no m�nimo 3 caracteres";
		}else if(pessoa.getNome().length() > 100) {
			mensagem += "O nome n�o pode passar de 100 caracteres";
		}
		//Email:
		if(pessoa.getEmail().length() < 3) {
			mensagem += "O email deve conter no m�nimo 3 caracteres";
		}else if(pessoa.getEmail().length() > 100) {
			mensagem += "O email n�o pode passar de 100 caracteres";
		}
		//Endereco:
		if(pessoa.getEndereco().length() < 3) {
			mensagem += "O endere�o deve conter no m�nimo 3 caracteres";
		}else if(pessoa.getEndereco().length() > 100) {
			mensagem += "O endere�o n�o pode passar de 100 caracteres";
		}
		
		//Os campos devem possuir no m�nimo d�gitos 3 e no m�ximo 50
		//Cidade:
		if(pessoa.getCidade().length() < 3) {
			mensagem += "A cidade deve conter no m�nimo 3 caracteres";
		}else if(pessoa.getNome().length() > 50) {
			mensagem += "A cidade n�o pode passar de 50 caracteres";
		}
		
		if(mensagem.isEmpty()) {
			mensagem = "Pessoa cadastrada com sucesso!";
		}		
		return mensagem;
	}

	public boolean alterar(PessoaVO pessoaAlterada) throws AnalisarCamposPessoaException{
		String respostaValidacao = validarCampos(pessoaAlterada);
		if(respostaValidacao != null && !respostaValidacao.isEmpty()) {
			throw new AnalisarCamposPessoaException(respostaValidacao);
		}
		return bo.alterar(pessoaAlterada);
	}

	public PessoaVO consultarPorCpf(String cpf) {
		return dao.consultarPorCpf(cpf);
	}

	public PessoaVO consultarPorId(Integer idPessoaSelecionada) {
		
		return bo.consultarPorId(idPessoaSelecionada);
	}

	public boolean excluir(Integer id) {
		return bo.excluir(id);
	}
	

}
