package br.com.ctcea.gestaoinv.controllers;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.ctcea.gestaoinv.dto.TangivelDTO;
import br.com.ctcea.gestaoinv.entities.Tangivel;
import br.com.ctcea.gestaoinv.services.TangivelService;

@RestController
@RequestMapping(value = "/tangiveis")
public class TangivelController {

	@Autowired
	private TangivelService tangivelService;

	@GetMapping(value = "/all")
	public ResponseEntity<List<Tangivel>> getAll() {
		return ResponseEntity.ok().body(tangivelService.getAll());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Tangivel> getObject(@PathVariable Long id) {
		return ResponseEntity.ok().body(tangivelService.getTangivelObject(id));
	}
	
	@GetMapping(value = "/dto/{id}")
	public ResponseEntity<TangivelDTO> getDto(@PathVariable Long id) {
		return ResponseEntity.ok().body(tangivelService.getDto(id));
	}

	@GetMapping(value = "/qrcode/{id}")
	public ResponseEntity<byte[]> generateQrCode(@PathVariable Long id, HttpServletResponse response) throws IOException {
		byte[] pdf;

		pdf = tangivelService.generateQrCode(id);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDisposition(ContentDisposition.builder("attachment").filename("qr-code.pdf").build());
		headers.setContentLength(pdf.length);
		response.getOutputStream().write(pdf);
		response.getOutputStream().flush();
		response.getOutputStream().close();
		return new ResponseEntity<>(headers, HttpStatus.OK);
	}

	@PostMapping(value = "/registrar")
	public ResponseEntity<TangivelDTO> register(@RequestBody TangivelDTO dto) {
		TangivelDTO t = tangivelService.register(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(t.getId()).toUri();
		return ResponseEntity.created(uri).body(t);
	}
	
	@PostMapping(value = "/importar-excel")
	public ResponseEntity<Void> importar(@RequestParam MultipartFile file) throws IOException {
		tangivelService.importarExcel(file);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/atualizar/{id}")
	public ResponseEntity<TangivelDTO> atualizar(@PathVariable Long id, @RequestBody TangivelDTO dto) {
		TangivelDTO t = tangivelService.update(id, dto);
		return ResponseEntity.ok().body(t);
	}

	@DeleteMapping(value = "/excluir/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		tangivelService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
