package br.com.ctcea.gestaoinv.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ctcea.gestaoinv.dto.AreaDTO;
import br.com.ctcea.gestaoinv.entities.Area;
import br.com.ctcea.gestaoinv.repositories.AreaRepository;

@Service
public class AreaService {

	@Autowired
	private AreaRepository areaRepository;
	
	@Transactional(readOnly = true)
	public List<AreaDTO> getAll() {
		List<Area> all = areaRepository.findAll();
		return all.stream().map(a -> new AreaDTO(a)).collect(Collectors.toList());
	}
}
