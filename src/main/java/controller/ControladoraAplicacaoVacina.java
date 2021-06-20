package controller;

import exception.AplicacaoException;
import model.bo.AplicacaoVacinaBO;
import model.dao.AplicacaoVacinaDAO;
import model.vo.AplicacaoVacinaVO;
import model.vo.PessoaVO;

public class ControladoraAplicacaoVacina {

	
	private AplicacaoVacinaBO bo = new AplicacaoVacinaBO();
	
	public AplicacaoVacinaVO cadastrar(AplicacaoVacinaVO aplicacaoVacina) throws AplicacaoException {
		return bo.cadastrar(aplicacaoVacina);
	}
	
	public String validarCampos(AplicacaoVacinaVO aplicacaoVacina) {
		String mensagem = " ";
		if(aplicacaoVacina.getDataAplicacao() == null) {
			mensagem += "O campo data da aplica��o deve ser preenchdido.";
		}
		return mensagem;
	}


}
