package ar.edu.unrn.seminario.modelo;

import ar.edu.unrn.seminario.Helper.ConditionHelper;
import ar.edu.unrn.seminario.exceptions.DataEmptyException;
import ar.edu.unrn.seminario.exceptions.IncorrectEmailException;
import ar.edu.unrn.seminario.exceptions.NotNumberException;
import ar.edu.unrn.seminario.exceptions.StringNullException;

public class Dueño extends Persona {
	private String correoElectronico;
	private String userName;
	
	public Dueño(String nombre, String apellido, String dni, String correoElectronico, String username) throws DataEmptyException, StringNullException, IncorrectEmailException, NotNumberException {
		super(nombre, apellido, dni);
		validarDatos(correoElectronico);
		this.correoElectronico = correoElectronico;
		this.userName = username;
	}
	
	private void validarDatos (String correoElectronico) throws IncorrectEmailException, DataEmptyException, StringNullException {
		if(ConditionHelper.IsIncorrectEmail(correoElectronico)) {throw new IncorrectEmailException ("Formato de email incorrecto.");}
		
		if(ConditionHelper.stringIsEmpty(correoElectronico)) {throw new DataEmptyException("Campo de Email vacío.");}
		
		if(ConditionHelper.stringIsNull(correoElectronico)) {throw new StringNullException("Email nulo.");}
	}


	@Override
	public String toString() {
		return nombre+" "+apellido+" || "+dni;
	}
	public String getCorreo() {
		return correoElectronico;
	}
	public void setCorreo(String correoElectronico) {
		this.correoElectronico=correoElectronico;
	}
	
	public String getUsername(){
		return this.userName;
	}
	
	public void setUsername(String username) {
		this.userName = username;
	}
}
