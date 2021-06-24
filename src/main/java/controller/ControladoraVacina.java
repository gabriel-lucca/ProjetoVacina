package controller;


import java.util.ArrayList;

import exception.exceptionVacina.AnalisarCamposVacinaException;
import exception.exceptionVacina.PaisJaTemVacinaRegistradaException;
import exception.exceptionVacina.VacinaJaExisteException;
import model.bo.VacinaBO;
import model.vo.VacinaVO;

public class ControladoraVacina {
	private VacinaBO bo = new VacinaBO();
	
	public String cadastrar(VacinaVO novaVacina) throws AnalisarCamposVacinaException, VacinaJaExisteException, PaisJaTemVacinaRegistradaException {
		String respostaValidacao = validarCampos(novaVacina);
		if(respostaValidacao != null && !respostaValidacao.isEmpty()) {
			throw new AnalisarCamposVacinaException(respostaValidacao);
		}
		return bo.cadastrar(novaVacina);
	}
	public String validarCampos(VacinaVO vacina) {
		String mensagem=null;
		//Os campos devem possuir no m�nimo d�gitos 3 e no m�ximo 100
		//NomePesquisador:
		if(vacina.getNomePesquisadorResponsavel().length() < 3) {
			mensagem += "O nome do deve possuir no mínimo 3 caracteres";
		}else if(vacina.getNomePesquisadorResponsavel().length() > 100) {
			mensagem += "O nome não deve possuir mais de 100 caracteres";
		}
		//paisOrigem:
		if(vacina.getPaisOrigem().length() < 3) {
			mensagem += "O pas de origem possuir no mínimo 3 caracteres";
		}else if(vacina.getPaisOrigem().length() > 100) {
			mensagem += "O ps de origem não pode passar de 100 caracteres";
		}
		//NomeVacina:
		if(vacina.getNomeVacina().length() < 3) {
			mensagem += "O nome da vacina possuir no mínimo 3 caracteres";
		}else if(vacina.getNomeVacina().length() > 100) {
			mensagem += "O nome da vacina n�o pode passar de 100 caracteres";
		}
		return mensagem;
	}
	public boolean alterar(VacinaVO vacinaAlterada) throws AnalisarCamposVacinaException{
		String respostaValidacao = validarCampos(vacinaAlterada);
		if(respostaValidacao != null && !respostaValidacao.isEmpty()) {
			throw new AnalisarCamposVacinaException(respostaValidacao);
		}
		return bo.alterar(vacinaAlterada);
	}
	public ArrayList<VacinaVO> consultarTodos(){
		return bo.consutarTodos();
	}
	public Integer excluir(Integer id) {
		return bo.excluir(id);
	}
	public VacinaVO consultarPorNome(String nome) {
		return bo.consultarPorNome(nome);
	}
	public VacinaVO consultarPorId(Integer id) {
		return bo.consultarPorId(id);
	}
}
