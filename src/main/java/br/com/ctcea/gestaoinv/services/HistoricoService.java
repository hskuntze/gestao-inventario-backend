package br.com.ctcea.gestaoinv.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ctcea.gestaoinv.dto.HistoricoDTO;
import br.com.ctcea.gestaoinv.entities.Ativo;
import br.com.ctcea.gestaoinv.entities.Historico;
import br.com.ctcea.gestaoinv.entities.Usuario;
import br.com.ctcea.gestaoinv.repositories.HistoricoRepository;

@Service
public class HistoricoService {

	@Autowired
	private HistoricoRepository historicoRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Transactional(readOnly = true)
	public List<Historico> getAll() {
		return historicoRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<HistoricoDTO> getAllDto() {
		List<Historico> all = historicoRepository.findAll();
		return all.stream().map(h -> new HistoricoDTO(h)).collect(Collectors.toList());
	}
	
	@Transactional
	public List<HistoricoDTO> getHistoricoByAtivoId(Long id) {
		List<Historico> list = historicoRepository.getHistoricoByAtivoId(id);
		return list.stream().map(h -> new HistoricoDTO(h)).collect(Collectors.toList());
	}
	
	@Transactional
	public void recordOperation(String operation, Ativo ativo) {
		Usuario usuario = usuarioService.getAuthenticatedUser();
		
		Historico historico = new Historico();
		historico.setOperacao(operation);
		historico.setAtivo(ativo);
		historico.setUserId(Long.valueOf(usuario.getId()));
		historico.setUserLogin(usuario.getLogin());
		historico.setArea(ativo.getArea().getNome());
		historico.setLocalizacao(ativo.getLocalizacao().getNome());
		historico.setUsuarioResponsavel(ativo.getUsuarioResponsavel().getNome());
		
		historicoRepository.save(historico);
	}
}
