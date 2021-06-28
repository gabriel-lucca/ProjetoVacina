package seletor;

import java.time.LocalDate;

public class FiltroPessoa {
	//Atributos para filtragem:
	private String nome;
	private LocalDate dtNascimento;
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
	public LocalDate getDtNascimento() {
		return dtNascimento;
	}
	public void setDtNascimento(LocalDate dtNascimento) {
		this.dtNascimento = dtNascimento;
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
		if(dtNascimento!=null) {
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
		if(seletor.getDtNascimento()!=null) {
			if(!primeiro) {
				sql += " and ";
			}
			sql+=" dtNascimento = "+seletor.getDtNascimento();
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
	@Override
	public String toString() {
		return "FiltroPessoa [nome=" + nome + ", dtNascimento=" + dtNascimento + ", cidade=" + cidade + "]";
	}
	
}




























