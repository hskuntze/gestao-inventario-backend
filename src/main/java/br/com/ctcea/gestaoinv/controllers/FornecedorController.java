package br.com.ctcea.gestaoinv.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	
	@PostMapping(value = "/register")
	public ResponseEntity<FornecedorDTO> register(@RequestBody FornecedorDTO dto) {
		FornecedorDTO newDto = fornecedorService.register(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDto.getId()).toUri();
		return ResponseEntity.created(uri).body(newDto);
	}
	
	@PutMapping(value = "/update/{id}")
	public ResponseEntity<FornecedorDTO> update(@PathVariable Long id, @RequestBody FornecedorDTO dto) {
		dto = fornecedorService.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
}
