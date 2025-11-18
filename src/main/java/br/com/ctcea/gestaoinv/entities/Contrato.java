package br.com.ctcea.gestaoinv.entities;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.ctcea.gestaoinv.enums.TermoParceria;

@Entity
@Table(name = "tb_contrato")
public class Contrato {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String descricao;
	private TermoParceria termoParceria;
	
	private LocalDate inicioDataVigencia;
	private LocalDate fimDataVigencia;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_fornecedor")
	private Fornecedor fornecedor;
	
	public Contrato() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TermoParceria getTermoParceria() {
		return termoParceria;
	}

	public void setTermoParceria(TermoParceria termoParceria) {
		this.termoParceria = termoParceria;
	}

	public LocalDate getInicioDataVigencia() {
		return inicioDataVigencia;
	}

	public void setInicioDataVigencia(LocalDate inicioDataVigencia) {
		this.inicioDataVigencia = inicioDataVigencia;
	}

	public LocalDate getFimDataVigencia() {
		return fimDataVigencia;
	}

	public void setFimDataVigencia(LocalDate fimDataVigencia) {
		this.fimDataVigencia = fimDataVigencia;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contrato other = (Contrato) obj;
		return Objects.equals(id, other.id);
	}
}