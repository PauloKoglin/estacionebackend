package br.furb.endpoints.estacionamento;

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
import br.furb.persistence.EstacionamentoDao;


@RestController
@RequestMapping("estacionamento")
public class EstacionamentoController {

	@Autowired @Lazy private EstacionamentoDao estacionamentoDao;

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<List<EstacionamentoPojo>> getEstacionamentos() {
		//List<EstacionamentoPojo> findAll = estacionamentoDao.findAll();
		return new ResponseEntity<>(estacionamentoDao.findAll(), HttpStatus.OK);
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET, value = "/{idEstacionamento}")
	public ResponseEntity<EstacionamentoPojo> getEstacionamento(@PathVariable("idEstacionamento") Long idEstacionamento) {
		return new ResponseEntity<>(estacionamentoDao.find(idEstacionamento), HttpStatus.OK);
	}

	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
			method = RequestMethod.POST, value = {"/{idEstacionamento}"})
	public ResponseEntity<EstacionamentoPojo> alterarEstacionamento(@RequestBody EstacionamentoPojo estacionamento,
			@PathVariable("idEstacionamento") Long idEstacionamento) {
		return new ResponseEntity<>(estacionamentoDao.save(estacionamento, idEstacionamento), HttpStatus.OK);
	}

	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
			method = RequestMethod.POST)
	public ResponseEntity<EstacionamentoPojo> inserirEstacionamento(@RequestBody EstacionamentoPojo estacionamento) {
		return new ResponseEntity<>(estacionamentoDao.save(estacionamento, null, estacionamento.getUsuario().getId()), HttpStatus.OK);
	}
}


