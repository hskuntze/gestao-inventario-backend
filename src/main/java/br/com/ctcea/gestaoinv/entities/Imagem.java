package br.com.ctcea.gestaoinv.entities;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_imagem")
public class Imagem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	
	@Lob
	@Type(type = "org.hibernate.type.BinaryType")
	@Column(columnDefinition = "BLOB")
	private byte[] conteudo;
	
	@ManyToOne
    @JoinColumn(name = "id_ativo")
	@JsonIgnore
    private Ativo ativo;
	
	public Imagem() {
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

	public byte[] getConteudo() {
		return conteudo;
	}

	public void setConteudo(byte[] conteudo) {
		this.conteudo = conteudo;
	}

	public Ativo getAtivo() {
		return ativo;
	}

	public void setAtivo(Ativo ativo) {
		this.ativo = ativo;
	}

	@Override
	public String toString() {
		return "Imagem [id=" + id + ", nome=" + nome + ", conteudo=" + Arrays.toString(conteudo) + "]";
	}
}
