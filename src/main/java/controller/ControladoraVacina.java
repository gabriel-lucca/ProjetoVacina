package controller;

import java.util.ArrayList;

import exception.exceptionVacina.AnalisarCamposVacinaException;
import exception.exceptionVacina.VacinaJaExisteException;
import model.bo.VacinaBO;
import model.vo.VacinaVO;
import seletor.FiltroVacina;

public class ControladoraVacina {
	private VacinaBO bo = new VacinaBO();

	public String cadastrar(VacinaVO novaVacina) throws AnalisarCamposVacinaException, VacinaJaExisteException {
		String respostaValidacao = validarCampos(novaVacina);
		if (respostaValidacao != "") {
			throw new AnalisarCamposVacinaException(respostaValidacao);
		}
		return bo.cadastrar(novaVacina);
	}

	public String validarCampos(VacinaVO vacina) {
		String mensagem = "";
		// Os campos devem possuir no m�nimo d�gitos 3 e no m�ximo 100
		// NomePesquisador:
		if (vacina.getNomePesquisadorResponsavel().length() < 3) {
			mensagem += "\nO nome do pesquisador responsavel deve possuir no mínimo 3 caracteres";
		} else if (vacina.getNomePesquisadorResponsavel().length() > 100) {
			mensagem += "\nO nome do pesquisador responsavel não deve possuir mais de 100 caracteres";
		} else if (!vacina.getNomePesquisadorResponsavel().matches("^([a-zA-Zà-úÀ-Ú]|'|\\s)+$")) {
			mensagem += "\nNome inválido.";
		}
		// paisOrigem:
		// if(vacina.getPaisOrigem().length() < 3) {
		// mensagem += "\nO pais de origem deve possuir no mínimo 3 caracteres";
		// }else if(vacina.getPaisOrigem().length() > 100) {
		// mensagem += "\nO pais de origem não deve possuir mais de 100 caracteres";
		// }

		// NomeVacina:
		if (vacina.getNomeVacina().length() < 3) {
			mensagem += "\nO nome da vacina deve possuir no mínimo 3 caracteres";
		} else if (vacina.getNomeVacina().length() > 50) {
			mensagem += "\nO nome da vacina nao deve possuir mais de 50 caracteres";
		} else if (!vacina.getNomeVacina().matches("^([a-zA-Zà-úÀ-Ú]|-|\\s)+$")) {
			mensagem += "\nNome da Nome da vacina inválida";
		}

		// Intervalo doses:
		if (vacina.getIntervaloDoses() < 0) {
			mensagem += "\nO Intervalo de doses deve possuir no mínimo 0 dias";
		} else if (vacina.getIntervaloDoses() > 365) {
			mensagem += "\nO Intervalo de doses tem um limite de 365 dias";
		} 

		return mensagem;
	}

	public boolean alterar(VacinaVO vacinaAlterada) throws AnalisarCamposVacinaException {
		String respostaValidacao = validarCampos(vacinaAlterada);
		if (respostaValidacao != null && !respostaValidacao.isEmpty()) {
			throw new AnalisarCamposVacinaException(respostaValidacao);
		}
		return bo.alterar(vacinaAlterada);
	}

	public ArrayList<VacinaVO> consultarTodos() {
		return bo.consultarTodos();
	}

	public boolean excluir(Integer id) {
		return bo.excluir(id);
	}

	public VacinaVO consultarPorNome(String nome) {
		return bo.consultarPorNome(nome);
	}

	public VacinaVO consultarPorId(Integer id) {
		return bo.consultarPorId(id);
	}

	public ArrayList<VacinaVO> consultarComFiltro(FiltroVacina seletor) {
		return bo.consultarComFiltro(seletor);
	}

	public ArrayList<VacinaVO> consultarTodosRelatorio() {
		return bo.consultarTodosRelatorio();
	}
}
