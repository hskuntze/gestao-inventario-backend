package br.com.ctcea.gestaoinv.dto;

import java.time.LocalDate;

import br.com.ctcea.gestaoinv.entities.Tangivel;
import br.com.ctcea.gestaoinv.enums.Categoria;

public class TangivelDTO {
	
	private Long id;
	private String idPatrimonial;
    private Categoria categoria;
    private String descricao;
    private AreaSimpleDTO area;	
    private LocalizacaoDTO localizacao;
    private UsuarioResponsavelDTO usuarioResponsavel; 
    private FornecedorDTO fornecedor;
    private LocalDate dataAquisicao;
    private String codigoSerie;
    private String observacoes;
    private String linkDocumento;
    private String estadoConservacao;

	private boolean gerarIdPatrimonial;
    private boolean desabilitado;
    private String razaoDesabilitado;
    
    public TangivelDTO() {
	}
    
    public TangivelDTO(Tangivel obj) {
    	this.id = obj.getId();
    	this.idPatrimonial = obj.getIdPatrimonial();
    	this.categoria = obj.getCategoria();
    	this.descricao = obj.getDescricao();
    	this.area = new AreaSimpleDTO(obj.getArea());
    	this.usuarioResponsavel = new UsuarioResponsavelDTO(obj.getUsuarioResponsavel());
    	this.fornecedor = new FornecedorDTO(obj.getFornecedor());
    	this.dataAquisicao = obj.getDataAquisicao();
    	this.codigoSerie = obj.getCodigoSerie();
    	this.observacoes = obj.getObservacoes();
    	this.linkDocumento = obj.getLinkDocumento();
    	this.estadoConservacao = obj.getEstadoConservacao();
    	this.gerarIdPatrimonial = obj.getGerarIdPatrimonial();
    	this.desabilitado = obj.isDesabilitado();
    	this.razaoDesabilitado = obj.getRazaoDesabilitado();
    	
    	if(obj.getLocalizacao() != null) {
        	this.localizacao = new LocalizacaoDTO(obj.getLocalizacao());
    	}
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

	public AreaSimpleDTO getArea() {
		return area;
	}

	public void setArea(AreaSimpleDTO area) {
		this.area = area;
	}

	public LocalizacaoDTO getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(LocalizacaoDTO localizacao) {
		this.localizacao = localizacao;
	}

	public UsuarioResponsavelDTO getUsuarioResponsavel() {
		return usuarioResponsavel;
	}

	public void setUsuarioResponsavel(UsuarioResponsavelDTO usuarioResponsavel) {
		this.usuarioResponsavel = usuarioResponsavel;
	}
	
	public FornecedorDTO getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(FornecedorDTO fornecedor) {
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

	public String getEstadoConservacao() {
		return estadoConservacao;
	}

	public void setEstadoConservacao(String estadoConservacao) {
		this.estadoConservacao = estadoConservacao;
	}

	public boolean getGerarIdPatrimonial() {
		return gerarIdPatrimonial;
	}

	public void setGerarIdPatrimonial(boolean gerarIdPatrimonial) {
		this.gerarIdPatrimonial = gerarIdPatrimonial;
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
}