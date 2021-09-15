package ar.edu.unrn.seminario.dto;

import ar.edu.unrn.seminario.Helper.ConditionHelper;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.NotNullException;
import ar.edu.unrn.seminario.modelo.Dueño;
import ar.edu.unrn.seminario.modelo.Persona;


public class DueñoDTO extends Persona {

private String correoElectronico;
public DueñoDTO (String nombre, String apellido, String dni, String correoElectronico) throws DataEmptyException, NotNullException, IncorrectEmailException {
	
	super(nombre, apellido, dni);
	validarDatos(correoElectronico);
	this.correoElectronico = correoElectronico;
	
}


public void validarDatos (String correoElectronico) throws NotNullException, DataEmptyException, IncorrectEmailException {
	if(ConditionHelper.stringIsNull(correoElectronico)) {throw new NotNullException ("correo electronico NULL");}
	if(ConditionHelper.stringIsEmpty(correoElectronico)) {throw new DataEmptyException("correo electronico vacio");}
	
	if(ConditionHelper.IsIncorrectEmail(correoElectronico)) {throw new IncorrectEmailException("correo electronico incorrecto");}
	
	
}
public String obtenerCorreo() {
	return correoElectronico;
}
}