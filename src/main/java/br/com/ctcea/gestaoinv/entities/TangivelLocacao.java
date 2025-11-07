package br.com.ctcea.gestaoinv.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_ativo_tangivel_locacao")
public class TangivelLocacao extends Ativo {

	private String estadoConservacao;
	private LocalDate dataDevolucaoPrevista;
	private LocalDate dataDevolucaoRealizada;
	
	public TangivelLocacao() {
	}

	public String getEstadoConservacao() {
		return estadoConservacao;
	}

	public void setEstadoConservacao(String estadoConservacao) {
		this.estadoConservacao = estadoConservacao;
	}

	public LocalDate getDataDevolucaoPrevista() {
		return dataDevolucaoPrevista;
	}

	public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) {
		this.dataDevolucaoPrevista = dataDevolucaoPrevista;
	}

	public LocalDate getDataDevolucaoRealizada() {
		return dataDevolucaoRealizada;
	}

	public void setDataDevolucaoRealizada(LocalDate dataDevolucaoRealizada) {
		this.dataDevolucaoRealizada = dataDevolucaoRealizada;
	}
}