package br.furb.persistence;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import br.furb.endpoints.estadia.EstadiaPojo;
import br.furb.model.EstacionamentoEntity;
import br.furb.model.EstadiaEntity;
import br.furb.model.UsuarioEntity;


@Repository
public class EstadiaDao extends BaseDao<EstadiaEntity, EstadiaPojo> {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

	@Override
	public Class<EstadiaEntity> getEntityClass() {
		return EstadiaEntity.class;
	}

	@Override
	protected EstadiaEntity pojoToEntity(EstadiaPojo pojo, EstadiaEntity entity) {
		entity.setIdEstadia(pojo.getIdEstadia());
		System.out.println("pojoToEntity Entrou 1");
		entity.setEstacionamento(hibernateTemplate.load(EstacionamentoEntity.class, pojo.getIdEstacionamento()));
		System.out.println("pojoToEntity Entrou 2");
		
		UsuarioEntity usuario = null; 
		Criteria usuarioCriteria = hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(UsuarioEntity.class);
		usuarioCriteria.add(Restrictions.or(Restrictions.eq("login", SecurityContextHolder.getContext().getAuthentication().getName())));
		Object uniqueResult = usuarioCriteria.uniqueResult();
		if (uniqueResult != null) {
			usuario = (UsuarioEntity)uniqueResult;
		}
		entity.setUsuario(usuario);
		
		System.out.println("pojoToEntity Entrou 3");
		entity.setPreco(pojo.getPreco());
		try {
			if (pojo.getDataEntrada() != "")
				entity.setDataEntrada(sdf.parse(pojo.getDataEntrada()));
			
			System.out.println("pojoToEntity Entrou 4");
			
			if (pojo.getDataSaida() != null && !pojo.getDataSaida().isEmpty())
				entity.setDataSaida(sdf.parse(pojo.getDataSaida()));
			
			System.out.println("pojoToEntity Entrou 5");
		} catch (ParseException e) {
			System.out.println(e.getMessage()); 
			e.printStackTrace();
		}	
		
		return entity;
	}

	@Override
	protected EstadiaPojo entityToPojo(EstadiaEntity entity, EstadiaPojo pojo) {
		pojo.setIdEstadia(entity.getIdEstadia());
		pojo.setIdEstacionamento(entity.getEstacionamento().getId());
		pojo.setIdUsuario(entity.getUsuario().getId());
		pojo.setPreco(entity.getPreco());
		
		if (entity.getDataEntrada() != null)
			pojo.setDataEntrada(sdf.format(entity.getDataEntrada()));
			
		System.out.println("entityToPojo Entrou 6");
			
		if (entity.getDataSaida() != null)
			pojo.setDataSaida(sdf.format(entity.getDataSaida()));
			
		System.out.println("entityToPojo Entrou 7");
		
		//pojo.setDataEntrada(sdf.format(entity.getDataEntrada()));
		//pojo.setDataSaida(sdf.format(entity.getDataSaida()));
		
		return pojo;
	}

	@Override
	protected EstadiaEntity newEntity(Object...adicionais) {
		EstadiaEntity estadiaEntity = new EstadiaEntity();
		/*if (adicionais != null && adicionais.length > 0) {
			EstacionamentoEntity estacionamentoEntity = hibernateTemplate.load(EstacionamentoEntity.class, (Long)adicionais[0]);
			estadiaEntity.setEstacionamento(estacionamentoEntity);			
		}*/
		/*if (adicionais != null && adicionais.length > 1) {
			UsuarioEntity usuarioEntity = hibernateTemplate.load(UsuarioEntity.class, (Long)adicionais[1]);
			estadiaEntity.setUsuario(usuarioEntity);			
		}*/
		return estadiaEntity;
	}

	@Override
	protected EstadiaPojo newPojo(Object...adicionais) {
		return new EstadiaPojo();
	}
	
	@SuppressWarnings("unchecked")
	public List<EstadiaPojo> findAllByEstacionamentoId(Long idEstacionamento) {
		System.out.println("Realizando consulta pelo ID do estacionamento.");		
		DetachedCriteria criteria = DetachedCriteria.forClass(EstadiaEntity.class);  
		criteria.createAlias("estacionamento", "et");
		criteria.add(Restrictions.eq("et.idEstacionamento", idEstacionamento));
				
		List<EstadiaPojo> list = (List<EstadiaPojo>) hibernateTemplate.findByCriteria(criteria);
		
		if (!list.isEmpty()) {
			return list;
		} else
			return null;
		
		/*return findAll(crit -> {
			crit.createAlias("estacionamento", "est");
			crit.add(Restrictions.eq("est.idEstacionamento", idEstacionamento));
		});*/
	}

	@SuppressWarnings("unchecked")
	public List<EstadiaPojo> findAllByUsuarioId(Long idUsuario) {
		System.out.println("Realizando consulta pelo ID do usuário.");		
		DetachedCriteria criteria = DetachedCriteria.forClass(EstadiaEntity.class);  
		criteria.createAlias("usuario", "usu");
		criteria.add(Restrictions.eq("usu.idUsuario", idUsuario));
				
		List<EstadiaPojo> list = (List<EstadiaPojo>) hibernateTemplate.findByCriteria(criteria);
		
		if (!list.isEmpty()) {
			return list;
		} else
			return null;
	}
	
	@SuppressWarnings("unchecked")
	public EstadiaPojo findAbertaUsuario() {				
		System.out.println("Realizando consulta de estadia aberta.");
		
		UsuarioEntity usuario = null; 
		Criteria usuarioCriteria = hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(UsuarioEntity.class);
		usuarioCriteria.add(Restrictions.or(Restrictions.eq("login", SecurityContextHolder.getContext().getAuthentication().getName())));
		Object uniqueResult = usuarioCriteria.uniqueResult();
		if (uniqueResult != null) {
			usuario = (UsuarioEntity)uniqueResult;
		}
		
		DetachedCriteria criteria = DetachedCriteria.forClass(EstadiaEntity.class);  
		criteria.createAlias("usuario", "usu");
		criteria.add(Restrictions.eq("usu.idUsuario", usuario.getId()));
		criteria.add(Restrictions.isNull("dt_saida"));
		criteria.add(Restrictions.isNotEmpty("dt_entrada"));		
		List<EstadiaPojo> list = (List<EstadiaPojo>) hibernateTemplate.findByCriteria(criteria);
		
		if (!list.isEmpty()) {
			return list.get(0);
		} else
			return null;		
		
		/*return findAll(crit -> {
			crit.add(Restrictions.eq("id_usuario", idEstacionamento));
			crit.add(Restrictions.isNull("dt_saida"));
			crit.add(Restrictions.isNotEmpty("dt_entrada"));
		}).get(0);*/
	}
}
