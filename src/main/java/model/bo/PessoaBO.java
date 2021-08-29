package model.bo;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import exception.exception_pessoa.AnalisarCamposPessoaException;
import model.dao.PessoaDAO;
import model.vo.PessoaVO;
import seletor.FiltroPessoa;

public class PessoaBO {
	
		PessoaDAO dao = new PessoaDAO();
		public String cadastrarPessoaBO(PessoaVO pessoa) throws AnalisarCamposPessoaException {
			String mensagem = "";
			//Nao deve repetir cpf :
			if(dao.cpfJaExiste(pessoa)) {
				throw new AnalisarCamposPessoaException("\nCPF já existe.");
			}
			
			//Nao deve repetir celular :
			if(dao.celularJaExiste(pessoa)) {
				throw new AnalisarCamposPessoaException("\nCelular já utilizado.");
			}
			
			//Nao deve repetir email :
			if(dao.emailJaExiste(pessoa)) {
				throw new AnalisarCamposPessoaException("\nEmail já utilizado.");
			}
			
			dao.cadastrar(pessoa);
			return mensagem;
		}
		public boolean alterar(PessoaVO pessoaAlterada) {
			return dao.alterar(pessoaAlterada);
		}
		
		public PessoaVO consultarPorCpf(String cpf) {
			return dao.consultarPorCpf(cpf);
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
		public ArrayList<PessoaVO> consultarTodosRelatorio() {
			return dao.consultarTodosRelatorio();
		}
		
}
