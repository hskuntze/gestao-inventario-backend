package br.com.ctcea.gestaoinv.enums;

public enum TermoParceria {

	CCOMGEX (1, "TP001/CCOMGEX/2021"),
	DECEA(2, "TP002/DECEA/2023"),
	CISCEA(3, "TP002/CISCEA/2023"),
	PAME(4, "TP001/PAME/2020"),
	MATRIZ(5, "ADMINISTRAÇÃO CENTRAL");
	
	private final Integer id;
    private final String nome;
    
    TermoParceria(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    
    public static TermoParceria getById(int id) {
        for (TermoParceria tp : values()) {
            if (tp.getId() == id) {
                return tp;
            }
        }
        throw new IllegalArgumentException("ID de Termo de Parceria inválido: " + id);
    }
}
