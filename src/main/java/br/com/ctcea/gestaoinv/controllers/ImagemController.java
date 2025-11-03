package br.com.ctcea.gestaoinv.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.ctcea.gestaoinv.entities.Imagem;
import br.com.ctcea.gestaoinv.enums.TipoAtivo;
import br.com.ctcea.gestaoinv.services.ImagemService;

@RestController
@RequestMapping(value = "/imagens")
public class ImagemController {

	@Autowired
	private ImagemService imagemService;

	@GetMapping("/download/{idImagem}")
	public ResponseEntity<byte[]> downloadImagem(@PathVariable Long idImagem) {
		Imagem imagem = imagemService.downloadImagem(idImagem);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(detectarMediaType(imagem.getNome()));
		headers.setContentDispositionFormData("attachment", imagem.getNome());

		return ResponseEntity.ok().headers(headers).body(imagem.getConteudo());
	}

	@PostMapping(value = "/upload")
	public ResponseEntity<String> uploadImagem(@RequestParam TipoAtivo tipoAtivo, @RequestParam Long idAtivo,
			@RequestParam MultipartFile file) {
		imagemService.uploadImagem(tipoAtivo, idAtivo, file);
		return ResponseEntity.ok().body("Sucesso");
	}

	private MediaType detectarMediaType(String nomeArquivo) {
		String nome = nomeArquivo.toLowerCase();
		if (nome.endsWith(".png"))
			return MediaType.IMAGE_PNG;
		if (nome.endsWith(".jpg") || nome.endsWith(".jpeg"))
			return MediaType.IMAGE_JPEG;
		if (nome.endsWith(".gif"))
			return MediaType.IMAGE_GIF;
		return MediaType.APPLICATION_OCTET_STREAM; // genérico
	}
}
