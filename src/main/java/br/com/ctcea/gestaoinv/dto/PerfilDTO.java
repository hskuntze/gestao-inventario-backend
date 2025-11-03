package br.com.ctcea.gestaoinv.dto;

import br.com.ctcea.gestaoinv.entities.Perfil;

public class PerfilDTO {

	private Long id;
    private String autorizacao;
    
    public PerfilDTO() {
    }

    public PerfilDTO(Perfil entity) {
        this.id = entity.getId();
        this.autorizacao = entity.getAutorizacao();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAutorizacao() {
		return autorizacao;
	}

	public void setAutorizacao(String autorizacao) {
		this.autorizacao = autorizacao;
	}
}