package br.furb.endpoints.estacionamento;

import java.util.ArrayList;

import br.furb.endpoints.usuario.UsuarioPojo;
import br.furb.model.UsuarioEntity;


public class EstacionamentoPojo {
	
	private Long idEstacionamento;	
	private UsuarioEntity usuarioProprietario;
	private String nome;
	private Long longitude;
	private Long latitude;
	private String complementoLocalizacao;
	private double preco;
	private ArrayList<EstacionamentoHorariosPojo> horarios;
	
	
	public Long getIdEstacionamento() {
		return idEstacionamento;
	}
	public void setIdEstacionamento(Long idEstacionamento) {
		this.idEstacionamento = idEstacionamento;
	}
	public UsuarioEntity getProprietario() {
		return usuarioProprietario;
	}
	public void setProprietario(UsuarioEntity proprietario) {
		this.usuarioProprietario = proprietario;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Long getLongitude() {
		return longitude;
	}
	public void setLongitude(Long longitude) {
		this.longitude = longitude;
	}
	public Long getLatitude() {
		return latitude;
	}
	public void setLatitude(Long latitude) {
		this.latitude = latitude;
	}
	public String getComplementoLocalizacao() {
		return complementoLocalizacao;
	}
	public void setComplementoLocalizacao(String complementoLocalizacao) {
		this.complementoLocalizacao = complementoLocalizacao;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	public ArrayList<EstacionamentoHorariosPojo> getHorarios() {
		return horarios;
	}
	public void setHorarios(ArrayList<EstacionamentoHorariosPojo> horarios) {
		this.horarios = horarios;
	}
		
	
	
	
}
