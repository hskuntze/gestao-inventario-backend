package br.com.ctcea.gestaoinv.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ctcea.gestaoinv.entities.Notificacao;
import br.com.ctcea.gestaoinv.services.NotificacaoService;

@RestController
@RequestMapping(value = "/notificacoes")
public class NotificacaoController {

	@Autowired
	private NotificacaoService notificacaoService;
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<Notificacao>> getAll() {
		return ResponseEntity.ok().body(notificacaoService.getAll());
	}
	
	@PostMapping(value = "/update/read/{id}")
	public ResponseEntity<Void> makeNotificationAsRead(@PathVariable Long id) {
		notificacaoService.makeNotificationAsRead(id);
		return ResponseEntity.noContent().build();
	}
}
