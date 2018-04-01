package br.furb.persistence;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.hql.internal.ast.tree.IsNullLogicOperatorNode;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.furb.endpoints.estadia.EstadiaPojo;
import br.furb.model.EstacionamentoEntity;
import br.furb.model.EstadiaEntity;
import br.furb.model.UsuarioEntity;


@Repository
public class EstadiaDao extends BaseDao<EstadiaEntity, EstadiaPojo> {

	@Override
	public Class<EstadiaEntity> getEntityClass() {
		return EstadiaEntity.class;
	}

	@Override
	protected EstadiaEntity pojoToEntity(EstadiaPojo pojo, EstadiaEntity entity) {
		entity.setId(pojo.getIdEstadia());
		entity.setEstacionamento(hibernateTemplate.load(EstacionamentoEntity.class, pojo.getIdEstacionamento()));
		entity.setUsuario(hibernateTemplate.load(UsuarioEntity.class, pojo.getIdUsuario()));
		entity.setDataEntrada(pojo.getDataEntrada());
		entity.setDataSaida(pojo.getDataSaida());
		entity.setPreco(pojo.getPreco());
		return entity;
	}

	@Override
	protected EstadiaPojo entityToPojo(EstadiaEntity entity, EstadiaPojo pojo) {
		pojo.setIdEstadia(entity.getId());
		pojo.setIdEstacionamento(entity.getEstacionamento().getId());
		pojo.setIdUsuario(entity.getUsuario().getId());
		pojo.setDataEntrada(entity.getDataEntrada());
		pojo.setDataSaida(entity.getDataSaida());
		pojo.setPreco(entity.getPreco());
		return pojo;
	}

	@Override
	protected EstadiaEntity newEntity(Object...adicionais) {
		EstadiaEntity estadiaEntity = new EstadiaEntity();
		if (adicionais != null && adicionais.length > 0) {
			EstacionamentoEntity estacionamentoEntity = hibernateTemplate.load(EstacionamentoEntity.class, (Long)adicionais[0]);
			estadiaEntity.setEstacionamento(estacionamentoEntity);			
		}
		if (adicionais != null && adicionais.length > 1) {
			UsuarioEntity usuarioEntity = hibernateTemplate.load(UsuarioEntity.class, (Long)adicionais[1]);
			estadiaEntity.setUsuario(usuarioEntity);			
		}
		return estadiaEntity;
	}

	@Override
	protected EstadiaPojo newPojo(Object...adicionais) {
		return new EstadiaPojo();
	}
	
	@SuppressWarnings("unchecked")
	public List<EstadiaPojo> findAllByEstacionamentoId(Long idEstacionamento) {
		Criteria criteria = hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(EstadiaEntity.class);
		criteria.add(Restrictions.eq("est.id_Estacionamento", idEstacionamento));
		List<EstadiaPojo> list = criteria.list();
		if (!list.isEmpty()) {
			return list;
		} else
			return null;
		
		/*return findAll(crit -> {
			crit.createAlias("estacionamento", "est");
			crit.add(Restrictions.eq("est.id_Estacionamento", idEstacionamento));
		});*/
	}

	public List<EstadiaPojo> findAllByUsuarioId(Long idUsuario) {
		return findAll(crit -> {
			crit.add(Restrictions.eq("id_usuario", idUsuario));
		});
	}
	
	public EstadiaPojo findAbertaUsuarioId(Long idEstacionamento) {				
		return findAll(crit -> {
			crit.createAlias("usuario", "usu");
			crit.add(Restrictions.eq("usu.id_usuario", idEstacionamento));
			crit.add(Restrictions.isNull("dt_saida"));
			crit.add(Restrictions.isNotEmpty("dt_entrada"));
		}).get(0);
	}
}
