package br.com.ctcea.gestaoinv.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ctcea.gestaoinv.dto.UsuarioDTO;
import br.com.ctcea.gestaoinv.services.UsuarioService;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UsuarioDTO> getById(@PathVariable Long id) {
		return ResponseEntity.ok().body(usuarioService.getById(id));
	}
	
	@GetMapping(value = "/login/{login}")
	public ResponseEntity<UsuarioDTO> getByLogin(@PathVariable String login) {
		return ResponseEntity.ok().body(usuarioService.getByLogin(login));
	}
}
