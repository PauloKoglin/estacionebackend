/**
 * 
 */
package br.furb.model;

import java.sql.Time;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * @author PauloArnoldo
 *
 */
@Entity
@Table(name = EstacionamentoHorariosEntity.TABLE_NAME)
@SequenceGenerator(name="estacionamento_horario_id")
public class EstacionamentoHorariosEntity implements BaseEntity {
	
	public static final String TABLE_NAME = "estacionamento_horario";	
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, targetEntity = EstacionamentoEntity.class)
	@JoinColumn(name="id_estacionamento", nullable=true)
	private EstacionamentoEntity estacionamento;	
	
	@Id
	@GeneratedValue
	@Column(name = "id_horario")
	private Long idHorario;	
	
	
	@Column(name = "nm_diaSemana")
	private int diaSemana;
	
	@Column(name = "hr_inicio")
	private Time horaInicio;
	
	@Column(name = "hr_fim")
	private Time horaFim;
	
		
	public EstacionamentoEntity getEstacionamento() {
		return estacionamento;
	}
	public void setEstacionamento(EstacionamentoEntity estacionamento) {
		this.estacionamento = estacionamento;
	}
	public Long getIdHorario() {
		return idHorario;
	}
	public void setIdHorario(Long idHorario) {
		this.idHorario = idHorario;
	}
	public int getDiaSemana() {
		return diaSemana;
	}
	public void setDiaSemana(int diaSemana) {
		this.diaSemana = diaSemana;
	}
	public Time getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(Time horaInicio) {
		this.horaInicio = horaInicio;
	}
	public Time getHoraFim() {
		return horaFim;
	}
	public void setHoraFim(Time horaFim) {
		this.horaFim = horaFim;
	}
	@Override
	public Long getId() {
		return this.idHorario;
	}
	@Override
	public void setId(Long id) {
		this.idHorario = id;		
	}
}
