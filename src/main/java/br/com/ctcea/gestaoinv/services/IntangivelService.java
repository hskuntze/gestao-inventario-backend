package br.com.ctcea.gestaoinv.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ctcea.gestaoinv.dto.IntangivelDTO;
import br.com.ctcea.gestaoinv.entities.gestaoinv.Intangivel;
import br.com.ctcea.gestaoinv.repositories.gestaoinv.IntangivelRepository;
import br.com.ctcea.gestaoinv.services.exceptions.RecursoNaoEncontradoException;

@Service
public class IntangivelService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IntangivelService.class);

	@Autowired
	private IntangivelRepository intangivelRepository;
	
	@Transactional(readOnly = true)
	public Intangivel getIntangivelObject(Long id) {
		return intangivelRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível localizar um ativo intangível com ID " + id));
	}
	
	@Transactional(readOnly = true)
	public List<Intangivel> getAll() {
		return intangivelRepository.findAll();
	}
	
	@Transactional
	public Intangivel register(IntangivelDTO dto) {
		Intangivel newRegister = new Intangivel();
		
		dtoToEntity(newRegister, dto);
		newRegister = intangivelRepository.save(newRegister);
		
		LOGGER.info("[LOG] - Novo ativo intangível {} registrado.", newRegister.getId());
		return newRegister;
	}
	
	@Transactional
	public Intangivel update(Long id, IntangivelDTO dto) {
		Intangivel toUpdate = getIntangivelObject(id);
		
		dtoToEntity(toUpdate, dto);
		toUpdate = intangivelRepository.save(toUpdate);
		
		LOGGER.info("[LOG] - Ativo intangível {} atualizado.", id);
		return toUpdate;
	}
	
	@Transactional
	public Intangivel update(Intangivel obj) {
		Intangivel ativo = intangivelRepository.save(obj);
		return ativo;
	}
	
	public void delete(Long id) {
		intangivelRepository.deleteById(id);
		LOGGER.info("[LOG] - Ativo intangível {} excluído.", id);
	}
	
	private void dtoToEntity(Intangivel entity, IntangivelDTO dto) {
		entity.setArea(dto.getArea());
		entity.setCategoria(dto.getCategoria());
		entity.setCodigoSerie(dto.getCodigoSerie());
		entity.setDataAquisicao(dto.getDataAquisicao());
		entity.setDescricao(dto.getDescricao());
		entity.setFornecedor(dto.getFornecedor());
		entity.setIdPatrimonial(dto.getIdPatrimonial());
		entity.setLinkDocumento(dto.getLinkDocumento());
		entity.setLocalizacao(dto.getLocalizacao());
		entity.setObservacoes(dto.getObservacoes());
		entity.setResponsavel(dto.getResponsavel());
		entity.setUsuarioResponsavel(dto.getUsuarioResponsavel());
	}
}
