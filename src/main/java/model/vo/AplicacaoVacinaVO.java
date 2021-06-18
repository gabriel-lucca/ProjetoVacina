package model.vo;

import java.time.LocalDate;

public class AplicacaoVacinaVO {

		private Integer idAplicacaoVacina;
		private PessoaVO pessoa;
		private VacinaVO vacina;
		private LocalDate dataAplicacao;
		
		public AplicacaoVacinaVO() {
			super();
		}
		public AplicacaoVacinaVO(Integer idAplicacaoVacina, PessoaVO pessoa, VacinaVO vacina,LocalDate dataAplicacao) {
			super();
			this.idAplicacaoVacina = idAplicacaoVacina;
			this.pessoa = pessoa;
			this.vacina = vacina;
			this.dataAplicacao = dataAplicacao;
		}
		public Integer getIdAplicacaoVacina() {
			return idAplicacaoVacina;
		}
		public void setIdAplicacaoVacina(Integer idAplicacaoVacina) {
			this.idAplicacaoVacina = idAplicacaoVacina;
		}
		public PessoaVO getIdPessoa() {
			return pessoa;
		}
		public void setIdPessoa(PessoaVO idPessoa) {
			this.pessoa = idPessoa;
		}
		
		public PessoaVO getPessoa() {
			return pessoa;
		}
		public void setPessoa(PessoaVO pessoa) {
			this.pessoa = pessoa;
		}
			
		public VacinaVO getVacina() {
			return vacina;
		}
		public void setVacina(VacinaVO vacina) {
			this.vacina = vacina;
		}

		public LocalDate getDataAplicacao() {
			return dataAplicacao;
		}

		public void setDataAplicacao(LocalDate dataAplicacao) {
			this.dataAplicacao = dataAplicacao;
		}
		@Override
		public String toString() {
			return "AplicacaoVacinaVO [idAplicacaoVacina=" + idAplicacaoVacina + ", pessoa=" + pessoa + ", vacina="
					+ vacina + ", dataAplicacao=" + dataAplicacao + "]";
		}
	}	