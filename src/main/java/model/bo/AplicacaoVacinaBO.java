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

	public boolean podeVacinar(String cpf) throws AplicacaoException {
		String mensagem = "";
		boolean resposta = false;
		
		PessoaDAO pDAO = new PessoaDAO();
		VacinaDAO vDAO = new VacinaDAO();
		AplicacaoVacinaDAO avDAO = new AplicacaoVacinaDAO();

		PessoaVO p = pDAO.consultarPorCpf(cpf);
		
		ArrayList<AplicacaoVacinaVO> aplicacoes = avDAO.buscarAplicacoes(p);
		
		//VacinaVO v = vDAO.buscarPorId(avVO.getVacina().getIdVacina());

		Integer dosesAplicadas = aplicacoes.size();
		//Period periodo = Period.between(LocalDate.now(), p.getAplicacoes().get(dosesAplicadas).getDataAplicacao().plusDays(v.getIntervaloDoses()));
		// obter as aplicacoes da vacina em questao
		//a) nao atingiu o limite de doses - olhar a quantidade de aplicacoes
		//b) intervalo entre a ultima dose e HOJE > intervalo da vacina em questao -- olhar a ultima aplicacao
		//pode receber nova aplicacao SE(e somente se)
		if(dosesAplicadas == 0) {
			mensagem += "Esta pessoa não aplicou nenhuma dose";
	//	} else if(p.getAplicacoes().size() == PessoaVO.getQuantidadeDoses()) {
			mensagem += "Esta pessoa atingiu o limite de doses";
		//} else if(p.getAplicacoes().size() < v.getQuantidadeDoses()) {
			//mensagem += "Esta pessoa aplicou "+dosesAplicadas+" doses.\nRestam "+(v.getQuantidadeDoses()-dosesAplicadas)+" doses.\nProxima dose: "+p.getAplicacoes().get(dosesAplicadas).getDataAplicacao().plusDays(v.getIntervaloDoses());
		//if(periodo.getDays() != v.getIntervaloDoses()) {
				//mensagem += "Restam "+periodo.getDays()+" dias até a próxima dose.";
			
		//Verificar intervalo
		 throw new AplicacaoException(mensagem);
		 
		//return resposta;
	
	//public String vacinar(PessoaVO p, VacinaVO v) {
		//String mensagem = "";
		//if(podeVacinar(p, v)) {
		//AplicacaoVacinaVO aplic = new AplicacaoVacinaVO();
		//aplic.setIdPessoa(p);
		//aplic.setVacina(v);

		//AplicacaoVacinaDAO.inserir(aplic);

		//return mensagem;
	}
		return resposta;

	//https://www.devmedia.com.br/como-manipular-datas-com-o-java-8/34135
	}
	
}


