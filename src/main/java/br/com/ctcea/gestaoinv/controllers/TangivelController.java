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

import br.com.ctcea.gestaoinv.dto.TangivelDTO;
import br.com.ctcea.gestaoinv.entities.gestaoinv.Tangivel;
import br.com.ctcea.gestaoinv.services.TangivelService;

@RestController
@RequestMapping(value = "/tangiveis")
public class TangivelController {

	@Autowired
	private TangivelService tangivelService;
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<Tangivel>> getAll() {
		return ResponseEntity.ok().body(tangivelService.getAll());
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Tangivel> getObject(Long id) {
		return ResponseEntity.ok().body(tangivelService.getTangivelObject(id));
	}
	
	@PostMapping(value = "/registrar")
	public ResponseEntity<Tangivel> register(@RequestBody TangivelDTO dto) {
		Tangivel t = tangivelService.register(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(t.getId()).toUri();
		return ResponseEntity.created(uri).body(t);
	}
	
	@PutMapping(value = "/atualizar/{id}")
	public ResponseEntity<Tangivel> atualizar(@PathVariable Long id, @RequestBody TangivelDTO dto) {
		Tangivel t = tangivelService.update(id, dto);
		return ResponseEntity.ok().body(t);
	}
	
	@DeleteMapping(value = "/excluir/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		tangivelService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
