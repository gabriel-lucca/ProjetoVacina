package util;

public class Data {
	public String validarData(int dia, int mes, int ano) {
		String mensagem = null ;
		if(mes>12) {
			mensagem+="\nO ano não pode possuir mais de 12 meses.";
		}
		
		if(mes%2!=0&&dia>31 || mes==8&&dia>31) {
			mensagem+="\nEste mês possui no máximo 31 dias.";
		}
		
		if(mes%2==0&&dia>30){
			if(mes==2&&dia>29) {
				mensagem+="\nEste mês possui no máximo 29 dias.";
			} else {
				mensagem+="\nEste mês possui no máximo 30 dias.";
			}
		}
	return mensagem;
	}
}
