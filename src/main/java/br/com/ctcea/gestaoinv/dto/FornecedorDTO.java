package br.com.ctcea.gestaoinv.dto;

import br.com.ctcea.gestaoinv.entities.Fornecedor;

public class FornecedorDTO {

	private Long id;
	private String nome;
	
	public FornecedorDTO() {
	}
	
	public FornecedorDTO(Fornecedor obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}