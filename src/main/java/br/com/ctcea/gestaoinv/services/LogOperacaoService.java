package br.com.ctcea.gestaoinv.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ctcea.gestaoinv.exceptions.OperacaoNaoExecutadaException;
import br.com.ctcea.gestaoinv.logs.LogOperacao;
import br.com.ctcea.gestaoinv.repositories.LogOperacaoRepository;

@Service
public class LogOperacaoService {

	@Autowired
	private LogOperacaoRepository logRepository;
	
	@Transactional(readOnly = true)
	public List<LogOperacao> getAll() {
		return logRepository.findAll();
	}
	
	@Async("logExecutor")
    public void registrarLog(String usuario,
                             String ip,
                             String metodo,
                             String url,
                             int status,
                             String detalhes) {
        try {
            LogOperacao log = new LogOperacao();
            log.setUsuario(usuario);
            log.setIp(ip);
            log.setMetodoHttp(metodo);
            log.setUrl(url);
            log.setStatusHttp(status);
            log.setDataOperacao(LocalDateTime.now());
            log.setDetalhes(detalhes);

            logRepository.save(log);
        } catch (Exception e) {
            throw new OperacaoNaoExecutadaException(e.getMessage());
        }
    }
}
