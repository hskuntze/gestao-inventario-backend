package br.com.ctcea.gestaoinv.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ctcea.gestaoinv.dto.AtivoDTO;
import br.com.ctcea.gestaoinv.dto.HistoricoDTO;
import br.com.ctcea.gestaoinv.services.HistoricoService;

@RestController
@RequestMapping(value = "/historico")
public class HistoricoController {

	@Autowired
	private HistoricoService historicoService;
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<HistoricoDTO>> getAllDto() {
		return ResponseEntity.ok().body(historicoService.getAllDto());
	}
	
	@GetMapping(value = "/recentes")
	public ResponseEntity<List<AtivoDTO>> getAtivosRecentes() {
		return ResponseEntity.ok().body(historicoService.getAtivosRecentes());
	}
	
	@GetMapping(value = "/ativo/{id}")
	public ResponseEntity<List<HistoricoDTO>> getHistoricoByAtivoId(@PathVariable Long id) {
		return ResponseEntity.ok().body(historicoService.getHistoricoByAtivoId(id));
	}
}