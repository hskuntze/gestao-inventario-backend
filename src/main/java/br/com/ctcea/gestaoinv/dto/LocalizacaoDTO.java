package br.com.ctcea.gestaoinv.dto;

import br.com.ctcea.gestaoinv.entities.Localizacao;

public class LocalizacaoDTO {

	private Long id;
	private String nome;
	
	public LocalizacaoDTO() {
	}
	
	public LocalizacaoDTO(Localizacao obj) {
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