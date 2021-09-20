package ar.edu.unrn.seminario.modelo;

public class Vivienda {
	private Dueño dueño;
	private Direccion direccion;
	private int id;
	
	public Vivienda(Direccion unaDireccion, Dueño unDueño){
		this.dueño=unDueño;
		this.direccion=unaDireccion;
	}
	public Vivienda(Direccion unaDireccion, Dueño unDueño, int unaID){
		this(unaDireccion,unDueño);
		this.id=unaID;
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
	public int getID() {
		return id;
	}
	public void setID(int unaID) {
		this.id=unaID;
	}
	
}
