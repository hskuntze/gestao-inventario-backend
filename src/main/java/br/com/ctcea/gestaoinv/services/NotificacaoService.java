package br.com.ctcea.gestaoinv.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ctcea.gestaoinv.entities.Notificacao;
import br.com.ctcea.gestaoinv.exceptions.RecursoNaoEncontradoException;
import br.com.ctcea.gestaoinv.repositories.NotificacaoRepository;

@Service
public class NotificacaoService {

	@Autowired
	private NotificacaoRepository notificacaoRepository;
	
	@Transactional(readOnly = true)
	public List<Notificacao> getAll() {
		return notificacaoRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Notificacao getObjectByAtivoId(Long idAtivo) {
		return notificacaoRepository.getByAtivoId(idAtivo).orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível localizar uma notificação com ID " + idAtivo));
	}
	
	@Transactional(readOnly = true)
	public Notificacao getObjectById(Long id) {
		return notificacaoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível localizar uma notificação com ID " + id));
	}
	
	@Transactional
	public void saveObject(Notificacao n) {
		notificacaoRepository.save(n);
	}
	
	@Transactional
	public void makeNotificationAsRead(Long id) {
		Notificacao n = getObjectById(id);
		n.setLida(true);
		saveObject(n);
	}
}