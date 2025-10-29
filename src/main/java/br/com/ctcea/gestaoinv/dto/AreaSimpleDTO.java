package br.com.ctcea.gestaoinv.dto;

import br.com.ctcea.gestaoinv.entities.Area;

public class AreaSimpleDTO {
	
	private Long id;
	private String nome;
	private String responsavel;
	private String substitutoResponsavel;
	
	public AreaSimpleDTO() {
	}
	
	public AreaSimpleDTO(Area obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.responsavel = obj.getResponsavel();
		this.substitutoResponsavel = obj.getSubstitutoResponsavel();
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

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getSubstitutoResponsavel() {
		return substitutoResponsavel;
	}

	public void setSubstitutoResponsavel(String substitutoResponsavel) {
		this.substitutoResponsavel = substitutoResponsavel;
	}
}