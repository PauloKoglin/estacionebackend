package br.furb.persistence;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.furb.endpoints.estadia.EstadiaPojo;
import br.furb.model.EstacionamentoEntity;
import br.furb.model.EstadiaEntity;
import br.furb.model.UsuarioEntity;


@Repository
public class EstadiaDao extends BaseDao<EstadiaEntity, EstadiaPojo> {
	
	private static final Locale locale = new Locale("pt","BR");
	private final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm", new Locale("pt","BR"));
	
	public EstadiaDao() {
		this.sdf.setTimeZone(TimeZone.getTimeZone("GMT-3"));
	}

	@Override
	public Class<EstadiaEntity> getEntityClass() {
		return EstadiaEntity.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected EstadiaEntity pojoToEntity(EstadiaPojo pojo, EstadiaEntity entity) {
		entity.setIdEstadia(pojo.getIdEstadia());
		System.out.println("pojoToEntity Entrou 1");
		entity.setEstacionamento(hibernateTemplate.load(EstacionamentoEntity.class, pojo.getIdEstacionamento()));
		System.out.println("pojoToEntity Entrou 2");
		
		/*UsuarioEntity usuario = null; 
		Criteria usuarioCriteria = hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(UsuarioEntity.class);
		usuarioCriteria.add(Restrictions.or(Restrictions.eq("login", SecurityContextHolder.getContext().getAuthentication().getName())));
		Object uniqueResult = usuarioCriteria.uniqueResult();
		if (uniqueResult != null) {
			usuario = (UsuarioEntity)uniqueResult;
		}*/
		UsuarioEntity usuario = null; 
		DetachedCriteria criteriaUsuario = DetachedCriteria.forClass(UsuarioEntity.class);  
		criteriaUsuario.add(Restrictions.eq("login", SecurityContextHolder.getContext().getAuthentication().getName()));
		List<UsuarioEntity> usuarioList = (List<UsuarioEntity>) hibernateTemplate.findByCriteria(criteriaUsuario);
		
		if (!usuarioList.isEmpty()) {
			usuario = usuarioList.get(0);
			System.out.println("Encontrou usuário. " + usuario.toString());
		} 
		
		entity.setUsuario(usuario);
		
		/*System.out.println("pojoToEntity Entrou 3");
		entity.setPreco(pojo.getPreco());
		try {
			if (pojo.getDataEntrada() != "" && !pojo.getDataEntrada().isEmpty())
				entity.setDataEntrada(sdf.parse(pojo.getDataEntrada()));
			
			System.out.println("pojoToEntity Entrou 4");
			
			if (pojo.getDataSaida() != null && !pojo.getDataSaida().isEmpty())
				entity.setDataSaida(sdf.parse(pojo.getDataSaida()));
			
			System.out.println("pojoToEntity Entrou 5");
		} catch (ParseException e) {
			System.out.println(e.getMessage()); 
			e.printStackTrace();
		}*/
		entity.setDataEntrada(pojo.getDataEntrada());
		entity.setDataSaida(pojo.getDataSaida());
		
		return entity;
	}

	@Override
	protected EstadiaPojo entityToPojo(EstadiaEntity entity, EstadiaPojo pojo) {
		pojo.setIdEstadia(entity.getIdEstadia());
		pojo.setIdEstacionamento(entity.getEstacionamento().getId());
		pojo.setIdUsuario(entity.getUsuario().getId());
		pojo.setPreco(entity.getPreco());
		
		/*if (entity.getDataEntrada() != null)
			pojo.setDataEntrada(sdf.format(entity.getDataEntrada()));
			
		System.out.println("entityToPojo Entrou 6");
			
		if (entity.getDataSaida() != null)
			pojo.setDataSaida(sdf.format(entity.getDataSaida()));*/
		
		pojo.setDataEntrada(entity.getDataEntrada());
		pojo.setDataSaida(entity.getDataSaida());
			
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
		
		//if (!list.isEmpty()) {
			return list;
		//} else
		//	return null;
		
		/*return findAll(crit -> {
			crit.createAlias("estacionamento", "est");
			crit.add(Restrictions.eq("est.idEstacionamento", idEstacionamento));
		});*/
	}

	@SuppressWarnings("unchecked")
	public List<EstadiaPojo> findAllUsuario() {
		System.out.println("Realizando consulta de todas as estadias do usuário.");
		
		UsuarioEntity usuario = null; 
		DetachedCriteria criteriaUsuario = DetachedCriteria.forClass(UsuarioEntity.class);  
		criteriaUsuario.add(Restrictions.eq("login", SecurityContextHolder.getContext().getAuthentication().getName()));
		List<UsuarioEntity> usuarioList = (List<UsuarioEntity>) hibernateTemplate.findByCriteria(criteriaUsuario);
		
		if (!usuarioList.isEmpty()) {
			usuario = usuarioList.get(0);
			System.out.println("Encontrou usuário. " + usuario.toString());
		} 	
		
		/*System.out.println("Realizando consulta pelo ID do usuário.");		
		DetachedCriteria criteria = DetachedCriteria.forClass(EstadiaEntity.class);  
		criteria.createAlias("usuario", "usu");
		criteria.add(Restrictions.eq("usu.id", usuario.getId()));
				
		List<EstadiaPojo> list = (List<EstadiaPojo>) hibernateTemplate.findByCriteria(criteria);*/
		DetachedCriteria criteria = DetachedCriteria.forClass(EstadiaEntity.class);  
		criteria.createAlias("usuario", "usu");
		criteria.add(Restrictions.eq("usu.id", usuario.getId()));		
		//List<EstadiaPojo> list = (List<EstadiaPojo>) hibernateTemplate.findByCriteria(criteria);
		List<EstadiaPojo> list = findAll(criteria);
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public EstadiaPojo findAbertaUsuario() {				
		System.out.println("Realizando consulta de estadia aberta.");
		
		UsuarioEntity usuario = null; 
		DetachedCriteria criteriaUsuario = DetachedCriteria.forClass(UsuarioEntity.class);  
		criteriaUsuario.add(Restrictions.eq("login", SecurityContextHolder.getContext().getAuthentication().getName()));
		List<UsuarioEntity> usuarioList = (List<UsuarioEntity>) hibernateTemplate.findByCriteria(criteriaUsuario);
		
		if (!usuarioList.isEmpty()) {
			usuario = usuarioList.get(0);
			System.out.println("Encontrou usuário. " + usuario.toString());
		} 	
		
		/*Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
		if (!session.isConnected())
			session = hibernateTemplate.getSessionFactory().openSession();
		
		Criteria usuarioCriteria = session.createCriteria(UsuarioEntity.class);
		usuarioCriteria.add(Restrictions.or(Restrictions.eq("login", SecurityContextHolder.getContext().getAuthentication().getName())));
		Object uniqueResult = usuarioCriteria.uniqueResult();
		if (uniqueResult != null) {
			usuario = (UsuarioEntity)uniqueResult;
		}*/
		
		DetachedCriteria criteria = DetachedCriteria.forClass(EstadiaEntity.class);  
		criteria.createAlias("usuario", "usu");
		criteria.add(Restrictions.eq("usu.id", usuario.getId()));
		criteria.add(Restrictions.isNull("dataSaida"));
		criteria.add(Restrictions.isNotNull("dataEntrada"));		
		//List<EstadiaPojo> list = (List<EstadiaPojo>) hibernateTemplate.findByCriteria(criteria);
		List<EstadiaPojo> list = findAll(criteria);
				
		if (!list.isEmpty()) {
			return list.get(0);
		} else
			return new EstadiaPojo();		
		
		/*return findAll(crit -> {
			crit.add(Restrictions.eq("id_usuario", idEstacionamento));
			crit.add(Restrictions.isNull("dt_saida"));
			crit.add(Restrictions.isNotEmpty("dt_entrada"));
		}).get(0);*/
	}
	
	@Transactional(rollbackFor = Throwable.class)
	public EstadiaPojo finalizarEstadia(Long idEstacionamento) {
		EstadiaPojo estadia = findAbertaUsuario();
		EstacionamentoEntity estacionamento = hibernateTemplate.load(EstacionamentoEntity.class, idEstacionamento); 
		Date dataSaida = new GregorianCalendar(locale).getTime();
		estadia.setDataSaida(dataSaida);
		Instant inicio = null;
		inicio = estadia.getDataEntrada().toInstant();
		Instant fim = dataSaida.toInstant();
		Duration d = Duration.between(inicio , fim);
		Long tempo = d.toMinutes();
		
		if (tempo < 15)
			estadia.setPreco(0);
		else if (tempo > 15 && tempo <= 60)
			estadia.setPreco(estacionamento.getPreco());
		else {
			long horas = tempo;
			double resto = Math.floorMod(horas, 60);
			if (resto == 0)
				estadia.setPreco(estacionamento.getPreco() * horas);
			else
				estadia.setPreco(estacionamento.getPreco() * horas + estacionamento.getPreco());			
		}	
		
		UsuarioDao usuarioDao = new UsuarioDao();		
		
		PagamentoDao pagamento = new PagamentoDao();
		String idPagamento = pagamento.realizarPagamento(estadia, usuarioDao.obterUsuarioPojoPorToken());
		System.out.println("Pagou - ID: " + idPagamento);
						
		return save(estadia, estadia.getIdEstadia());
	}
	
	@Transactional(rollbackFor = Throwable.class)
	public EstadiaPojo iniciarEstadia(Long idEstacionamento) {
		System.out.println("Iniciando estadia...");
		
		/*UsuarioEntity usuario = null; 
		DetachedCriteria criteriaUsuario = DetachedCriteria.forClass(UsuarioEntity.class);  
		criteriaUsuario.add(Restrictions.eq("login", SecurityContextHolder.getContext().getAuthentication().getName()));
		List<UsuarioEntity> usuarioList = (List<UsuarioEntity>) hibernateTemplate.findByCriteria(criteriaUsuario);
		
		if (!usuarioList.isEmpty()) {
			usuario = usuarioList.get(0);
			System.out.println("Encontrou usuário. " + usuario.toString());
		} */
		
		EstadiaPojo estadia = new EstadiaPojo();
		//estadia.setIdUsuario(usuario.getId());
		estadia.setIdEstacionamento(idEstacionamento);		
		//estadia.setDataEntrada(sdf.format(new GregorianCalendar(locale).getTime()));
		estadia.setDataEntrada(new GregorianCalendar(locale).getTime());
						
		return this.save(estadia, null);
	}
}
