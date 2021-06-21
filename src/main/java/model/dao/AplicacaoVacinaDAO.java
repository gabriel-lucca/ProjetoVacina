package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.vo.AplicacaoVacinaVO;
import model.vo.PessoaVO;
import model.vo.VacinaVO;

public class AplicacaoVacinaDAO {

	public AplicacaoVacinaVO cadastrar(AplicacaoVacinaVO novaAplicacao) {
		Connection conn = Banco.getConnection();
		String sql = "insert into aplicacaoVacina(fkIdPessoa, idVacina, dtAplicacao)"
				+ "values(?, ?, ?)";
		PreparedStatement ps = Banco.getPreparedStatement(conn, sql);
		PessoaVO pessoa = new PessoaVO();
		VacinaVO vacina = new VacinaVO();
		ResultSet rs = null;
		try {
			ps.setInt(1, pessoa.getIdPessoa());
			ps.setInt(2, vacina.getIdVacina());
			ps.setDate(3, java.sql.Date.valueOf(novaAplicacao.getDataAplicacao()));
			ps.execute();
			rs = ps.getGeneratedKeys();
			if(rs.next()) {
				novaAplicacao.setIdAplicacaoVacina(rs.getInt(1));
			}
		}catch(SQLException e) {
			System.out.println("Erro ao cadastrar aplicação vacina.\nErro: "+e.getMessage());
		}finally {
			Banco.closeConnection(conn);
			Banco.closePreparedStatement(ps);
		}
		return novaAplicacao;
	}
	public boolean alterar(AplicacaoVacinaVO aplicacaoAlterada) {
		Connection conn = Banco.getConnection();
		String sql = "update aplicacaoVacina"
				+ "set idPessoa=?, idVacina=? dataAplicacao=?"
				+ "where idVacina=?";
		PreparedStatement ps = Banco.getPreparedStatement(conn, sql);
		PessoaVO pessoa = new PessoaVO();
		VacinaVO vacina = new VacinaVO();
		boolean resposta = false;
		try {
			ps.setInt(1, pessoa.getIdPessoa());
			ps.setInt(2, vacina.getIdVacina());
			ps.setDate(3, java.sql.Date.valueOf(aplicacaoAlterada.getDataAplicacao()));
			resposta = ps.executeUpdate() > 0;
		}catch(SQLException e) {
			System.out.println("Erro ao alterar aplicação.\nErro: "+e.getMessage());
		}finally {
			Banco.closeConnection(conn);
			Banco.closePreparedStatement(ps);
		}
		return resposta;
	}
	public boolean excluir(Integer id) {
		Connection conn = Banco.getConnection();
		String sql = "delete from aplicacaoVacina where idAplicacaoVacina=?";
		PreparedStatement ps = Banco.getPreparedStatement(conn, sql);
		boolean resposta = false;
		try {
			ps.setInt(1, id);
			resposta = ps.executeUpdate()>0;
		}catch(SQLException e) {
			System.out.println("Erro ao excluir aplicação.\nErro: "+e.getMessage());
		}finally {
			Banco.closeConnection(conn);
			Banco.closePreparedStatement(ps);
		}
		return resposta;
	}
	private AplicacaoVacinaVO construirDoResultSet(ResultSet rs) throws SQLException {
		AplicacaoVacinaVO aplicacaoVacina = new AplicacaoVacinaVO();
		aplicacaoVacina.setIdAplicacaoVacina(rs.getInt("idAplicacao"));
		aplicacaoVacina.setDataAplicacao(rs.getDate("dtAplicacao").toLocalDate());
		PessoaDAO pessoaDAO = new PessoaDAO();
		PessoaVO pessoaVO = pessoaDAO.buscarPorId(rs.getInt("fkIdPessoa"));
		aplicacaoVacina.setPessoa(pessoaVO);
		
		VacinaDAO vacinaDao = new VacinaDAO();
		VacinaVO vacinaAplicada = vacinaDao.buscarPorId(rs.getInt("IdVacina"));
		aplicacaoVacina.setVacina(vacinaAplicada);
		
		return aplicacaoVacina;
	}
		
	public AplicacaoVacinaVO buscarPorId(Integer id) {
		Connection conn= Banco.getConnection();
		String sql = "select * from aplicacaoVacina where idVacina=?";
		PreparedStatement ps = Banco.getPreparedStatement(conn, sql);
		AplicacaoVacinaVO aplicacaoEncontrada = new AplicacaoVacinaVO();
		try {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				AplicacaoVacinaVO aplicacao = construirDoResultSet(rs);
			}
		}catch(SQLException e) {
			System.out.println("Erro ao buscar por vacina.\nErro: "+e.getMessage());
		}finally {
			Banco.closeConnection(conn);
			Banco.closePreparedStatement(ps);
		}
		return aplicacaoEncontrada;
	}
	
	public AplicacaoVacinaVO consultarPorCpf(String cpf) {
		
		AplicacaoVacinaVO aplicacaoVacinaConsultada = null;
		
		String sql = "select * from aplicacaoVacina where idAplicacao = ?";

		try (Connection conn = Banco.getConnection();
				PreparedStatement stmt = Banco.getPreparedStatement(conn, sql);) {
			stmt.setString(1, cpf);
			
			ResultSet resultadoConsulta = stmt.executeQuery();
			
			if (resultadoConsulta.next()) {
				aplicacaoVacinaConsultada = this.construirDoResultSet(resultadoConsulta);
				
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar aplicacao vacina por CPF: \n" + e.getMessage());
		}
		return aplicacaoVacinaConsultada;
	}
	
	public ArrayList<AplicacaoVacinaVO> buscarTodos(){
		Connection conn = Banco.getConnection();
		String sql = "select * from vacina";
		PreparedStatement ps = Banco.getPreparedStatementWithPk(conn, sql);
		ArrayList<AplicacaoVacinaVO> aplicacoes = new ArrayList<AplicacaoVacinaVO>();
		try {
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				AplicacaoVacinaVO vacina = construirDoResultSet(rs);
				aplicacoes.add(vacina);
			}
		}catch(SQLException e) {
			System.out.println("Erro ao buscar todas vacinas.\nErro: "+e.getMessage());
		}finally {
			Banco.closeConnection(conn);
			Banco.closePreparedStatement(ps);
		}
		
		return aplicacoes;
	}
	public ArrayList<AplicacaoVacinaVO> consultarAplicacoes(PessoaVO p) {
		Connection conn = Banco.getConnection();
		String sql = "select * from aplicacaoVacina where fkIdPessoa= ?";
		PreparedStatement ps = Banco.getPreparedStatementWithPk(conn, sql);
		ArrayList<AplicacaoVacinaVO> aplicacoesEncontradas = new ArrayList<AplicacaoVacinaVO>();
		
		try {
			ps.setInt(1, p.getIdPessoa());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				AplicacaoVacinaVO aplicacaoEncontrada = construirDoResultSet(rs);
				aplicacoesEncontradas.add(aplicacaoEncontrada);
			}
			
		} catch(SQLException e) {
			System.out.println("Erro ao buscar aplicações de "+p.getNome()+ "\nErro: "+e.getMessage());
		}
		
		return aplicacoesEncontradas;
	}
	
	
}
