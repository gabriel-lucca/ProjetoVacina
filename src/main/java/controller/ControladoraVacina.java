package controller;

import model.bo.VacinaBO;
import model.vo.VacinaVO;

public class ControladoraVacina {
	
	private VacinaBO bo = new VacinaBO();
	
	public String cadastrar(VacinaVO vacina) {
		return bo.cadastrarVacinaBO(vacina);
	}
	
	public String validarCampos(VacinaVO vacina) {
		String mensagem = bo.cadastrarVacinaBO(vacina);
		
		return mensagem;
	}

	public void alteraInformacoes(VacinaVO vacinaAlterada) {
		bo.alterarVacina(vacinaAlterada);
		
	}

	public Integer excluir(Integer id) {
		
		return bo.excluir(id);
		
	}
	
	public VacinaVO consultarPorNome(String nome) {
		
		return bo.consultarPorNome(nome);
	}
	
}
