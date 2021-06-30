package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.vo.PessoaVO;
import model.vo.VacinaVO;
import seletor.FiltroVacina;

public class VacinaDAO  {
	public VacinaVO cadastrar(VacinaVO vacina) {
		Connection conn = Banco.getConnection();
		String sql = "insert into vacina(nomePesquisadorResponsavel, paisOrigem, nomeVacina, dtInicioPesquisa, quantidadeDoses, intervaloDoses)"
				+ "values(?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = Banco.getPreparedStatementWithPk(conn, sql);
		ResultSet rs = null;
		try {
			ps.setString(1, vacina.getNomePesquisadorResponsavel());
			ps.setString(2, vacina.getPaisOrigem());
			ps.setString(3,  vacina.getNomeVacina());
			ps.setDate(4, java.sql.Date.valueOf(vacina.getDataInicioPesquisa()));
			ps.setString(5, vacina.getQuantidadeDoses());
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
			Banco.closeResultSet(rs);
		}
		return vacina;
	}
	public boolean alterar(VacinaVO vacina) {
		Connection conn = Banco.getConnection();
		String sql = " update vacina "
				+ " set nomePesquisadorResponsavel=?, paisOrigem=?, nomeVacina=?, dtInicioPesquisa=?, quantidadeDoses=?, intervaloDoses=?"
				+ " where idVacina=?";
		PreparedStatement ps = Banco.getPreparedStatement(conn, sql);
		boolean resposta = false;
		try {
			ps.setString(1, vacina.getNomePesquisadorResponsavel());
			ps.setString(2, vacina.getPaisOrigem());
			ps.setString(3,  vacina.getNomeVacina());
			ps.setDate(4, java.sql.Date.valueOf(vacina.getDataInicioPesquisa()));
			ps.setString(5, vacina.getQuantidadeDoses());
			ps.setInt(6, vacina.getIntervaloDoses());
			ps.setInt(7, vacina.getIdVacina());
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
	public VacinaVO construirDoResultSet(ResultSet rs) throws SQLException {
		VacinaVO vacina = new VacinaVO();
		vacina.setIdVacina(rs.getInt("idVacina"));
		vacina.setNomePesquisadorResponsavel(rs.getString("nomePesquisadorResponsavel"));
		vacina.setPaisOrigem(rs.getString("paisOrigem"));
		vacina.setNomeVacina(rs.getString("nomeVacina"));
		vacina.setDataInicioPesquisa(rs.getDate("dtInicioPesquisa").toLocalDate());
		vacina.setQuantidadeDoses(rs.getString("quantidadeDoses"));
		vacina.setIntervaloDoses(rs.getInt("intervaloDoses"));
		return vacina;
	}
	public VacinaVO consultarPorId(Integer idVacinaSelecionada) {
		Connection conn= Banco.getConnection();
		String sql = "select * from vacina where idVacina=?";
		PreparedStatement ps = Banco.getPreparedStatement(conn, sql);
		VacinaVO vacinaEncontrada = new VacinaVO();
		try {
			ps.setInt(1, idVacinaSelecionada);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				vacinaEncontrada = construirDoResultSet(rs);
			}
		}catch(SQLException e) {
			System.out.println("Erro ao buscar por vacina.\nErro: "+e.getMessage());
		}finally {
			Banco.closeConnection(conn);
			Banco.closePreparedStatement(ps);
		}
		return vacinaEncontrada;
	}
	public ArrayList<VacinaVO> consultarTodos(){
		Connection conn = Banco.getConnection();
		String sql = "select * from vacina";
		PreparedStatement ps = Banco.getPreparedStatementWithPk(conn, sql);
		ArrayList<VacinaVO> vacinas = new ArrayList<VacinaVO>();
		try {
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				VacinaVO vacina = construirDoResultSet(rs);
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
		Connection conn = Banco.getConnection();
		String sql = "select * from vacina where nomeVacina = ?";
		PreparedStatement ps = Banco.getPreparedStatement(conn, sql);
		boolean resposta = false;
		try {
			ps.setString(1, vacina.getNomeVacina());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				resposta = true;
			}
		} catch(SQLException e) {
			System.out.println("Erro ao verificar se vacina existe.\nErro: "+e.getMessage());
		} finally {
			Banco.closeConnection(conn);
			Banco.closePreparedStatement(ps);
		}
		return resposta;
	}
	public boolean paisJaTemVacinaCadastrada(VacinaVO vacina) {
		Connection conn = Banco.getConnection();
		String sql = "select * from vacina where paisOrigem = ?";
		PreparedStatement ps = Banco.getPreparedStatement(conn, sql);
		boolean resposta = false;
		try {
			ps.setString(1, vacina.getPaisOrigem());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				resposta = true;
			}
		} catch(SQLException e) {
			System.out.println("Erro ao verificar se vacina existe.\nErro: "+e.getMessage());
		} finally {
			Banco.closeConnection(conn);
			Banco.closePreparedStatement(ps);
		}
		return resposta;
	}
	public VacinaVO consultarPorNome(String nome) {
		VacinaVO vacinaEncontrada = new VacinaVO();	
		String sql = "select * from vacina where nomeVacina = ?";
		try (Connection conn = Banco.getConnection();
				PreparedStatement stmt = Banco.getPreparedStatement(conn, sql);) {
			stmt.setString(1, nome);
			ResultSet resultadoConsulta = stmt.executeQuery();
			if (resultadoConsulta.next()) {
				vacinaEncontrada = this.construirDoResultSet(resultadoConsulta);	
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar vacina por nome: \n" + e.getMessage());
		}
		return vacinaEncontrada;
	}
	public ArrayList<VacinaVO> consultarComFiltro(FiltroVacina seletor) {
		ArrayList<VacinaVO> encontrado = new ArrayList<VacinaVO>();
		Connection conn = Banco.getConnection();
		String sql = "select * from vacina ";
		
		if(seletor.temFiltro()) {
			sql += seletor.criarFiltros(seletor, sql);
		}
		PreparedStatement ps = Banco.getPreparedStatement(conn, sql);
		try {
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				encontrado.add(construirDoResultSet(rs));
			}
		} catch(SQLException e) {
			System.out.println("Erro ao buscar por filtro.\nErro: "+e.getMessage());
		} finally {
			Banco.closeConnection(conn);
			Banco.closeConnection(conn);
		}
		return encontrado;
	}
}
