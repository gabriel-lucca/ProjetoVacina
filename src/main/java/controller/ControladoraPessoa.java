package controller;

import model.bo.PessoaBO;
import model.vo.PessoaVO;

public class ControladoraPessoa {

private PessoaBO bo = new PessoaBO();
	
	public String cadastrar(PessoaVO pessoa) {
		return bo.cadastrarPessoaBO(pessoa);
	}
	
	public String validarCampos(PessoaVO pessoa) {
		String mensagem = bo.cadastrarPessoaBO(pessoa);
		
		return mensagem;
	}

	public void alteraInformacoes(PessoaVO pessoaAlterada) {
		bo.alterarPessoa(pessoaAlterada);
		
	}
	

}
