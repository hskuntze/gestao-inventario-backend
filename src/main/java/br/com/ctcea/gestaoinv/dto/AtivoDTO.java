package br.com.ctcea.gestaoinv.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.ctcea.gestaoinv.entities.gestaoinv.Ativo;
import br.com.ctcea.gestaoinv.entities.gestaoinv.Imagem;
import br.com.ctcea.gestaoinv.enums.Area;
import br.com.ctcea.gestaoinv.enums.Categoria;

public class AtivoDTO {
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
	private String estadoConservacao;
	private String qrCodeUrl;
	private byte[] qrCodeImage;
	private List<Imagem> imagens = new ArrayList<>();
	private String tipoAtivo;
	
	public AtivoDTO() {
	}
	
	public AtivoDTO(Ativo obj) {
    	this.id = obj.getId();
    	this.idPatrimonial = obj.getIdPatrimonial();
    	this.categoria = obj.getCategoria();
    	this.descricao = obj.getDescricao();
    	this.area = obj.getArea();
    	this.localizacao = obj.getLocalizacao();
    	this.responsavel = obj.getResponsavel();
    	this.usuarioResponsavel = obj.getUsuarioResponsavel();
    	this.fornecedor = obj.getFornecedor();
    	this.dataAquisicao = obj.getDataAquisicao();
    	this.codigoSerie = obj.getCodigoSerie();
    	this.observacoes = obj.getObservacoes();
    	this.linkDocumento = obj.getLinkDocumento();
    	this.qrCodeImage = obj.getQrCodeImage();
    	this.qrCodeUrl = obj.getQrCodeUrl();
    	
    	this.imagens.clear();
    	
    	for(Imagem i : obj.getImagens()) {
    		this.imagens.add(i);
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

	public String getEstadoConservacao() {
		return estadoConservacao;
	}

	public void setEstadoConservacao(String estadoConservacao) {
		this.estadoConservacao = estadoConservacao;
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

	public void setImagens(List<Imagem> imagens) {
		this.imagens = imagens;
	}

	public String getTipoAtivo() {
		return tipoAtivo;
	}

	public void setTipoAtivo(String tipoAtivo) {
		this.tipoAtivo = tipoAtivo;
	}
}