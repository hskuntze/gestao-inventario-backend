package br.com.ctcea.gestaoinv.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_area")
public class Area {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String responsavel;
	private String substitutoResponsavel;
	
	public Area() {
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