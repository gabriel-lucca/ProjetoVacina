package model.bo;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

import model.dao.AplicacaoVacinaDAO;
import model.dao.PessoaDAO;
import model.dao.VacinaDAO;
import model.vo.AplicacaoVacinaVO;
import model.vo.PessoaVO;
import model.vo.VacinaVO;

import exception.AnalisarCamposVacinaException;

public class AplicacaoVacinaBO {

	//Quando clicar em buscar deve mostrar quantas doses de vacinas foram tomadas ou se n�o tomou nenhuma;
	//� permitido selecionar o campo vacinar quando tiver faltando alguma dose;
	//Uma pessoa n�o pode tomar vacinas diferentes;
	//Deve-se aplicar apenas quando for cumprido o tempo de intervalo das doses;
	//N�o deve aplicar acima do limite de doses por pessoa
	PessoaDAO pDAO = new PessoaDAO();
	VacinaDAO vDAO = new VacinaDAO();
	AplicacaoVacinaDAO avDAO = new AplicacaoVacinaDAO();

	public boolean podeVacinar(String cpf, int id) throws AnalisarCamposVacinaException {
		String mensagem = null;
		boolean resposta = false;
		
		AplicacaoVacinaDAO avDAO = new AplicacaoVacinaDAO();
		PessoaVO avVO = new PessoaVO();

		PessoaVO p = pDAO.consultarPorCpf(cpf);
		ArrayList<AplicacaoVacinaVO> aplicacoes = avDAO.consultarAplicacoes(p);
		VacinaVO v = vDAO.buscarPorId(id);

		Integer dosesAplicadas = aplicacoes.size();
		Period periodo = Period.between(LocalDate.now(), aplicacoes.get(dosesAplicadas).getDataAplicacao().plusDays(v.getIntervaloDoses()));
		// obter as aplicacoes da vacina em questao
		//a) nao atingiu o limite de doses - olhar a quantidade de aplicacoes
		//b) intervalo entre a ultima dose e HOJE > intervalo da vacina em questao -- olhar a ultima aplicacao
		//pode receber nova aplicacao SE(e somente se)
		if(aplicacoes.size() == v.getQuantidadeDoses()) {
			mensagem += "Esta pessoa atingiu o limite de doses";
		}
		if(aplicacoes.size() < v.getQuantidadeDoses()) {
			mensagem += "Esta pessoa aplicou "+dosesAplicadas+" doses.\nRestam "+(v.getQuantidadeDoses()-dosesAplicadas)+" doses.\nProxima dose: "+aplicacoes.get(dosesAplicadas).getDataAplicacao().plusDays(v.getIntervaloDoses());
		}
		if(periodo.getDays() != v.getIntervaloDoses()) {
			mensagem += "Restam "+periodo.getDays()+" dias at� a pr�xima dose.";	
		}
		if(mensagem == null) {
			resposta = true;
		} else {
			resposta = false;
			throw new AnalisarCamposVacinaException(mensagem);
		}
		 return resposta;
		}
	
	public AplicacaoVacinaVO cadastrar(AplicacaoVacinaVO aplicacaoVacinaVO) throws AnalisarCamposVacinaException {
		String mensagem = "";
		boolean resposta = false;
		PessoaVO pVO = aplicacaoVacinaVO.getPessoa();
		
		VacinaVO vVO = aplicacaoVacinaVO.getVacina();

		if(podeVacinar(pVO.getCpf(), vVO.getIdVacina())) {
			AplicacaoVacinaDAO avDAO = new AplicacaoVacinaDAO();
			avDAO.cadastrar(aplicacaoVacinaVO);
		} else {
			throw new AnalisarCamposVacinaException(mensagem);
		}
		return aplicacaoVacinaVO;
	}

	public PessoaVO consultarPorCpf(String cpf) {
		return pDAO.consultarPorCpf(cpf);
	}

	public ArrayList<AplicacaoVacinaVO> consultarAplicacoes(PessoaVO p) {
		return avDAO.consultarAplicacoes(p);

	}

	
}


