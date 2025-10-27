package br.com.ctcea.gestaoinv.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
