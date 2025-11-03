package br.com.ctcea.gestaoinv.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_ativo_intangivel")
public class Intangivel extends Ativo {

	public Intangivel() {
	}
}
