package br.com.ctcea.gestaoinv.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ctcea.gestaoinv.dto.FornecedorDTO;
import br.com.ctcea.gestaoinv.services.FornecedorService;

@RestController
@RequestMapping(value = "/fornecedores")
public class FornecedorController {

	@Autowired
	private FornecedorService fornecedorService;
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<FornecedorDTO>> getAll() {
		return ResponseEntity.ok().body(fornecedorService.getAll());
	}
}
