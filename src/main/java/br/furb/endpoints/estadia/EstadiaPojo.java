/**
 * 
 */
package br.furb.endpoints.estadia;

import java.util.Date;

/**
 * @author PauloArnoldo
 *
 */
public class EstadiaPojo {
	private Long idEstadia;
	private Long idUsuario;
	private Long idEstacionamento;
	private Date dataEntrada;
	private Date dataSaida;
	private double preco;
	
	
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
	public Long getIdEstacionamento() {
		return idEstacionamento;
	}
	public void setIdEstacionamento(Long idEstacionamento) {
		this.idEstacionamento = idEstacionamento;
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
	
	
}
