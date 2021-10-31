package ar.edu.unrn.seminario.dto;

import ar.edu.unrn.seminario.Helper.ConditionHelper;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.modelo.Dueño;
import ar.edu.unrn.seminario.modelo.Persona;


public class DueñoDTO  { 
	
private String nombre;
private String apellido;
private String dni;
private String correoElectronico;
private String username;
	
	public DueñoDTO (String unNombre, String unApellido, String unDni, String unCorreoElectronico, String unUsername) {
		this.nombre = unNombre;
		this.apellido = unApellido;
		this.dni = unDni;
		this.correoElectronico = unCorreoElectronico;
		this.username = unUsername;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}