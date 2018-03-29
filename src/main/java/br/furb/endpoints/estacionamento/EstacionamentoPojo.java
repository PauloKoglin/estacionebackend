package br.furb.endpoints.estacionamento;

import java.util.ArrayList;
import br.furb.model.UsuarioEntity;


public class EstacionamentoPojo {
	
	private Long idEstacionamento;	
	private UsuarioEntity usuario;
	private String nome;
	private double longitude;
	private double latitude;
	private String complementoLocalizacao;
	private double preco;
	private ArrayList<EstacionamentoHorariosPojo> horarios;
	
	
	public Long getIdEstacionamento() {
		return idEstacionamento;
	}
	public void setIdEstacionamento(Long idEstacionamento) {
		this.idEstacionamento = idEstacionamento;
	}
	public UsuarioEntity getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioEntity usuario) {
		this.usuario = usuario;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
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
