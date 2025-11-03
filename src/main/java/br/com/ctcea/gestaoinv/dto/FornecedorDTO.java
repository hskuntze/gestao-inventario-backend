package br.com.ctcea.gestaoinv.dto;

import br.com.ctcea.gestaoinv.entities.Fornecedor;

public class FornecedorDTO {

	private Long id;
	private String nome;
	private String contatoEmail;
	private String contatoNome;
	private String contatoTelefone;
	private String cnpj;
	
	public FornecedorDTO() {
	}
	
	public FornecedorDTO(Fornecedor obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.cnpj = obj.getCnpj();
		this.contatoEmail = obj.getContatoEmail();
		this.contatoNome = obj.getContatoNome();
		this.contatoTelefone = obj.getContatoTelefone();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getContatoEmail() {
		return contatoEmail;
	}

	public void setContatoEmail(String contatoEmail) {
		this.contatoEmail = contatoEmail;
	}

	public String getContatoNome() {
		return contatoNome;
	}

	public void setContatoNome(String contatoNome) {
		this.contatoNome = contatoNome;
	}

	public String getContatoTelefone() {
		return contatoTelefone;
	}

	public void setContatoTelefone(String contatoTelefone) {
		this.contatoTelefone = contatoTelefone;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
}