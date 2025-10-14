package br.com.ctcea.gestaoinv.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ctcea.gestaoinv.dto.TangivelDTO;
import br.com.ctcea.gestaoinv.entities.gestaoinv.Tangivel;
import br.com.ctcea.gestaoinv.repositories.gestaoinv.TangivelRepository;
import br.com.ctcea.gestaoinv.services.exceptions.RecursoNaoEncontradoException;

@Service
public class TangivelService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TangivelService.class);

	@Autowired
	private TangivelRepository tangivelRepository;
	
	@Transactional(readOnly = true)
	public Tangivel getTangivelObject(Long id) {
		return tangivelRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível localizar um ativo tangível com ID " + id));
	}
	
	@Transactional(readOnly = true)
	public List<Tangivel> getAll() {
		return tangivelRepository.findAll();
	}
	
	@Transactional
	public Tangivel register(TangivelDTO dto) {
		Tangivel newRegister = new Tangivel();
		
		dtoToEntity(newRegister, dto);
		newRegister = tangivelRepository.save(newRegister);
		
		LOGGER.info("[LOG] - Novo ativo tangível {} registrado.", newRegister.getId());
		return newRegister;
	}
	
	@Transactional
	public Tangivel update(Long id, TangivelDTO dto) {
		Tangivel toUpdate = getTangivelObject(id);
		
		dtoToEntity(toUpdate, dto);
		toUpdate = tangivelRepository.save(toUpdate);
		
		LOGGER.info("[LOG] - Ativo tangível {} atualizado.", id);
		return toUpdate;
	}
	
	@Transactional
	public Tangivel update(Tangivel obj) {
		Tangivel ativo = tangivelRepository.save(obj);
		return ativo;
	}
	
	public void delete(Long id) {
		tangivelRepository.deleteById(id);
		LOGGER.info("[LOG] - Ativo tangível {} excluído.", id);
	}
	
	private void dtoToEntity(Tangivel entity, TangivelDTO dto) {
		entity.setArea(dto.getArea());
		entity.setCategoria(dto.getCategoria());
		entity.setCodigoSerie(dto.getCodigoSerie());
		entity.setDataAquisicao(dto.getDataAquisicao());
		entity.setDescricao(dto.getDescricao());
		entity.setEstadoConservacao(dto.getEstadoConservacao());
		entity.setFornecedor(dto.getFornecedor());
		entity.setIdPatrimonial(dto.getIdPatrimonial());
		entity.setLinkDocumento(dto.getLinkDocumento());
		entity.setLocalizacao(dto.getLocalizacao());
		entity.setObservacoes(dto.getObservacoes());
		entity.setResponsavel(dto.getResponsavel());
		entity.setUsuarioResponsavel(dto.getUsuarioResponsavel());
	}
}
