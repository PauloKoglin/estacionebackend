/**
 * 
 */
package br.furb.endpoints.pagamento;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.furb.endpoints.estadia.EstadiaPojo;
import br.furb.endpoints.usuario.UsuarioPojo;
import br.furb.persistence.EstadiaDao;
import br.furb.persistence.FormaPagamentoDao;

/**
 * @author PauloArnoldo
 *
 */
@RestController
@RequestMapping("formaPagamento")
public class FormaPagamentoController {
	@Autowired @Lazy private FormaPagamentoDao formaPagamentoDao;

	/*@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<List<FormaPagamentoPojo>> getFormaPagamento() {
		return new ResponseEntity<>(formaPagamentoDao.findAll(), HttpStatus.OK);
	}*/
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<String> salvarFormaPagamento(@RequestBody FormaPagamentoPojo formaPagamento) {
		String retorno = formaPagamentoDao.salvarCartaoCredito(formaPagamento);		
		return new ResponseEntity<>("retorno:"+retorno, HttpStatus.OK);
	}
	
}
