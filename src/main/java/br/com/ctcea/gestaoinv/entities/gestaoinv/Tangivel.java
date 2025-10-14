package br.com.ctcea.gestaoinv.entities.gestaoinv;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_ativo_tangivel")
public class Tangivel extends Ativo {

	private String estadoConservacao;
	
	public Tangivel() {
	}

	public String getEstadoConservacao() {
		return estadoConservacao;
	}

	public void setEstadoConservacao(String estadoConservacao) {
		this.estadoConservacao = estadoConservacao;
	}
}