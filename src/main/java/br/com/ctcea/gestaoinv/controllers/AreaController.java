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

import br.com.ctcea.gestaoinv.dto.AreaDTO;
import br.com.ctcea.gestaoinv.services.AreaService;

@RestController
@RequestMapping(value = "/areas")
public class AreaController {

	@Autowired
	private AreaService areaService;
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<AreaDTO>> getAll() {
		return ResponseEntity.ok().body(areaService.getAll());
	}
	
	@PostMapping(value = "/register")
	public ResponseEntity<AreaDTO> register(@RequestBody AreaDTO dto) {
		AreaDTO newDto = areaService.register(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDto.getId()).toUri();
		return ResponseEntity.created(uri).body(newDto);
	}
	
	@PutMapping(value = "/update/{id}")
	public ResponseEntity<AreaDTO> update(@PathVariable Long id, @RequestBody AreaDTO dto) {
		dto = areaService.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
}
