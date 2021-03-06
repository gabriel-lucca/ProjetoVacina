package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.vo.PessoaVO;
import seletor.FiltroPessoa;

public class PessoaDAO {
	public PessoaVO cadastrar(PessoaVO pessoa) {
		Connection conn = Banco.getConnection();
		String sql = "insert into pessoa(nomePessoa, cpf, email, telefone, dtNascimento, cidade, estado, endereco)"
				+ "values(?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = Banco.getPreparedStatementWithPk(conn, sql);
		ResultSet rs = null;
		try {
			ps.setString(1, pessoa.getNome());
			ps.setString(2, pessoa.getCpf());
			ps.setString(3, pessoa.getEmail());
			ps.setString(4, pessoa.getTelefone());
			ps.setDate(5, java.sql.Date.valueOf(pessoa.getDataNascimento()));
			ps.setString(6, pessoa.getCidade());
			ps.setString(7, pessoa.getEstado());
			ps.setString(8, pessoa.getEndereco());
			ps.execute();
			
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				pessoa.setIdPessoa(rs.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao cadastrar pessoa.\nErro: " + e.getMessage());
		} finally {
			Banco.closeConnection(conn);
			Banco.closePreparedStatement(ps);
			Banco.closeResultSet(rs);
		}
		return pessoa;
	}

	public boolean alterar(PessoaVO pessoaAlterada) {
		Connection conn = Banco.getConnection();
		String sql = "update pessoa "
				+ "set nomePessoa=?, cpf=?, email=?, telefone=?, dtNascimento=?, cidade=?, estado=?, endereco=?"
				+ "where idPessoa=?";
		PreparedStatement ps = Banco.getPreparedStatement(conn, sql);
		boolean resposta = false;
		try {
			ps.setString(1, pessoaAlterada.getNome());
			ps.setString(2, pessoaAlterada.getCpf());
			ps.setString(3, pessoaAlterada.getEmail());
			ps.setString(4, pessoaAlterada.getTelefone());
			ps.setDate(5, java.sql.Date.valueOf(pessoaAlterada.getDataNascimento()));
			ps.setString(6, pessoaAlterada.getCidade());
			ps.setString(7, pessoaAlterada.getEstado());
			ps.setString(8, pessoaAlterada.getEndereco());
			ps.setInt(9, pessoaAlterada.getIdPessoa());
			resposta = ps.executeUpdate() > 0;
		} catch (SQLException e) {
			System.out.println("Erro ao alterar pessoa.\nErro: " + e.getMessage());
		} finally {
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
			resposta = ps.executeUpdate() > 0;
		} catch (SQLException e) {
			System.out.println("Erro ao excluir pessoa.\nErro: " + e.getMessage());
		} finally {
			Banco.closeConnection(conn);
			Banco.closePreparedStatement(ps);
		}
		return resposta;
	}

	public PessoaVO construirDoResultSet(ResultSet rs) throws SQLException {
		PessoaVO pessoa = new PessoaVO();
		pessoa.setIdPessoa(rs.getInt("idPessoa"));
		pessoa.setNome(rs.getString("nomePessoa"));
		pessoa.setCpf(rs.getString("cpf"));
		pessoa.setTelefone(rs.getString("telefone"));
		pessoa.setDataNascimento(rs.getDate("dtNascimento").toLocalDate());
		pessoa.setEmail(rs.getString("email"));
		pessoa.setCidade(rs.getString("cidade"));
		pessoa.setEstado(rs.getString("estado"));
		pessoa.setEndereco(rs.getString("endereco"));
		return pessoa;
	}

	public PessoaVO buscarPorId(Integer idPessoaSelecionada) {
		Connection conn = Banco.getConnection();
		String sql = "select * from pessoa where idPessoa=?";
		PreparedStatement ps = Banco.getPreparedStatement(conn, sql);
		PessoaVO pessoaEncontrada = new PessoaVO();
		try {
			ps.setInt(1, idPessoaSelecionada);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				pessoaEncontrada = construirDoResultSet(rs);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao buscar por pessoa.\nErro: " + e.getMessage());
		} finally {
			Banco.closeConnection(conn);
			Banco.closePreparedStatement(ps);
		}
		return pessoaEncontrada;
	}

	public ArrayList<PessoaVO> buscarTodos() {
		Connection conn = Banco.getConnection();
		String sql = "select * from pessoa";
		PreparedStatement ps = Banco.getPreparedStatementWithPk(conn, sql);
		ArrayList<PessoaVO> pessoas = new ArrayList<PessoaVO>();
		try {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				PessoaVO pessoa = construirDoResultSet(rs);
				pessoas.add(pessoa);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao buscar todas pessoas.\nErro: " + e.getMessage());
		} finally {
			Banco.closeConnection(conn);
			Banco.closePreparedStatement(ps);
		}
		return pessoas;
	}

	public boolean pessoaJaExiste(PessoaVO pessoa) {
		Connection conn = Banco.getConnection();
		String sql = "select * from pessoa where nomePessoa =?";
		PreparedStatement ps = Banco.getPreparedStatement(conn, sql);
		boolean resposta = false;
		try {
			ps.setString(1, pessoa.getNome());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				resposta = true;
			}
		} catch(SQLException e) {
			System.out.println("Erro ao verificar se pessoa existe.\nErro: "+e.getMessage());
		} finally {
			Banco.closeConnection(conn);
			Banco.closePreparedStatement(ps);
		}
		return resposta;
	}
	public PessoaVO consultarPorCpf(String cpf) {
		Connection conn = Banco.getConnection();
		String sql = "select * from pessoa where cpf=?";
		PreparedStatement ps = Banco.getPreparedStatement(conn, sql);
		PessoaVO pessoaEncontrada = new PessoaVO();
		try {
			ps.setString(1, cpf);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				pessoaEncontrada = construirDoResultSet(rs);
			}
		} catch(SQLException e) {
			System.out.println("Erro ao buscar CPF.\nErro: "+e.getMessage());
		} finally {
			Banco.closeConnection(conn);
			Banco.closePreparedStatement(ps);
		}
		return pessoaEncontrada;
	}

	public PessoaVO consultarPorNome(String nome) {
		PessoaVO pessoaEncontrada = new PessoaVO();	
		String sql = "select * from pessoa where nomePessoa = ?";
		try (Connection conn = Banco.getConnection();
				PreparedStatement stmt = Banco.getPreparedStatement(conn, sql);) {
			stmt.setString(1, nome);
			ResultSet resultadoConsulta = stmt.executeQuery();
			if (resultadoConsulta.next()) {
				pessoaEncontrada = this.construirDoResultSet(resultadoConsulta);	
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar pessoa por nome: \n" + e.getMessage());
		}
		return pessoaEncontrada;
	}

	public ArrayList<PessoaVO> consultarComFiltro(FiltroPessoa seletor) {
		ArrayList<PessoaVO> encontrado = new ArrayList<PessoaVO>();
		Connection conn = Banco.getConnection();
		String sql = "select * from pessoa ";
		
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

	public boolean cpfJaExiste(PessoaVO pessoa) {
		Connection conn = Banco.getConnection();
		String sql = "select * from pessoa where cpf = ?";
		PreparedStatement ps = Banco.getPreparedStatement(conn, sql);
		boolean resposta = false;
		try {
			ps.setString(1, pessoa.getCpf());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				resposta = true;
			}
		} catch(SQLException e) {
			System.out.println("Erro ao verificar se CPF existe.\nErro: "+e.getMessage());
		} finally {
			Banco.closeConnection(conn);
			Banco.closePreparedStatement(ps);
		}
		return resposta;
	}

	public boolean celularJaExiste(PessoaVO pessoa) {
		Connection conn = Banco.getConnection();
		String sql = "select * from pessoa where telefone = ?";
		PreparedStatement ps = Banco.getPreparedStatement(conn, sql);
		boolean resposta = false;
		try {
			ps.setString(1, pessoa.getTelefone());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				resposta = true;
			}
		} catch(SQLException e) {
			System.out.println("Erro ao verificar se celular existe.\nErro: "+e.getMessage());
		} finally {
			Banco.closeConnection(conn);
			Banco.closePreparedStatement(ps);
		}
		return resposta;
	}

	public ArrayList<PessoaVO> consultarTodosRelatorio() {
		Connection conn = Banco.getConnection();
		String sql = "select * from pessoa order by nomePessoa asc";
		PreparedStatement ps = Banco.getPreparedStatementWithPk(conn, sql);
		ArrayList<PessoaVO> pessoas = new ArrayList<PessoaVO>();
		try {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				PessoaVO pessoa = construirDoResultSet(rs);
				pessoas.add(pessoa);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao buscar todas pessoas.\nErro: " + e.getMessage());
		} finally {
			Banco.closeConnection(conn);
			Banco.closePreparedStatement(ps);
		}
		return pessoas;
	}

	public boolean emailJaExiste(PessoaVO pessoa) {
		Connection conn = Banco.getConnection();
		String sql = "select * from pessoa where email = ?";
		PreparedStatement ps = Banco.getPreparedStatement(conn, sql);
		boolean resposta = false;
		try {
			ps.setString(1, pessoa.getEmail());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				resposta = true;
			}
		} catch(SQLException e) {
			System.out.println("Erro ao verificar se Email existe.\nErro: "+e.getMessage());
		} finally {
			Banco.closeConnection(conn);
			Banco.closePreparedStatement(ps);
		}
		return resposta;
	}
	
}

















