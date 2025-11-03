package br.com.ctcea.gestaoinv.dto;

public class QuantidadeAtivoDTO {

	private Integer qtdTangivel;
	private Integer qtdIntangivel;
	private Integer qtdTangivelLocacao;
	
	public QuantidadeAtivoDTO() {
	}

	public QuantidadeAtivoDTO(Integer qtdTangivel, Integer qtdIntangivel, Integer qtdTangivelLocacao) {
		this.qtdTangivel = qtdTangivel;
		this.qtdIntangivel = qtdIntangivel;
		this.qtdTangivelLocacao = qtdTangivelLocacao;
	}

	public Integer getQtdTangivel() {
		return qtdTangivel;
	}

	public Integer getQtdIntangivel() {
		return qtdIntangivel;
	}

	public Integer getQtdTangivelLocacao() {
		return qtdTangivelLocacao;
	}
	
	public Integer getTotal() {
		return qtdTangivel + qtdIntangivel + qtdTangivelLocacao;
	}
}