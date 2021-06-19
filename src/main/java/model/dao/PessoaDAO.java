package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.vo.AplicacaoVacinaVO;
import model.vo.PessoaVO;
import model.vo.VacinaVO;

public class PessoaDAO {
	public PessoaVO cadastrar(PessoaVO novaPessoa) {
		Connection conn = Banco.getConnection();
		String sql = "insert into pessoa(nomePessoa, cpf, email, telefone, dtNascimento, cidade, estado, endereco)"
				+ "values(?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = Banco.getPreparedStatementWithPk(conn, sql);
		ResultSet rs = null;
		try {
			ps.setString(1, novaPessoa.getNome());
			ps.setString(2, novaPessoa.getCpf());
			ps.setString(3,  novaPessoa.getEmail());
			ps.setString(4, novaPessoa.getTelefone());
			ps.setDate(5, java.sql.Date.valueOf(novaPessoa.getDataNascimento()));
			ps.setString(6, novaPessoa.getCidade());
			ps.setString(7, novaPessoa.getEstado());
			ps.setString(8, novaPessoa.getEndereco());
			ps.executeUpdate();
			
			System.out.println("print"+ ps.toString());
			rs = ps.getGeneratedKeys();
			if(rs.next()) {
				novaPessoa.setIdPessoa(rs.getInt(1));
			}
		}catch(SQLException e) {
			System.out.println("Erro ao cadastrar pessoa.\nErro: "+e.getMessage());
		}finally {
			Banco.closeConnection(conn);
			Banco.closePreparedStatement(ps);
			Banco.closeResultSet(rs);
		}
		return novaPessoa;
	}
	public boolean alterar(PessoaVO PessoaAlterada) {
		Connection conn = Banco.getConnection();
		String sql = "update pessoa "
				+ "set nomePessoa=?, cpf=?, email=?, telefone=?, dataNascimento=?, cidade=?, estado=?, endereco=?"
				+ "where idPessoa=?";
		PreparedStatement ps = Banco.getPreparedStatement(conn, sql);
		boolean resposta = false;
		try {
			ps.setString(1, PessoaAlterada.getNome());
			ps.setString(2, PessoaAlterada.getCpf());
			ps.setString(3,  PessoaAlterada.getEmail());
			ps.setString(4, PessoaAlterada.getTelefone());
			ps.setDate(5, java.sql.Date.valueOf(PessoaAlterada.getDataNascimento()));
			ps.setString(6, PessoaAlterada.getCidade()); 
			ps.setString(7, PessoaAlterada.getEstado());
			ps.setString(8, PessoaAlterada.getEndereco());
			resposta = ps.executeUpdate() > 0;
		}catch(SQLException e) {
			System.out.println("Erro ao alterar pessoa.\nErro: "+e.getMessage());
		}finally {
			Banco.closeConnection(conn);
			Banco.closePreparedStatement(ps);
		}
		return resposta;
	}
	public boolean excluir(Integer id) {
		Connection conn = Banco.getConnection();
		String sql = "delete from pessoa where idPessoa=?";
		PreparedStatement ps = Banco.getPreparedStatement(conn, sql);
		boolean resposta = false;
		try {
			ps.setInt(1, id);
			resposta = ps.executeUpdate()>0;
		}catch(SQLException e) {
			System.out.println("Erro ao excluir pessoa.\nErro: "+e.getMessage());
		}finally {
			Banco.closeConnection(conn);
			Banco.closePreparedStatement(ps);
		}
		return resposta;
	}
	public PessoaVO construirDoResultSet(ResultSet rs) throws SQLException {
		PessoaVO pessoa = new PessoaVO();
		pessoa.setIdPessoa(rs.getInt("idVacina"));
		pessoa.setNome(rs.getString("nomePessoa"));
		pessoa.setCpf(rs.getString("cpf"));
		pessoa.setTelefone(rs.getString("telefone"));
		pessoa.setDataNascimento(rs.getDate("dataNascimento").toLocalDate());
		pessoa.setCidade(rs.getString("cidade"));
		pessoa.setEstado(rs.getString("estado"));
		pessoa.setEndereco(rs.getString("endereco"));
		return pessoa;
	}
	public PessoaVO buscarPorId(Integer id) {
		Connection conn= Banco.getConnection();
		String sql = "select * from pessoa where idPessoa=?";
		PreparedStatement ps = Banco.getPreparedStatement(conn, sql);
		PessoaVO pessoaEncontrada = new PessoaVO();
		try {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				PessoaVO pessoa = construirDoResultSet(rs);
			}
		}catch(SQLException e) {
			System.out.println("Erro ao buscar por pessoa.\nErro: "+e.getMessage());
		}finally {
			Banco.closeConnection(conn);
			Banco.closePreparedStatement(ps);
		}
		return pessoaEncontrada;
	}
	public ArrayList<PessoaVO> buscarTodos(){
		Connection conn = Banco.getConnection();
		String sql = "select * from vacina";
		PreparedStatement ps = Banco.getPreparedStatementWithPk(conn, sql);
		ArrayList<PessoaVO> pessoasEncontradas = new ArrayList<PessoaVO>();
		try {
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				PessoaVO pessoaEncontrada = construirDoResultSet(rs);
				pessoasEncontradas.add(pessoaEncontrada);
			}
		}catch(SQLException e) {
			System.out.println("Erro ao buscar todas pessoas.\nErro: "+e.getMessage());
		}finally {
			Banco.closeConnection(conn);
			Banco.closePreparedStatement(ps);
		}
		
		return pessoasEncontradas;
	}
	public boolean vacinaJaExiste(PessoaVO pessoa) {
		ArrayList<PessoaVO> vacinas = new ArrayList<PessoaVO>();
		boolean resposta = false;
		for(PessoaVO v : vacinas) {
			 if(pessoa.getNome().equalsIgnoreCase(v.getNome())) {
				 resposta = true;
			 }
			}
		return resposta;
	}
	public PessoaVO consultarPorCpf(String cpf) {
		PessoaVO pessoaVacinaConsultada = null;
		
		String sql = "select * from pessoa where idPessoa = ?";

		try (Connection conn = Banco.getConnection();
				PreparedStatement stmt = Banco.getPreparedStatement(conn, sql);) {
			stmt.setString(1, cpf);
			
			ResultSet resultadoConsulta = stmt.executeQuery();
			
			if (resultadoConsulta.next()) {
				pessoaVacinaConsultada = this.construirDoResultSet(resultadoConsulta);
				
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar pessoa por CPF: \n" + e.getMessage());
		}
		return pessoaVacinaConsultada;
	}
}
