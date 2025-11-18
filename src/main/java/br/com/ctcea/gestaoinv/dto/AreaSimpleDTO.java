package br.com.ctcea.gestaoinv.dto;

import br.com.ctcea.gestaoinv.entities.Area;

public class AreaSimpleDTO {
	
	private Long id;
	private String nome;
	private String responsavel;
	
	public AreaSimpleDTO() {
	}
	
	public AreaSimpleDTO(Area obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.responsavel = obj.getResponsavel();
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

	@Override
	public String toString() {
		return "AreaSimpleDTO [id=" + id + ", nome=" + nome + ", responsavel=" + responsavel + "]";
	}
}