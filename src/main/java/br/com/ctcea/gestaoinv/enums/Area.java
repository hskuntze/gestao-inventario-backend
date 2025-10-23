package br.com.ctcea.gestaoinv.enums;

public enum Area {
	GAP("GAP"),
	GPS("GPS"),
	GTI("GTI");
	
	private final String area;
	
	Area(String area) {
		this.area = area;
	}

	public String getArea() {
		return area;
	}
	
	@Override
	public String toString() {
		return this.area;
	}
}
