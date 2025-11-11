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

import br.com.ctcea.gestaoinv.dto.ContratoDTO;
import br.com.ctcea.gestaoinv.services.ContratoService;

@RestController
@RequestMapping(value = "/contratos")
public class ContratoController {

	@Autowired
	private ContratoService contratoService;
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<ContratoDTO>> getAll() {
		return ResponseEntity.ok().body(contratoService.getAll());
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ContratoDTO> getById(@PathVariable Long id) {
		return ResponseEntity.ok().body(contratoService.getDtoById(id));
	}
	
	@PostMapping(value = "/register")
	public ResponseEntity<ContratoDTO> register(@RequestBody ContratoDTO dto) {
		ContratoDTO newDto = contratoService.register(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(newDto);
	}
	
	@PutMapping(value = "/update/{id}")
	public ResponseEntity<ContratoDTO> update(@PathVariable Long id, @RequestBody ContratoDTO dto) {
		dto = contratoService.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
}
