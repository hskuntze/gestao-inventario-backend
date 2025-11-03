package br.com.ctcea.gestaoinv.exceptions;

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
