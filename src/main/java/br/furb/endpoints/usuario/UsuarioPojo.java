package br.furb.endpoints.usuario;

import br.furb.model.FormaPagamentoEntity;

public class UsuarioPojo {

	private Long idUsuario;
	private String nome;
	private String login;
	private String email;
	private String senha;
	private boolean TermosPoliticas;
	private String tipoUsuario;
	
	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public boolean isTermosPoliticas() {
		return TermosPoliticas;
	}

	public void setTermosPoliticas(boolean termosPoliticas) {
		TermosPoliticas = termosPoliticas;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}	
	
}
