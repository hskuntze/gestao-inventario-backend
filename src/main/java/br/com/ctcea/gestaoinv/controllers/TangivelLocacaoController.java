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

import br.com.ctcea.gestaoinv.dto.TangivelLocacaoDTO;
import br.com.ctcea.gestaoinv.entities.gestaoinv.TangivelLocacao;
import br.com.ctcea.gestaoinv.services.TangivelLocacaoService;

@RestController
@RequestMapping(value = "/tangiveis/locacao")
public class TangivelLocacaoController {

	@Autowired
	private TangivelLocacaoService tangivelLocacaoService;
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<TangivelLocacao>> getAll() {
		return ResponseEntity.ok().body(tangivelLocacaoService.getAll());
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<TangivelLocacao> getObject(Long id) {
		return ResponseEntity.ok().body(tangivelLocacaoService.getTangivelLocacaoObject(id));
	}
	
	@PostMapping(value = "/registrar")
	public ResponseEntity<TangivelLocacao> register(@RequestBody TangivelLocacaoDTO dto) {
		TangivelLocacao tl = tangivelLocacaoService.register(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(tl.getId()).toUri();
		return ResponseEntity.created(uri).body(tl);
	}
	
	@PutMapping(value = "/atualizar/{id}")
	public ResponseEntity<TangivelLocacao> atualizar(@PathVariable Long id, @RequestBody TangivelLocacaoDTO dto) {
		TangivelLocacao tl = tangivelLocacaoService.update(id, dto);
		return ResponseEntity.ok().body(tl);
	}
	
	@DeleteMapping(value = "/excluir/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		tangivelLocacaoService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
