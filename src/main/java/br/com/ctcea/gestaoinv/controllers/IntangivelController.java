package br.com.ctcea.gestaoinv.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.ctcea.gestaoinv.dto.IntangivelDTO;
import br.com.ctcea.gestaoinv.entities.gestaoinv.Intangivel;
import br.com.ctcea.gestaoinv.services.IntangivelService;

@RestController
@RequestMapping(value = "/intangiveis")
public class IntangivelController {

	@Autowired
	private IntangivelService intangivelService;
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<Intangivel>> getAll() {
		return ResponseEntity.ok().body(intangivelService.getAll());
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Intangivel> getObject(Long id) {
		return ResponseEntity.ok().body(intangivelService.getIntangivelObject(id));
	}
	
	@PostMapping(value = "/registrar")
	public ResponseEntity<Intangivel> register(@RequestBody IntangivelDTO dto) {
		Intangivel i = intangivelService.register(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(i.getId()).toUri();
		return ResponseEntity.created(uri).body(i);
	}
	
	@PutMapping(value = "/atualizar/{id}")
	public ResponseEntity<Intangivel> atualizar(@PathVariable Long id, @RequestBody IntangivelDTO dto) {
		Intangivel i = intangivelService.update(id, dto);
		return ResponseEntity.ok().body(i);
	}
	
	@DeleteMapping(value = "/excluir/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		intangivelService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
