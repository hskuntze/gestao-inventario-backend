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

import br.com.ctcea.gestaoinv.dto.TangivelDTO;
import br.com.ctcea.gestaoinv.entities.gestaoinv.Ativo;
import br.com.ctcea.gestaoinv.entities.gestaoinv.Tangivel;
import br.com.ctcea.gestaoinv.repositories.gestaoinv.TangivelRepository;
import br.com.ctcea.gestaoinv.services.exceptions.RecursoNaoEncontradoException;

@Service
public class TangivelService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TangivelService.class);
	
	private static final String BASE_URL = "http://localhost:3000/gestao-inventario/ativo";

	@Autowired
	private TangivelRepository tangivelRepository;
	
	@Autowired
	private QRCodeGenerator qrCodeGenerator;
	
	@Autowired
	private HistoricoService historicoService;
	
	@Transactional(readOnly = true)
	public Tangivel getTangivelObject(Long id) {
		return tangivelRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível localizar um ativo tangível com ID " + id));
	}
	
	@Transactional(readOnly = true)
	public List<Tangivel> getAll() {
		return tangivelRepository.findAll();
	}
	
	@Transactional
	public Tangivel register(TangivelDTO dto) {
		Tangivel newRegister = new Tangivel();
		
		dtoToEntity(newRegister, dto);
		newRegister = tangivelRepository.save(newRegister);
		
		String qrCodeUrl = BASE_URL + "/tangivel/" + newRegister.getId();
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
		
		historicoService.recordOperation("INSERT", newRegister);
		
		LOGGER.info("[LOG] - Novo ativo tangível {} registrado.", newRegister.getId());
		return newRegister;
	}
	
	@Transactional
	public Tangivel update(Long id, TangivelDTO dto) {
		Tangivel toUpdate = getTangivelObject(id);
		
		dtoToEntity(toUpdate, dto);
		toUpdate = tangivelRepository.save(toUpdate);
		
		historicoService.recordOperation("UPDATE", toUpdate);
		
		LOGGER.info("[LOG] - Ativo tangível {} atualizado.", id);
		return toUpdate;
	}
	
	@Transactional
	public Tangivel update(Tangivel obj) {
		Tangivel ativo = tangivelRepository.save(obj);
		historicoService.recordOperation("UPDATE", ativo);
		return ativo;
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
	
	private void dtoToEntity(Tangivel entity, TangivelDTO dto) {
		entity.setArea(dto.getArea());
		entity.setCategoria(dto.getCategoria());
		entity.setCodigoSerie(dto.getCodigoSerie());
		entity.setDataAquisicao(dto.getDataAquisicao());
		entity.setDescricao(dto.getDescricao());
		entity.setEstadoConservacao(dto.getEstadoConservacao());
		entity.setFornecedor(dto.getFornecedor());
		entity.setIdPatrimonial(dto.getIdPatrimonial());
		entity.setLinkDocumento(dto.getLinkDocumento());
		entity.setLocalizacao(dto.getLocalizacao());
		entity.setObservacoes(dto.getObservacoes());
		entity.setResponsavel(dto.getResponsavel());
		entity.setUsuarioResponsavel(dto.getUsuarioResponsavel());
	}
}
