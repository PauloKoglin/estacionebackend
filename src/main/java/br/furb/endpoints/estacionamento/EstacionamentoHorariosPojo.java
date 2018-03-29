/**
 * 
 */
package br.furb.endpoints.estacionamento;

import java.sql.Time;

import br.furb.model.EstacionamentoEntity;


/**
 * @author PauloArnoldo
 *
 */
public class EstacionamentoHorariosPojo {
	
	private EstacionamentoEntity estacionamento;
	private Long idHorario;
	private int diaSemana;
	private Time horaInicio;
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
	
	
	
}
