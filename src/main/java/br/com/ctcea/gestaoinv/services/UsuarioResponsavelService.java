package br.com.ctcea.gestaoinv.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ctcea.gestaoinv.dto.UsuarioResponsavelDTO;
import br.com.ctcea.gestaoinv.entities.Area;
import br.com.ctcea.gestaoinv.entities.UsuarioResponsavel;
import br.com.ctcea.gestaoinv.exceptions.RecursoExistenteException;
import br.com.ctcea.gestaoinv.exceptions.RecursoNaoEncontradoException;
import br.com.ctcea.gestaoinv.repositories.AreaRepository;
import br.com.ctcea.gestaoinv.repositories.UsuarioResponsavelRepository;

@Service
public class UsuarioResponsavelService {

	@Autowired
	private UsuarioResponsavelRepository usuarioResponsavelRepository;
	
	@Autowired
	private AreaRepository areaRepository;
	
	@Transactional(readOnly = true)
	public List<UsuarioResponsavelDTO> getAll() {
		List<UsuarioResponsavel> all = usuarioResponsavelRepository.findAll();
		return all.stream().map(ur -> new UsuarioResponsavelDTO(ur)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public List<UsuarioResponsavelDTO> getAllByAreaId(Long idArea) {
		List<UsuarioResponsavel> all = usuarioResponsavelRepository.getAllByAreaId(idArea);
		return all.stream().map(ur -> new UsuarioResponsavelDTO(ur)).collect(Collectors.toList());
	}
	
	@Transactional
	public UsuarioResponsavelDTO register(UsuarioResponsavelDTO dto) {
		UsuarioResponsavel ur = new UsuarioResponsavel();
		Area area = areaRepository.findById(dto.getArea().getId()).orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível localizar uma área com id " + dto.getArea().getId()));
		
		ur.setNome(dto.getNome());
		ur.setEmail(dto.getEmail());
		ur.setArea(area);
		
		ur = usuarioResponsavelRepository.save(ur);
		
		return new UsuarioResponsavelDTO(ur);
	}
	
	@Transactional
	public UsuarioResponsavelDTO update(Long id, UsuarioResponsavelDTO dto) {
		UsuarioResponsavel ur = usuarioResponsavelRepository.findById(id).orElseThrow(() -> new RecursoExistenteException("Não foi possível localizar usuário responsável com ID " + id));
		Area area = areaRepository.findById(dto.getArea().getId()).orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível localizar uma área com id " + dto.getArea().getId()));
		
		ur.setNome(dto.getNome());
		ur.setEmail(dto.getEmail());
		ur.setArea(area);
		
		ur = usuarioResponsavelRepository.save(ur);
		
		return new UsuarioResponsavelDTO(ur);
	}
}
