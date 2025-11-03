package br.com.ctcea.gestaoinv.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ctcea.gestaoinv.logs.LogOperacao;
import br.com.ctcea.gestaoinv.services.LogOperacaoService;

@RestController
@RequestMapping(value = "/log/operacao")
public class LogOperacaoController {

	@Autowired
	private LogOperacaoService logService;
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<LogOperacao>> getAll() {
		return ResponseEntity.ok().body(logService.getAll());
	}
}
