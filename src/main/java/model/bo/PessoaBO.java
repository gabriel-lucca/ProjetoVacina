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
			// TODO Auto-generated method stub
			return dao.consultarComFiltro(seletor);
		}
		
}
