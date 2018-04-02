/**
 * 
 */
package br.furb.endpoints.estadia;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.furb.endpoints.estadia.EstadiaPojo;


import br.furb.persistence.EstadiaDao;

/**
 * @author PauloArnoldo
 *
 */
@RestController
@RequestMapping("estadia")
public class EstadiaController {

	@Autowired @Lazy private EstadiaDao estadiaDao;

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<List<EstadiaPojo>> getEstadias() {
		//List<EstadiaPojo> findAll = EstadiaDao.findAll();
		return new ResponseEntity<>(estadiaDao.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET, value = "/estacionamento/{idEstacionamento}")
	public ResponseEntity<List<EstadiaPojo>> getEstadiasEstacionamento(@PathVariable("idEstacionamento") Long idEstacionamento) {				
		return new ResponseEntity<>(estadiaDao.findAllByEstacionamentoId(idEstacionamento), HttpStatus.OK);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET, value = "/usuario/{idUsuario}")
	public ResponseEntity<List<EstadiaPojo>> getEstadiasUsuario(@PathVariable("idUsuario") Long idUsuario) {				
		return new ResponseEntity<>(estadiaDao.findAllByUsuarioId(idUsuario), HttpStatus.OK);
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET, value = "/estadiaAberta")
	public ResponseEntity<EstadiaPojo> getEstadiaAbertaUsuario() {				
		return new ResponseEntity<>(estadiaDao.findAbertaUsuario(), HttpStatus.OK);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET, value = "/{idEstadia}")
	public ResponseEntity<EstadiaPojo> getEstadia(@PathVariable("idEstadia") Long idEstadia) {
		return new ResponseEntity<>(estadiaDao.find(idEstadia), HttpStatus.OK);
	}

	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
			method = RequestMethod.POST, value = {"/{idEstadia}"})
	public ResponseEntity<EstadiaPojo> alterarEstadia(@RequestBody EstadiaPojo Estadia,
			@PathVariable("idEstadia") Long idEstadia) {
		return new ResponseEntity<>(estadiaDao.save(Estadia, idEstadia), HttpStatus.OK);
	}

	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
			method = RequestMethod.POST) //@PathVariable("idUsuario") Long idUsuario
	public ResponseEntity<EstadiaPojo> inserirEstadia(@RequestBody EstadiaPojo Estadia) {
		return new ResponseEntity<>(estadiaDao.save(Estadia, null), HttpStatus.OK);
	}
}

