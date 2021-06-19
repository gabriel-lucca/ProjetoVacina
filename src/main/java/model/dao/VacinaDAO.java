package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.vo.VacinaVO;

public class VacinaDAO  {
	public VacinaVO cadastrar(VacinaVO vacina) {
		Connection conn = Banco.getConnection();
		String sql = "insert into vacina(nomePesquisadorResponsavel, paisOrigem, nomeVacina, dtInicioPesquisa, quantidadeDoses, intervaloDoses)"
				+ "values(?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = Banco.getPreparedStatement(conn, sql);
		ResultSet rs = null;
		try {
			ps.setString(1, vacina.getNomePesquisadorResponsavel());
			ps.setString(2, vacina.getPaisOrigem());
			ps.setString(3,  vacina.getNomeVacina());
			ps.setDate(4, java.sql.Date.valueOf(vacina.getDataInicioPesquisa()));
			ps.setInt(5, vacina.getQuantidadeDoses());
			ps.setInt(6, vacina.getIntervaloDoses());
			ps.execute();
			rs = ps.getGeneratedKeys();
			if(rs.next()) {
				vacina.setIdVacina(rs.getInt(1));
			}
		}catch(SQLException e) {
			System.out.println("Erro ao cadastrar vacina.\nErro: "+e.getMessage());
		}finally {
			Banco.closeConnection(conn);
			Banco.closePreparedStatement(ps);
		}
		return vacina;
	}
	public boolean alterar(VacinaVO vacina) {
		Connection conn = Banco.getConnection();
		String sql = "update vacina "
				+ "set nomePesquisadorResponsavel=?, paisOrigem=?, nomeVacina=?, dtInicioPesquisa=?, quantidadeDoses=?, intervaloDoses=?"
				+ "where idVacina=?";
		PreparedStatement ps = Banco.getPreparedStatement(conn, sql);
		boolean resposta = false;
		try {
			ps.setString(1, vacina.getNomePesquisadorResponsavel());
			ps.setString(2, vacina.getPaisOrigem());
			ps.setString(3,  vacina.getNomeVacina());
			ps.setDate(4, java.sql.Date.valueOf(vacina.getDataInicioPesquisa()));
			ps.setInt(5, vacina.getQuantidadeDoses());
			ps.setInt(6, vacina.getIntervaloDoses());
			resposta = ps.executeUpdate() > 0;
		}catch(SQLException e) {
			System.out.println("Erro ao alterar vacina.\nErro: "+e.getMessage());
		}finally {
			Banco.closeConnection(conn);
			Banco.closePreparedStatement(ps);
		}
		return resposta;
	}
	public boolean excluir(Integer id) {
		Connection conn = Banco.getConnection();
		String sql = "delete from vacina where idVacina=?";
		PreparedStatement ps = Banco.getPreparedStatement(conn, sql);
		boolean resposta = false;
		try {
			ps.setInt(1, id);
			resposta = ps.executeUpdate()>0;
		}catch(SQLException e) {
			System.out.println("Erro ao excluir vacina.\nErro: "+e.getMessage());
		}finally {
			Banco.closeConnection(conn);
			Banco.closePreparedStatement(ps);
		}
		return resposta;
	}
	public VacinaVO construirVacinaDoResultSet(ResultSet rs) throws SQLException {
		VacinaVO vacina = new VacinaVO();
		vacina.setIdVacina(rs.getInt("idVacina"));
		vacina.setNomePesquisadorResponsavel(rs.getString("nomePesquisadorResponsavel"));
		vacina.setPaisOrigem(rs.getString("paisOrigem"));
		vacina.setNomeVacina(rs.getString("nomeVacina"));
		vacina.setDataInicioPesquisa(rs.getDate("dtInicioPesquisa").toLocalDate());
		vacina.setQuantidadeDoses(rs.getInt("quantidadeDoses"));
		vacina.setIntervaloDoses(rs.getInt("intervaloDoses"));
		return vacina;
	}
	public VacinaVO buscarPorId(Integer id) {
		Connection conn= Banco.getConnection();
		String sql = "select * from vacina where idVacina=?";
		PreparedStatement ps = Banco.getPreparedStatement(conn, sql);
		VacinaVO vacinaEncontrada = new VacinaVO();
		try {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				VacinaVO vacina = construirVacinaDoResultSet(rs);
			}
		}catch(SQLException e) {
			System.out.println("Erro ao buscar por vacina.\nErro: "+e.getMessage());
		}finally {
			Banco.closeConnection(conn);
			Banco.closePreparedStatement(ps);
		}
		return vacinaEncontrada;
	}
	public ArrayList<VacinaVO> buscarTodos(){
		Connection conn = Banco.getConnection();
		String sql = "select * from vacina";
		PreparedStatement ps = Banco.getPreparedStatementWithPk(conn, sql);
		ArrayList<VacinaVO> vacinas = new ArrayList<VacinaVO>();
		try {
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				VacinaVO vacina = construirVacinaDoResultSet(rs);
				vacinas.add(vacina);
			}
		}catch(SQLException e) {
			System.out.println("Erro ao buscar todas vacinas.\nErro: "+e.getMessage());
		}finally {
			Banco.closeConnection(conn);
			Banco.closePreparedStatement(ps);
		}
		
		return vacinas;
	}
	public boolean vacinaJaExiste(VacinaVO vacina) {
		ArrayList<VacinaVO> vacinas = new ArrayList<VacinaVO>();
		boolean resposta = false;
		for(VacinaVO v : vacinas) {
			 if(vacina.getNomeVacina().equalsIgnoreCase(v.getNomeVacina())) {
				 resposta = true;
			 }
			}
		return resposta;
	}
	
	public boolean paisJaTemVacinaCadastrada(VacinaVO vacina) {
		ArrayList<VacinaVO> vacinas = new ArrayList<VacinaVO>();
		boolean resposta = false;
		for(VacinaVO v : vacinas) {
			 if(vacina.getPaisOrigem().equalsIgnoreCase(v.getPaisOrigem())) {
				 resposta = true;
			 }
			}
		return resposta;
	}
	public VacinaVO getVacina() {

	return buscarPorId(0);
	}
	
}