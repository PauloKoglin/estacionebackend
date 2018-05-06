/**
 * 
 */
package br.furb.persistence;

import java.io.IOException;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
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
import br.furb.model.FormaPagamentoEntity;
import br.furb.model.UsuarioEntity;


/**
 * @author PauloArnoldo
 *
 */

@Repository
public class FormaPagamentoDao  extends BaseDao<FormaPagamentoEntity, FormaPagamentoPojo> {
	private final String MERCHANT_ID = "1198ba2c-3097-41bd-9205-44d8cc7488d2";
	private final String MERCHANT_KEY = "WMZZZOTCFFWYQYADNHSUBPFQJBOWOLDNJIFRWTZP";
	
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
		
		List<FormaPagamentoPojo> list = obterFormaPagamentoUsario(estadia.getIdUsuario());
		FormaPagamentoPojo cartao = list.get(0);
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
	
	
	@SuppressWarnings("unchecked")
	public List<FormaPagamentoPojo> obterFormaPagamentoUsario(Long idUsuario) {
		if (idUsuario == null) {
			UsuarioEntity usuario = null; 
			DetachedCriteria criteriaUsuario = DetachedCriteria.forClass(UsuarioEntity.class);  
			criteriaUsuario.add(Restrictions.eq("login", SecurityContextHolder.getContext().getAuthentication().getName()));
			List<UsuarioEntity> usuarioList = (List<UsuarioEntity>) hibernateTemplate.findByCriteria(criteriaUsuario);
			
			if (!usuarioList.isEmpty()) {
				usuario = usuarioList.get(0);
				idUsuario = usuario.getId();
				System.out.println("Encontrou usuário. " + usuario.toString());
			} 	
		}
		
		DetachedCriteria criteria = DetachedCriteria.forClass(FormaPagamentoEntity.class);  
		criteria.createAlias("usuario", "usu");
		criteria.add(Restrictions.eq("usu.id", idUsuario));		
		//List<FormaPagamentoPojo> list = (List<FormaPagamentoPojo>) hibernateTemplate.findByCriteria(criteria);
		List<FormaPagamentoPojo> list = findAll(criteria);
				
		return list;		
	}

	@Override
	public Class<FormaPagamentoEntity> getEntityClass() {
		return FormaPagamentoEntity.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected FormaPagamentoEntity pojoToEntity(FormaPagamentoPojo pojo, FormaPagamentoEntity entity) {
		entity.setId(pojo.getIdFormaPagamento());
		entity.setNomeCartao(pojo.getNomeCartao());
		entity.setNumero(pojo.getNumero());
		entity.setCodigoSeguranca(pojo.getCodigoSeguranca());
		entity.setBandeira(pojo.getBandeira());
		entity.setValidade(pojo.getValidade());		
		
		DetachedCriteria criteriaUsuario = DetachedCriteria.forClass(UsuarioEntity.class);  
		criteriaUsuario.add(Restrictions.eq("login", SecurityContextHolder.getContext().getAuthentication().getName()));
		List<UsuarioEntity> usuarioList = (List<UsuarioEntity>) hibernateTemplate.findByCriteria(criteriaUsuario);
		
		if (!usuarioList.isEmpty()) {
			entity.setUsuario(usuarioList.get(0));
		} 
		
		return entity;
	}

	@Override
	protected FormaPagamentoPojo entityToPojo(FormaPagamentoEntity entity, FormaPagamentoPojo pojo) {
		pojo.setIdFormaPagamento(entity.getIdFormaPagamento());
		pojo.setNumero(entity.getNumero());
		pojo.setNomeCartao(entity.getNomeCartao());
		pojo.setValidade(entity.getValidade());
		pojo.setBandeira(entity.getBandeira());
		pojo.setCodigoSeguranca(entity.getCodigoSeguranca());
		pojo.setUsuario(entity.getUsuario());
		
		return pojo;
	}

	@Override
	protected FormaPagamentoEntity newEntity(Object... adicionais) {
		// TODO Auto-generated method stub
		return new FormaPagamentoEntity();
	}

	@Override
	protected FormaPagamentoPojo newPojo(Object... adicionais) {
		// TODO Auto-generated method stub
		return new FormaPagamentoPojo();
	}
}
