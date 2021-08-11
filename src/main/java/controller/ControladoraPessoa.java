package controller;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import exception.exception_pessoa.AnalisarCamposPessoaException;
import model.bo.PessoaBO;
import model.dao.PessoaDAO;
import model.vo.PessoaVO;
import seletor.FiltroPessoa;
import util.Data;

public class ControladoraPessoa {

	private PessoaBO bo = new PessoaBO();
	private PessoaDAO dao = new PessoaDAO();

	public String cadastrar(PessoaVO novaPessoa) throws AnalisarCamposPessoaException {
		String respostaValidacao = validarCampos(novaPessoa);
		if (respostaValidacao != "") {
			throw new AnalisarCamposPessoaException(respostaValidacao);
		}

		return bo.cadastrarPessoaBO(novaPessoa);
	}

	public String validarCampos(PessoaVO pessoa) {
		String mensagem = "";
		// Os campos devem possuir no m�nimo d�gitos 3 e no m�ximo 100
		// Nome:
		if (pessoa.getNome().length() < 3) {
			mensagem += "\nO nome deve conter no minimo 3 caracteres";
		} else if (pessoa.getNome().length() > 100) {
			mensagem += "\nO nome nao pode passar de 100 caracteres";
		}

		// Email:
		String email = pessoa.getEmail();
		boolean respostaVerificacao = false;
		for (int i = 0; i < email.length(); i++) {
			if (String.valueOf(email.charAt(i)).equalsIgnoreCase("@")) {
				respostaVerificacao = true;
			}
		}
		boolean respostaVerificacaoo = false;
		for (int i = 0; i < email.length(); i++) {
			if (String.valueOf(email.charAt(i)).equalsIgnoreCase(".")) {
				respostaVerificacaoo = true;
			}
		}
		if (pessoa.getEmail().length() < 3) {
			mensagem += "\nO email deve conter no m�nimo 3 caracteres";
		} else if (pessoa.getEmail().length() > 100) {
			mensagem += "\nO email n�o pode passar de 100 caracteres";
		} else if (!respostaVerificacao) {
			mensagem += "\nO email deve conter '@' ";
		} else if (!respostaVerificacaoo) {
			mensagem += "\nO email deve conter '.' ";
		}

		// Endereco:
		if (pessoa.getEndereco().length() < 3) {
			mensagem += "\nO endereco deve conter no minimo 3 caracteres";
		} else if (pessoa.getEndereco().length() > 100) {
			mensagem += "\nO endereco nao pode passar de 100 caracteres";
		}

		// Os campos devem possuir no m�nimo d�gitos 3 e no m�ximo 50
		// Cidade:
		if (pessoa.getCidade().length() < 3) {
			mensagem += "\nA cidade deve conter no minimo 3 caracteres";
		} else if (pessoa.getCidade().length() > 50) {
			mensagem += "\nA cidade nao pode passar de 50 caracteres";
		}
		return mensagem;
	}

	public boolean alterar(PessoaVO pessoaAlterada) throws AnalisarCamposPessoaException {
		String respostaValidacao = validarCampos(pessoaAlterada);
		if (respostaValidacao != null && !respostaValidacao.isEmpty()) {
			throw new AnalisarCamposPessoaException(respostaValidacao);
		}
		return bo.alterar(pessoaAlterada);
	}

	public PessoaVO consultarPorCpf(String cpf) {
		return bo.consultarPorCpf(cpf);
	}

	public PessoaVO consultarPorId(Integer idPessoaSelecionada) {

		return bo.consultarPorId(idPessoaSelecionada);
	}

	public ArrayList<PessoaVO> consultarTodos() {
		return bo.consultarTodos();
	}

	public boolean excluir(Integer id) {
		return bo.excluir(id);
	}

	public ArrayList<PessoaVO> consultarComFiltro(FiltroPessoa seletor) {
		return bo.consultarComFiltro(seletor);

	}

	public ArrayList<PessoaVO> consultarTodosRelatorio() {
		return bo.consultarTodosRelatorio();
	}

}
