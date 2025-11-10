package br.com.ctcea.gestaoinv.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.ctcea.gestaoinv.entities.Ativo;
import br.com.ctcea.gestaoinv.entities.Imagem;
import br.com.ctcea.gestaoinv.enums.Categoria;
import br.com.ctcea.gestaoinv.enums.TermoParceria;

public class AtivoDTO {
	private Long id;
	private String idPatrimonial;
    private Categoria categoria;
    private TermoParceria termoParceria;
    private String descricao;
    private AreaSimpleDTO area;
    private LocalizacaoDTO localizacao;
    private UsuarioResponsavelDTO usuarioResponsavel; //Usuário que está utilizando este ativo
    private FornecedorDTO fornecedor;
    private ContratoDTO contrato;
    private LocalDate dataAquisicao;
	private LocalDate dataDevolucaoPrevista;
	private LocalDate dataDevolucaoRealizada;
    private String codigoSerie;
    private String observacoes;
    private String linkDocumento;
	private String estadoConservacao;
	private String qrCodeUrl;
	private byte[] qrCodeImage;
	private List<Imagem> imagens = new ArrayList<>();
	private String tipoAtivo;
	
	private boolean gerarIdPatrimonial;
    private boolean desabilitado;
    private String razaoDesabilitado;
	
	public AtivoDTO() {
	}
	
	public AtivoDTO(Ativo obj) {
    	this.id = obj.getId();
    	this.idPatrimonial = obj.getIdPatrimonial();
    	this.categoria = obj.getCategoria();
    	this.termoParceria = obj.getTermoParceria();
    	this.descricao = obj.getDescricao();
    	this.area = new AreaSimpleDTO(obj.getArea());
    	this.usuarioResponsavel = new UsuarioResponsavelDTO(obj.getUsuarioResponsavel());
    	this.fornecedor = new FornecedorDTO(obj.getFornecedor());
    	
    	if(obj.getContrato() != null) {
        	this.contrato = new ContratoDTO(obj.getContrato());
    	}
    	
    	this.dataAquisicao = obj.getDataAquisicao();
    	this.codigoSerie = obj.getCodigoSerie();
    	this.observacoes = obj.getObservacoes();
    	this.linkDocumento = obj.getLinkDocumento();
    	this.qrCodeImage = obj.getQrCodeImage();
    	this.qrCodeUrl = obj.getQrCodeUrl();
    	this.gerarIdPatrimonial = obj.getGerarIdPatrimonial();
    	this.desabilitado = obj.isDesabilitado();
    	this.razaoDesabilitado = obj.getRazaoDesabilitado();
    	
    	if(obj.getLocalizacao() != null) {
    		this.localizacao = new LocalizacaoDTO(obj.getLocalizacao());
    	}
    	
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

	public AreaSimpleDTO getArea() {
		return area;
	}

	public void setArea(AreaSimpleDTO area) {
		this.area = area;
	}

	public FornecedorDTO getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(FornecedorDTO fornecedor) {
		this.fornecedor = fornecedor;
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

	public ContratoDTO getContrato() {
		return contrato;
	}

	public void setContrato(ContratoDTO contrato) {
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

	public LocalDate getDataDevolucaoPrevista() {
		return dataDevolucaoPrevista;
	}

	public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) {
		this.dataDevolucaoPrevista = dataDevolucaoPrevista;
	}

	public LocalDate getDataDevolucaoRealizada() {
		return dataDevolucaoRealizada;
	}

	public void setDataDevolucaoRealizada(LocalDate dataDevolucaoRealizada) {
		this.dataDevolucaoRealizada = dataDevolucaoRealizada;
	}
}