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
import org.hibernate.criterion.Order;
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
		entity.setEstacionamento(pojo.getEstacionamento());
		
		UsuarioEntity usuario = null; 
		DetachedCriteria criteriaUsuario = DetachedCriteria.forClass(UsuarioEntity.class);  
		criteriaUsuario.add(Restrictions.eq("login", SecurityContextHolder.getContext().getAuthentication().getName()));
		List<UsuarioEntity> usuarioList = (List<UsuarioEntity>) hibernateTemplate.findByCriteria(criteriaUsuario);
		
		if (!usuarioList.isEmpty()) {
			usuario = usuarioList.get(0);
			System.out.println("Encontrou usuário. " + usuario.toString());
		} 
		
		entity.setUsuario(usuario);
		entity.setDataEntrada(pojo.getDataEntrada());
		entity.setDataSaida(pojo.getDataSaida());
		
		return entity;
	}

	@Override
	protected EstadiaPojo entityToPojo(EstadiaEntity entity, EstadiaPojo pojo) {
		pojo.setIdEstadia(entity.getIdEstadia());
		pojo.setEstacionamento(entity.getEstacionamento());
		pojo.setIdUsuario(entity.getUsuario().getId());
		pojo.setPreco(entity.getPreco());		
		pojo.setDataEntrada(entity.getDataEntrada());
		pojo.setDataSaida(entity.getDataSaida());
		
		return pojo;
	}

	@Override
	protected EstadiaEntity newEntity(Object...adicionais) {
		EstadiaEntity estadiaEntity = new EstadiaEntity();
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
		criteria.addOrder(Order.desc("idEstadia"));
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
	}
	
	@Transactional(rollbackFor = Throwable.class)
	public EstadiaPojo finalizarEstadia(Long idEstacionamento) {
		EstadiaPojo estadia = findAbertaUsuario();
		EstacionamentoEntity estacionamento = hibernateTemplate.load(EstacionamentoEntity.class, idEstacionamento);
		//UsuarioEntity usuario = hibernateTemplate.load(UsuarioEntity.class, estadia.getIdUsuario()); 
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
		
		
		FormaPagamentoDao pagamento = new FormaPagamentoDao();
		String idPagamento = pagamento.realizarPagamento(estadia);
		System.out.println("Pagou - ID: " + idPagamento);
						
		return save(estadia, estadia.getIdEstadia());
	}
	
	@Transactional(rollbackFor = Throwable.class)
	public EstadiaPojo iniciarEstadia(Long idEstacionamento) {
		System.out.println("Iniciando estadia no estacionamento id: "+ idEstacionamento);
				
		EstadiaPojo estadia = new EstadiaPojo();		
		estadia.setEstacionamento(hibernateTemplate.get(EstacionamentoEntity.class, idEstacionamento));
		estadia.setDataEntrada(new GregorianCalendar(locale).getTime());
						
		return this.save(estadia, null);
	}
}
