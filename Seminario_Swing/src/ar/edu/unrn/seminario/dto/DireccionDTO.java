package ar.edu.unrn.seminario.dto;

public class DireccionDTO {
	private String calle;
	private String longitud;
	private String latitud;
	private String altura;
	private String codPostal;
	private String barrio;
	public DireccionDTO(String calle, String altura, String codPostal, String longitud, String latitud, String barrio) {
		super();
		this.calle = calle;
		this.longitud = longitud;
		this.latitud = latitud;
		this.altura = altura;
		this.codPostal = codPostal;
		this.barrio = barrio;
	}
	
	public String getBarrio() {
		return barrio;
	}

	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}
	public String toString() {
		return  calle + " " + altura + " /CP: " + codPostal;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public String getLatitud() {
		return latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getAltura() {
		return altura;
	}

	public void setAltura(String altura) {
		this.altura = altura;
	}

	public String getCodPostal() {
		return codPostal;
	}

	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}
	
	
}


