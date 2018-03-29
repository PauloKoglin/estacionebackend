/**
 * 
 */
package br.furb.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author PauloArnoldo
 *
 */

@Entity
@Table(name = EstacionamentoEntity.TABLE_NAME)
public class EstacionamentoEntity implements BaseEntity {

	public static final String TABLE_NAME = "estacionamento";
	
	@Id
	@GeneratedValue
	@Column(name = "id_estacionamento")
	private Long idEstacionamento;	
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "usuario_id")
	private UsuarioEntity usuarioProprietario;
	
	@Column(name = "nm_estacionamento", length = 1500)
	private String nome;
	
	@Column(name = "vl_longitude")
	private double longitude;
	
	@Column(name = "vl_latitude")
	private double latitude;
	
	@Column(name = "ds_localizacao", length = 1500)
	private String complementoLocalizacao;
	
	@Column(name = "vl_preco")
	private double preco;
	
	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, targetEntity = EstacionamentoHorariosEntity.class, mappedBy = "estacionamento")
	private List<EstacionamentoHorariosEntity> horarios = new ArrayList<>();
		
	@Override
	public Long getId() {
		return this.idEstacionamento;
	}
	@Override
	public void setId(Long id) {
		this.idEstacionamento = id;
	}
	public Long getIdEstacionamento() {
		return idEstacionamento;
	}
	public void setIdEstacionamento(Long idEstacionamento) {
		this.idEstacionamento = idEstacionamento;
	}
	public UsuarioEntity getUsuario() {
		return usuarioProprietario;
	}
	public void setUsuario(UsuarioEntity proprietario) {
		this.usuarioProprietario = proprietario;
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
	public List<EstacionamentoHorariosEntity> getHorarios() {
		return horarios;
	}
	public void setHorarios(List<EstacionamentoHorariosEntity> horarios) {
		this.horarios = horarios;
	}
	
}
