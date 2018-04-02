/**
 * 
 */
package br.furb.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author PauloArnoldo
 *
 */

@Entity
@Table(name = EstadiaEntity.TABLE_NAME)
@SequenceGenerator(name="estadia_sequence", sequenceName="estadia_id", initialValue=1, allocationSize=1)
public class EstadiaEntity implements BaseEntity {
	
	public static final String TABLE_NAME = "estadia";

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="estadia_sequence")	
	@Column(name = "id_estadia")	
	private Long idEstadia;
	
	@ManyToOne(targetEntity = UsuarioEntity.class)//, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id")
	private UsuarioEntity usuario;
	
	@ManyToOne(targetEntity = EstacionamentoEntity.class)//, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "estacionamento_id")
	private EstacionamentoEntity estacionamento;
	
	@Column(name = "dt_entrada")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataEntrada;
	
	@Column(name = "dt_saida")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataSaida;
	
	@Column(name="vl_preco", length=10, precision=2, nullable=false)
	private double preco;

	@Override
	public Long getId() {
		return this.idEstadia;
	}

	@Override
	public void setId(Long id) {
		this.idEstadia = id;		
	}

	public Long getIdEstadia() {
		return idEstadia;
	}

	public void setIdEstadia(Long idEstadia) {
		this.idEstadia = idEstadia;
	}

	public UsuarioEntity getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioEntity usuario) {
		this.usuario = usuario;
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
}
