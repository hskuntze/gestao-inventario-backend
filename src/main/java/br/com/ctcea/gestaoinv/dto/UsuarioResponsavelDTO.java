package br.com.ctcea.gestaoinv.dto;

import java.io.Serializable;

import br.com.ctcea.gestaoinv.entities.UsuarioResponsavel;

public class UsuarioResponsavelDTO implements Serializable {
	private static final long serialVersionUID = -6343536609834502568L;
	
	private Long id;
	private String nome;
	private String email;
	
	public UsuarioResponsavelDTO() {
	}
	
	public UsuarioResponsavelDTO(UsuarioResponsavel obj) {
		this.email = obj.getEmail();
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}