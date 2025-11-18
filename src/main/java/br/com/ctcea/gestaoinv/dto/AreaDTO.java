package br.com.ctcea.gestaoinv.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.ctcea.gestaoinv.entities.Area;

public class AreaDTO {
	
	private Long id;
	private String nome;
	private String responsavel;
	
	private List<LocalizacaoDTO> localizacoes = new ArrayList<>();
	
	public AreaDTO() {
	}
	
	public AreaDTO(Area obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.responsavel = obj.getResponsavel();
		
		obj.getLocalizacoes().forEach(l -> this.localizacoes.add(new LocalizacaoDTO(l)));
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

	public List<LocalizacaoDTO> getLocalizacoes() {
		return localizacoes;
	}
}