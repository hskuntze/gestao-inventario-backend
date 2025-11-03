package br.com.ctcea.gestaoinv.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ctcea.gestaoinv.logs.LogAcesso;
import br.com.ctcea.gestaoinv.services.LogAcessoService;

@RestController
@RequestMapping(value = "/log/acesso")
public class LogAcessoController {

	@Autowired
	private LogAcessoService logService;
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<LogAcesso>> getAll() {
		return ResponseEntity.ok().body(logService.getAll());
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<LogAcesso> getById(@PathVariable Long id) {
		return ResponseEntity.ok().body(logService.getById(id));
	}
}
