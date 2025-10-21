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

import br.com.ctcea.gestaoinv.dto.TangivelLocacaoDTO;
import br.com.ctcea.gestaoinv.entities.gestaoinv.TangivelLocacao;
import br.com.ctcea.gestaoinv.repositories.gestaoinv.TangivelLocacaoRepository;
import br.com.ctcea.gestaoinv.services.exceptions.RecursoNaoEncontradoException;

@Service
public class TangivelLocacaoService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TangivelLocacaoService.class);
	
	private static final String BASE_URL = "http://localhost:3000/gestao-inventario/ativo";

	@Autowired
	private TangivelLocacaoRepository tangivelLocacaoRepository;
	
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
	public TangivelLocacao register(TangivelLocacaoDTO dto) {
		TangivelLocacao newRegister = new TangivelLocacao();
		
		dtoToEntity(newRegister, dto);
		newRegister = tangivelLocacaoRepository.save(newRegister);
		
		String qrCodeUrl = BASE_URL + "/locacao/" + newRegister.getId();
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
		
		historicoService.recordOperation("INSERT", newRegister);
		
		LOGGER.info("[LOG] - Novo ativo tangível de locação {} registrado.", newRegister.getId());
		return newRegister;
	}
	
	@Transactional
	public TangivelLocacao update(Long id, TangivelLocacaoDTO dto) {
		TangivelLocacao toUpdate = getTangivelLocacaoObject(id);
		
		dtoToEntity(toUpdate, dto);
		toUpdate = tangivelLocacaoRepository.save(toUpdate);
		
		historicoService.recordOperation("UPDATE", toUpdate);
		
		LOGGER.info("[LOG] - Ativo tangível de locação {} atualizado.", id);
		return toUpdate;
	}
	
	@Transactional
	public TangivelLocacao update(TangivelLocacao obj) {
		TangivelLocacao ativo = tangivelLocacaoRepository.save(obj);
		historicoService.recordOperation("UPDATE", ativo);
		return ativo;
	}
	
	public void delete(Long id) {
		tangivelLocacaoRepository.deleteById(id);
		LOGGER.info("[LOG] - Ativo tangível de locação {} excluído.", id);
	}
	
	private void dtoToEntity(TangivelLocacao entity, TangivelLocacaoDTO dto) {
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
