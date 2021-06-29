package seletor;

public class FiltroVacina {
	//Atributos para filtragem:
		private String nomeVacina;
		private String pais;
		private String doses;
		//Atributos para possível pagição dos resultados;
		private int limite;
		private int pagina;
		
		public FiltroVacina() {
			this.limite=0;
			this.limite=-1;
		}

		public String getNomeVacina() {
			return nomeVacina;
		}

		public void setNomeVacina(String nomeVacina) {
			this.nomeVacina = nomeVacina;
		}

		public String getPais() {
			return pais;
		}

		public void setPais(String pais) {
			this.pais = pais;
		}

		public String getDoses() {
			return doses;
		}

		public void setDoses(String doses) {
			this.doses = doses;
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
			if(nomeVacina!=null&&!nomeVacina.isEmpty()) {
				return true;
			}
			if(pais!=null&&!pais.isEmpty()) {
				return true;
			}
			
			if(doses!=null&&!doses.isEmpty()) {
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
		
		public String criarFiltros(FiltroVacina seletor, String sql) {
			sql = " where ";
			boolean primeiro = true;
			if(seletor.getNomeVacina()!=null && !seletor.getNomeVacina().isEmpty()) {
				if(!primeiro) {
					sql += " and ";
				}
				sql+=" nomeVacina LIKE '%"+seletor.getNomeVacina()+"%'";
				primeiro = false;
			}
			
			if(seletor.getPais()!=null && !seletor.getPais().isEmpty()) {
				if(!primeiro) {
					sql += " and ";
				}
				sql+=" paisOrigem LIKE '%"+seletor.getPais()+"%'";
				primeiro = false;
			}
			
			if(seletor.getDoses()!=null && !seletor.getDoses().isEmpty()) {
				if(!primeiro) {
					sql += " and ";
				}
				sql+=" quantidadeDoses = " +Integer.parseInt(seletor.getDoses());
				primeiro = false;
			}
			
			return sql;
	}
}

