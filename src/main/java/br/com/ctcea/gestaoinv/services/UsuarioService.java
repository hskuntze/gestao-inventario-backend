package br.com.ctcea.gestaoinv.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ctcea.gestaoinv.dto.UsuarioDTO;
import br.com.ctcea.gestaoinv.dto.UsuarioRegistroDTO;
import br.com.ctcea.gestaoinv.dto.UsuarioSimpleDTO;
import br.com.ctcea.gestaoinv.entities.Perfil;
import br.com.ctcea.gestaoinv.entities.Usuario;
import br.com.ctcea.gestaoinv.repositories.PerfilRepository;
import br.com.ctcea.gestaoinv.repositories.UsuarioRepository;
import br.com.ctcea.gestaoinv.services.exceptions.RecursoExistenteException;
import br.com.ctcea.gestaoinv.services.exceptions.RecursoNaoEncontradoException;
import br.com.ctcea.gestaoinv.services.exceptions.RequisicaoNaoProcessavelException;

@Service
public class UsuarioService implements UserDetailsService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Transactional(readOnly = true)
	public Usuario getAuthenticatedUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = usuarioRepository.findByLogin(auth.getName()).orElseThrow(() -> new RecursoNaoEncontradoException("Erro ao tentar resgatar usuário logado"));
		return usuario;
	}
	
	@Transactional(readOnly = true)
	public UsuarioSimpleDTO getAuthenticatedUserInfo() {
		Usuario user = getAuthenticatedUser();
		return new UsuarioSimpleDTO(user);
	}
 	
	@Transactional(readOnly = true)
	public UsuarioDTO getById(Long id) {
		Usuario u = usuarioRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível localizar um usuário com ID " + id));
		return new UsuarioDTO(u);
	}
	
	@Transactional(readOnly = true)
	public UsuarioDTO getByLogin(String login) {
		Usuario u = usuarioRepository.findByLogin(login).orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível localizar um usuário com login " + login));
		return new UsuarioDTO(u); 
	}
	
	@Transactional(readOnly = true)
	public List<UsuarioDTO> getAll() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		return usuarios.stream().map(u -> new UsuarioDTO(u)).collect(Collectors.toList());
	}
	
	@Transactional
	public UsuarioDTO registrar(UsuarioRegistroDTO dto) {
		if(dto != null) {
			Optional<Usuario> aux = usuarioRepository.findByLogin(dto.getLogin());
			
			if(aux.isEmpty()) {
				Usuario usuario = new Usuario();
				
				dtoParaEntidade(usuario, dto);
				usuario.setUserUuid(UUID.randomUUID().toString());
				usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
				usuario = usuarioRepository.save(usuario);
				
				return new UsuarioDTO(usuario);
			} else {
				throw new RecursoExistenteException("Já existe um usuário com este login");
			}
		} else {
			throw new RequisicaoNaoProcessavelException("Argumento nulo. Requisição improcessável.");
		}
	}
	
	@Transactional
	public UsuarioDTO atualizar(Long id, UsuarioDTO dto) {
		Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Usuário com ID " + id + " não foi encontrado"));
		dtoParaEntidade(usuario, dto);
		return new UsuarioDTO(usuario);
	}
	
	@Transactional
	public void trocarSenhaDoUsuario(String novaSenha, String senhaAntiga) {
		if(novaSenha != null && senhaAntiga != null) {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Optional<Usuario> usuario = usuarioRepository.findByLogin(username);
			
			if(!validarSenhaAntiga(usuario.get(), senhaAntiga)) {
				throw new RequisicaoNaoProcessavelException("Senha inválida.");
			}
			
			Usuario entity = usuario.get();
			entity.setPassword(passwordEncoder.encode(novaSenha));
			usuarioRepository.save(entity);
		} else {
			throw new RequisicaoNaoProcessavelException("Requisição improcessável. Nova senha e/ou senha antiga estão vazias ou nulas.");
		}
	}
	
	@Transactional
	public void trocarSenhaDoUsuarioPorAdmin(Long userId, String novaSenha) {
		LOGGER.info(">>> TROCA DE SENHA");
		if(novaSenha != null && userId != null) {
			LOGGER.info(">>> TROCA DE SENHA - usuario e senha presentes");
			String usernameLogado = SecurityContextHolder.getContext().getAuthentication().getName();
			Usuario usuarioLogado = usuarioRepository.findByLogin(usernameLogado).get();
			
			boolean isAdmin = usuarioLogado.getPerfis().stream()
				    .anyMatch(perfil -> "PERFIL_ADMIN".equals(perfil.getAutorizacao()));
			
			if(isAdmin) {
				Usuario usuario = usuarioRepository.findById(userId).orElseThrow(() -> new RecursoNaoEncontradoException("Usuário com ID " + userId + " não foi localizado."));
				
				usuario.setPassword(passwordEncoder.encode(novaSenha));
				usuarioRepository.save(usuario);
			} else {
				throw new RequisicaoNaoProcessavelException("Acesso negado! Você precisa ser administrador para realizar essa ação. Registrando IP em log.");
			}
		} else {
			throw new RequisicaoNaoProcessavelException("Requisição improcessável. Nome de usuário e/ou senha estão vazias ou nulas.");
		}
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> usuario = usuarioRepository.findByLogin(username);
		if(usuario.isEmpty()) {
			throw new RecursoNaoEncontradoException("Usuario " + username + " não foi encontrado.");
		}
		
		return usuario.get();
	}
	
	private void dtoParaEntidade(Usuario usuario, UsuarioDTO dto) {
		usuario.setNome(dto.getNome());
		usuario.setEmail(dto.getEmail());
		usuario.setLogin(dto.getLogin());
		usuario.setTermoParceria(dto.getTermoParceria());
		
		usuario.getPerfis().clear();
		dto.getPerfis().forEach(perfil -> {
			Perfil p = perfilRepository.getReferenceById(perfil.getId());
			usuario.getPerfis().add(p);
		});
	}
	
	private boolean validarSenhaAntiga(Usuario usuario, String senhaAntiga) {
		return passwordEncoder.matches(senhaAntiga, usuario.getPassword());
	}
}