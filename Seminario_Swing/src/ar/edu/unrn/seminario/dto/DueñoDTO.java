package ar.edu.unrn.seminario.dto;

import ar.edu.unrn.seminario.Helper.ConditionHelper;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.modelo.Dueño;
import ar.edu.unrn.seminario.modelo.Persona;


public class DueñoDTO  {
	
private String correoElectronico;
public DueñoDTO (String nombre, String apellido, String dni, String correoElectronico) {
	this.correoElectronico = correoElectronico;
}
public String obtenerCorreo() {
	return correoElectronico;
}
}