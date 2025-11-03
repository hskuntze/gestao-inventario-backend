package br.com.ctcea.gestaoinv.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ctcea.gestaoinv.dto.FornecedorDTO;
import br.com.ctcea.gestaoinv.entities.Fornecedor;
import br.com.ctcea.gestaoinv.exceptions.RecursoNaoEncontradoException;
import br.com.ctcea.gestaoinv.repositories.FornecedorRepository;

@Service
public class FornecedorService {

	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	@Transactional(readOnly = true)
	public List<FornecedorDTO> getAll() {
		List<Fornecedor> all = fornecedorRepository.findAll();
		return all.stream().map(f -> new FornecedorDTO(f)).collect(Collectors.toList());
	}
	
	@Transactional
	public FornecedorDTO register(FornecedorDTO dto) {
		Fornecedor f = new Fornecedor();
		dtoToEntity(f, dto);
		f = fornecedorRepository.save(f);
		
		return new FornecedorDTO(f);
	}
	
	@Transactional
	public FornecedorDTO update(Long id, FornecedorDTO dto) {
		Fornecedor f = fornecedorRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível localizar um fornecedor com ID " + id));
		dtoToEntity(f, dto);
		f = fornecedorRepository.save(f);
		
		return new FornecedorDTO(f);
	}

	private void dtoToEntity(Fornecedor f, FornecedorDTO dto) {
		f.setNome(dto.getNome());
		f.setCnpj(dto.getCnpj());
		f.setContatoEmail(dto.getContatoEmail());
		f.setContatoNome(dto.getContatoNome());
		f.setContatoTelefone(dto.getContatoTelefone());
	}
}
