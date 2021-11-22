package controller;

import java.util.ArrayList;

import exception.exception_pessoa.AnalisarCamposPessoaException;
import model.bo.PessoaBO;
import model.dao.PessoaDAO;
import model.vo.PessoaVO;
import seletor.FiltroPessoa;

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
			// } else if (!pessoa.getNome().matches("[A-Z][a-z]* [A-Z][a-z]*")) {
			/*
			 * } else if (!pessoa.getNome().matches(
			 * "^[A-ZÀ-Ÿ][A-zÀ-ÿ']+\s([A-zÀ-ÿ']\s?)*[A-ZÀ-Ÿ][A-zÀ-ÿ']+$")) { mensagem +=
			 * "\nNome inválido." +
			 * "\nO nome e sobrenome precisa começar com letra maiuscula."; }
			 */
		} else if (!pessoa.getNome().matches("^([a-zA-Zà-úÀ-Ú]|'|\\s)+$")) {
			mensagem += "\nNome inválido.";
		} 

		/*
		 * / Email: String email = pessoa.getEmail(); boolean respostaVerificacao =
		 * false; for (int i = 0; i < email.length(); i++) { if
		 * (String.valueOf(email.charAt(i)).equalsIgnoreCase("@")) { respostaVerificacao
		 * = true; } } boolean respostaVerificacaoo = false; for (int i = 0; i <
		 * email.length(); i++) { if
		 * (String.valueOf(email.charAt(i)).equalsIgnoreCase(".")) {
		 * respostaVerificacaoo = true; } }
		 */
		if (pessoa.getEmail().length() < 3) {
			mensagem += "\nO email deve conter no mínimo 3 caracteres";
		} else if (pessoa.getEmail().length() > 100) {
			mensagem += "\nO email não pode passar de 100 caracteres";
			// } else if (!respostaVerificacao) {
			// mensagem += "\nO email deve conter '@' ";
			// } else if (!respostaVerificacaoo) {
			// mensagem += "\nO email deve conter '.' ";
		} else if (!pessoa.getEmail().matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
			mensagem += "\nEmail inválido";
		}

		// Endereco:
		if (pessoa.getEndereco().length() < 3) {
			mensagem += "\nO endereco deve conter no minimo 3 caracteres";
		} else if (pessoa.getEndereco().length() > 100) {
			mensagem += "\nO endereco nao pode passar de 100 caracteres";
		} else if (!pessoa.getEndereco().matches("^([a-zA-Zà-úÀ-Ú0-9]|-|,|\\s)+$")) {
			mensagem += "\nEndereço inválido";
		}

		// Os campos devem possuir no m�nimo d�gitos 3 e no m�ximo 50
		// Cidade:
		if (pessoa.getCidade().length() < 3) {
			mensagem += "\nA cidade deve conter no minimo 3 caracteres";
		} else if (pessoa.getCidade().length() > 50) {
			mensagem += "\nA cidade nao pode passar de 50 caracteres";
		} else if (!pessoa.getCidade().matches("^([a-zA-Zà-úÀ-Ú]|-|\\s)+$")) {
			mensagem += "\nCidade inválida";
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
