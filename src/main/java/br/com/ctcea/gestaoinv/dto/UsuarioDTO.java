package br.com.ctcea.gestaoinv.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.ctcea.gestaoinv.entities.Usuario;
import br.com.ctcea.gestaoinv.enums.TermoParceria;

public class UsuarioDTO {

	private Long id;
	private String email;
	private String nome;
	private String login;
	private String userUuid;
	private TermoParceria termoParceria;
	private List<PerfilDTO> perfis = new ArrayList<>();
	
 	public UsuarioDTO() {
	}
	
	public UsuarioDTO(Usuario user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.nome = user.getNome();
		this.login = user.getLogin();
		this.userUuid = user.getUserUuid();
		this.termoParceria = user.getTermoParceria();
		
		this.perfis.clear();
		this.perfis = user.getPerfis().stream()
                .map(PerfilDTO::new)
                .collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TermoParceria getTermoParceria() {
		return termoParceria;
	}

	public void setTermoParceria(TermoParceria termoParceria) {
		this.termoParceria = termoParceria;
	}

	public List<PerfilDTO> getPerfis() {
		return perfis;
	}
}