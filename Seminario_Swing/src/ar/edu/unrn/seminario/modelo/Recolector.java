package ar.edu.unrn.seminario.modelo;

import ar.edu.unrn.seminario.Helper.ConditionHelper;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;
import ar.edu.unrn.seminario.exceptions.StringNullException;

public class Recolector extends Persona{
	private String email;
	private int telefono;
	public Recolector(String nombre, String apellido, String dni, String email, String telefono) 
		throws DataEmptyException, StringNullException, IncorrectEmailException, NotNumberException {
		super(nombre,apellido,dni);

		verificarDatos(email, telefono);
		this.email = email;
		
	}
	private void verificarDatos(String email, String telefono) throws StringNullException, DataEmptyException, IncorrectEmailException, NotNumberException {
		if(ConditionHelper.stringIsNull(telefono)) {throw new StringNullException("telefono vacio");}
		if(ConditionHelper.IsNotNumber(telefono)) {throw new NotNumberException("telefono debe ser numerico.");}
		
		if(ConditionHelper.stringIsNull(email)) {throw new StringNullException("email vacio");}
		
		
		if(ConditionHelper.stringIsEmpty(telefono)) {throw new DataEmptyException("El campo de telefono está vacío");}
		if(ConditionHelper.stringIsEmpty(email)) {throw new DataEmptyException("El campo de correo está vacío");}
		
		if(ConditionHelper.IsIncorrectEmail(email)) {throw new IncorrectEmailException("email incorrecto");}
		
	}
	
	public int getTelefono () {
		return telefono;
	}
	public String getEmail() {
		return this.email;
	}
	public String toString() {
		return super.toString() + "telefono: " +this.telefono
				+ "correo electronico: "+ this.email;
	}
	
}
