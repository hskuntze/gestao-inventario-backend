package br.com.ctcea.gestaoinv.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ctcea.gestaoinv.dto.AreaDTO;
import br.com.ctcea.gestaoinv.dto.LocalizacaoDTO;
import br.com.ctcea.gestaoinv.entities.Area;
import br.com.ctcea.gestaoinv.entities.Localizacao;
import br.com.ctcea.gestaoinv.repositories.AreaRepository;
import br.com.ctcea.gestaoinv.services.exceptions.RecursoNaoEncontradoException;

@Service
public class AreaService {

	@Autowired
	private AreaRepository areaRepository;
	
	@Transactional(readOnly = true)
	public List<AreaDTO> getAll() {
		List<Area> all = areaRepository.findAll();
		return all.stream().map(a -> new AreaDTO(a)).collect(Collectors.toList());
	}
	
	@Transactional
	public AreaDTO register(AreaDTO dto) {
		Area area = new Area();
		dtoToEntity(dto, area);
		
		area = areaRepository.save(area);
		
		return new AreaDTO(area);
	}
	
	@Transactional
	public AreaDTO update(Long id, AreaDTO dto) {
		Area area = areaRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível localizar uma área com ID " + id));
		dtoToEntity(dto, area);
		
		area = areaRepository.save(area);
		return new AreaDTO(area);
	}

	private void dtoToEntity(AreaDTO dto, Area area) {
		area.setNome(dto.getNome());
		area.setResponsavel(dto.getResponsavel());
		area.setSubstitutoResponsavel(dto.getSubstitutoResponsavel());
		
		area.getLocalizacoes().removeIf(existing ->
	        dto.getLocalizacoes().stream().noneMatch(l -> l.getId() != null && l.getId().equals(existing.getId()))
	    );
		
		for (LocalizacaoDTO lDto : dto.getLocalizacoes()) {
	        Localizacao localizacao;

	        if (lDto.getId() != null) {
	            // Tenta encontrar uma existente na lista atual
	            localizacao = area.getLocalizacoes().stream()
	                .filter(existing -> existing.getId().equals(lDto.getId()))
	                .findFirst()
	                .orElseGet(Localizacao::new);
	        } else {
	            localizacao = new Localizacao();
	        }

	        localizacao.setNome(lDto.getNome());
	        localizacao.setArea(area);

	        if (localizacao.getId() == null) {
	            area.getLocalizacoes().add(localizacao);
	        }
	    }
	}
}
