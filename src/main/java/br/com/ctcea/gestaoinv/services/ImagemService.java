package br.com.ctcea.gestaoinv.services;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.ctcea.gestaoinv.entities.gestaoinv.Ativo;
import br.com.ctcea.gestaoinv.entities.gestaoinv.Imagem;
import br.com.ctcea.gestaoinv.entities.gestaoinv.Intangivel;
import br.com.ctcea.gestaoinv.entities.gestaoinv.Tangivel;
import br.com.ctcea.gestaoinv.entities.gestaoinv.TangivelLocacao;
import br.com.ctcea.gestaoinv.enums.TipoAtivo;
import br.com.ctcea.gestaoinv.repositories.gestaoinv.ImagemRepository;
import br.com.ctcea.gestaoinv.services.exceptions.RecursoNaoEncontradoException;
import br.com.ctcea.gestaoinv.services.exceptions.RequisicaoNaoProcessavelException;

@Service
public class ImagemService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImagemService.class);

	@Autowired
	private ImagemRepository imagemRepository;

	@Autowired
	private TangivelService tangivelService;

	@Autowired
	private IntangivelService intangivelService;

	@Autowired
	private TangivelLocacaoService tangivelLocacaoService;

	public void uploadImagem(TipoAtivo tipoAtivo, Long idAtivo, MultipartFile file) {
		if (file.isEmpty()) {
			throw new RequisicaoNaoProcessavelException("O arquivo está vazio.");
		}

		Imagem imagem = new Imagem();
		imagem.setNome(file.getOriginalFilename());

		try {
			imagem.setConteudo(file.getBytes());
		} catch (IOException e) {
			LOGGER.error("[LOG] - Erro ao processar arquivo '{}': {}", file.getOriginalFilename(), e.getMessage());
			throw new RequisicaoNaoProcessavelException("Erro ao tentar processar a imagem: " + e.getMessage());
		}

		switch (tipoAtivo) {
		case TANGIVEL:
			Tangivel t = tangivelService.getTangivelObject(idAtivo);
			associarImagemAoAtivo(t, imagem);
			tangivelService.update(t);
			break;
		case INTANGIVEL:
			Intangivel i = intangivelService.getIntangivelObject(idAtivo);
			associarImagemAoAtivo(i, imagem);
			intangivelService.update(i);
			break;
		case TANGIVEL_LOCACAO:
			TangivelLocacao tl = tangivelLocacaoService.getTangivelLocacaoObject(idAtivo);
			associarImagemAoAtivo(tl, imagem);
			tangivelLocacaoService.update(tl);
			break;
		default:
			LOGGER.error("[LOG] - Erro ao processar imagem: tipo de ativo não existe.");
			break;
		}
	}

	public Imagem downloadImagem(Long idImagem) {
		return imagemRepository.findById(idImagem)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Imagem não encontrada com o ID: " + idImagem));
	}

	private void associarImagemAoAtivo(Ativo ativo, Imagem imagem) {
		imagem.setAtivo(ativo);
		ativo.getImagens().add(imagem);
	}
}
