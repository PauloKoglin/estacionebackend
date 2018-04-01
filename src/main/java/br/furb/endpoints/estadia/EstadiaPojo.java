/**
 * 
 */
package br.furb.endpoints.estadia;

/**
 * @author PauloArnoldo
 *
 */
public class EstadiaPojo {
	private Long idEstadia;
	private Long idUsuario;
	private Long idEstacionamento;
	private String dataEntrada;
	private String dataSaida;
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
	public String getDataEntrada() {
		return dataEntrada;
	}
	public void setDataEntrada(String dataEntrada) {
		this.dataEntrada = dataEntrada;
	}
	public String getDataSaida() {
		return dataSaida;
	}
	public void setDataSaida(String dataSaida) {
		this.dataSaida = dataSaida;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	
	
}
