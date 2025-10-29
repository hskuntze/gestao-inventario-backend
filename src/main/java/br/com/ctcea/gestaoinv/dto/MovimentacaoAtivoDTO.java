package br.com.ctcea.gestaoinv.dto;

public class MovimentacaoAtivoDTO {

	private Long idAtivo;
	
	//Propriedades a serem atualizadas
	private Long idArea;
	private Long idLocalizacao;
	private Long idUsuarioResponsavel;
	
	public MovimentacaoAtivoDTO() {
	}

	public MovimentacaoAtivoDTO(Long idAtivo, Long idArea, Long idLocalizacao, Long idUsuarioResponsavel) {
		this.idAtivo = idAtivo;
		this.idArea = idArea;
		this.idLocalizacao = idLocalizacao;
		this.idUsuarioResponsavel = idUsuarioResponsavel;
	}

	public Long getIdAtivo() {
		return idAtivo;
	}

	public void setIdAtivo(Long idAtivo) {
		this.idAtivo = idAtivo;
	}

	public Long getIdArea() {
		return idArea;
	}

	public void setIdArea(Long idArea) {
		this.idArea = idArea;
	}

	public Long getIdLocalizacao() {
		return idLocalizacao;
	}

	public void setIdLocalizacao(Long idLocalizacao) {
		this.idLocalizacao = idLocalizacao;
	}

	public Long getIdUsuarioResponsavel() {
		return idUsuarioResponsavel;
	}

	public void setIdUsuarioResponsavel(Long idUsuarioResponsavel) {
		this.idUsuarioResponsavel = idUsuarioResponsavel;
	}
}