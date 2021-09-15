package ar.edu.unrn.seminario.modelo;

public class Vivienda {
	private Dueño dueño;
	private Direccion direccion;
	
	public Vivienda(Direccion unaDireccion, Dueño unDueño){
		this.dueño=unDueño;
		this.direccion=unaDireccion;
	}
	
	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}
	
	public Dueño getDueño() {
		return dueño;
	}

	public void setDueño(Dueño dueño) {
		this.dueño = dueño;
	}
	
}
