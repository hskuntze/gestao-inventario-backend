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

import br.com.ctcea.gestaoinv.dto.IntangivelDTO;
import br.com.ctcea.gestaoinv.entities.gestaoinv.Ativo;
import br.com.ctcea.gestaoinv.entities.gestaoinv.Intangivel;
import br.com.ctcea.gestaoinv.repositories.gestaoinv.IntangivelRepository;
import br.com.ctcea.gestaoinv.services.exceptions.RecursoNaoEncontradoException;

@Service
public class IntangivelService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IntangivelService.class);
	
	private static final String BASE_URL = "http://localhost:3000/gestao-inventario/ativo";

	@Autowired
	private IntangivelRepository intangivelRepository;
	
	@Autowired
	private QRCodeGenerator qrCodeGenerator;
	
	@Autowired
	private HistoricoService historicoService;
	
	@Transactional(readOnly = true)
	public Intangivel getIntangivelObject(Long id) {
		return intangivelRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível localizar um ativo intangível com ID " + id));
	}
	
	@Transactional(readOnly = true)
	public List<Intangivel> getAll() {
		return intangivelRepository.findAll();
	}
	
	@Transactional
	public Intangivel register(IntangivelDTO dto) {
		Intangivel newRegister = new Intangivel();
		
		dtoToEntity(newRegister, dto);
		newRegister = intangivelRepository.save(newRegister);
		
		String qrCodeUrl = BASE_URL + "/intangivel/" + newRegister.getId();
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
		
		historicoService.recordOperation("INSERT", newRegister);
		
		LOGGER.info("[LOG] - Novo ativo intangível {} registrado.", newRegister.getId());
		return newRegister;
	}
	
	@Transactional
	public Intangivel update(Long id, IntangivelDTO dto) {
		Intangivel toUpdate = getIntangivelObject(id);
		
		dtoToEntity(toUpdate, dto);
		toUpdate = intangivelRepository.save(toUpdate);
		
		historicoService.recordOperation("UPDATE", toUpdate);
		
		LOGGER.info("[LOG] - Ativo intangível {} atualizado.", id);
		return toUpdate;
	}
	
	@Transactional
	public Intangivel update(Intangivel obj) {
		Intangivel ativo = intangivelRepository.save(obj);
		historicoService.recordOperation("UPDATE", ativo);
		return ativo;
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
		entity.setArea(dto.getArea());
		entity.setCategoria(dto.getCategoria());
		entity.setCodigoSerie(dto.getCodigoSerie());
		entity.setDataAquisicao(dto.getDataAquisicao());
		entity.setDescricao(dto.getDescricao());
		entity.setFornecedor(dto.getFornecedor());
		entity.setIdPatrimonial(dto.getIdPatrimonial());
		entity.setLinkDocumento(dto.getLinkDocumento());
		entity.setLocalizacao(dto.getLocalizacao());
		entity.setObservacoes(dto.getObservacoes());
		entity.setResponsavel(dto.getResponsavel());
		entity.setUsuarioResponsavel(dto.getUsuarioResponsavel());
	}
}
