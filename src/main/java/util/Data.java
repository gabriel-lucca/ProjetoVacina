package util;

public class Data {
	
	public String validarData(int dia, int mes, int ano) {
		String mensagem = "" ;
		
		 if(dia>0 && dia<32 && mes>0 && mes<13 && ano>1900 && ano<2022 &&
				 ((mes==1 || mes==3 || mes==5 || mes==7 || mes==8 || mes==10 || mes==12) || ((mes==4 || 
				 mes==6 || mes==9 || mes==11) && dia<=30) || 
				 (mes==2 &&(dia<=29 && ano%4==0 && (ano%100!=0 || ano%400==0))|| dia<=28))){
			  mensagem+="DATA VÁLIDA.";
		 }else{
		      mensagem+="DATA INVÁLIDA.";
		 }
		
	return mensagem;
	}

}
	
	
