package ar.edu.unrn.seminario.dto;

import ar.edu.unrn.seminario.modelo.Direccion;
import ar.edu.unrn.seminario.modelo.Dueño;

public class ViviendaDTO {
	private Dueño dueño;
	private Direccion direccion;
	private int id;
	
	public ViviendaDTO(Direccion direccion2, Dueño dueño2){
		this.dueño=dueño2;
		this.direccion=direccion2;
	}
	public ViviendaDTO(Direccion unaDireccion, Dueño unDueño, int unaID){
		this(unaDireccion,unDueño);
		this.id=unaID;
	}
	public Direccion getDireccion() {
		return direccion;
	}
	public Dueño getDueño() {
		return dueño;
	}
	
		public int getID() {
		return id;
	}
	
}
