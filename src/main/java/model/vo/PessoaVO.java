package model.vo;

import java.time.LocalDate;
import java.util.ArrayList;

public class PessoaVO {

	private Integer idPessoa;
	private String nome;
	private String cpf;
	private String email;
	private String telefone;
	private LocalDate dataNascimento;
	private String cidade;
	private String estado;
	private String endereco;
	
	public PessoaVO(Integer idPessoa, String nome, String cpf, String email, String telefone, LocalDate dataNascimento,
			String cidade, String estado, String endereco) {
		super();
		this.idPessoa = idPessoa;
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.telefone = telefone;
		this.dataNascimento = dataNascimento;
		this.cidade = cidade;
		this.estado = estado;
		this.endereco = endereco;
	}

	public PessoaVO() {
		super();
	}

	public Integer getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Integer idPessoa) {
		this.idPessoa = idPessoa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@Override
	public String toString() {
		return "PessoaVO [idPessoa=" + idPessoa + ", nome=" + nome + ", cpf=" + cpf + ", email=" + email + ", telefone="
				+ telefone + ", dataNascimento=" + dataNascimento + ", cidade=" + cidade + ", estado=" + estado
				+ ", endereco=" + endereco + "]";
	}

	public LocalDate getAplicacoes() {
		// TODO Auto-generated method stub
		return null;
	}

}

