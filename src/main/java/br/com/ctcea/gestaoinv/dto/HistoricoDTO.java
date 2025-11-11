package br.com.ctcea.gestaoinv.dto;

import java.time.LocalDateTime;

import br.com.ctcea.gestaoinv.entities.Historico;

public class HistoricoDTO {

	private Long id;
	private LocalDateTime createdAt;
	private String operacao;
	private String area;
	private String localizacao;
	private String usuarioResponsavel;
	private Long userId;
	private String userLogin;
	private AtivoDTO ativo;
	
	public HistoricoDTO() {
	}
	
	public HistoricoDTO(Historico obj) {
		this.id = obj.getId();
		this.createdAt = obj.getCreatedAt();
		this.operacao = obj.getOperacao();
		this.userId = obj.getUserId();
		this.userLogin = obj.getUserLogin();
		this.area = obj.getArea();
		this.localizacao = obj.getLocalizacao();
		this.usuarioResponsavel = obj.getUsuarioResponsavel();
		
		this.ativo = new AtivoDTO(obj.getAtivo());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public AtivoDTO getAtivo() {
		return ativo;
	}

	public void setAtivo(AtivoDTO ativo) {
		this.ativo = ativo;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public String getUsuarioResponsavel() {
		return usuarioResponsavel;
	}

	public void setUsuarioResponsavel(String usuarioResponsavel) {
		this.usuarioResponsavel = usuarioResponsavel;
	}
}