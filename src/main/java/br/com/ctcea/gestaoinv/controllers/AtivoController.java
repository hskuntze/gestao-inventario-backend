package br.com.ctcea.gestaoinv.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ctcea.gestaoinv.dto.AtivoDTO;
import br.com.ctcea.gestaoinv.dto.QuantidadeAtivoDTO;
import br.com.ctcea.gestaoinv.services.AtivoService;

@RestController
@RequestMapping(value = "/ativos")
public class AtivoController {

	@Autowired
	private AtivoService ativoService;
	
	@GetMapping(value = "/qtd/total")
	public ResponseEntity<QuantidadeAtivoDTO> getQtdAtivos() {
		return ResponseEntity.ok().body(ativoService.getQtdAtivos());
	}
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<AtivoDTO>> getAll() {
		return ResponseEntity.ok().body(ativoService.getAll());
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<AtivoDTO> getAtivoById(@PathVariable Long id) {
		return ResponseEntity.ok().body(ativoService.getAtivoById(id));
	}
}
