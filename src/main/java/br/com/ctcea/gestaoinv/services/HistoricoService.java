package br.com.ctcea.gestaoinv.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ctcea.gestaoinv.components.TenantFilterInterceptor;
import br.com.ctcea.gestaoinv.dto.AtivoDTO;
import br.com.ctcea.gestaoinv.dto.HistoricoDTO;
import br.com.ctcea.gestaoinv.entities.Ativo;
import br.com.ctcea.gestaoinv.entities.Historico;
import br.com.ctcea.gestaoinv.entities.Usuario;
import br.com.ctcea.gestaoinv.repositories.HistoricoRepository;

@Service
public class HistoricoService {
	
	@Autowired
	private TenantFilterInterceptor filterInterceptor;

	@Autowired
	private HistoricoRepository historicoRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Transactional(readOnly = true)
	public List<Historico> getAll() {
		filterInterceptor.applyFilter();
		
		return historicoRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<HistoricoDTO> getAllDto() {
		filterInterceptor.applyFilter();
		
		List<Historico> all = historicoRepository.findAll();
		return all.stream().map(h -> new HistoricoDTO(h)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<AtivoDTO> getAtivosRecentes() {
		filterInterceptor.applyFilter();
		
		List<Ativo> all = historicoRepository.getAtivosRecentes();
		return all.stream().map(a -> new AtivoDTO(a)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public List<HistoricoDTO> getHistoricoByAtivoId(Long id) {
		filterInterceptor.applyFilter();
		
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
		historico.setTermoParceria(usuario.getTermoParceria());
		
		historicoRepository.save(historico);
	}
}
