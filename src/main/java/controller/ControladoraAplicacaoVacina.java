package controller;

import java.util.ArrayList;

import exception.exception_aplicacao_vacina.AnalisarCamposAplicacaoException;
import exception.exception_aplicacao_vacina.AnalisarSePodeAplicarException;
import model.bo.AplicacaoVacinaBO;
import model.dao.AplicacaoVacinaDAO;
import model.vo.AplicacaoVacinaVO;
import model.vo.PessoaVO;
import model.vo.VacinaVO;

public class ControladoraAplicacaoVacina {

	
	private AplicacaoVacinaBO bo = new AplicacaoVacinaBO();
	
	public AplicacaoVacinaVO cadastrar(AplicacaoVacinaVO novaAplicacaoVacina) throws AnalisarCamposAplicacaoException, AnalisarSePodeAplicarException {
		String resultadoValidacao = validarCampos(novaAplicacaoVacina);
		if(resultadoValidacao != null && !resultadoValidacao.isEmpty()) {
			throw new AnalisarCamposAplicacaoException(resultadoValidacao);
		}
		return bo.cadastrar(novaAplicacaoVacina);
	}
	public String validarCampos(AplicacaoVacinaVO aplicacaoVacina) {
		String mensagem = " ";
		if(aplicacaoVacina.getPessoa() == null) {
			mensagem += "\nInforme o nome da pessoa à ser aplicada.";
		}
		if(aplicacaoVacina.getVacina() == null) {
			mensagem += "\nSelecione a vacina a ser aplicada.";
		}
		return mensagem;
	}
	public boolean excluir(Integer id) {
		return bo.excluir(id);
	}
	public boolean alterar(AplicacaoVacinaVO aplicacaoAlterada) throws AnalisarCamposAplicacaoException {
		String resultadoValidacao = validarCampos(aplicacaoAlterada);
		if(resultadoValidacao != null && !resultadoValidacao.isEmpty()) {
			throw new AnalisarCamposAplicacaoException(resultadoValidacao);
		}
		return bo.alterar(aplicacaoAlterada);
	}
	public ArrayList<AplicacaoVacinaVO> consultarAplicacoes(Integer id) {
		return bo.consultarAplicacoes(id);
	}
	public AplicacaoVacinaVO consultarPorId(Integer id) {
		return bo.consultarPorId(id);
	}
}
