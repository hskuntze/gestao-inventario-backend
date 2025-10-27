package br.com.ctcea.gestaoinv.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ctcea.gestaoinv.dto.LocalizacaoDTO;
import br.com.ctcea.gestaoinv.entities.Localizacao;
import br.com.ctcea.gestaoinv.repositories.LocalizacaoRepository;

@Service
public class LocalizacaoService {

	@Autowired
	private LocalizacaoRepository localizacaoRepository;
	
	@Transactional(readOnly = true)
	public List<LocalizacaoDTO> getAll() {
		List<Localizacao> all = localizacaoRepository.findAll();
		return all.stream().map(l -> new LocalizacaoDTO(l)).collect(Collectors.toList());
	}
}
