package br.furb.persistence;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.furb.endpoints.usuario.UsuarioPojo;
import br.furb.model.UsuarioEntity;

@Repository
public class UsuarioDao extends BaseDao<UsuarioEntity, UsuarioPojo> {

	/*private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());*/
	
	@Override
	public Class<UsuarioEntity> getEntityClass() {
		return UsuarioEntity.class;
	}

	@Override
	protected UsuarioEntity pojoToEntity(UsuarioPojo pojo, UsuarioEntity entity) {
		entity.setId(pojo.getIdUsuario());
		entity.setNome(pojo.getNome());
		entity.setEmail(pojo.getEmail());
		entity.setSenha(pojo.getSenha());
		entity.setLogin(pojo.getLogin());
		entity.setTermosPoliticas(pojo.isTermosPoliticas());
		entity.setTipoUsuario(pojo.getTipoUsuario());
		
		/*Criteria criteria = hibernateTemplate.getSessionFactory().getCurrentSession()
				.createCriteria(UsuarioEntity.class);
		criteria.add(Restrictions.or(Restrictions.eq("login", SecurityContextHolder.getContext().getAuthentication().getName())));
		Object uniqueResult = criteria.uniqueResult();
		if (uniqueResult != null) {
			entity.setUsuario((UsuarioEntity)uniqueResult);
		}
		return entity;*/
		return entity;
	}

	@Override
	protected UsuarioPojo entityToPojo(UsuarioEntity entity, UsuarioPojo pojo) {
		pojo.setIdUsuario(entity.getId());
		pojo.setEmail(entity.getEmail());
		pojo.setNome(entity.getNome());
		pojo.setSenha(entity.getSenha());
		pojo.setLogin(entity.getLogin());
		pojo.setTermosPoliticas(entity.getTermosPoliticas());
		pojo.setTipoUsuario(entity.getTipoUsuario());
		
		return pojo;
	}

	@Override
	protected UsuarioEntity newEntity(Object...adicionais) {
		return new UsuarioEntity();
	}

	@Override
	protected UsuarioPojo newPojo(Object...adicionais) {
		return new UsuarioPojo();
	}

}
