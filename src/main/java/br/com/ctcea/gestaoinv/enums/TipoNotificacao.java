package br.com.ctcea.gestaoinv.enums;

public enum TipoNotificacao {
	EXPIRADO("EXPIRADO"),
	ALERTA("ALERTA"),
	GARANTIA("GARANTIA");
	
	private final String tipo;
	
	TipoNotificacao(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}
	
	@Override
	public String toString() {
		return this.tipo;
	}
}