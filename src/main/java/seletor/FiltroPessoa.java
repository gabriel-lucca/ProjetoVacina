package seletor;

import java.time.LocalDate;

public class FiltroPessoa {
	//Atributos para filtragem:
	private String nome;
	private int idadeMinima;
	private int idadeMaxima;
	private String cidade;
	//Atributos para possível pagição dos resultados;
	private int limite;
	private int pagina;
	
	public FiltroPessoa() {
		this.limite=0;
		this.limite=-1;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public int getIdadeMinima() {
		return idadeMinima;
	}
	public void setIdadeMinima(int idadeMinima) {
		this.idadeMinima = idadeMinima;
	}
	public int getIdadeMaxima() {
		return idadeMaxima;
	}
	public void setIdadeMaxima(int idadeMaxima) {
		this.idadeMaxima = idadeMaxima;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public int getLimite() {
		return limite;
	}
	public void setLimite(int limite) {
		this.limite = limite;
	}
	public int getPagina() {
		return pagina;
	}
	public void setPagina(int pagina) {
		this.pagina = pagina;
	}
	
	public boolean temFiltro() {
		if(nome!=null&&!nome.isEmpty()) {
			return true;
		}
		if(idadeMinima!=0) {
			return true;
		}
		if(idadeMaxima!=0) {
			return true;
		}
		if(cidade!=null&&!cidade.isEmpty()) {
			return true;
		}
		return false;
	}
	public boolean temPaginacao() {		
		return ((this.limite>0)&&(this.pagina>-1));
	}
	public int getOffset() {
		return (this.limite)*(this.pagina-1);
	}
	public String criarFiltros(FiltroPessoa seletor, String sql) {
		sql = " where ";
		boolean primeiro = true;
		if(seletor.getNome()!=null && !seletor.getNome().isEmpty()) {
			if(!primeiro) {
				sql += " and ";
			}
			sql+=" nomePessoa LIKE '%"+seletor.getNome()+"%'";
			primeiro = false;
		}
		if(seletor.getIdadeMinima()!=0) {
			if(!primeiro) {
				sql += " and ";
			}
			sql+=" timestampdiff(YEAR, dtNascimento, now()) >= "+seletor.getIdadeMinima();
			primeiro = false;
		}
		if(seletor.getIdadeMaxima()!=0) {
			if(!primeiro) {
				sql += " and ";
			}
			sql+=" timestampdiff(YEAR, dtNascimento, now()) <= "+seletor.getIdadeMaxima();
			primeiro = false;
		}
		if(seletor.getCidade()!=null && !seletor.getCidade().isEmpty()) {
			if(!primeiro) {
				sql += " and ";
			}
			sql+=" cidade LIKE '%"+seletor.getCidade()+"%'";
			primeiro = false;
		}
		return sql;
	}
}




























