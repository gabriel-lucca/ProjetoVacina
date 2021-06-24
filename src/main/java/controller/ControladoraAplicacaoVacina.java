package controller;

import java.util.ArrayList;

import exception.AnalisarCamposVacinaException;
import model.bo.AplicacaoVacinaBO;
import model.dao.AplicacaoVacinaDAO;
import model.vo.AplicacaoVacinaVO;
import model.vo.PessoaVO;
import model.vo.VacinaVO;

public class ControladoraAplicacaoVacina {

	
	private AplicacaoVacinaBO bo = new AplicacaoVacinaBO();
	
	public AplicacaoVacinaVO cadastrar(AplicacaoVacinaVO aplicacaoVacina) throws AnalisarCamposVacinaException {
		return bo.cadastrar(aplicacaoVacina);
	}
	
	public String validarCampos(AplicacaoVacinaVO aplicacaoVacina) {
		String mensagem = " ";
		if(aplicacaoVacina.getDataAplicacao() == null) {
			mensagem += "O campo data da aplica��o deve ser preenchdido.";
		}
		return mensagem;
	}

	public PessoaVO consultarPorCpf(String cpf) {
		return bo.consultarPorCpf(cpf);
	}

	public ArrayList<AplicacaoVacinaVO> consultarAplicacoes(PessoaVO p) {
		return bo.consultarAplicacoes(p);
	}



}
