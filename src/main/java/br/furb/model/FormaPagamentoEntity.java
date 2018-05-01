/**
 * 
 */
package br.furb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author PauloArnoldo
 *
 */
@Entity
@Table(name = FormaPagamentoEntity.TABLE_NAME)
@SequenceGenerator(name="formaPagemento_sequence", sequenceName="formaPagamento_id", initialValue=1, allocationSize=1)
public class FormaPagamentoEntity implements BaseEntity {
	
	public static final String TABLE_NAME = "formaPagamento";
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="estacionamento_sequence")
	@Column(name = "id_formaPagamento")
	private Long idFormaPagamento;	
	
	@Column(name = "nr_cartao")
	private Long numero;
	
	@Column(name = "nm_cartao")
	private String nomeCartao;
	
	@Column(name = "dt_validade")
	private String validade;
	
	@Column(name = "cd_seguranca")
	private String codigoSeguranca;
	
	@Column(name = "ds_bandeira")
	private String bandeira;

	public Long getIdFormaPagamento() {
		return idFormaPagamento;
	}

	public void setIdFormaPagamento(Long idFormaPagamento) {
		this.idFormaPagamento = idFormaPagamento;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
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

	public String getCodigoSeguranca() {
		return codigoSeguranca;
	}

	public void setCodigoSeguranca(String codigoSeguranca) {
		this.codigoSeguranca = codigoSeguranca;
	}

	public String getBandeira() {
		return bandeira;
	}

	public void setBandeira(String bandeira) {
		this.bandeira = bandeira;
	}

	@Override
	public Long getId() {
		return this.idFormaPagamento;
	}

	@Override
	public void setId(Long id) {
		this.idFormaPagamento = id;		
	}
	
	
}
