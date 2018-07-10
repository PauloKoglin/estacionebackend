package br.furb.persistence;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
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

import br.furb.cielopayment.Merchant;
import br.furb.cielopayment.ecommerce.CieloEcommerce;
import br.furb.cielopayment.ecommerce.Environment;
import br.furb.cielopayment.ecommerce.Payment;
import br.furb.cielopayment.ecommerce.Sale;
import br.furb.cielopayment.request.CieloError;
import br.furb.cielopayment.request.CieloRequestException;
import br.furb.endpoints.estadia.EstadiaPojo;
import br.furb.endpoints.pagamento.FormaPagamentoPojo;
import br.furb.model.EstacionamentoEntity;
import br.furb.model.EstadiaEntity;
import br.furb.model.FormaPagamentoEntity;
import br.furb.model.UsuarioEntity;


@Repository
public class EstadiaDao extends BaseDao<EstadiaEntity, EstadiaPojo> {
	
	private static final Locale locale = new Locale("pt","BR");
	private final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm", new Locale("pt","BR"));
	private final String MERCHANT_ID = "1198ba2c-3097-41bd-9205-44d8cc7488d2";
	private final String MERCHANT_KEY = "WMZZZOTCFFWYQYADNHSUBPFQJBOWOLDNJIFRWTZP";
	
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
		entity.setPreco(pojo.getPreco());
		entity.setIdPagamento(pojo.getIdPagamento());
		
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
		pojo.setIdPagamento(entity.getIdPagamento());
		
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
		estadia.setPreco(estacionamento.getPreco());
		if (tempo > 60) {
			long horas = (tempo / 60);
			double resto = Math.floorMod(tempo, 60);
			if (resto == 0)
				estadia.setPreco(estacionamento.getPreco() * horas);
			else
				estadia.setPreco((estacionamento.getPreco() * horas) + estacionamento.getPreco());
		}
		
		//FormaPagamentoDao pagamento = new FormaPagamentoDao();
		String idPagamento = realizarPagamento(estadia);
		System.out.println("Pagou - ID: " + idPagamento);
		estadia.setIdPagamento(idPagamento);
						
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
	
	public String realizarPagamento(EstadiaPojo estadia) {
		System.out.println("Iniciando pagamento via cartão de crédito!");
		// Configure seu merchant
		Merchant merchant = new Merchant(this.MERCHANT_ID, this.MERCHANT_KEY);

		// Crie uma instância de Sale informando o ID do pagamento
		Sale sale = new Sale(estadia.getIdEstadia().toString());

		// Crie uma instância de Customer informando o nome do cliente
		//Customer customer = sale.customer(usuario.getNome());

		// Crie uma instância de Payment informando o valor do pagamento
		int valor = new Integer( Double.toString(Math.round(estadia.getPreco() * 100)).replace(".0", ""));
		Payment payment = sale.payment(valor);
		
		UsuarioEntity usuario = null; 
		DetachedCriteria criteriaUsuario = DetachedCriteria.forClass(UsuarioEntity.class);  
		criteriaUsuario.add(Restrictions.eq("login", SecurityContextHolder.getContext().getAuthentication().getName()));
		List<UsuarioEntity> usuarioList = (List<UsuarioEntity>) hibernateTemplate.findByCriteria(criteriaUsuario);
		
		if (!usuarioList.isEmpty()) {
			usuario = usuarioList.get(0);
			System.out.println("Encontrou usuário. " + usuario.toString());
		}
		
		DetachedCriteria criteria = DetachedCriteria.forClass(FormaPagamentoEntity.class);  
		criteria.createAlias("usuario", "usu");
		criteria.add(Restrictions.eq("usu.id", usuario.getId()));		
		//List<FormaPagamentoPojo> list = (List<FormaPagamentoPojo>) hibernateTemplate.findByCriteria(criteria);
		List<FormaPagamentoEntity> list = (List<FormaPagamentoEntity>) hibernateTemplate.findByCriteria(criteria);
		FormaPagamentoEntity cartao = list.get(0);
		// Crie  uma instância de Credit Card utilizando os dados de teste
		// esses dados estão disponíveis no manual de integração
		payment.creditCard(cartao.getCodigoSeguranca(), cartao.getBandeira()).setExpirationDate(cartao.getValidade())
		                                 .setCardNumber(cartao.getNumero().toString())
		                                 .setHolder(cartao.getNomeCartao());

		sale.setPayment(payment);
		
		// Crie o pagamento na Cielo
		try {
		    // Configure o SDK com seu merchant e o ambiente apropriado para criar a venda
		    sale = new CieloEcommerce(merchant, Environment.SANDBOX).createSale(sale);
		    System.out.println("Pagamento confirmado! Retorno : " + sale.getPayment().toString());
		    // Com a venda criada na Cielo, já temos o ID do pagamento, TID e demais
		    // dados retornados pela Cielo
		    

		    // Com o ID do pagamento, podemos fazer sua captura, se ela não tiver sido capturada ainda
		    //sale = new CieloEcommerce(merchant, Environment.SANDBOX).captureSale(paymentId, 15700, 0);

		    // E também podemos fazer seu cancelamento, se for o caso
		    //sale = new CieloEcommerce(merchant, Environment.SANDBOX).cancelSale(paymentId, 15700);
		} catch (CieloRequestException e) {
		    // Em caso de erros de integração, podemos tratar o erro aqui.
		    // os códigos de erro estão todos disponíveis no manual de integração.
			
		    CieloError error = e.getError();
		    System.out.println("Deu ruim!"+ error.getMessage());
		} catch (IOException e) {
			System.out.println("Deu ruim!"+ e.getMessage());
			e.printStackTrace();
		}
		return sale.getPayment().getPaymentId();
	}
}
