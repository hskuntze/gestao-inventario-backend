package br.com.ctcea.gestaoinv.services.exceptions;

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
