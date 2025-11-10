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
import br.com.ctcea.gestaoinv.dto.TangivelDTO;
import br.com.ctcea.gestaoinv.entities.Area;
import br.com.ctcea.gestaoinv.entities.Ativo;
import br.com.ctcea.gestaoinv.entities.Contrato;
import br.com.ctcea.gestaoinv.entities.Fornecedor;
import br.com.ctcea.gestaoinv.entities.Localizacao;
import br.com.ctcea.gestaoinv.entities.Tangivel;
import br.com.ctcea.gestaoinv.entities.Usuario;
import br.com.ctcea.gestaoinv.entities.UsuarioResponsavel;
import br.com.ctcea.gestaoinv.exceptions.RecursoNaoEncontradoException;
import br.com.ctcea.gestaoinv.repositories.AreaRepository;
import br.com.ctcea.gestaoinv.repositories.ContratoRepository;
import br.com.ctcea.gestaoinv.repositories.FornecedorRepository;
import br.com.ctcea.gestaoinv.repositories.LocalizacaoRepository;
import br.com.ctcea.gestaoinv.repositories.TangivelRepository;
import br.com.ctcea.gestaoinv.repositories.UsuarioResponsavelRepository;

@Service
public class TangivelService {
	
	private GeradorIDPatrimonial geradorId;
	
	public TangivelService(GeradorIDPatrimonial gerador) {
		this.geradorId = gerador;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(TangivelService.class);
	private static final String BASE_URL_QR_CODE = "inventario://patrimonio/";

	@Autowired
	private TangivelRepository tangivelRepository;
	
	@Autowired
	private LocalizacaoRepository localizacaoRepository;

	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	@Autowired
	private ContratoRepository contratoRepository;

	@Autowired
	private UsuarioResponsavelRepository usuarioResponsavelRepository;

	@Autowired
	private AreaRepository areaRepository;

	@Autowired
	private QRCodeGenerator qrCodeGenerator;

	@Autowired
	private HistoricoService historicoService;
	
	@Autowired
	private UsuarioService usuarioService;

	@Transactional(readOnly = true)
	public Tangivel getTangivelObject(Long id) {
		return tangivelRepository.findById(id).orElseThrow(
				() -> new RecursoNaoEncontradoException("Não foi possível localizar um ativo tangível com ID " + id));
	}
	
	@Transactional(readOnly = true)
	public TangivelDTO getDto(Long id) {
		Tangivel t = tangivelRepository.findById(id).orElseThrow(
				() -> new RecursoNaoEncontradoException("Não foi possível localizar um ativo tangível com ID " + id));
		return new TangivelDTO(t);
	}

	@Transactional(readOnly = true)
	public List<Tangivel> getAll() {
		return tangivelRepository.findAll();
	}

	@Transactional
	public TangivelDTO register(TangivelDTO dto) {
		Tangivel newRegister = new Tangivel();

		dtoToEntity(newRegister, dto);
		gerarIdPatrimonial(dto, newRegister);
		gerarQRCode(newRegister);

		historicoService.recordOperation("REGISTRO", newRegister);

		if (newRegister.getUsuarioResponsavel().getId() != null
				|| !newRegister.getUsuarioResponsavel().getNome().equals("N/A")
				|| !newRegister.getUsuarioResponsavel().getNome().equals("Sem usuário")) {
			historicoService.recordOperation("ATRIBUIÇÃO", newRegister);
		}

		LOGGER.info("[LOG] - Novo ativo tangível {} registrado.", newRegister.getId());
		return new TangivelDTO(newRegister);
	}

	@Transactional
	public TangivelDTO update(Long id, TangivelDTO dto) {
		Tangivel toUpdate = getTangivelObject(id);

		dtoToEntity(toUpdate, dto);
		toUpdate = tangivelRepository.save(toUpdate);

		historicoService.recordOperation("ATUALIZAÇÃO", toUpdate);

		LOGGER.info("[LOG] - Ativo tangível {} atualizado.", id);
		return new TangivelDTO(toUpdate);
	}

	@Transactional
	public TangivelDTO update(Tangivel obj) {
		Tangivel ativo = tangivelRepository.save(obj);
		historicoService.recordOperation("ATUALIZAÇÃO", ativo);
		return new TangivelDTO(ativo);
	}

	public byte[] generateQrCode(Long id) {
		Ativo object = getTangivelObject(id);

		byte[] qrCode = null;
		qrCodeGenerator.clearParams();

		qrCodeGenerator.addParam("QR_CODE_PARAM", object.getQrCodeImage());

		qrCode = qrCodeGenerator.qrCodeFromJasper();
		return qrCode;
	}

	public void delete(Long id) {
		tangivelRepository.deleteById(id);
		LOGGER.info("[LOG] - Ativo tangível {} excluído.", id);
	}
	
	private void setTermoParceria(Tangivel entity, TangivelDTO dto) {
		Usuario usuario = usuarioService.getAuthenticatedUser();
		if(usuario.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("PERFIL_ADMIN"))) {
			entity.setTermoParceria(dto.getTermoParceria());
		} else {
			entity.setTermoParceria(usuario.getTermoParceria());
		}
	}

	private void dtoToEntity(Tangivel entity, TangivelDTO dto) {
		setTermoParceria(entity, dto);
		
		Area a = areaRepository.getReferenceById(dto.getArea().getId());
		entity.setArea(a);
		
		Localizacao l = localizacaoRepository.getReferenceById(dto.getLocalizacao().getId());
		entity.setLocalizacao(l);
		
		entity.setCategoria(dto.getCategoria());
		entity.setTermoParceria(dto.getTermoParceria());
		entity.setCodigoSerie(dto.getCodigoSerie());
		entity.setDataAquisicao(dto.getDataAquisicao());
		entity.setDescricao(dto.getDescricao());
		entity.setEstadoConservacao(dto.getEstadoConservacao());

		Fornecedor f = fornecedorRepository.getReferenceById(dto.getFornecedor().getId());
		entity.setFornecedor(f);
		
		if(dto.getContrato().getId() != null) {
			Contrato c = contratoRepository.getReferenceById(dto.getContrato().getId());
			entity.setContrato(c);
		} else {
			entity.setContrato(null);
		}

		entity.setIdPatrimonial(dto.getIdPatrimonial());
		entity.setLinkDocumento(dto.getLinkDocumento());

		entity.setObservacoes(dto.getObservacoes());

		UsuarioResponsavel ur = usuarioResponsavelRepository.getReferenceById(dto.getUsuarioResponsavel().getId());
		entity.setUsuarioResponsavel(ur);
	}
	
	private void gerarQRCode(Tangivel newRegister) {
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

		newRegister = tangivelRepository.save(newRegister);
	}
	
	private void gerarIdPatrimonial(TangivelDTO dto, Tangivel newRegister) {
		if (dto.getGerarIdPatrimonial() == true) {
			newRegister.setGerarIdPatrimonial(true);
		    String idGerado;
		    boolean idJaExiste;

		    do {
		        idGerado = geradorId.generate();
		        idJaExiste = tangivelRepository.existsByIdPatrimonial(idGerado);

		    } while (idJaExiste);

		    newRegister.setIdPatrimonial(idGerado);
		}
		
		newRegister = tangivelRepository.save(newRegister);
	}
}
