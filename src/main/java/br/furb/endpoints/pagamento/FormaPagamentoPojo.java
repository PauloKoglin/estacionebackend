/**
 * 
 */
package br.furb.endpoints.pagamento;

import br.furb.model.UsuarioEntity;


/**
 * @author PauloArnoldo
 *
 */

public class FormaPagamentoPojo {

	private Long idFormaPagamento;	
	private UsuarioEntity usuario;
	private String numero;
	private String nomeCartao;
	private String validade;
	private String bandeira;
	private String codigoSeguranca;
	
	public Long getIdFormaPagamento() {
		return idFormaPagamento;
	}
	public void setIdFormaPagamento(Long idFormaPagamento) {
		this.idFormaPagamento = idFormaPagamento;
	}
	public UsuarioEntity getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioEntity usuario) {
		this.usuario = usuario;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getNomeCartao() {
		return nomeCartao;
	}
	public void setNomeCartao(String nomeCartao) {
		this.nomeCartao = nomeCartao;
	}
	public String getValidade() {
		return validade;
	}
	public void setValidade(String validade) {
		this.validade = validade;
	}
	public String getBandeira() {
		return bandeira;
	}
	public void setBandeira(String bandeira) {
		this.bandeira = bandeira;
	}
	public String getCodigoSeguranca() {
		return codigoSeguranca;
	}
	public void setCodigoSeguranca(String codigoSeguranca) {
		this.codigoSeguranca = codigoSeguranca;
	}
	
	
}
