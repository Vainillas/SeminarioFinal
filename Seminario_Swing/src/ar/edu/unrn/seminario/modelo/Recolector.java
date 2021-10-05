package ar.edu.unrn.seminario.modelo;

import ar.edu.unrn.seminario.Helper.ConditionHelper;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.StringNullException;

public class Recolector extends Persona{
	private String email;
	private int telefono;
	public Recolector(String nombre, String apellido, String dni, String telefono, String email) throws DataEmptyException, StringNullException, IncorrectEmailException {
		super(nombre,apellido,dni);
		verificarDatos(email, telefono);
		this.email = email;
		this.telefono = Integer.parseInt(telefono);
		
	}
	private void verificarDatos(String email, String telefono) throws StringNullException, DataEmptyException, IncorrectEmailException {
		if(ConditionHelper.stringIsNull(telefono)) {throw new StringNullException("telefono vacio");}
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
