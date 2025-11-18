package br.com.ctcea.gestaoinv.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	
	@GetMapping(value = "/area/{id}")
	public ResponseEntity<List<UsuarioResponsavelDTO>> getAllByAreaId(@PathVariable Long id) {
		return ResponseEntity.ok().body(usuarioResponsavelService.getAllByAreaId(id));
	}

	@PostMapping(value = "/register")
	public ResponseEntity<UsuarioResponsavelDTO> register(@RequestBody UsuarioResponsavelDTO dto) {
		UsuarioResponsavelDTO newDto = usuarioResponsavelService.register(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDto.getId()).toUri();
		return ResponseEntity.created(uri).body(newDto);
	}
	
	@PutMapping(value = "/update/{id}")
	public ResponseEntity<UsuarioResponsavelDTO> update(@PathVariable Long id, @RequestBody UsuarioResponsavelDTO dto) {
		dto = usuarioResponsavelService.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
}