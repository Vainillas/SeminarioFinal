package ar.edu.unrn.seminario.dto;

import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.StringNullException;

public class RecolectorDTO {
	private String email;
	private String telefono;
	private String nombre;
	private String apellido;
	private String dni;
	public RecolectorDTO(String nombre, String apellido, String dni, String telefono, String email) 
		throws DataEmptyException, StringNullException, IncorrectEmailException {
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.email = email;
		this.telefono = telefono;
		
	}
	public String getNombre() {
		return this.nombre;
	}
	public String getApellido() {
		return this.apellido;
	}
	public String getDni() {
		return this.dni;
	}
	public String getTelefono () {
		return telefono;
	}
	public String getEmail() {
		return this.email;
	}
	public String toString() {
		return "nombre: "+ this.nombre 
				+"apellido: "+ this.apellido
				+"dni: "+ this.dni
				+"telefono: " +this.telefono
				+ "correo electronico: "+ this.email;
	}
}
