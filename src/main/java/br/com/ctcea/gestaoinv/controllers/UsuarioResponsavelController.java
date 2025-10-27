package br.com.ctcea.gestaoinv.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ctcea.gestaoinv.dto.UsuarioResponsavelDTO;
import br.com.ctcea.gestaoinv.services.UsuarioResponsavelService;

@RestController
@RequestMapping(value = "/usuarios/responsaveis")
public class UsuarioResponsavelController {

	@Autowired
	private UsuarioResponsavelService usuarioResponsavelService;
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<UsuarioResponsavelDTO>> getAll() {
		return ResponseEntity.ok().body(usuarioResponsavelService.getAll());
	}
}
