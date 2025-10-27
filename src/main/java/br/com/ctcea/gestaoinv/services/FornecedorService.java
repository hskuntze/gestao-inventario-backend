package br.com.ctcea.gestaoinv.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ctcea.gestaoinv.dto.FornecedorDTO;
import br.com.ctcea.gestaoinv.entities.Fornecedor;
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
}
