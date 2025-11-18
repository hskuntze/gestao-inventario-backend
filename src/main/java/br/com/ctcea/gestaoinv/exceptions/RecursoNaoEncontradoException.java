package br.com.ctcea.gestaoinv.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecursoNaoEncontradoException extends RuntimeException {
	private static final long serialVersionUID = 6016576005960652003L;
	
	public RecursoNaoEncontradoException() {
	}

	public RecursoNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public RecursoNaoEncontradoException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
