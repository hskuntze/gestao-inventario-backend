package br.com.ctcea.gestaoinv.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import br.com.ctcea.gestaoinv.enums.Categoria;
import br.com.ctcea.gestaoinv.enums.TermoParceria;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Ativo {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
	private String idPatrimonial;
    private Categoria categoria;
    private TermoParceria termoParceria;
    private String descricao;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_area")
    private Area area;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_localizacao")
    private Localizacao localizacao;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario_responsavel")
    private UsuarioResponsavel usuarioResponsavel; //Usuário que está utilizando este ativo
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_fornecedor")
    private Fornecedor fornecedor;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_contrato")
    private Contrato contrato;
    private LocalDate dataAquisicao;
    private String codigoSerie;
    private String observacoes;
    private String linkDocumento;
    
    private boolean gerarIdPatrimonial;
    private boolean desabilitado;
    private String razaoDesabilitado;
    
    private String qrCodeUrl;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    @Lob
    private byte[] qrCodeImage;
    
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

	public TermoParceria getTermoParceria() {
		return termoParceria;
	}

	public void setTermoParceria(TermoParceria termoParceria) {
		this.termoParceria = termoParceria;
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

	public Localizacao getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(Localizacao localizacao) {
		this.localizacao = localizacao;
	}

	public UsuarioResponsavel getUsuarioResponsavel() {
		return usuarioResponsavel;
	}

	public void setUsuarioResponsavel(UsuarioResponsavel usuarioResponsavel) {
		this.usuarioResponsavel = usuarioResponsavel;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
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

	public boolean getGerarIdPatrimonial() {
		return gerarIdPatrimonial;
	}

	public void setGerarIdPatrimonial(boolean gerarIdPatrimonial) {
		this.gerarIdPatrimonial = gerarIdPatrimonial;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public boolean isDesabilitado() {
		return desabilitado;
	}

	public void setDesabilitado(boolean desabilitado) {
		this.desabilitado = desabilitado;
	}

	public String getRazaoDesabilitado() {
		return razaoDesabilitado;
	}

	public void setRazaoDesabilitado(String razaoDesabilitado) {
		this.razaoDesabilitado = razaoDesabilitado;
	}

	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

	public byte[] getQrCodeImage() {
		return qrCodeImage;
	}

	public void setQrCodeImage(byte[] qrCodeImage) {
		this.qrCodeImage = qrCodeImage;
	}

	public List<Imagem> getImagens() {
		return imagens;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	
	@PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
    	updatedAt = LocalDateTime.now();
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
}