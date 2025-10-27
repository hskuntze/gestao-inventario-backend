package br.com.ctcea.gestaoinv.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ctcea.gestaoinv.dto.LocalizacaoDTO;
import br.com.ctcea.gestaoinv.services.LocalizacaoService;

@RestController
@RequestMapping(value = "/localizacoes")
public class LocalizacaoController {

	@Autowired
	private LocalizacaoService localizacaoService;
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<LocalizacaoDTO>> getAll() {
		return ResponseEntity.ok().body(localizacaoService.getAll());
	}
}
