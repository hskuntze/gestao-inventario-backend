package br.com.ctcea.gestaoinv.task;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.ctcea.gestaoinv.entities.Notificacao;
import br.com.ctcea.gestaoinv.entities.TangivelLocacao;
import br.com.ctcea.gestaoinv.enums.TipoNotificacao;
import br.com.ctcea.gestaoinv.repositories.NotificacaoRepository;
import br.com.ctcea.gestaoinv.repositories.TangivelLocacaoRepository;

@Component
public class VerificarLocacaoTask {
	private static final Logger LOGGER = LoggerFactory.getLogger(VerificarLocacaoTask.class);
	
	@Autowired
	private TangivelLocacaoRepository locacaoRepository;
	
	@Autowired
	private NotificacaoRepository notificacaoRepository;
	
	private static final ZoneId ZONE = ZoneId.of("America/Sao_Paulo");
	
	@Scheduled(cron = "0 * * * * *", zone = "America/Sao_Paulo")
	public void verificarLocacoesPertoExpirar() {
		LOGGER.info("[TASK ATL] - Verificando se existem ativos de locação com data de devolução perto de expirar.");
		
		LocalDate hoje = LocalDate.now(ZONE);
	    LocalDate limite = hoje.plusDays(30);
		List<TangivelLocacao> ativos = locacaoRepository.findAtivosComDevolucaoParaExpirar(hoje, limite);
		
		if(!ativos.isEmpty()) {
			
			for(TangivelLocacao atl : ativos) {
				Optional<Notificacao> notificacao = notificacaoRepository.getByAtivoId(atl.getId());
				
				if(notificacao.isEmpty()) {
					LOGGER.warn("[TASK ATL] - Encontrados ativos com devolução para expirar em 30 dias ou menos.");
					Notificacao n = new Notificacao(
							atl.getId(),
							"LOCACAO",
							"Locação expira em 30 dias ou menos",
							"O ativo de código de série " + atl.getCodigoSerie() + " deve ser devolvido em 30 dias ou menos.",
							TipoNotificacao.ALERTA);
					notificacaoRepository.save(n);
				}
			}
		}
	}
	
	@Scheduled(cron = "0 * * * * *", zone = "America/Sao_Paulo")
	public void verificarLocacoesNaoDevolvidas() {
		LOGGER.info("[TASK ATL] - Verificando se existem ativos de locação com data de devolução expirados.");
		
		LocalDate agora = LocalDate.now(ZONE);
		List<TangivelLocacao> ativos = locacaoRepository.findAtivosComDevolucaoExpirada(agora);
		
		if(!ativos.isEmpty()) {
			
			for(TangivelLocacao atl : ativos) {
				Optional<Notificacao> notificacao = notificacaoRepository.getByAtivoId(atl.getId());
				
				if(notificacao.isEmpty()) {
					LOGGER.warn("[TASK ATL] - Encontrados ativos com devolução expirada.");
					Notificacao n = new Notificacao(
							atl.getId(), 
							"LOCACAO", 
							"Ativo com data de devolução expirada", 
							"O ativo de código de série " + atl.getCodigoSerie() + " está com a data de devolução expirada.", 
							TipoNotificacao.EXPIRADO);
					notificacaoRepository.save(n);
				}
			}
		}
	}
}
