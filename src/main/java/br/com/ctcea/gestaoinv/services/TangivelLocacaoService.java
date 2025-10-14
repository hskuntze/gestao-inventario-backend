package br.com.ctcea.gestaoinv.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ctcea.gestaoinv.dto.TangivelLocacaoDTO;
import br.com.ctcea.gestaoinv.entities.gestaoinv.TangivelLocacao;
import br.com.ctcea.gestaoinv.repositories.gestaoinv.TangivelLocacaoRepository;
import br.com.ctcea.gestaoinv.services.exceptions.RecursoNaoEncontradoException;

@Service
public class TangivelLocacaoService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TangivelLocacaoService.class);

	@Autowired
	private TangivelLocacaoRepository tangivelLocacaoRepository;
	
	@Transactional(readOnly = true)
	public TangivelLocacao getTangivelLocacaoObject(Long id) {
		return tangivelLocacaoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível localizar um ativo tangível de locação com ID " + id));
	}
	
	@Transactional(readOnly = true)
	public List<TangivelLocacao> getAll() {
		return tangivelLocacaoRepository.findAll();
	}
	
	@Transactional
	public TangivelLocacao register(TangivelLocacaoDTO dto) {
		TangivelLocacao newRegister = new TangivelLocacao();
		
		dtoToEntity(newRegister, dto);
		newRegister = tangivelLocacaoRepository.save(newRegister);
		
		LOGGER.info("[LOG] - Novo ativo tangível de locação {} registrado.", newRegister.getId());
		return newRegister;
	}
	
	@Transactional
	public TangivelLocacao update(Long id, TangivelLocacaoDTO dto) {
		TangivelLocacao toUpdate = getTangivelLocacaoObject(id);
		
		dtoToEntity(toUpdate, dto);
		toUpdate = tangivelLocacaoRepository.save(toUpdate);
		
		LOGGER.info("[LOG] - Ativo tangível de locação {} atualizado.", id);
		return toUpdate;
	}
	
	@Transactional
	public TangivelLocacao update(TangivelLocacao obj) {
		TangivelLocacao ativo = tangivelLocacaoRepository.save(obj);
		return ativo;
	}
	
	public void delete(Long id) {
		tangivelLocacaoRepository.deleteById(id);
		LOGGER.info("[LOG] - Ativo tangível de locação {} excluído.", id);
	}
	
	private void dtoToEntity(TangivelLocacao entity, TangivelLocacaoDTO dto) {
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
