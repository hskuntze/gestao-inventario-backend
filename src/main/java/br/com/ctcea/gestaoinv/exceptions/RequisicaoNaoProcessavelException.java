package br.com.ctcea.gestaoinv.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequisicaoNaoProcessavelException extends RuntimeException {
	private static final long serialVersionUID = 1979951337649570934L;

	public RequisicaoNaoProcessavelException() {
	}

	public RequisicaoNaoProcessavelException(String msg) {
		super(msg);
	}
	
	public RequisicaoNaoProcessavelException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
