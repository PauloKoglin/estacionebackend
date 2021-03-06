/**
 * 
 */
package br.furb.persistence;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import br.furb.endpoints.estacionamento.EstacionamentoHorariosPojo;
import br.furb.endpoints.estacionamento.EstacionamentoPojo;
import br.furb.model.EstacionamentoEntity;
import br.furb.model.UsuarioEntity;

/**
 * @author PauloArnoldo
 *
 */

@Repository
public class EstacionamentoDao extends BaseDao<EstacionamentoEntity, EstacionamentoPojo> {

	/*private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());*/
	
	@Override
	public Class<EstacionamentoEntity> getEntityClass() {
		return EstacionamentoEntity.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected EstacionamentoEntity pojoToEntity(EstacionamentoPojo pojo, EstacionamentoEntity entity) {
		entity.setIdEstacionamento(pojo.getIdEstacionamento());
		entity.setNome(pojo.getNome());
		entity.setLatitude(pojo.getLatitude());
		entity.setLongitude(pojo.getLongitude());
		entity.setComplementoLocalizacao(pojo.getComplementoLocalizacao());
		entity.setPreco(pojo.getPreco());
		//entity.setHorarios(null);
		//entity.setUsuario(pojo.getUsuario());
		//entity.setHorarios(null);
		/*
		if (pojo.getHorarios().size() > 0) {
			for (EstacionamentoHorariosPojo horarioPojo : pojo.getHorarios()) {
				EstacionamentoHorariosEntity horarioEntity = new EstacionamentoHorariosEntity();
				horarioEntity.setEstacionamento(horarioPojo.getEstacionamento());
				horarioEntity.setIdHorario(horarioPojo.getIdHorario());
				horarioEntity.setDiaSemana(horarioPojo.getDiaSemana());
				horarioEntity.setHoraFim(horarioPojo.getHoraFim());
				horarioEntity.setHoraInicio(horarioPojo.getHoraInicio());			
				entity.getHorarios().add(horarioEntity);
			}		
		}*/
		/*Criteria criteria = hibernateTemplate.getSessionFactory().getCurrentSession()
				.createCriteria(UsuarioEntity.class);
		criteria.add(Restrictions.or(Restrictions.eq("login", SecurityContextHolder.getContext().getAuthentication().getName())));
		Object uniqueResult = criteria.uniqueResult();
		if (uniqueResult != null) {
			entity.setUsuario((UsuarioEntity)uniqueResult);
		}*/
		DetachedCriteria criteriaUsuario = DetachedCriteria.forClass(UsuarioEntity.class);  
		criteriaUsuario.add(Restrictions.eq("login", SecurityContextHolder.getContext().getAuthentication().getName()));
		List<UsuarioEntity> usuarioList = (List<UsuarioEntity>) hibernateTemplate.findByCriteria(criteriaUsuario);
		
		if (!usuarioList.isEmpty()) {
			entity.setUsuario(usuarioList.get(0));
		} 
		
		return entity;
	}

	@Override
	protected EstacionamentoPojo entityToPojo(EstacionamentoEntity entity, EstacionamentoPojo pojo) {
		pojo.setIdEstacionamento(entity.getIdEstacionamento());
		pojo.setNome(entity.getNome());
		pojo.setComplementoLocalizacao(entity.getComplementoLocalizacao());
		pojo.setLatitude(entity.getLatitude());
		pojo.setLongitude(entity.getLongitude());
		pojo.setPreco(entity.getPreco());
		pojo.setUsuario(entity.getUsuario());
		//pojo.setHorarios(null);
		/*
		for (EstacionamentoHorariosEntity horarioEntity : entity.getHorarios()) {
			EstacionamentoHorariosPojo horarioPojo = new EstacionamentoHorariosPojo();
			horarioPojo.setEstacionamento(horarioEntity.getEstacionamento());
			horarioPojo.setIdHorario(horarioEntity.getIdHorario());
			horarioPojo.setDiaSemana(horarioEntity.getDiaSemana());
			horarioPojo.setHoraFim(horarioEntity.getHoraFim());
			horarioPojo.setHoraInicio(horarioEntity.getHoraInicio());			
			pojo.getHorarios().add(horarioPojo);
		}*/
		
		return pojo;
	}

	@Override
	protected EstacionamentoEntity newEntity(Object...adicionais) {
		return new EstacionamentoEntity();
	}

	@Override
	protected EstacionamentoPojo newPojo(Object...adicionais) {
		return new EstacionamentoPojo();
	}

}
