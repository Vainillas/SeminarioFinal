package ar.edu.unrn.seminario.modelo;

public class TipoResiduo {
	private String nombre;
	private int PuntajeXKg;

	public TipoResiduo(String nombre, int puntajeXKg) {
		this.nombre = nombre;
		PuntajeXKg = puntajeXKg;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPuntajeXKg() {
		return PuntajeXKg;
	}

	public void setPuntajeXKg(int puntajeXKg) {
		PuntajeXKg = puntajeXKg;
	}
}
