package br.com.ctcea.gestaoinv.enums;

public enum Categoria {
	ACESSORIO("Acessório"),
	ELETRONICO("Eletrônico"),
	EPI("EPI"),
	INFORMATICA("Informática"),
	MOBILIARIO("Mobiliário"),
	SOFTWARE("Software");
	
	private final String tipo;
	
	Categoria(String tipo) {
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
