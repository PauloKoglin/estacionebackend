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
