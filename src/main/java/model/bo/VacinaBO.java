package model.bo;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import exception.exceptionVacina.VacinaJaExisteException;
import model.dao.VacinaDAO;
import model.vo.VacinaVO;
import seletor.FiltroVacina;

public class VacinaBO {
	VacinaDAO dao = new VacinaDAO();
	
	public String cadastrar(VacinaVO vacina) throws VacinaJaExisteException {
		String mensagem = "";
		//N�o deve repetir nomes de vacinas:
		if(dao.vacinaJaExiste(vacina)) {
			throw new VacinaJaExisteException("\nVacina já existe.");
		}
		
		dao.cadastrar(vacina);
		return mensagem;
	}
	
	public boolean alterar(VacinaVO vacinaAlterada) {
		return dao.alterar(vacinaAlterada);
	}
	
	public boolean excluir(Integer id) {
		return dao.excluir(id);
	}
	public VacinaVO consultarPorNome(String nome) {
		return dao.consultarPorNome(nome);
	}
	public ArrayList<VacinaVO> consultarTodos() {
		return dao.consultarTodos();
	}
	public VacinaVO consultarPorId(Integer id) {
		return dao.consultarPorId(id);
	}

	public ArrayList<VacinaVO> consultarComFiltro(FiltroVacina seletor) {
		// TODO Auto-generated method stub
		return dao.consultarComFiltro(seletor);
	}

	public ArrayList<VacinaVO> consultarTodosRelatorio() {
		return dao.consultarTodosRelatorio();
	}
}
 