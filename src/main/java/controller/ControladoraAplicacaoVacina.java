package controller;

import exception.AplicacaoException;
import model.bo.AplicacaoVacinaBO;
import model.vo.AplicacaoVacinaVO;

public class ControladoraAplicacaoVacina {

	
	private AplicacaoVacinaBO bo = new AplicacaoVacinaBO();
	
	public AplicacaoVacinaVO cadastrar(AplicacaoVacinaVO aplicacaoVacina) throws AplicacaoException {
		return bo.cadastrar(aplicacaoVacina);
	}
	
	public String validarCampos(AplicacaoVacinaVO aplicacaoVacina) {
		String mensagem = " ";
		if(aplicacaoVacina.getDataAplicacao() == null) {
			mensagem += "O campo data da aplicação deve ser preenchdido.";
		}
		return mensagem;
	}
}
