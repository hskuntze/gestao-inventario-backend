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
import br.com.ctcea.gestaoinv.dto.IntangivelDTO;
import br.com.ctcea.gestaoinv.entities.Area;
import br.com.ctcea.gestaoinv.entities.Ativo;
import br.com.ctcea.gestaoinv.entities.Fornecedor;
import br.com.ctcea.gestaoinv.entities.Intangivel;
import br.com.ctcea.gestaoinv.entities.Localizacao;
import br.com.ctcea.gestaoinv.entities.UsuarioResponsavel;
import br.com.ctcea.gestaoinv.exceptions.RecursoNaoEncontradoException;
import br.com.ctcea.gestaoinv.repositories.AreaRepository;
import br.com.ctcea.gestaoinv.repositories.FornecedorRepository;
import br.com.ctcea.gestaoinv.repositories.IntangivelRepository;
import br.com.ctcea.gestaoinv.repositories.LocalizacaoRepository;
import br.com.ctcea.gestaoinv.repositories.UsuarioResponsavelRepository;

@Service
public class IntangivelService {

	private GeradorIDPatrimonial geradorId;

	public IntangivelService(GeradorIDPatrimonial gerador) {
		this.geradorId = gerador;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(IntangivelService.class);
	private static final String BASE_URL_QR_CODE = "inventario://patrimonio/";

	@Autowired
	private IntangivelRepository intangivelRepository;

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
	public Intangivel getIntangivelObject(Long id) {
		return intangivelRepository.findById(id).orElseThrow(
				() -> new RecursoNaoEncontradoException("Não foi possível localizar um ativo intangível com ID " + id));
	}

	@Transactional(readOnly = true)
	public List<Intangivel> getAll() {
		return intangivelRepository.findAll();
	}

	@Transactional
	public IntangivelDTO register(IntangivelDTO dto) {
		Intangivel newRegister = new Intangivel();

		dtoToEntity(newRegister, dto);
		gerarIdPatrimonial(dto, newRegister);
		gerarQRCode(newRegister);

		historicoService.recordOperation("REGISTRO", newRegister);

		if (newRegister.getUsuarioResponsavel().getId() != null
				|| !newRegister.getUsuarioResponsavel().getNome().equals("N/A")
				|| !newRegister.getUsuarioResponsavel().getNome().equals("Sem usuário")) {
			historicoService.recordOperation("ATRIBUIÇÃO", newRegister);
		}

		LOGGER.info("[LOG] - Novo ativo intangível {} registrado.", newRegister.getId());
		return new IntangivelDTO(newRegister);
	}

	@Transactional
	public IntangivelDTO update(Long id, IntangivelDTO dto) {
		Intangivel toUpdate = getIntangivelObject(id);

		dtoToEntity(toUpdate, dto);
		toUpdate = intangivelRepository.save(toUpdate);

		historicoService.recordOperation("ATUALIZAÇÃO", toUpdate);

		LOGGER.info("[LOG] - Ativo intangível {} atualizado.", id);
		return new IntangivelDTO(toUpdate);
	}

	@Transactional
	public IntangivelDTO update(Intangivel obj) {
		Intangivel ativo = intangivelRepository.save(obj);
		historicoService.recordOperation("ATUALIZAÇÃO", ativo);
		return new IntangivelDTO(ativo);
	}

	public byte[] generateQrCode(Long id) {
		Ativo object = getIntangivelObject(id);

		byte[] qrCode = null;
		qrCodeGenerator.clearParams();

		qrCodeGenerator.addParam("QR_CODE_PARAM", object.getQrCodeImage());

		qrCode = qrCodeGenerator.qrCodeFromJasper();
		return qrCode;
	}

	public void delete(Long id) {
		intangivelRepository.deleteById(id);
		LOGGER.info("[LOG] - Ativo intangível {} excluído.", id);
	}

	private void dtoToEntity(Intangivel entity, IntangivelDTO dto) {
		Area a = areaRepository.getReferenceById(dto.getArea().getId());
		entity.setArea(a);
		
		Localizacao l = localizacaoRepository.getReferenceById(dto.getLocalizacao().getId());
		entity.setLocalizacao(l);

		entity.setCategoria(dto.getCategoria());
		entity.setCodigoSerie(dto.getCodigoSerie());
		entity.setDataAquisicao(dto.getDataAquisicao());
		entity.setDescricao(dto.getDescricao());

		Fornecedor f = fornecedorRepository.getReferenceById(dto.getFornecedor().getId());
		entity.setFornecedor(f);

		entity.setIdPatrimonial(dto.getIdPatrimonial());
		entity.setLinkDocumento(dto.getLinkDocumento());

		entity.setObservacoes(dto.getObservacoes());

		UsuarioResponsavel ur = usuarioResponsavelRepository.getReferenceById(dto.getUsuarioResponsavel().getId());
		entity.setUsuarioResponsavel(ur);
	}
	
	private void gerarQRCode(Intangivel newRegister) {
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

		newRegister = intangivelRepository.save(newRegister);
	}
	
	private void gerarIdPatrimonial(IntangivelDTO dto, Intangivel newRegister) {
		if (dto.getGerarIdPatrimonial() == true) {
			newRegister.setGerarIdPatrimonial(true);
		    String idGerado;
		    boolean idJaExiste;

		    do {
		        idGerado = geradorId.generate();
		        idJaExiste = intangivelRepository.existsByIdPatrimonial(idGerado);

		    } while (idJaExiste);

		    newRegister.setIdPatrimonial(idGerado);
		}
		
		newRegister = intangivelRepository.save(newRegister);
	}
}
