package model.vo;

import java.time.LocalDate;

public class VacinaVO {

	private Integer idVacina;
	private String nomePesquisadorResponsavel;
	private String PaisOrigem;
	private String nomeVacina;
	private LocalDate dataInicioPesquisa;
	private int quantidadeDoses;
	private int intervaloDoses;
	
	public VacinaVO(Integer idVacina, String nomePesquisadorResponsavel, String paisOrigem, String nomeVacina,
			LocalDate dataInicioPesquisa, int quantidadeDoses, int intervaloDoses) {
		super();
		this.idVacina = idVacina;
		this.nomePesquisadorResponsavel = nomePesquisadorResponsavel;
		PaisOrigem = paisOrigem;
		this.nomeVacina = nomeVacina;
		this.dataInicioPesquisa = dataInicioPesquisa;
		this.quantidadeDoses = quantidadeDoses;
		this.intervaloDoses = intervaloDoses;
	}

	public VacinaVO() {
		super();
	}

	public Integer getIdVacina() {
		return idVacina;
	}

	public void setIdVacina(Integer idVacina) {
		this.idVacina = idVacina;
	}

	public String getNomePesquisadorResponsavel() {
		return nomePesquisadorResponsavel;
	}

	public void setNomePesquisadorResponsavel(String nomePesquisadorResponsavel) {
		this.nomePesquisadorResponsavel = nomePesquisadorResponsavel;
	}

	public String getPaisOrigem() {
		return PaisOrigem;
	}

	public void setPaisOrigem(String paisOrigem) {
		PaisOrigem = paisOrigem;
	}

	public String getNomeVacina() {
		return nomeVacina;
	}

	public void setNomeVacina(String nomeVacina) {
		this.nomeVacina = nomeVacina;
	}

	public LocalDate getDataInicioPesquisa() {
		return dataInicioPesquisa;
	}

	public void setDataInicioPesquisa(LocalDate dataInicioPesquisa) {
		this.dataInicioPesquisa = dataInicioPesquisa;
	}

	public int getQuantidadeDoses() {
		return quantidadeDoses;
	}

	public void setQuantidadeDoses(int quantidadeDoses) {
		this.quantidadeDoses = quantidadeDoses;
	}

	public int getIntervaloDoses() {
		return intervaloDoses;
	}

	public void setIntervaloDoses(int intervaloDoses) {
		this.intervaloDoses = intervaloDoses;
	}

	@Override
	public String toString() {
		return "VacinaVO [idVacina=" + idVacina + ", nomePesquisadorResponsavel=" + nomePesquisadorResponsavel
				+ ", PaisOrigem=" + PaisOrigem + ", nomeVacina=" + nomeVacina + ", dataInicioPesquisa="
				+ dataInicioPesquisa + ", quantidadeDoses=" + quantidadeDoses + ", intervaloDoses=" + intervaloDoses
				+ "]";
	}

	
	
 
}
