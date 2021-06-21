package model.bo;

import javax.swing.JOptionPane;

import model.dao.VacinaDAO;
import model.vo.VacinaVO;

public class VacinaBO {
	
	VacinaDAO dao = new VacinaDAO();
	public String cadastrarVacinaBO(VacinaVO vacina) {
		String mensagem = "";
		//N�o deve repetir nomes de vacinas:
		if(dao.vacinaJaExiste(vacina)) {
			mensagem = "Vacina j� existe.";
		}
		
		//N�o deve haver mais de uma vacina por pa�s;
		if(dao.paisJaTemVacinaCadastrada(vacina)) {
			mensagem += "Este pa�s j� possui vacina cadastrada.";
		}
		
		//Os campos devem possuir no m�nimo d�gitos 3 e no m�ximo 100
		//NomePesquisador:
		if(vacina.getNomePesquisadorResponsavel().length() < 3) {
			mensagem += "O nome do pesquisador deve conter no m�nimo 3 caracteres";
		}else if(vacina.getNomePesquisadorResponsavel().length() > 100) {
			mensagem += "O nome do pesquisador n�o pode passar de 100 caracteres";
		}
		//paisOrigem:
		if(vacina.getPaisOrigem().length() < 3) {
			mensagem += "O pa�s de origem deve conter no m�nimo 3 caracteres";
		}else if(vacina.getPaisOrigem().length() > 100) {
			mensagem += "O pa�s de origem n�o pode passar de 100 caracteres";
		}
		//NomeVacina:
		if(vacina.getNomeVacina().length() < 3) {
			mensagem += "O nome da vacina deve conter no m�nimo 3 caracteres";
		}else if(vacina.getNomeVacina().length() > 100) {
			mensagem += "O nome da vacina n�o pode passar de 100 caracteres";
		}
		
		if(mensagem.isEmpty()) {
			mensagem = "Vacina cadastrada com sucesso!";
		}
		
		dao.cadastrar(vacina);
		return mensagem;
	}
	public void alterarVacina(VacinaVO vacinaAlterada) {
		System.out.println(vacinaAlterada);
	}
	public Integer excluir(Integer id) {
		dao.excluir(id);
		return id;

	}
	
	public VacinaVO consultarPorNome(String nome) {
		return dao.consultarPorNome(nome);
	}
	
}
 