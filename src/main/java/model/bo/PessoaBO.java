package model.bo;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.dao.PessoaDAO;
import model.vo.PessoaVO;
import model.vo.VacinaVO;
import seletor.FiltroPessoa;

public class PessoaBO {
	
		PessoaDAO dao = new PessoaDAO();
		public String cadastrarPessoaBO(PessoaVO pessoa) {
			String mensagem = "";

			//Os campos devem possuir no m�nimo d�gitos 3 e no m�ximo 100
			//Nome:
			if(pessoa.getNome().length() < 3) {
				mensagem += "\nO nome deve conter no minimo 3 caracteres";
			}else if(pessoa.getNome().length() > 100) {
				mensagem += "\nO nome nao pode passar de 100 caracteres";
			}
			//Email:
			if(pessoa.getEmail().length() < 3) {
				mensagem += "\nO email deve conter no minimo 3 caracteres";
			}else if(pessoa.getEmail().length() > 100) {
				mensagem += "\nO email nao pode passar de 100 caracteres";
			}
			//Endereco:
			if(pessoa.getEndereco().length() < 3) {
				mensagem += "\nO endereco deve conter no minimo 3 caracteres";
			}else if(pessoa.getEndereco().length() > 100) {
				mensagem += "\nO endereco nao pode passar de 100 caracteres";
			}
			//Os campos devem possuir no m�nimo d�gitos 3 e no m�ximo 50
			//Cidade:
			if(pessoa.getCidade().length() < 3) {
				mensagem += "\nA cidade deve conter no minimo 3 caracteres";
			}else if(pessoa.getCidade().length() > 50) {
				mensagem += "\nA cidade nao pode passar de 50 caracteres";
			}
			if(mensagem.isEmpty()) {
				mensagem = "\nPessoa cadastrada com sucesso!";
			}
			dao.cadastrar(pessoa);
			return mensagem;
		}
		public boolean alterar(PessoaVO pessoaAlterada) {
			return dao.alterar(pessoaAlterada);
		}
	
		public boolean excluir(Integer id) {
			return dao.excluir(id);
		}
		
		public PessoaVO consultarPorId(Integer idPessoaSelecionada) {
			return dao.buscarPorId(idPessoaSelecionada);
		}
		
		public PessoaVO consultarPorNome(String nome) {
			return dao.consultarPorNome(nome);
		}
		public ArrayList<PessoaVO> consultarTodos() {
			return dao.buscarTodos();
		}
		public ArrayList<PessoaVO> consultarComFiltro(FiltroPessoa seletor) {
			return dao.consultarComFiltro(seletor);
		}
		
}
