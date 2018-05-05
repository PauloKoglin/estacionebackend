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

import br.furb.persistence.FormaPagamentoDao;

/**
 * @author PauloArnoldo
 *
 */
@RestController
@RequestMapping("formaPagamento")
public class FormaPagamentoController {
	@Autowired @Lazy private FormaPagamentoDao formaPagamentoDao;

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<List<FormaPagamentoPojo>> getFormaPagamentoUsuario() {
		return new ResponseEntity<>(formaPagamentoDao.obterFormaPagamentoUsario(), HttpStatus.OK);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<FormaPagamentoPojo> inserirFormaPagamento(@RequestBody FormaPagamentoPojo formaPagamento) {	
		return new ResponseEntity<>(formaPagamentoDao.save(formaPagamento, null), HttpStatus.OK);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST, value = {"/{idFormaPagamento}"})
	public ResponseEntity<FormaPagamentoPojo> alterarFormaPagamento(@RequestBody FormaPagamentoPojo formaPagamento, @PathVariable("idFormaPagamento") Long idFormaPagamento) {	
		return new ResponseEntity<>(formaPagamentoDao.save(formaPagamento, idFormaPagamento), HttpStatus.OK);
	}
	
}
