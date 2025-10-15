package br.com.ctcea.gestaoinv.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.ctcea.gestaoinv.entities.safe.Usuario;

public class UsuarioDTO {

	private Long id;
	private String email;
	private String nome;
	private String login;
	private String username;
	private String userUuid;
	private List<String> groups = new ArrayList<>();
	
 	public UsuarioDTO() {
	}
	
	public UsuarioDTO(Usuario user) {
		this.id = user.getUserTenantId();
		this.email = user.getEmail();
		this.nome = user.getNomeCompleto();
		this.login = user.getLogin();
		this.username = user.getUsername();
		this.userUuid = user.getUserUuid();
		
		this.groups.clear();
		user.getAssociacoes().forEach(a -> this.groups.add(a));
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public List<String> getGroups() {
		return groups;
	}
}