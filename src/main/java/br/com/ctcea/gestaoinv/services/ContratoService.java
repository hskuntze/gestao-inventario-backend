package br.com.ctcea.gestaoinv.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ctcea.gestaoinv.dto.ContratoDTO;
import br.com.ctcea.gestaoinv.dto.FornecedorDTO;
import br.com.ctcea.gestaoinv.entities.Contrato;
import br.com.ctcea.gestaoinv.entities.Fornecedor;
import br.com.ctcea.gestaoinv.exceptions.RecursoNaoEncontradoException;
import br.com.ctcea.gestaoinv.repositories.ContratoRepository;

@Service
public class ContratoService {

	@Autowired
	private ContratoRepository contratoRepository;

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
		Contrato newRegister = new Contrato();
		dtoToEntity(dto, newRegister);
		
		newRegister = contratoRepository.save(newRegister);
		return new ContratoDTO(newRegister);
	}
	
	@Transactional
	public ContratoDTO update(Long id, ContratoDTO dto) {
		Contrato toUpdate = getObjectById(id);
		dtoToEntity(dto, toUpdate);

		toUpdate = contratoRepository.save(toUpdate);
		return new ContratoDTO(toUpdate);
	}

	private void dtoToEntity(ContratoDTO dto, Contrato entity) {
		entity.setTitulo(dto.getTitulo());
		entity.setDescricao(dto.getDescricao());
		entity.setInicioDataVigencia(dto.getInicioDataVigencia());
		entity.setFimDataVigencia(dto.getFimDataVigencia());
		entity.setTermoParceria(dto.getTermoParceria());

		entity.getFornecedores().removeIf(existing -> dto.getFornecedores().stream()
				.noneMatch(f -> f.getId() != null && f.getId() > 0 && f.getId().equals(existing.getId())));

		for (FornecedorDTO fDto : dto.getFornecedores()) {
			Long fId = (fDto.getId() != null && fDto.getId() > 0) ? fDto.getId() : null;
			Fornecedor fornecedor;

			if (fId != null) {
				fornecedor = entity.getFornecedores().stream()
						.filter(existing -> existing.getId() != null && existing.getId().equals(fDto.getId()))
						.findFirst()
						.orElseGet(Fornecedor::new);
			} else {
				fornecedor = new Fornecedor();
			}
			
			fornecedor.setNome(fDto.getNome());
			fornecedor.setCnpj(fDto.getCnpj());
			fornecedor.setContatoEmail(fDto.getContatoEmail());
			fornecedor.setContatoNome(fDto.getContatoNome());
			fornecedor.setContatoTelefone(fDto.getContatoTelefone());
			
			if (!entity.getFornecedores().contains(fornecedor)) {
	            entity.getFornecedores().add(fornecedor);
	        }
		}
	}
}
