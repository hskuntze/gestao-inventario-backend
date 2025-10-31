package br.com.ctcea.gestaoinv.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.ctcea.gestaoinv.entities.Usuario;
import br.com.ctcea.gestaoinv.enums.TermoParceria;

public class UsuarioSimpleDTO implements Serializable {
	private static final long serialVersionUID = -1687165994596734956L;
	
	private Long id;
	private String nome;
	private String email;
	private String login;
	private Integer userState;
	private Integer firstAccess;
	private TermoParceria termoParceria;
	private List<PerfilDTO> perfis = new ArrayList<>();
	
	public UsuarioSimpleDTO() {
	}
	
	public UsuarioSimpleDTO(Usuario obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.email = obj.getEmail();
		this.login = obj.getLogin();
		this.userState = obj.getUserState();
		this.firstAccess = obj.getFirstAccess();
		this.termoParceria = obj.getTermoParceria();
		
		this.perfis.clear();
		obj.getPerfis().forEach(p -> new PerfilDTO(p));
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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Integer getUserState() {
		return userState;
	}

	public void setUserState(Integer userState) {
		this.userState = userState;
	}

	public Integer getFirstAccess() {
		return firstAccess;
	}

	public void setFirstAccess(Integer firstAccess) {
		this.firstAccess = firstAccess;
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