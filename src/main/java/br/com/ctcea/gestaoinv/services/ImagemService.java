package br.com.ctcea.gestaoinv.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.ctcea.gestaoinv.entities.Ativo;
import br.com.ctcea.gestaoinv.entities.Imagem;
import br.com.ctcea.gestaoinv.entities.Intangivel;
import br.com.ctcea.gestaoinv.entities.Tangivel;
import br.com.ctcea.gestaoinv.entities.TangivelLocacao;
import br.com.ctcea.gestaoinv.enums.TipoAtivo;
import br.com.ctcea.gestaoinv.exceptions.RecursoNaoEncontradoException;
import br.com.ctcea.gestaoinv.exceptions.RequisicaoNaoProcessavelException;
import br.com.ctcea.gestaoinv.repositories.ImagemRepository;

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
		imagem.setTamanho(file.getSize());

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
			tangivelService.update("ARQUIVO INSERIDO", t);
			break;
		case INTANGIVEL:
			Intangivel i = intangivelService.getIntangivelObject(idAtivo);
			associarImagemAoAtivo(i, imagem);
			intangivelService.update("ARQUIVO INSERIDO", i);
			break;
		case TANGIVEL_LOCACAO:
			TangivelLocacao tl = tangivelLocacaoService.getTangivelLocacaoObject(idAtivo);
			associarImagemAoAtivo(tl, imagem);
			tangivelLocacaoService.update("ARQUIVO INSERIDO", tl);
			break;
		default:
			LOGGER.error("[LOG] - Erro ao processar imagem: tipo de ativo não existe.");
			break;
		}
	}

	public void uploadMultiplosArquivos(TipoAtivo tipoAtivo, Long idAtivo, List<MultipartFile> files) {
		Ativo ativo = buscarAtivoGenerico(tipoAtivo, idAtivo);

	    for (MultipartFile file : files) {
	        if (!file.isEmpty()) {
	            Imagem imagem = new Imagem();
	            imagem.setNome(file.getOriginalFilename());
	            imagem.setTamanho(file.getSize());
	            try {
	                imagem.setConteudo(file.getBytes());
	            } catch (IOException e) {
	                throw new RequisicaoNaoProcessavelException("Erro ao processar imagem: " + e.getMessage());
	            }

	            ativo.getImagens().add(imagem);
	            imagem.setAtivo(ativo);
	        }
	    }

	    saveAtivoGenerico(tipoAtivo, ativo);
	}

	public Imagem downloadImagem(Long idImagem) {
		return imagemRepository.findById(idImagem)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Imagem não encontrada com o ID: " + idImagem));
	}
	
	@Transactional
	public void deleteImagem(Long id) {
		Optional<Imagem> imagem = imagemRepository.findById(id);
		if(imagem.isPresent()) {
			Ativo ativo = imagem.get().getAtivo();
			
			if (ativo != null) {
		        ativo.getImagens().remove(imagem.get());
		    }
			
	        imagemRepository.delete(imagem.get());
		}
	}

	private void associarImagemAoAtivo(Ativo ativo, Imagem imagem) {
		imagem.setAtivo(ativo);
		ativo.getImagens().add(imagem);
	}
	
	private Ativo buscarAtivoGenerico(TipoAtivo tipoAtivo, Long idAtivo) {
	    switch (tipoAtivo) {
	        case TANGIVEL:
	            return tangivelService.getTangivelObject(idAtivo);
	        case INTANGIVEL:
	            return intangivelService.getIntangivelObject(idAtivo);
	        case TANGIVEL_LOCACAO:
	            return tangivelLocacaoService.getTangivelLocacaoObject(idAtivo);
	        default:
	            throw new IllegalArgumentException("Tipo de ativo inválido");
	    }
	}
	
	private void saveAtivoGenerico(TipoAtivo tipoAtivo, Ativo ativo) {
		switch (tipoAtivo) {
			case TANGIVEL:
				tangivelService.update("ARQUIVO INSERIDO", (Tangivel) ativo);
				break;
			case INTANGIVEL:
				intangivelService.update("ARQUIVO INSERIDO", (Intangivel) ativo);
				break;
			case TANGIVEL_LOCACAO:
				tangivelLocacaoService.update("ARQUIVO INSERIDO", (TangivelLocacao) ativo);
				break;
			default:
				LOGGER.error("[LOG] - Erro ao processar imagem: tipo de ativo não existe.");
				break;
		}
	}
}
