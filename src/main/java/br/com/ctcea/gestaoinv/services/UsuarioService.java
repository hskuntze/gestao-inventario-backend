package br.com.ctcea.gestaoinv.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ctcea.gestaoinv.dto.UsuarioDTO;
import br.com.ctcea.gestaoinv.entities.safe.Usuario;
import br.com.ctcea.gestaoinv.repositories.safe.UsuarioRepository;
import br.com.ctcea.gestaoinv.services.exceptions.RecursoNaoEncontradoException;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
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
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByLogin(username).orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível localizar o usuário " + username));
		List<GrantedAuthority> authorities = usuario.getAuthorities().stream().map(p -> new SimpleGrantedAuthority(p.toString())).collect(Collectors.toList());
		
		return new User(usuario.getLogin(), usuario.getPassword(), authorities);
	}
}
