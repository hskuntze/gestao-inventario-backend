package br.com.ctcea.gestaoinv.entities;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import br.com.ctcea.gestaoinv.enums.TermoParceria;

@FilterDef(name = "filialFilter", parameters = @ParamDef(name = "termoParceria", type = "string"))
@Filter(name = "filialFilter", condition = "termo_parceria = :termoParceria")
@Entity
@Table(name = "tb_historico_ativo")
public class Historico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(updatable = false)
	private LocalDateTime createdAt;
	private String operacao;
	
	@OneToOne(fetch = FetchType.EAGER)
	private Ativo ativo;
	
	private String area;
	private String localizacao;
	private String usuarioResponsavel;
	
	private Long userId;
	private String userLogin;
	
	@Column(name = "termo_parceria")
    @Enumerated(EnumType.STRING)
    private TermoParceria termoParceria;
	
	public Historico() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public Ativo getAtivo() {
		return ativo;
	}

	public void setAtivo(Ativo ativo) {
		this.ativo = ativo;
	}
	
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public String getUsuarioResponsavel() {
		return usuarioResponsavel;
	}

	public void setUsuarioResponsavel(String usuarioResponsavel) {
		this.usuarioResponsavel = usuarioResponsavel;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public TermoParceria getTermoParceria() {
		return termoParceria;
	}

	public void setTermoParceria(TermoParceria termoParceria) {
		this.termoParceria = termoParceria;
	}

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
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
		Historico other = (Historico) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Historico [id=" + id + ", createdAt=" + createdAt + ", operacao=" + operacao + ", ativo=" + ativo
				+ ", area=" + area + ", localizacao=" + localizacao + ", usuarioResponsavel=" + usuarioResponsavel
				+ ", userId=" + userId + ", userLogin=" + userLogin + "]";
	}
}