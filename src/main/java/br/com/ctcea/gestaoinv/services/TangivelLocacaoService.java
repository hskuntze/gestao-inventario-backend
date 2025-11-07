package br.com.ctcea.gestaoinv.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.zxing.WriterException;

import br.com.ctcea.gestaoinv.components.GeradorIDPatrimonial;
import br.com.ctcea.gestaoinv.dto.TangivelLocacaoDTO;
import br.com.ctcea.gestaoinv.entities.Area;
import br.com.ctcea.gestaoinv.entities.Ativo;
import br.com.ctcea.gestaoinv.entities.Fornecedor;
import br.com.ctcea.gestaoinv.entities.Localizacao;
import br.com.ctcea.gestaoinv.entities.TangivelLocacao;
import br.com.ctcea.gestaoinv.entities.UsuarioResponsavel;
import br.com.ctcea.gestaoinv.exceptions.RecursoNaoEncontradoException;
import br.com.ctcea.gestaoinv.repositories.AreaRepository;
import br.com.ctcea.gestaoinv.repositories.FornecedorRepository;
import br.com.ctcea.gestaoinv.repositories.LocalizacaoRepository;
import br.com.ctcea.gestaoinv.repositories.TangivelLocacaoRepository;
import br.com.ctcea.gestaoinv.repositories.UsuarioResponsavelRepository;

@Service
public class TangivelLocacaoService {
	
	private GeradorIDPatrimonial geradorId;
	
	public TangivelLocacaoService(GeradorIDPatrimonial gerador) {
		this.geradorId = gerador;
	}
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TangivelLocacaoService.class);
	private static final String BASE_URL_QR_CODE = "inventario://patrimonio/";

	@Autowired
	private TangivelLocacaoRepository tangivelLocacaoRepository;

	@Autowired
	private LocalizacaoRepository localizacaoRepository;
	
	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	@Autowired
	private UsuarioResponsavelRepository usuarioResponsavelRepository;
	
	@Autowired
	private AreaRepository areaRepository;
	
	@Autowired
	private QRCodeGenerator qrCodeGenerator;
	
	@Autowired
	private HistoricoService historicoService;
	
	@Transactional(readOnly = true)
	public TangivelLocacao getTangivelLocacaoObject(Long id) {
		return tangivelLocacaoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível localizar um ativo tangível de locação com ID " + id));
	}
	
	@Transactional(readOnly = true)
	public List<TangivelLocacao> getAll() {
		return tangivelLocacaoRepository.findAll();
	}
	
	@Transactional
	public TangivelLocacaoDTO register(TangivelLocacaoDTO dto) {
		TangivelLocacao newRegister = new TangivelLocacao();
		
		dtoToEntity(newRegister, dto);
		gerarIdPatrimonial(dto, newRegister);
		gerarQRCode(newRegister);
		
		historicoService.recordOperation("REGISTRO", newRegister);
		
		if (newRegister.getUsuarioResponsavel().getId() != null
				|| !newRegister.getUsuarioResponsavel().getNome().equals("N/A")
				|| !newRegister.getUsuarioResponsavel().getNome().equals("Sem usuário")) {
			historicoService.recordOperation("ATRIBUIÇÃO", newRegister);
		}
		
		LOGGER.info("[LOG] - Novo ativo tangível de locação {} registrado.", newRegister.getId());
		return new TangivelLocacaoDTO(newRegister);
	}
	
	@Transactional
	public TangivelLocacaoDTO update(Long id, TangivelLocacaoDTO dto) {
		TangivelLocacao toUpdate = getTangivelLocacaoObject(id);
		
		dtoToEntity(toUpdate, dto);
		toUpdate = tangivelLocacaoRepository.save(toUpdate);
		
		historicoService.recordOperation("ATUALIZAÇÃO", toUpdate);
		
		LOGGER.info("[LOG] - Ativo tangível de locação {} atualizado.", id);
		return new TangivelLocacaoDTO(toUpdate);
	}
	
	@Transactional
	public TangivelLocacaoDTO update(TangivelLocacao obj) {
		TangivelLocacao ativo = tangivelLocacaoRepository.save(obj);
		historicoService.recordOperation("ATUALIZAÇÃO", ativo);
		return new TangivelLocacaoDTO(ativo);
	}
	
	public byte[] generateQrCode(Long id) {
		Ativo object = getTangivelLocacaoObject(id);
		
		byte[] qrCode = null;
		qrCodeGenerator.clearParams();
		
		qrCodeGenerator.addParam("QR_CODE_PARAM", object.getQrCodeImage());
		
		qrCode = qrCodeGenerator.qrCodeFromJasper();
		return qrCode;
	}
	
	public void delete(Long id) {
		tangivelLocacaoRepository.deleteById(id);
		LOGGER.info("[LOG] - Ativo tangível de locação {} excluído.", id);
	}
	
	private void dtoToEntity(TangivelLocacao entity, TangivelLocacaoDTO dto) {
		Area a = areaRepository.getReferenceById(dto.getArea().getId());
		entity.setArea(a);
		
		Localizacao l = localizacaoRepository.getReferenceById(dto.getLocalizacao().getId());
		entity.setLocalizacao(l);
		
		entity.setCategoria(dto.getCategoria());
		entity.setCodigoSerie(dto.getCodigoSerie());
		entity.setDataAquisicao(dto.getDataAquisicao());
		entity.setDataDevolucaoPrevista(dto.getDataDevolucaoPrevista());
		entity.setDataDevolucaoRealizada(dto.getDataDevolucaoRealizada());
		entity.setDescricao(dto.getDescricao());
		entity.setEstadoConservacao(dto.getEstadoConservacao());
		
		Fornecedor f = fornecedorRepository.getReferenceById(dto.getFornecedor().getId());
		entity.setFornecedor(f);
		
		entity.setIdPatrimonial(dto.getIdPatrimonial());
		entity.setLinkDocumento(dto.getLinkDocumento());
		
		entity.setObservacoes(dto.getObservacoes());
		
		UsuarioResponsavel ur = usuarioResponsavelRepository.getReferenceById(dto.getUsuarioResponsavel().getId());
		entity.setUsuarioResponsavel(ur);
	}
	
	private void gerarQRCode(TangivelLocacao newRegister) {
		String qrCodeUrl = BASE_URL_QR_CODE + newRegister.getId();
		newRegister.setQrCodeUrl(qrCodeUrl);

		try {
			BufferedImage qr = QRCodeGenerator.gerarQRCode(qrCodeUrl);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(qr, "png", baos);
			newRegister.setQrCodeImage(baos.toByteArray());
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		newRegister = tangivelLocacaoRepository.save(newRegister);
	}
	
	private void gerarIdPatrimonial(TangivelLocacaoDTO dto, TangivelLocacao newRegister) {
		if (dto.getGerarIdPatrimonial() == true) {
			newRegister.setGerarIdPatrimonial(true);
		    String idGerado;
		    boolean idJaExiste;

		    do {
		        idGerado = geradorId.generate();
		        idJaExiste = tangivelLocacaoRepository.existsByIdPatrimonial(idGerado);

		    } while (idJaExiste);

		    newRegister.setIdPatrimonial(idGerado);
		}
		
		newRegister = tangivelLocacaoRepository.save(newRegister);
	}
}
