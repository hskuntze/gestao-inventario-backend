package br.com.ctcea.gestaoinv.controllers;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.ctcea.gestaoinv.dto.TrocaSenhaRequestDTO;
import br.com.ctcea.gestaoinv.dto.UsuarioDTO;
import br.com.ctcea.gestaoinv.dto.UsuarioRegistroDTO;
import br.com.ctcea.gestaoinv.services.UsuarioService;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UsuarioDTO> getById(@PathVariable Long id) {
		return ResponseEntity.ok().body(usuarioService.getById(id));
	}
	
	@GetMapping(value = "/login/{login}")
	public ResponseEntity<UsuarioDTO> getByLogin(@PathVariable String login) {
		return ResponseEntity.ok().body(usuarioService.getByLogin(login));
	}
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<UsuarioDTO>> getAll() {
		return ResponseEntity.ok().body(usuarioService.getAll());
	}
	
	/**
	 * Endpoint para registrar um usuário na aplicação
	 * @param dto
	 * @param request
	 * @param errors
	 * @return UsuarioDTO
	 */
	@PostMapping(value = "/registrar")
	public ResponseEntity<UsuarioDTO> registrar(@RequestBody UsuarioRegistroDTO dto, HttpServletRequest request, Errors errors) {
		UsuarioDTO usuario = usuarioService.registrar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuario.getId()).toUri();
		
		return ResponseEntity.created(uri).body(usuario);
	}
	
	/**
	 * Endpoint que invoca as funções para realizar a troca da senha do usuário
	 * @param dto
	 * @return
	 */
	@PostMapping(value = "/salvarTrocaDeSenha")
	public ResponseEntity<Void> salvarTrocaDeSenha(@RequestParam String novaSenha, @RequestParam String senhaAntiga) {
		usuarioService.trocarSenhaDoUsuario(novaSenha, senhaAntiga);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping(value = "/admin/trocarSenhaUsuario")
	public ResponseEntity<Void> trocarSenhaDoUsuarioPorAdmin(@RequestBody TrocaSenhaRequestDTO request) {
		usuarioService.trocarSenhaDoUsuarioPorAdmin(request.getUserId(), request.getNovaSenha());
		return ResponseEntity.ok().build();
	}
	
	/**
	 * Endpoint que atualiza o usuário
	 * @param id
	 * @param dto
	 * @return
	 */
	@PutMapping(value = "/atualizar/{id}")
	public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
		UsuarioDTO atualizado = usuarioService.atualizar(id, dto);
		return ResponseEntity.ok().body(atualizado);
	}
}
