package br.com.ctcea.gestaoinv.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ctcea.gestaoinv.dto.AtivoDTO;
import br.com.ctcea.gestaoinv.dto.QuantidadeAtivoDTO;
import br.com.ctcea.gestaoinv.entities.gestaoinv.Ativo;
import br.com.ctcea.gestaoinv.entities.gestaoinv.Intangivel;
import br.com.ctcea.gestaoinv.entities.gestaoinv.Tangivel;
import br.com.ctcea.gestaoinv.entities.gestaoinv.TangivelLocacao;
import br.com.ctcea.gestaoinv.repositories.gestaoinv.IntangivelRepository;
import br.com.ctcea.gestaoinv.repositories.gestaoinv.TangivelLocacaoRepository;
import br.com.ctcea.gestaoinv.repositories.gestaoinv.TangivelRepository;
import br.com.ctcea.gestaoinv.services.exceptions.RecursoNaoEncontradoException;

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

	@Transactional(readOnly = true)
	public List<AtivoDTO> getAll() {
		List<Tangivel> tangiveis = tangivelRepository.findAll();
		List<Intangivel> intangiveis = intangivelRepository.findAll();
		List<TangivelLocacao> tangiveisLoc = tangivelLocacaoRepository.findAll();

		List<Ativo> todos = new ArrayList<>();
		todos.addAll(tangiveisLoc);
		todos.addAll(intangiveis);
		todos.addAll(tangiveis);

		return todos.stream().map(ativo -> {
			AtivoDTO dto = new AtivoDTO(ativo);

			// Define o tipo
			if (ativo instanceof Tangivel) {
				dto.setTipoAtivo("t");
				dto.setEstadoConservacao(((Tangivel) ativo).getEstadoConservacao());
			} else if (ativo instanceof Intangivel) {
				dto.setTipoAtivo("i");
			} else if (ativo instanceof TangivelLocacao) {
				dto.setTipoAtivo("tl");
				dto.setEstadoConservacao(((TangivelLocacao) ativo).getEstadoConservacao());
			}

			return dto;
		}).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public AtivoDTO getAtivoById(Long id) {
		Optional<? extends Ativo> obj = tangivelRepository.findById(id);
		String tipoAtivo = "t";

		if (!obj.isPresent()) {
			obj = intangivelRepository.findById(id);
			tipoAtivo = "i";
		}

		if (!obj.isPresent()) {
			obj = tangivelLocacaoRepository.findById(id);
			tipoAtivo = "tl";
		}

		Ativo ativo = obj.orElseThrow(() -> new RecursoNaoEncontradoException("Não foi possível localizar um ativo com ID " + id));
		AtivoDTO dto = new AtivoDTO(ativo);
		dto.setTipoAtivo(tipoAtivo);

		if (ativo instanceof Tangivel) {
			dto.setEstadoConservacao(((Tangivel) ativo).getEstadoConservacao());
		} else if (ativo instanceof TangivelLocacao) {
			dto.setEstadoConservacao(((TangivelLocacao) ativo).getEstadoConservacao());
		}

		return dto;
	}
}
