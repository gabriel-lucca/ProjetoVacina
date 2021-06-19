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

import exception.AplicacaoException;

public class AplicacaoVacinaBO {

	//Quando clicar em buscar deve mostrar quantas doses de vacinas foram tomadas ou se não tomou nenhuma;
	//É permitido selecionar o campo vacinar quando tiver faltando alguma dose;
	//Uma pessoa não pode tomar vacinas diferentes;
	//Deve-se aplicar apenas quando for cumprido o tempo de intervalo das doses;
	//Não deve aplicar acima do limite de doses por pessoa

	public boolean podeVacinar(String cpf, int id) throws AplicacaoException {
		String mensagem = null;
		boolean resposta = false;
		
		PessoaDAO pDAO = new PessoaDAO();
		VacinaDAO vDAO = new VacinaDAO();
		AplicacaoVacinaDAO avDAO = new AplicacaoVacinaDAO();
		PessoaVO avVO = new PessoaVO();

		PessoaVO p = pDAO.consultarPorCpf(cpf);
		ArrayList<AplicacaoVacinaVO> aplicacoes = avDAO.buscarAplicacoes(p);
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
			mensagem += "Restam "+periodo.getDays()+" dias até a próxima dose.";	
		}
		if(mensagem == null) {
			resposta = true;
		} else {
			resposta = false;
			throw new AplicacaoException(mensagem);
		}
		 return resposta;
		}
	
	public AplicacaoVacinaVO cadastrar(AplicacaoVacinaVO aplicacaoVacinaVO) throws AplicacaoException {
		String mensagem = "";
		boolean resposta = false;
		PessoaDAO pDAO = new PessoaDAO();
		VacinaDAO vDAO = new VacinaDAO();
		PessoaVO pVO = aplicacaoVacinaVO.getPessoa();
		
		VacinaVO vVO = aplicacaoVacinaVO.getVacina();

		if(podeVacinar(pVO.getCpf(), pVO.getIdPessoa())) {
			AplicacaoVacinaDAO avDAO = new AplicacaoVacinaDAO();
			avDAO.cadastrar(aplicacaoVacinaVO);
		} else {
			throw new AplicacaoException(mensagem);
		}
		return aplicacaoVacinaVO;
	}
	
}


