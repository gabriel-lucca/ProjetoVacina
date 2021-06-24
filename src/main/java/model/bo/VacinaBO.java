package model.bo;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import exception.exceptionVacina.PaisJaTemVacinaRegistradaException;
import exception.exceptionVacina.VacinaJaExisteException;
import model.dao.VacinaDAO;
import model.vo.VacinaVO;

public class VacinaBO {
	VacinaDAO dao = new VacinaDAO();
	
	public String cadastrar(VacinaVO vacina) throws VacinaJaExisteException, PaisJaTemVacinaRegistradaException{
		String mensagem = "";
		//N�o deve repetir nomes de vacinas:
		if(dao.vacinaJaExiste(vacina)) {
			throw new VacinaJaExisteException("\nVacina já existe.");
		}
		//N�o deve haver mais de uma vacina por país;
		if(dao.paisJaTemVacinaCadastrada(vacina)) {
			throw new VacinaJaExisteException("\nEste país já possui vacina cadastrada.");
		}
		dao.cadastrar(vacina);
		return mensagem;
	}
	
	public boolean alterar(VacinaVO vacinaAlterada) {
		return dao.alterar(vacinaAlterada);
	}
	
	public Integer excluir(Integer id) {
		dao.excluir(id);
		return id;
	}
	public VacinaVO consultarPorNome(String nome) {
		return dao.consultarPorNome(nome);
	}
	public ArrayList<VacinaVO> consutarTodos() {
		
		return dao.consultarTodos();
	}
	public VacinaVO consultarPorId(Integer id) {
		return dao.consultarPorId(id);
	}
}
 