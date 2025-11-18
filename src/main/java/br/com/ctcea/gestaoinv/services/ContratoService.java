package br.com.ctcea.gestaoinv.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ctcea.gestaoinv.dto.ContratoDTO;
import br.com.ctcea.gestaoinv.entities.Contrato;
import br.com.ctcea.gestaoinv.entities.Fornecedor;
import br.com.ctcea.gestaoinv.exceptions.RecursoExistenteException;
import br.com.ctcea.gestaoinv.exceptions.RecursoNaoEncontradoException;
import br.com.ctcea.gestaoinv.repositories.ContratoRepository;
import br.com.ctcea.gestaoinv.repositories.FornecedorRepository;

@Service
public class ContratoService {

	@Autowired
	private ContratoRepository contratoRepository;
	
	@Autowired
	private FornecedorRepository fornecedorRepository;

	@Transactional(readOnly = true)
	public Contrato getObjectById(Long id) {
		return contratoRepository.findById(id).orElseThrow(
				() -> new RecursoNaoEncontradoException("Não foi possível localizar contrato com ID " + id));
	}

	@Transactional(readOnly = true)
	public ContratoDTO getDtoById(Long id) {
		Contrato c = getObjectById(id);
		return new ContratoDTO(c);
	}

	@Transactional(readOnly = true)
	public List<ContratoDTO> getAll() {
		List<Contrato> all = contratoRepository.findAll();
		return all.stream().map(c -> new ContratoDTO(c)).collect(Collectors.toList());
	}

	@Transactional
	public ContratoDTO register(ContratoDTO dto) {
		String titulo = dto.getTitulo();
		Optional<Contrato> contrato = contratoRepository.findByTitulo(titulo);
		
		if(contrato.isPresent()) {
			throw new RecursoExistenteException("Já existe um contrato com este título");
		}
		
		Contrato newRegister = new Contrato();
		dtoToEntity(dto, newRegister);
		
		Fornecedor f = fornecedorRepository.findById(dto.getFornecedor().getId()).orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível localizar um fornecedor com ID " + dto.getFornecedor().getId()));
		newRegister.setFornecedor(f);
		
		newRegister = contratoRepository.save(newRegister);
		return new ContratoDTO(newRegister);
	}
	
	@Transactional
	public ContratoDTO update(Long id, ContratoDTO dto) {
		Contrato toUpdate = getObjectById(id);
		dtoToEntity(dto, toUpdate);
		
		Fornecedor f = fornecedorRepository.findById(dto.getFornecedor().getId()).orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível localizar um fornecedor com ID " + dto.getFornecedor().getId()));
		toUpdate.setFornecedor(f);

		toUpdate = contratoRepository.save(toUpdate);
		return new ContratoDTO(toUpdate);
	}

	private void dtoToEntity(ContratoDTO dto, Contrato entity) {
		entity.setTitulo(dto.getTitulo());
		entity.setDescricao(dto.getDescricao());
		entity.setInicioDataVigencia(dto.getInicioDataVigencia());
		entity.setFimDataVigencia(dto.getFimDataVigencia());
		entity.setTermoParceria(dto.getTermoParceria());
	}
}
