/**
 * 
 */
package br.furb.endpoints.usuario;


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

import br.furb.endpoints.usuario.UsuarioPojo;
import br.furb.persistence.UsuarioDao;

/**
 * @author PauloArnoldo
 *
 */


@RestController
@RequestMapping("usuario")
public class UsuarioController {

	@Autowired @Lazy private UsuarioDao usuarioDao;

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET, value = "/{idUsuario}")
	public ResponseEntity<UsuarioPojo> getusuario(@PathVariable("idUsuario") Long idUsuario) {
		UsuarioPojo usuario = null;
		try {
			usuario = usuarioDao.find(idUsuario);
			return new ResponseEntity<>(usuario, HttpStatus.OK);
		} catch (Exception e) {
			System.out.print("Usuário não encontrado.");
			return new ResponseEntity<>(usuario, HttpStatus.NOT_FOUND);
		}			
	}
	
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET, value = "/")
	public ResponseEntity<List<UsuarioPojo>> getTodosUsuarios() {
		List<UsuarioPojo> usuarios = null;
		try {
			usuarios = usuarioDao.findAll();
			return new ResponseEntity<>(usuarios, HttpStatus.OK);
		} catch (Exception e) {
			System.out.print("Nenhum usuário foi encontrado.");
			return new ResponseEntity<>(usuarios, HttpStatus.NOT_FOUND);
		}			
	}
	/*
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET, value = "/login/{login}")
	public ResponseEntity<List<UsuarioPojo>> getUsuarioByLogin() {
		List<UsuarioPojo> usuarios = null;
		try {
			usuarios = usuarioDao.findAll();
			return new ResponseEntity<>(usuarios, HttpStatus.OK);
		} catch (Exception e) {
			System.out.print("Nenhum usuário foi encontrado.");
			return new ResponseEntity<>(usuarios, HttpStatus.NOT_FOUND);
		}			
	}
	*/
	

	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
			method = RequestMethod.POST, value = {"/{idUsuario}"})
	public ResponseEntity<UsuarioPojo> saveusuario(@RequestBody UsuarioPojo usuario, @PathVariable("idUsuario") Long idUsuario) {
		return new ResponseEntity<>(usuarioDao.save(usuario, idUsuario), HttpStatus.OK);
	}
	
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
			method = RequestMethod.POST)
	public ResponseEntity<UsuarioPojo> cadastrausuario(@RequestBody UsuarioPojo usuario) {
		System.out.print("Gravando usuário via endpoint.");
		UsuarioPojo usuarioPojo = usuarioDao.save(usuario, null);
		if (usuarioPojo.equals(null)) {
			System.out.print("Não foi gravado o usuário.");			
			return new ResponseEntity<>(usuario, HttpStatus.NO_CONTENT);
		} else
			return new ResponseEntity<>(usuarioPojo, HttpStatus.OK);
	}
}
