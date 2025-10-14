package br.com.ctcea.gestaoinv.entities.gestaoinv;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import br.com.ctcea.gestaoinv.enums.Area;
import br.com.ctcea.gestaoinv.enums.Categoria;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Ativo {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
	private String idPatrimonial;
    private Categoria categoria;
    private String descricao;
    private Area area;
    private String localizacao;
    private String responsavel; //Responsável da ÁREA
    private String usuarioResponsavel; //Usuário que está utilizando este ativo
    private String fornecedor;
    private LocalDate dataAquisicao;
    private String codigoSerie;
    private String observacoes;
    private String linkDocumento;
    
    @OneToMany(mappedBy = "ativo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Imagem> imagens = new ArrayList<>();
    
    public Ativo() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdPatrimonial() {
		return idPatrimonial;
	}

	public void setIdPatrimonial(String idPatrimonial) {
		this.idPatrimonial = idPatrimonial;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getUsuarioResponsavel() {
		return usuarioResponsavel;
	}

	public void setUsuarioResponsavel(String usuarioResponsavel) {
		this.usuarioResponsavel = usuarioResponsavel;
	}

	public String getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}

	public LocalDate getDataAquisicao() {
		return dataAquisicao;
	}

	public void setDataAquisicao(LocalDate dataAquisicao) {
		this.dataAquisicao = dataAquisicao;
	}

	public String getCodigoSerie() {
		return codigoSerie;
	}

	public void setCodigoSerie(String codigoSerie) {
		this.codigoSerie = codigoSerie;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public String getLinkDocumento() {
		return linkDocumento;
	}

	public void setLinkDocumento(String linkDocumento) {
		this.linkDocumento = linkDocumento;
	}

	public List<Imagem> getImagens() {
		return imagens;
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
		Ativo other = (Ativo) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Ativo [idPatrimonial=" + idPatrimonial + ", categoria=" + categoria + ", descricao=" + descricao
				+ ", area=" + area + ", responsavel=" + responsavel + "]";
	}
}