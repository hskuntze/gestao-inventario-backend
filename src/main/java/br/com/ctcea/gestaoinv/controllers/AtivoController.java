package br.com.ctcea.gestaoinv.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
