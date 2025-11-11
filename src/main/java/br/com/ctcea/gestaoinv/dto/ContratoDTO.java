package br.com.ctcea.gestaoinv.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.ctcea.gestaoinv.entities.Contrato;
import br.com.ctcea.gestaoinv.enums.TermoParceria;

public class ContratoDTO implements Serializable {
	private static final long serialVersionUID = 2861708132679328285L;

	private Long id;
	private String titulo;
	private String descricao;
	private TermoParceria termoParceria;
	
	private LocalDate inicioDataVigencia;
	private LocalDate fimDataVigencia;
	
	private List<FornecedorDTO> fornecedores = new ArrayList<>();
	
	public ContratoDTO() {
	}
	
	public ContratoDTO(Contrato c) {
		if(c.getId() == null) {
			this.id = null;
		} else {
			this.id = c.getId();
		}
		this.titulo = c.getTitulo();
		this.descricao = c.getDescricao();
		this.termoParceria = c.getTermoParceria();
		this.inicioDataVigencia = c.getInicioDataVigencia();
		this.fimDataVigencia = c.getFimDataVigencia();
		
		c.getFornecedores().forEach(f -> this.fornecedores.add(new FornecedorDTO(f)));
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

	public List<FornecedorDTO> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<FornecedorDTO> fornecedores) {
		this.fornecedores = fornecedores;
	}
}