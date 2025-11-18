package br.com.ctcea.gestaoinv.enums;

public enum Categoria {
	ACESSORIO("Acessórios"),
	ELETRONICO("Eletrônicos"),
	EPI("EPI"),
	INFORMATICA("Informática"),
	MOBILIARIO("Mobiliário"),
	CERTIFICADO("Certificados"),
	SOFTWARE("Softwares");
	
	private final String tipo;
	
	Categoria(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}
	
	public static Categoria fromTipo(String tipo) {
	    if (tipo == null) return null;

	    for (Categoria c : Categoria.values()) {
	        if (c.getTipo().equalsIgnoreCase(tipo.trim())) {
	            return c;
	        }
	    }

	    throw new IllegalArgumentException("Categoria inválida: " + tipo);
	}
	
	@Override
	public String toString() {
		return this.tipo;
	}
}
