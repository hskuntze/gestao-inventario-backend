package br.com.ctcea.gestaoinv.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ctcea.gestaoinv.dto.QuantidadeAtivoDTO;
import br.com.ctcea.gestaoinv.repositories.gestaoinv.IntangivelRepository;
import br.com.ctcea.gestaoinv.repositories.gestaoinv.TangivelLocacaoRepository;
import br.com.ctcea.gestaoinv.repositories.gestaoinv.TangivelRepository;

@Service
public class AtivoService {

	@Autowired
	private TangivelRepository tangivelRepository;
	
	@Autowired
	private IntangivelRepository intangivelRepository;
	
	@Autowired
	private TangivelLocacaoRepository tangivelLocacaoRepository;
	
	@Transactional(readOnly = true)
	public QuantidadeAtivoDTO getQtdAtivos() {
		Integer qtdT = tangivelRepository.getCount();
		Integer qtdI = intangivelRepository.getCount();
		Integer qtdTL = tangivelLocacaoRepository.getCount();
		
		return new QuantidadeAtivoDTO(qtdT, qtdI, qtdTL);
	}
}
