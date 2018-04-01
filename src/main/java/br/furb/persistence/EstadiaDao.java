package br.furb.persistence;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
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
		entity.setUsuario(hibernateTemplate.load(UsuarioEntity.class, pojo.getIdUsuario()));
		System.out.println("pojoToEntity Entrou 3");
		entity.setPreco(pojo.getPreco());
		try {
			if (!pojo.getDataEntrada().isEmpty())
				entity.setDataEntrada(sdf.parse(pojo.getDataEntrada()));
			
			System.out.println("pojoToEntity Entrou 4");
			
			if (!pojo.getDataSaida().isEmpty())
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
		pojo.setDataEntrada(sdf.format(entity.getDataEntrada()));
		pojo.setDataSaida(sdf.format(entity.getDataSaida()));
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
		System.out.println("Realizando consulta pelo ID do estacionamento.");
		//Criteria criteria = hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(EstadiaEntity.class);		
		DetachedCriteria criteria = DetachedCriteria.forClass(EstadiaEntity.class);  
		criteria.add(Restrictions.eq("id_estacionamento", idEstacionamento));
		
		List<EstadiaPojo> list = (List<EstadiaPojo>) hibernateTemplate.findByCriteria(criteria);
		
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
			crit.add(Restrictions.eq("id_usuario", idEstacionamento));
			crit.add(Restrictions.isNull("dt_saida"));
			crit.add(Restrictions.isNotEmpty("dt_entrada"));
		}).get(0);
	}
}
