package br.com.ctcea.gestaoinv.dto;

import java.time.LocalDateTime;

import br.com.ctcea.gestaoinv.entities.gestaoinv.Historico;

public class HistoricoDTO {

	private Long id;
	private LocalDateTime createdAt;
	private String operation;
	private Long ativo;
	private Long userId;
	private String userLogin;
	
	public HistoricoDTO() {
	}
	
	public HistoricoDTO(Historico obj) {
		this.id = obj.getId();
		this.createdAt = obj.getCreatedAt();
		this.operation = obj.getOperation();
		this.ativo = obj.getAtivo().getId();
		this.userId = obj.getUserId();
		this.userLogin = obj.getUserLogin();
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

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Long getAtivo() {
		return ativo;
	}

	public void setAtivo(Long ativo) {
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
}