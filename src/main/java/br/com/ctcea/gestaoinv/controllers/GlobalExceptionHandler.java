package br.com.ctcea.gestaoinv.controllers;

import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.ctcea.gestaoinv.dto.LocalizacaoDTO;
import br.com.ctcea.gestaoinv.exceptions.StandardError;
import br.com.ctcea.gestaoinv.services.LocalizacaoService;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@Autowired
	private LocalizacaoService localizacaoService;

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> handleDataIntegrityViolation(DataIntegrityViolationException ex,
			HttpServletRequest request) {
		String message = "Violação de integridade referencial.";
		Throwable rootCause = ex.getRootCause();

		Long idLocalizacao = extractIdFromMessage(rootCause != null ? rootCause.getMessage() : "");

		if (idLocalizacao != null) {
	        LocalizacaoDTO loc = localizacaoService.getDtoFromId(idLocalizacao);
	        if (loc != null) {
	            message = String.format(
	                "Não é possível excluir a localização \"%s\" pois está associada a um ou mais ativos.",
	                loc.getNome()
	            );
	        } else {
	            message = String.format(
	                "Não é possível excluir a localização (ID %d) pois está associada a um ou mais ativos.",
	                idLocalizacao
	            );
	        }
	    }

		StandardError err = new StandardError(Instant.now(), HttpStatus.CONFLICT.value(), "Integridade referencial violada", message, request.getRequestURI());

		return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
	}

	private Long extractIdFromMessage(String msg) {
		try {
			Pattern pattern = Pattern.compile("\\(CAST\\((\\d+) AS BIGINT\\)\\)");
			Matcher matcher = pattern.matcher(msg);
			if (matcher.find()) {
				return Long.parseLong(matcher.group(1));
			}
		} catch (Exception ignored) {
		}
		return null;
	}
}
