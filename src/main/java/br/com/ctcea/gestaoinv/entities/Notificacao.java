package br.com.ctcea.gestaoinv.entities;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.ctcea.gestaoinv.enums.TipoNotificacao;

@Entity
@Table(name = "tb_notificacao")
public class Notificacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String mensagem;
	private Long idAtivo;
	private String tipoAtivo;
	private LocalDateTime dataCriacao;
	private boolean lida;
	private TipoNotificacao tipoNotificacao;
	
	public Notificacao() {
	}

	public Notificacao(Long idAtivo, String tipoAtivo, String titulo, String mensagem, TipoNotificacao tipo) {
		this.idAtivo = idAtivo;
		this.tipoAtivo = tipoAtivo;
		this.titulo = titulo;
		this.mensagem = mensagem;
		this.dataCriacao = LocalDateTime.now();
		this.lida = false;
		this.tipoNotificacao = tipo;
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

	public Long getIdAtivo() {
		return idAtivo;
	}

	public void setIdAtivo(Long idAtivo) {
		this.idAtivo = idAtivo;
	}

	public String getTipoAtivo() {
		return tipoAtivo;
	}

	public void setTipoAtivo(String tipoAtivo) {
		this.tipoAtivo = tipoAtivo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public boolean isLida() {
		return lida;
	}

	public void setLida(boolean lida) {
		this.lida = lida;
	}

	public TipoNotificacao getTipoNotificacao() {
		return tipoNotificacao;
	}

	public void setTipoNotificacao(TipoNotificacao tipoNotificacao) {
		this.tipoNotificacao = tipoNotificacao;
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
		Notificacao other = (Notificacao) obj;
		return Objects.equals(id, other.id);
	}
}