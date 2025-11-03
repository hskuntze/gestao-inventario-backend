package br.com.ctcea.gestaoinv.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ctcea.gestaoinv.dto.LocalizacaoDTO;
import br.com.ctcea.gestaoinv.entities.Localizacao;
import br.com.ctcea.gestaoinv.exceptions.RecursoNaoEncontradoException;
import br.com.ctcea.gestaoinv.repositories.LocalizacaoRepository;

@Service
public class LocalizacaoService {

	@Autowired
	private LocalizacaoRepository localizacaoRepository;
	
	@Transactional(readOnly = true)
	public LocalizacaoDTO getDtoFromId(Long id) {
		Localizacao l = localizacaoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível localizar uma localização com ID " + id));
		return new LocalizacaoDTO(l);
	}
	
	@Transactional(readOnly = true)
	public List<LocalizacaoDTO> getAllFromArea(Long id) {
		List<Localizacao> all = localizacaoRepository.getAllFromArea(id);
		return all.stream().map(l -> new LocalizacaoDTO(l)).collect(Collectors.toList());
	}
}
