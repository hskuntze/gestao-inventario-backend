package br.com.ctcea.gestaoinv.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ctcea.gestaoinv.logs.LogAcesso;
import br.com.ctcea.gestaoinv.repositories.LogAcessoRepository;

@Service
public class LogAcessoService {

	@Autowired
	private LogAcessoRepository logRepository;
	
	@Transactional(readOnly = true)
	public List<LogAcesso> getAll() {
		return logRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public LogAcesso getById(Long id) {
		return logRepository.findById(id).get();
	}
	
	@Transactional
	public void registrarAcesso(String username, String ip, String mensagem) {
		LogAcesso la = new LogAcesso();
		la.setDataHora(LocalDateTime.now());
		la.setIp(ip);
		la.setLogin(username);
		la.setMensagem(mensagem);
		la.setSucesso(true);
		
		logRepository.save(la);
	}
}
