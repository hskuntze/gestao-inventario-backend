package br.com.ctcea.gestaoinv.dto;

public class TrocaSenhaRequestDTO {

	private Long userId;
	private String novaSenha;
	
	public TrocaSenhaRequestDTO() {
	}

	public TrocaSenhaRequestDTO(Long userId, String novaSenha) {
		this.userId = userId;
		this.novaSenha = novaSenha;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}
}