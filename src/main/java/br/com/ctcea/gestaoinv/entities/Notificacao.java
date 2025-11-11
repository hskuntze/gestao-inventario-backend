package br.com.ctcea.gestaoinv.entities;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import br.com.ctcea.gestaoinv.enums.TermoParceria;
import br.com.ctcea.gestaoinv.enums.TipoNotificacao;

@FilterDef(name = "filialFilter", parameters = @ParamDef(name = "termoParceria", type = "string"))
@Filter(name = "filialFilter", condition = "termo_parceria = :termoParceria")
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
	
    @Column(name = "termo_parceria")
    @Enumerated(EnumType.STRING)
	private TermoParceria termoParceria;
	
	public Notificacao() {
	}

	public Notificacao(Long idAtivo, String tipoAtivo, String titulo, String mensagem, TipoNotificacao tipo, TermoParceria tp) {
		this.idAtivo = idAtivo;
		this.tipoAtivo = tipoAtivo;
		this.titulo = titulo;
		this.mensagem = mensagem;
		this.dataCriacao = LocalDateTime.now();
		this.lida = false;
		this.tipoNotificacao = tipo;
		this.termoParceria = tp;
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

	public TermoParceria getTermoParceria() {
		return termoParceria;
	}

	public void setTermoParceria(TermoParceria termoParceria) {
		this.termoParceria = termoParceria;
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