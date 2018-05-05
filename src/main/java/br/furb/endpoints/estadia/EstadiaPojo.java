/**
 * 
 */
package br.furb.endpoints.estadia;

import java.util.Date;

import br.furb.endpoints.estacionamento.EstacionamentoPojo;
import br.furb.model.EstacionamentoEntity;

/**
 * @author PauloArnoldo
 *
 */
public class EstadiaPojo {
	private Long idEstadia;
	private Long idUsuario;
	private EstacionamentoEntity estacionamento;
	private Date dataEntrada;
	private Date dataSaida;
	private double preco;
	private String idPagamento;
	
	
	public Long getIdEstadia() {
		return idEstadia;
	}
	public void setIdEstadia(Long idEstadia) {
		this.idEstadia = idEstadia;
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public EstacionamentoEntity getEstacionamento() {
		return estacionamento;
	}
	public void setEstacionamento(EstacionamentoEntity estacionamento) {
		this.estacionamento = estacionamento;
	}
	public Date getDataEntrada() {
		return dataEntrada;
	}
	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}
	public Date getDataSaida() {
		return dataSaida;
	}
	public void setDataSaida(Date dataSaida) {
		this.dataSaida = dataSaida;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	public String getIdPagamento() {
		return idPagamento;
	}
	public void setIdPagamento(String idPagamento) {
		this.idPagamento = idPagamento;
	}
	
	
}
