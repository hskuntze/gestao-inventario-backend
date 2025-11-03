package br.com.ctcea.gestaoinv.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_ativo_tangivel_locacao")
public class TangivelLocacao extends Ativo {

	private String estadoConservacao;
	
	public TangivelLocacao() {
	}

	public String getEstadoConservacao() {
		return estadoConservacao;
	}

	public void setEstadoConservacao(String estadoConservacao) {
		this.estadoConservacao = estadoConservacao;
	}
}