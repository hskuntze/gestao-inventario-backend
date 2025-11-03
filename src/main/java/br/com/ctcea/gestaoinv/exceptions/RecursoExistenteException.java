package br.com.ctcea.gestaoinv.exceptions;

public class RecursoExistenteException extends RuntimeException {
	private static final long serialVersionUID = -1050710709668769192L;

	public RecursoExistenteException() {
	}
	
	public RecursoExistenteException(String msg) {
		super(msg);
	}
	
	public RecursoExistenteException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
