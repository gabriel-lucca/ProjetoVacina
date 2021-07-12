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
import exception.exception_aplicacao_vacina.AnalisarSePodeAplicarException;

public class AplicacaoVacinaBO {

	//Quando clicar em buscar deve mostrar quantas doses de vacinas foram tomadas ou se n�o tomou nenhuma;
	//� permitido selecionar o campo vacinar quando tiver faltando alguma dose;
	//Uma pessoa n�o pode tomar vacinas diferentes;
	//Deve-se aplicar apenas quando for cumprido o tempo de intervalo das doses;
	//N�o deve aplicar acima do limite de doses por pessoa
	PessoaDAO pDAO = new PessoaDAO();
	VacinaDAO vDAO = new VacinaDAO();
	AplicacaoVacinaDAO avDAO = new AplicacaoVacinaDAO();

	public String podeVacinar(String cpf, int id) {
		String mensagem = null;
		PessoaVO avVO = new PessoaVO();
		PessoaVO p = pDAO.consultarPorCpf(cpf);
		VacinaVO v = vDAO.consultarPorId(id);
		ArrayList<AplicacaoVacinaVO> aplicacoes = avDAO.consultarAplicacoes(id);
		Integer dosesAplicadas = aplicacoes.size();
		LocalDate dtProximaAplicacao = aplicacoes.get(dosesAplicadas).getDataAplicacao().plusDays(v.getIntervaloDoses());
		int tempoRestante = dtProximaAplicacao.compareTo(dtProximaAplicacao);
		// obter as aplicacoes da vacina em questao
		//a) nao atingiu o limite de doses - olhar a quantidade de aplicacoes
		//b) intervalo entre a ultima dose e HOJE > intervalo da vacina em questao -- olhar a ultima aplicacao
		//pode receber nova aplicacao SE(e somente se)
		//if(aplicacoes.size() == v.getQuantidadeDoses()) {
	//		mensagem += "\nEsta pessoa atingiu o limite de doses.\n Foram aplicadas "+aplicacoes.size();
		//} else if(tempoRestante > 0) {
		//	mensagem += "\nAinda não pode aplicar.\nTempo restante "+tempoRestante;
		//}
		return mensagem;
	}
	public AplicacaoVacinaVO cadastrar(AplicacaoVacinaVO aplicacaoVacinaVO) throws AnalisarSePodeAplicarException {
		VacinaVO vVO = aplicacaoVacinaVO.getVacina();
		PessoaVO pVO = aplicacaoVacinaVO.getPessoa();
		
		String resultadoPodeVacinar=null;
		if(resultadoPodeVacinar != null && !resultadoPodeVacinar.isEmpty()) {
			throw new AnalisarSePodeAplicarException(resultadoPodeVacinar);
		}
		return aplicacaoVacinaVO;
	}
	public boolean alterar(AplicacaoVacinaVO aplicacaoAlterada) {
		return avDAO.alterar(aplicacaoAlterada);
	}
	public boolean excluir(Integer id) {
		return avDAO.excluir(id);
	}
	public ArrayList<AplicacaoVacinaVO> consultarAplicacoes(Integer id) {
		return avDAO.consultarAplicacoes(id);

	}
	public AplicacaoVacinaVO consultarPorId(Integer id) {
		return avDAO.consultarPorId(id);
	}
}


