package br.com.ctcea.gestaoinv.logs;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_log_operacao")
public class LogOperacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String usuario;
	private String ip;
	private String metodoHttp;
	private String url;
	private int statusHttp;
	private LocalDateTime dataOperacao;
	@Column(length = 255)
	private String detalhes;
	
	public LogOperacao() {
	}

	public LogOperacao(Long id, String usuario, String ip, String metodoHttp, String url, int statusHttp,
			String detalhes) {
		this.id = id;
		this.usuario = usuario;
		this.ip = ip;
		this.metodoHttp = metodoHttp;
		this.url = url;
		this.statusHttp = statusHttp;
		this.detalhes = detalhes;
		this.dataOperacao = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMetodoHttp() {
		return metodoHttp;
	}

	public void setMetodoHttp(String metodoHttp) {
		this.metodoHttp = metodoHttp;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getStatusHttp() {
		return statusHttp;
	}

	public void setStatusHttp(int statusHttp) {
		this.statusHttp = statusHttp;
	}

	public LocalDateTime getDataOperacao() {
		return dataOperacao;
	}

	public void setDataOperacao(LocalDateTime dataOperacao) {
		this.dataOperacao = dataOperacao;
	}

	public String getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
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
		LogOperacao other = (LogOperacao) obj;
		return Objects.equals(id, other.id);
	}
}