package br.com.ctcea.gestaoinv.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class OperacaoNaoExecutadaException extends RuntimeException {
	private static final long serialVersionUID = 3473192075864369275L;

	public OperacaoNaoExecutadaException() {
	}
	
	public OperacaoNaoExecutadaException(String msg) {
		super(msg);
	}
	
	public OperacaoNaoExecutadaException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
